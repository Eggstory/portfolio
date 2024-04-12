package com.spring.portfolio.store.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsByRefreshToken(String token);

    Optional<RefreshToken> findByMember(Member member);

}
