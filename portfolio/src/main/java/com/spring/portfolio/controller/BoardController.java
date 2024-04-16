package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
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
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {


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
    public String boardList(Model model, @RequestParam(value = "search", required = false) String keyword,
                            @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<Board> paging = boardService.loadBoardList(keyword,page);
        List<BoardResponseDto> list = new ArrayList<>();
        for(Board board : paging) {
            list.add(new BoardResponseDto(board));
        }
        if(keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("board",list);
        return "client/boardList";
    }

//    @GetMapping("/board/list")
//    public String boardSearchList(Model model, @RequestParam("search") String keyword,
//                            @RequestParam(value = "page", defaultValue = "1") int page) {
//
//        Page<Board> paging = boardService.boardSearchList(keyword,page);
//        List<BoardResponseDto> list = new ArrayList<>();
//        for(Board board : paging) {
//            list.add(new BoardResponseDto(board));
//        }
//
//        model.addAttribute("paging", paging);
//        model.addAttribute("board",list);
//        return "client/boardList";
//    }
    
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
            Model model, HttpServletRequest request, HttpServletResponse response,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        BoardResponseDto boardView = boardService.loadBoardView(boardIdx,request,response);
        String mail = "";
        Long writerIdx;
        if(principalDetails == null || principalDetails.equals("")) {
//            String writer = memberService.loadWriter(request.getSession());
            mail = request.getSession().getAttribute("memberMail").toString();
            writerIdx = memberService.loadMemberIdx(request.getSession());
        } else  {
//            String writer = memberService.loadWriter(principalDetails.getMember().getMemberMail());
            mail = principalDetails.getMember().getMemberMail();
            writerIdx = memberService.loadMemberIdx(principalDetails.getMember().getMemberMail());
        }
//        String writer = memberService.loadWriter(request.getSession());
//        Long writerIdx = memberService.loadMemberIdx(request.getSession());
//        String writer = memberService.loadWriter(principalDetails.getMember().getMemberMail());
//        String mail = principalDetails.getMember().getMemberMail();
//        Long writerIdx = memberService.loadMemberIdx(principalDetails.getMember().getMemberMail());
        List<ReplyResponseDto> replyResponseDto = replyService.loadReply(boardIdx);
        int count = (int) replyResponseDto.stream().count();
//        List<Integer> integers = IntStream.range(0, count).boxed().toList();

        model.addAttribute("board",boardView);
        model.addAttribute("mail",mail);
        model.addAttribute("writerIdx",writerIdx);
        model.addAttribute("reply", replyResponseDto);
        model.addAttribute("count", count);

        return "client/boardView";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model, HttpServletRequest request,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long writer;
        if(principalDetails == null || principalDetails.equals("")) {
            writer = memberService.loadMemberIdx(request.getSession());
        } else  {
            writer = memberService.loadMemberIdx(principalDetails.getMember().getMemberMail());
        }

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

        boardService.summer_file(file, response);
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

    @PostMapping("/board/delete")
    public String boardDelete(Long boardIdx) {

        boardService.deleteBoard(boardIdx);

        return "redirect:/board";
    }
}
