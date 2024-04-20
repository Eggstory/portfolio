package com.spring.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {
    /*

    private final MemberRepository memberRepository;
//    private final ProductRepository productRepository;
//    private final OrdersRepository ordersRepository;
//    private final ReviewRepository reviewRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    // 멤버 검색
    @Transactional(readOnly = true)
    public Page<Member> findSearchMember(String category, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("member_NAME"));
        sorts.add(Sort.Order.desc("member_ID"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Member> a = memberRepository.findByCategory(category,keyword,pageable);
        return a;
    }


    @Transactional(readOnly = true)
    public Page<Member> findSearchMember(String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("member_NAME"));
        sorts.add(Sort.Order.desc("member_ID"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Member> a = memberRepository.findByCategory(keyword,pageable);
        return a;
    }

    public void memberInsertAndFilter(){
        if(!cateMap.containsKey("memberCate")) {
            cateMap.add("memberCate", "활동");
            cateMap.add("memberCate", "정지");
            cateMap.add("memberCate", "탈퇴");
        }
    }


//    // 제품 검색
//    @Transactional(readOnly = true)
//    public Page<Product> findSearchProduct(String category1, String category2, String keyword, int page){
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("item_NAME"));
//        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
//        Page<Product> result = null;
//        if(category1.equals("전체") && category2.equals("전체")) {
//            result = productRepository.searchAll(keyword, pageable);
//            return result;// 둘다 전체일때
//        } else if (!category1.equals("전체") && category2.equals("전체")) {
//            result = productRepository.searchWithCate1( category1, keyword ,pageable);
//            return result;// 1만 선택되었을때
//        } else if (!category2.equals("전체") && category1.equals("전체")) {
//            result = productRepository.searchWithCate2(category2, keyword, pageable);
//            return result;// 2만 선택되었을때
//        } else if (!category1.equals("전체") && !category2.equals("전체")) {
//            result = productRepository.searchWithCate1AndCate2(category1, category2, keyword, pageable);
//            return result;// 둘다 옵션 선택했을때
//        }
//        return result;
//    }
//
//    public void categoryInsertAndFilter(){
//
//        List<Product> productList = productRepository.findAll();
//        Set<String> set = new HashSet<>();
//
//        for (Product products : productList) {
//            set.add(products.getItem_CATEGORY1());
//        }
//        for (String cate1 : set) {
//            if(cateMap.containsKey("cate1") && !cateMap.get("cate1").contains(cate1)) {
//                cateMap.add("cate1", cate1);
//            }else if(!cateMap.containsKey("cate1")){
//                cateMap.add("cate1", cate1);
//            }
//        }
//        set.clear();
//
//        for (Product products : productList) {
//            set.add(products.getItem_CATEGORY2());
//        }
//
//        for (String cate2 : set) {
//            if(cateMap.containsKey("cate2") && !cateMap.get("cate2").contains(cate2)) {
//                cateMap.add("cate2", cate2);
//            }else if(!cateMap.containsKey("cate2")){
//                cateMap.add("cate2", cate2);
//            }
//        }
//        set.clear();
//    }
//
//
//    // 주문 검색
//    @Transactional(readOnly = true)
//    public Page<Order> OrderSearcher(String cate1, String cate2, String keyword, int page){
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("orders_IDX"));
//        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
//        Page<Order> a = null;
//        if(cate1.equals("전체") && cate2.equals("전체")){
//            a = ordersRepository.searchByKeyword(keyword,pageable);
//        }else if (cate1.equals("회원") && cate2.equals("전체")){
//            a = ordersRepository.searchOnlyMember(keyword,pageable);
//        }else if (cate1.equals("비회원") && cate2.equals("전체")){
//            a = ordersRepository.searchOnlyNonMember(keyword,pageable);
//        }else if (cate1.equals("전체") && !cate2.equals("전체")){
//            a = ordersRepository.searchOnlyStatus(cate2, keyword,pageable);
//        }else if (cate1.equals("회원") && !cate2.equals("전체")){
//            a = ordersRepository.searchMemberAndStatus(cate2, keyword,pageable);
//        }else if (cate1.equals("비회원") && !cate2.equals("전체")){
//            a = ordersRepository.searchNonMemberAndStatus(cate2, keyword,pageable);
//        }
//        return a;
//    }
//
//    public void orderCateInsert(){
//        if(!cateMap.containsKey("orderCate1") && !cateMap.containsKey("orderCate2")) {
//            cateMap.add("orderCate1", "회원");
//            cateMap.add("orderCate1", "비회원");
//            cateMap.add("orderCate2", "입금대기");
//            cateMap.add("orderCate2", "배송준비");
//            cateMap.add("orderCate2", "배송중");
//            cateMap.add("orderCate2", "배송완료");
//        }
//    }
//
//
//    // 리뷰 검색
//    @Transactional(readOnly = true)
//    public Page<Review> findSearchReviewWriter(String cate1, String keyword, int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("review_WRITER"));
//        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
//        Page<Review> a = null;
//        if(cate1.equals("전체")){
//            a = reviewRepository.findByAll(keyword,pageable);
//        }
//        else if(cate1.equals("작성자")) {
//            a = reviewRepository.findByWriter(keyword, pageable);
//        }else if(cate1.equals("제목")) {
//            a = reviewRepository.findByTitle(keyword, pageable);
//        }else if(cate1.equals("번호")) {
//            a = reviewRepository.findByIdx(keyword, pageable);
//        }
//        return a;
//    }
//
//    public void reviewInsertAndFilter() {
//        if (!cateMap.containsKey("reviewCate")) {
//            cateMap.add("reviewCate", "작성자");
//            cateMap.add("reviewCate", "제목");
//            cateMap.add("reviewCate", "번호");
//        }
//    }



    // 게시글 검색
    @Transactional(readOnly = true)
    public Page<Board> findSearchProduct(String category1, String category2, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("item_NAME"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Board> result = null;
        if(category1.equals("전체") && category2.equals("전체")) {
            result = boardRepository.searchAll(keyword, pageable);
            return result;// 둘다 전체일때
        } else if (!category1.equals("전체") && category2.equals("전체")) {
            result = boardRepository.searchWithCate1( category1, keyword ,pageable);
            return result;// 1만 선택되었을때
        } else if (!category2.equals("전체") && category1.equals("전체")) {
            result = boardRepository.searchWithCate2(category2, keyword, pageable);
            return result;// 2만 선택되었을때
        } else if (!category1.equals("전체") && !category2.equals("전체")) {
            result = boardRepository.searchWithCate1AndCate2(category1, category2, keyword, pageable);
            return result;// 둘다 옵션 선택했을때
        }
        return result;
    }

    public void categoryInsertAndFilter(){

        List<Board> productList = productRepository.findAll();
        Set<String> set = new HashSet<>();

        for (Board products : productList) {
            set.add(products.getItem_CATEGORY1());
        }
        for (String cate1 : set) {
            if(cateMap.containsKey("cate1") && !cateMap.get("cate1").contains(cate1)) {
                cateMap.add("cate1", cate1);
            }else if(!cateMap.containsKey("cate1")){
                cateMap.add("cate1", cate1);
            }
        }
        set.clear();

        for (Board products : productList) {
            set.add(products.getItem_CATEGORY2());
        }

        for (String cate2 : set) {
            if(cateMap.containsKey("cate2") && !cateMap.get("cate2").contains(cate2)) {
                cateMap.add("cate2", cate2);
            }else if(!cateMap.containsKey("cate2")){
                cateMap.add("cate2", cate2);
            }
        }
        set.clear();
    }


    // 댓글 검색
    @Transactional(readOnly = true)
    public Page<Reply> findSearchReviewWriter(String cate1, String keyword, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("review_WRITER"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Reply> a = null;
        if(cate1.equals("전체")){
            a = replyRepository.findByAll(keyword,pageable);
        }
        else if(cate1.equals("작성자")) {
            a = replyRepository.findByWriter(keyword, pageable);
        }else if(cate1.equals("제목")) {
            a = replyRepository.findByTitle(keyword, pageable);
        }else if(cate1.equals("번호")) {
            a = replyRepository.findByIdx(keyword, pageable);
        }
        return a;
    }

    public void reviewInsertAndFilter() {
        if (!cateMap.containsKey("reviewCate")) {
            cateMap.add("reviewCate", "작성자");
            cateMap.add("reviewCate", "제목");
            cateMap.add("reviewCate", "번호");
        }
    }


     */

}
