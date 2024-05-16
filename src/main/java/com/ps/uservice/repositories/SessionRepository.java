package com.ps.uservice.repositories;

import com.ps.uservice.models.Session;
import com.ps.uservice.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByUserIdAndTokenAndStatusAndExpiryingAtAfter(UUID userId, String token,SessionStatus sessionStatus, Date currentDate);

    Optional<Session> findByUserIdAndToken(UUID userId, String token);

    List<Session> findAllByUserIdAndStatusAndExpiryingAtAfter(UUID userId, SessionStatus sessionStatus, Date currentDate);
}
