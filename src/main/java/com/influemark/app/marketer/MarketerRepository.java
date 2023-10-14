package com.influemark.app.marketer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketerRepository extends JpaRepository<Marketer,Long> {
}
