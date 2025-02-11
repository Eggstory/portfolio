package com.spring.portfolio.service;

import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.store.repository.BoardRepository;
import com.spring.portfolio.store.repository.MemberRepository;
import com.spring.portfolio.store.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;


    public Page<Member> loadMemberList(String keyword, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("memberIdx").descending());

        Page<Member> memberPage;

        if (keyword == null || keyword.equals("")) {
            memberPage = memberRepository.findAll(pageable);
        } else {
            memberPage = memberRepository.findByMemberNameContaining(keyword, pageable);
        }
        return memberPage;
    }

    public Page<Member> loadLimitMemberList(String keyword, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("memberIdx").descending());

        Page<Member> memberPage;

        if (keyword == null || keyword.equals("")) {
            memberPage = memberRepository.findByIsLimited(pageable);
        } else {
            memberPage = memberRepository.findByLimitedMemberNameContaining(keyword, pageable);
        }
        return memberPage;
    }

    public Page<Board> loadBoardList(String keyword, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardIdx").descending());

        Page<Board> boardPage;

        if (keyword == null || keyword.equals("")) {
            boardPage = boardRepository.findAll(pageable);
        } else {
            boardPage = boardRepository.findByBoardTitleContaining(keyword, pageable);
        }
        return boardPage;
    }

    public Page<Reply> findReplyList(String keyword, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("replyIdx").descending());

        Page<Reply> replyPage;

        if (keyword == null || keyword.equals("")) {
            replyPage = replyRepository.findAll(pageable);
        } else {
            replyPage = replyRepository.findByReplyCommentContaining(keyword, pageable);
        }
        return replyPage;
    }

}
