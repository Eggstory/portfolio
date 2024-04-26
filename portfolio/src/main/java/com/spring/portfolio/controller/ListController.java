package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.service.AdminService;
import com.spring.portfolio.service.ListService;
import com.spring.portfolio.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class ListController {
    private final ListService listService;
    private final AdminService adminService;

    @GetMapping("/list/member")  // 맴버 리스트 찾기
    public String getMemberList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminService.setNavName(model, request, principalDetails);
        Page<Member> paging = listService.loadMemberList(keyword, page);
        List<MemberResponseDto> list = new ArrayList<>();
        for(Member entity : paging){
            list.add(new MemberResponseDto(entity));
        }

//        searchService.memberInsertAndFilter();

        if(keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

//        model.addAttribute("memberCate", cateMap.get("memberCate"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","member");
        model.addAttribute("list", list);
        return "admin/index";
    }

    @GetMapping("/list/limitMember")  // 맴버 리스트 찾기
    public String getLimitMemberList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminService.setNavName(model, request, principalDetails);
        Page<Member> paging = listService.loadLimitMemberList(keyword, page);
        List<MemberResponseDto> list = new ArrayList<>();
        for(Member entity : paging){
            list.add(new MemberResponseDto(entity));
        }

//        searchService.memberInsertAndFilter();

        if(keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

//        model.addAttribute("memberCate", cateMap.get("memberCate"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","limitMember");
        model.addAttribute("list", list);
        return "admin/index";
    }

    /*
    @GetMapping("/list/product")  // 제품 리스트 찾기
    public String getProductList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Product> paging = listService.findProductList(page);
        model.addAttribute("paging",paging);
        List<ProductResponseDto> list = new ArrayList<>();
        for(Product entity : paging){
            list.add(new ProductResponseDto(entity));
        }
        searchService.categoryInsertAndFilter();
        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));
        model.addAttribute("type","product");
        model.addAttribute("list", list);
        return "listForm";
    }

    @GetMapping("/list/order")  // 오더 리스트 찾기
    public String getOrderList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){ // 오더 리스트
        Page<Order> paging = listService.findOrderList(page);
        List<OrderResponseDto> list = new ArrayList<>();
        for(Order entity : paging){
            list.add(new OrderResponseDto(entity));
        }
        searchService.orderCateInsert();
        model.addAttribute("paging",paging);
        model.addAttribute("cate1", cateMap.get("orderCate1"));
        model.addAttribute("cate2", cateMap.get("orderCate2"));
        model.addAttribute("type","order");
        model.addAttribute("list", list);
        return "listForm";
    }

    @GetMapping("/list/review")  // 리뷰 리스트 찾기
    public String getReviewList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Review> paging = listService.findReviewList(page);
        model.addAttribute("paging",paging);
        List<ReviewResponseDTO> list = new ArrayList<>();
        for(Review entity : paging){
            list.add(new ReviewResponseDTO(entity));
        }
        searchService.reviewInsertAndFilter();
        model.addAttribute("review",cateMap.get("reviewCate"));
        model.addAttribute("type","review");
        model.addAttribute("list", list);
        return "listForm";
    }
     */

    @GetMapping("/list/board")  // 게시판 리스트 찾기
    public String getQnAList(Model model, @RequestParam(value = "search", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             HttpServletRequest request,
                             @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminService.setNavName(model, request, principalDetails);
        Page<Board> paging = listService.loadBoardList(keyword, page);
        List<BoardResponseDto> list = new ArrayList<>();
        for(Board entity : paging){
            list.add(new BoardResponseDto(entity));
        }

//        searchService.qnaInsertAndFilter();

        if(keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

//        model.addAttribute("qnaCate1",cateMap.get("qnaCate1"));
//        model.addAttribute("qnaCate2",cateMap.get("qnaCate2"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","board");
        model.addAttribute("list", list);
        return "admin/index";
    }

    @GetMapping("/list/reply")  // 댓글 리스트 찾기
    public String getNoticeList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminService.setNavName(model, request, principalDetails);
        Page<Reply> paging = listService.findReplyList(keyword, page);
        List<ReplyResponseDto> list = new ArrayList<>();
        for(Reply entity : paging){
            list.add(new ReplyResponseDto(entity));
        }

//        searchService.noticeCteInsert();

        if(keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

//        model.addAttribute("cate1",cateMap.get("noticeCate1"));
//        model.addAttribute("cate2",cateMap.get("noticeCate2"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","reply");
        model.addAttribute("list", list);
        return "admin/index";
    }



}
