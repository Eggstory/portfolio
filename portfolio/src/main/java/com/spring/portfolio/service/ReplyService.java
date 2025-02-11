package com.spring.portfolio.service;

import com.spring.portfolio.dto.ReplyRequestDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.store.repository.BoardRepository;
import com.spring.portfolio.store.repository.MemberRepository;
import com.spring.portfolio.store.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<ReplyResponseDto> loadReply(long idx) {
        List<Reply> replyList = replyRepository.findByBoardIdx(idx);
        List<ReplyResponseDto> dtoList = new ArrayList<>();
        for(Reply entity : replyList) {
            ReplyResponseDto dto = new ReplyResponseDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<ReplyResponseDto> loadReplyList(Long memberIdx) {
        List<Reply> replyList = replyRepository.findByMemberIdx(memberIdx);

        List<ReplyResponseDto> dtoList = new ArrayList<>();
        for(Reply entity : replyList) {
            ReplyResponseDto dto = new ReplyResponseDto(entity);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public Page<Reply> loadReplyInMyInfo(Long memberIdx, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("replyIdx").descending());

        Page<Reply> replyPage = replyRepository.findByMemberNameContaining(memberIdx, pageable);

        return replyPage;
    }

    @Transactional
    public void saveReply(ReplyRequestDto dto) {

        Reply entity = dto.toEntity();
        replyRepository.save(entity);

    }

    @Transactional
    public void updateReply(ReplyRequestDto dto) throws Exception {
        Reply reply = replyRepository.findById(dto.getReplyIdx())
                .orElseThrow(() -> new Exception("Board Not Found"));

        reply.modifyReply(dto);


    }

    @Transactional
    public void deleteReply(long idx) {

        replyRepository.deleteByReplyIdx(idx);
    }

    @Transactional
    public void restoreReply(Long idx) {

        replyRepository.restoreByReplyIdx(idx);
    }

    @Transactional
    public void changeReplyInfo(Long idx) {

        replyRepository.changeReplyInfo(idx);
        Reply reply = replyRepository.findById(idx)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Reply"));

        if(reply.getParent() != null) {
            replyRepository.deleteById(idx);
        }
    }

    @Transactional
    public void updateMemberNull(Long idx) {

        replyRepository.updateMemberNull(idx);
    }

    @Transactional
    public void updateIsDeleted(Long idx) {

        replyRepository.updateIsDeleted(idx);
    }




//    public Reply addReply(Long boardIdx, Long MemberIx) throws Exception {
//        Board board = boardRepository.findById(boardIdx)
//                .orElseThrow(() -> new Exception("Board Not Found"));
//        memberRepository.findByMemberMail()
//    }
}
