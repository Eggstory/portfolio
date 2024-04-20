package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select * from member where member_mail =:mail AND member_pw = :pw", nativeQuery = true)
    Optional<Member> findByMailAndPw(@Param("mail") String memberMail, @Param("pw")String memberPw);

//    @Query(value = "select * from member where member_ID = :member_ID", nativeQuery = true)
//    List<Member> findByUserLoginId(@Param("member_ID") String memberId);

    @Query(value = "select m from Member m where m.memberMail =:memberMail")
    Optional<Member> findByMemberMail(@Param("memberMail")String memberMail);

//    Member findByProviderId(String providerId);

    Optional<Member> findByMemberName(String memberName);

    @Query(value = "select m from Member m where m.memberName like %:keyword% or m.memberMail like %:keyword%")
    Page<Member> findByMemberNameContaining(String keyword, Pageable pageable);
}
