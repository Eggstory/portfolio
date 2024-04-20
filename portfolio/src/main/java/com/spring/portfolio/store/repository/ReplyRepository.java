package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @EntityGraph(attributePaths = {"board"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select r from Reply r join r.board b where b.boardIdx = :idx")
//    @Query(value = "SELECT r FROM Reply r WHERE r.board.boardIdx = :idx")
    List<Reply> findByBoardIdx(long idx);

    @Modifying
    @Query(value = "delete from Reply r where r.board.boardIdx = :idx")
    void deleteByBoardIdx(@Param("idx") Long idx);

    @Modifying
    @Query(value = "delete from Reply r where r.board.boardIdx = :idx and r.parent is not null")
    void deleteReReplyByBoardIdx(@Param("idx")Long idx);


//    @Query(value = "delete from Reply where reply_idx = :idx OR parent_idx = :idx", nativeQuery = true)
//    void deleteByReplyIdx(long idx);
    @Modifying
    @Query(value = "update Reply r set r.isDeleted = true where r.replyIdx = :idx")
    void deleteByReplyIdx(@Param("idx") Long idx);

    @Modifying
    @Query(value = "update Reply r set r.isDeleted = false where r.replyIdx = :idx")
    void restoreByReplyIdx(@Param("idx") Long idx);

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select r from Reply r join r.member m where r.replyComment like %:keyword% or m.memberName like %:keyword%")
    Page<Reply> findByReplyCommentContaining(String keyword, Pageable pageable);



//    @Query(value = "select * from reply where board_idx = :idx", nativeQuery = true)
//    List<Reply> findByBoardIdx(long idx);

}
