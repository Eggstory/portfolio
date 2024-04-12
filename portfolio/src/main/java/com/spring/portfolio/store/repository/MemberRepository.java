package com.spring.portfolio.store.repository;

import com.spring.portfolio.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select * from member where member_mail =:mail AND member_pw = :pw", nativeQuery = true)
    Optional<Member> findByMailAndPw(@Param("mail") String memberMail, @Param("pw")String memberPw);

    @Query(value = "select * from member where member_ID = :member_ID", nativeQuery = true)
    List<Member> findByUserLoginId(@Param("member_ID") String memberId);

    @Query(value = "select * from member where member_mail =:mail", nativeQuery = true)
    Optional<Member> findByMemberMail(@Param("mail")String memberMail);

    Member findByOauthId(String oauthId);

    Optional<Member> findByUsername(String username);
}
