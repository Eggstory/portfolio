package com.spring.portfolio.service;

import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.exception.CustomException;
import com.spring.portfolio.exception.ErrorCode;
import com.spring.portfolio.store.repository.BoardRepository;
import com.spring.portfolio.store.repository.ReplyRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final WebApplicationContext context;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public Page<Board> loadBoardList(String keyword, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("idx"));
//         Sort.by(sorts) : 아래줄 PageRequest.of의 세번째 인자
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardIdx").descending());

        Page<Board> boardPage;

        if(keyword == null || keyword.equals("")) {
            boardPage = boardRepository.findAll(pageable);
        } else {
            boardPage = boardRepository.findByBoardTitleContaining(keyword, pageable);
        }
        return boardPage;
    }

    public Page<Board> loadBoardInMyInfo(Long memberIdx, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("idx"));
//         Sort.by(sorts) : 아래줄 PageRequest.of의 세번째 인자
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardIdx").descending());

        Page<Board> boardPage = boardRepository.findByMemberNameContaining(memberIdx, pageable);

        return boardPage;
    }

//    public Page<Board> boardSearchList(String keyword, int page) {
//
//        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardIdx").descending());
//        Page<Board> boardPage = boardRepository.findByBoardTitleContaining(keyword,pageable);
//        return boardPage;
//    }

    public List<BoardResponseDto> loadBoardList() {
        List<Board> first4Board = boardRepository.findTop4ByOrderByBoardIdxDesc();

        List<BoardResponseDto> boardDto = new ArrayList<>();

        for(Board entity : first4Board){
            BoardResponseDto boardResponseDto = new BoardResponseDto(entity);
            String boardContent = boardResponseDto.getBoardContent();
            String boardImage = boardResponseDto.getBoardImage();

            String s = boardImage.split(",")[0];
            Pattern pattern = Pattern.compile("/images/upload/([a-f0-9\\-]+)\\.(png|jpg)");
            Matcher matcher = pattern.matcher(boardContent);

//            List<String> imgFiles = new ArrayList<>();
//            List<String> imgExtensions = new ArrayList<>();
//
//            while (matcher.find()) {
//                imgFiles.add(matcher.group(1)); // UUID 값 추출 후 리스트에 추가
//                imgExtensions.add(matcher.group(2)); // 확자자 지정
//            }



            if(boardImage.isEmpty()) {
                boardResponseDto.setBoardImage("/images/no-image.png");
//            } else if (!boardImage.contains(",")) {
//                // 이미지가 한개일때
//                boardResponseDto.setBoardImage(boardImage);
//            } else if(boardImage.contains(",") && !Thumb.equals(s)) {
//                // 이미지가 여러개 들어가 있을 경우, 제일 첫번째 이미지를 썸네일용으로 쓰기 위한 코드
//                boardResponseDto.setBoardImage(Thumb);
//            }
            } else if (matcher.find()) {
                String imgFile = matcher.group(1); // 첫 번째 UUID
                String iImgExtension = matcher.group(2); // 첫 번째 확장자
                String Thumb = "/images/upload/" + imgFile + "." + iImgExtension;
                // 이미지가 여러개 들어가 있을 경우, 제일 첫번째 이미지를 썸네일용으로 쓰기 위한 코드
                boardResponseDto.setBoardImage(Thumb);
            }
            boardDto.add(boardResponseDto);
        }

        return boardDto;
    }

    public List<BoardResponseDto> loadBoardList(Long memberIdx) {
        List<Board> board = boardRepository.findByMemberIdx(memberIdx);

        List<BoardResponseDto> boardDto = new ArrayList<>();
        for(Board entity : board){
            BoardResponseDto boardResponseDto = new BoardResponseDto(entity);
            boardDto.add(boardResponseDto);
        }

        return boardDto;
    }


