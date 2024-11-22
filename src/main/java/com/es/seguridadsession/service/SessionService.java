package com.es.seguridadsession.service;

import com.es.seguridadsession.exception.NotFoundException;
import com.es.seguridadsession.exception.UnauthorizedException;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public boolean checkToken(String token) {
        Session s = sessionRepository
                .findByToken(token)
                .orElseThrow(() -> new NotFoundException("not found : " + token));

        LocalDateTime ahora = LocalDateTime.now();

        if(ahora.isAfter(s.getExpirationDate())) {
            throw new UnauthorizedException("Token caducado");
        }

        return true;
    }
}
