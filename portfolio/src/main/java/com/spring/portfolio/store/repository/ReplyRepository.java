package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @EntityGraph(attributePaths = {"board"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select r from Reply r join r.board b where b.boardIdx = :idx")
    List<Reply> findByBoardIdx(long idx);

//    @Query(value = "select * from reply where board_idx = :idx", nativeQuery = true)
//    List<Reply> findByBoardIdx(long idx);

}
