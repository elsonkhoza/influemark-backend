package com.influemark.app.influencer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InfluencerRepository extends JpaRepository<Influencer,Long> {
}
