package com.es.seguridadsession.controller;

import com.es.seguridadsession.exception.BadRequestException;
import com.es.seguridadsession.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rutaProtegida")
public class RutaProtegidaController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/")
    public String rutaProtegida(
            HttpServletRequest request
    ) {
        String token = "";

        if (request.getCookies() == null) {
            throw new BadRequestException("Cookies no pueden ser null");
        }

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("tokenSession")) {
                token = cookie.getValue();
            }
        }

        System.out.println("Token: "+token);

        if (sessionService.checkToken(token)) {
            return "RECURSO SUPER IMPORTANTE";
        } else {
            return "NO TIENES ACCESO";
        }
    }
}
