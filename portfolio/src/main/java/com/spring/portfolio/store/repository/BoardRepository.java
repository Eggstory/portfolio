package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

    // count 쿼리를 쓰고싶으면 @Query를 써서 직접 쿼리를 짜줘야한다.
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select b from Board b join b.member m where b.boardTitle like %:keyword% or m.memberId like %:keyword%")
    Page<Board> findByBoardTitleContaining(String keyword, Pageable pageable);

    @Modifying
    @Query(value = "delete from Board b where b.boardIdx = :idx")
    void deleteByBoardIdx(@Param("idx") Long idx);
}
