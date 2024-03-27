package com.spring.portfolio.service;

import com.spring.portfolio.dto.ReplyRequestDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.store.repository.BoardRepository;
import com.spring.portfolio.store.repository.MemberRepository;
import com.spring.portfolio.store.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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

    public void saveReply(ReplyRequestDto dto) {

        Reply entity = dto.toEntity();
        replyRepository.save(entity);

    }

//    public Reply addReply(Long boardIdx, Long MemberIx) throws Exception {
//        Board board = boardRepository.findById(boardIdx)
//                .orElseThrow(() -> new Exception("Board Not Found"));
//        memberRepository.findByMemberMail()
//    }
}
