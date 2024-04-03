package com.spring.portfolio.controller;

import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.service.BoardService;
import com.spring.portfolio.service.MemberService;
import com.spring.portfolio.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final WebApplicationContext context;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {

        List<BoardResponseDto> boardList4 = boardService.loadBoardList();

        model.addAttribute("board", boardList4);

        return "client/index";
    }

    @GetMapping("/board")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Board> paging = boardService.loadBoardList(page);
        List<BoardResponseDto> list = new ArrayList<>();
        for(Board board : paging) {
            list.add(new BoardResponseDto(board));
        }

        model.addAttribute("paging", paging);
        model.addAttribute("board",list);
        return "client/boardList";
    }

    
//      paging 안쓰는 방식인데 paging 쓰는걸 더 선호해서 주석
//    @GetMapping("/board")
//    public String boardList(Model model) {
//
//        List<BoardResponseDto> board = boardService.loadBoardList();
//        model.addAttribute("board", board);
//        return "client/boardList";
//    }

    @GetMapping("/board/view")
    public String boardView(@RequestParam(value = "boardIdx") Long boardIdx,
            Model model, HttpServletRequest request, HttpServletResponse response) {

        BoardResponseDto boardView = boardService.loadBoardView(boardIdx,request,response);
        String writer = memberService.loadWriter(request.getSession());
        Long writerIdx = memberService.loadMemberIdx(request.getSession());
        List<ReplyResponseDto> replyResponseDto = replyService.loadReply(boardIdx);
        int count = (int) replyResponseDto.stream().count();
//        List<Integer> integers = IntStream.range(0, count).boxed().toList();

        model.addAttribute("board",boardView);
        model.addAttribute("writer",writer);
        model.addAttribute("writerIdx",writerIdx);
        model.addAttribute("reply", replyResponseDto);
        model.addAttribute("count", count);

        return "client/boardView";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model, HttpServletRequest request) {
        Long writer = memberService.loadMemberIdx(request.getSession());
        model.addAttribute("writer",writer);

        return "client/boardWrite";
    }

    @PostMapping("/board/writeAction")
    public String boardWriteAction(BoardRequestDto dto) {

        boardService.saveBoard(dto);

        return "redirect:/board";
    }

    @ResponseBody
    @PostMapping("/board/summer_image")
    public void summer_image(MultipartFile file, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
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

    @GetMapping("/board/edit")
    public String boardEdit(@RequestParam(value = "boardIdx") Long boardIdx, Model model) {

        BoardResponseDto boardResponseDto = boardService.loadBoardInfo(boardIdx);
        model.addAttribute("board",boardResponseDto);

        return "client/boardEdit";
    }

    @PostMapping("/board/editAction")
    public String boardEditAction(BoardRequestDto dto, HttpServletRequest request) {

        boardService.editBoard(dto);

//        // 이전 페이지로 이동되는 코드
//        String referer = request.getHeader("Referer");
//        return "redirect:"+ referer;

        return "redirect:/board";
    }

}
