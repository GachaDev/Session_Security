package com.es.seguridadsession.repository;

import com.es.seguridadsession.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE s.token = ?1 ORDER BY s.expirationDate DESC LIMIT 1")
    Optional<Session> findByToken(String token);
}
