package com.spring.portfolio.service;

import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.store.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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


    public BoardResponseDto loadBoardView(long idx) {

        Board board = boardRepository.findById(idx)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Board"));

        BoardResponseDto boardDto = new BoardResponseDto(board);

        return boardDto;
    }


    public void saveBoard(BoardRequestDto dto) {

        Board entity = dto.toEntity();
        boardRepository.save(entity);

    }
}
