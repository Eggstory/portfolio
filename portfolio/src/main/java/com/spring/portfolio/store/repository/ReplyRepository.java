package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Reply;
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
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @EntityGraph(attributePaths = {"board"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select r from Reply r join r.board b where b.boardIdx = :idx")
//    @Query(value = "SELECT r FROM Reply r WHERE r.board.boardIdx = :idx")
    List<Reply> findByBoardIdx(long idx);


    @Query(value = "delete from Reply where reply_idx = :idx OR parent_idx = :idx", nativeQuery = true)
    void deleteByReplyIdx(long idx);

    @Modifying
    @Query(value = "delete from Reply r where r.board.boardIdx = :idx")
    void deleteByBoardIdx(@Param("idx") Long idx);


//    @Query(value = "select * from reply where board_idx = :idx", nativeQuery = true)
//    List<Reply> findByBoardIdx(long idx);

}
