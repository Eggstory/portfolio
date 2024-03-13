package com.spring.portfolio.service;

import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.store.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> loadBoardList(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("idx"));
//         Sort.by(sorts) : 아래줄 PageRequest.of의 세번째 인자
        Pageable pageable = PageRequest.of(page, 10, Sort.by("idx").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage;
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
}
