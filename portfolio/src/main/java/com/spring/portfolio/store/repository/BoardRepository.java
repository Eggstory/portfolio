package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // BoardIdx를 DESC방식으로 정렬 시킨 후 제일 최상단 4개를 가져온다는 메소드이름
    List<Board> findTop4ByOrderByBoardIdxDesc();

    @Modifying
    @Query(value = "update Board b set b.viewCount = b.viewCount + 1 where b.boardIdx = :idx")
    int updateViewCount(Long idx);
}