//      paging 안쓰는 방식
//    public List<BoardResponseDto> loadBoardList() {
//
//        List<Board> board = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "idx"));
//        List<BoardResponseDto> boardDto = new ArrayList<>();
//        for(Board entity : board){
//            boardDto.add(new BoardResponseDto(entity));
//        }
//
//        return boardDto;
//    }


    public BoardResponseDto loadBoardView(Long idx, HttpServletRequest request, HttpServletResponse response) {

        Board board = boardRepository.findById(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        BoardResponseDto boardDto = new BoardResponseDto(board);

        increaseViewCount(idx, request, response);

        return boardDto;
    }

    public void increaseViewCount(Long idx, HttpServletRequest request, HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if( cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("boardView")) {
                    oldCookie = cookie;
                }
            }
        }
        if( oldCookie != null) {
            if(!oldCookie.getValue().contains("["+ idx.toString() +"]")) {
                updateViewCount(idx);
                oldCookie.setValue(oldCookie.getValue() + "_[" + idx + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 30);
                response.addCookie(oldCookie);
            }
        } else {
            updateViewCount(idx);
            Cookie newCookie = new Cookie("boardView", "[" + idx + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 30);
            response.addCookie(newCookie);
        }

    }

    @Transactional
    private int updateViewCount(Long idx) {

        return boardRepository.updateViewCount(idx);
    }

    @Transactional
    public void saveBoard(BoardRequestDto dto) {

        Board entity = dto.toEntity();
        boardRepository.save(entity);
    }

    public BoardResponseDto loadBoardInfo(Long boardIdx) {

        Board board = boardRepository.findById(boardIdx).orElseThrow(()
                -> new UsernameNotFoundException("Not Found Board"));

        BoardResponseDto boardDto = new BoardResponseDto(board);

        return boardDto;
    }

    @Transactional
    public void editBoard(BoardRequestDto dto) {

        Board board = boardRepository.findById(dto.getBoardIdx()).orElseThrow(()
                -> new UsernameNotFoundException("Not Found Board"));

        List<Reply> replies = replyRepository.findAllByBoard(board);

        boardRepository.save(dto.toEntity(dto.getBoardIdx(), board.getMember(), replies ));

//        board.modifyBoard(dto);
    }

    @Transactional
    public void deleteBoard(Long boardIdx) {

        replyRepository.updateParentId(boardIdx);
        replyRepository.deleteReReplyByBoardIdx(boardIdx);
        replyRepository.deleteByBoardIdx(boardIdx);
        boardRepository.deleteByBoardIdx(boardIdx);

    }

    public void summer_file(MultipartFile file, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 서버에 저장할 경로
        // context.getServletContext().getRealPath("파라미터") : 루트부터 파라미터까지의 경로
        // TomcatConfig파일(WebServerFactoryCustomizer<TomcatServletWebServerFactory>의 구현체)에서
        // factory.setDocumentRoot를 통해 절대 경로 지정해줬음.
        String save_folder = context.getServletContext().getRealPath("/images/upload");

        // 업로드 된 파일의 이름
        String file_name = file.getOriginalFilename();

        // 업로드 된 파일의 확장자
        String fileExtension = file_name.substring(file_name.lastIndexOf("."));

        // 업로드 될 파일의 이름 재설정 (중복 방지를 위해 UUID 사용)
        String uuidFileName = UUID.randomUUID().toString() + fileExtension;

        // 위에서 설정한 서버 경로에 이미지 저장
        file.transferTo(new File(save_folder , uuidFileName));

        // ajax에서 get메소드로 보내지는 url
        out.println("/images/upload/"+uuidFileName);
        out.close();
    }

    @Transactional
    public void updateMemberNull(Long idx) {

        boardRepository.updateMemberNull(idx);
    }

//    @Transactional
//    public void clear(Board board) {
//
//        board.getReplies().clear();
//
//    }

    /*
    public String add(HttpServletRequest request, MultipartFile file, BannerInput parameter) {

    if (file != null) {
        // 파일 확장자 검사하는 코드
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension != null) {
            FileUtil files = new FileUtil();
            String imgPath = files.save(file).getUrlFilePath();
            parameter.setImgPath(imgPath);
            }
        }   
    }
     */


}
