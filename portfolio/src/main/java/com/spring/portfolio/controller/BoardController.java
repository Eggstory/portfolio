package com.spring.portfolio.controller;

import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.service.BoardService;
import com.spring.portfolio.service.MemberService;
import com.spring.portfolio.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final MemberService memberService;

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
    public String boardView(@RequestParam long idx, Model model, HttpServletRequest request) {

        BoardResponseDto boardView = boardService.loadBoardView(idx);
        String writer = memberService.loadWriter(request.getSession());
        List<ReplyResponseDto> replyResponseDto = replyService.loadReply(idx);

        model.addAttribute("board",boardView);
        model.addAttribute("writer",writer);
        model.addAttribute("reply", replyResponseDto);

        return "client/boardView";
    }

    @GetMapping("/board/write")
    public String boardWrite() {
        return "client/boardWrite";
    }

    @GetMapping("/board/writeAction")
    public String boardWriteAction() {
        return "redirect:/board";
    }

}
