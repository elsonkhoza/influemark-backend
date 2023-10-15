package com.influemark.app.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("select t from Token t where t.influencer.id = ?1 " +
            "and (t.expired = false or t.revoked = false)")
    List<Token> findAllActiveTokensByInfluencer(Long id);





}
