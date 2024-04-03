package com.spring.portfolio.service;

import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.store.repository.BoardRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> loadBoardList(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("idx"));
//         Sort.by(sorts) : 아래줄 PageRequest.of의 세번째 인자
        Pageable pageable = PageRequest.of(page, 10, Sort.by("boardIdx").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage;
    }

    public List<BoardResponseDto> loadBoardList() {
        List<Board> first4Board = boardRepository.findTop4ByOrderByBoardIdxDesc();

        List<BoardResponseDto> boardDto = new ArrayList<>();
        for(Board entity : first4Board){
            BoardResponseDto boardResponseDto = new BoardResponseDto(entity);
            if(boardResponseDto.getBoardImage() != null) {
                String boardImage = boardResponseDto.getBoardImage();
                // 이미지가 여러개 들어가 있을 경우, 제일 첫번째 이미지를 썸네일용으로 쓰기 위한 코드
                String s = boardImage.split(",")[0];
                boardResponseDto.setBoardImage(s);
            }
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
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Board"));

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
            System.out.println(newCookie);
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
                -> new UsernameNotFoundException("Not Found Board"));;

        board.modifyBoard(dto);

    }
}
