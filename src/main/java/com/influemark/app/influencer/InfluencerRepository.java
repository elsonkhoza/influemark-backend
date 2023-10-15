package com.influemark.app.influencer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    Optional<Influencer> findByEmail(String email);

}
