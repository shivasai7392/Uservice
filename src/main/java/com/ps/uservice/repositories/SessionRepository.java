package com.ps.uservice.repositories;

import com.ps.uservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
