package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.UsuarioDTO;
import com.es.seguridadsession.dto.UsuarioInsertDTO;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.model.Usuario;
import com.es.seguridadsession.repository.SessionRepository;
import com.es.seguridadsession.repository.UsuarioRepository;
import com.es.seguridadsession.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public String login(UsuarioDTO userLogin) {

        // Comprobar si user y pass son correctos -> obtener de la BDD el usuario
        String nombreUser = userLogin.getNombre();
        String passUser = userLogin.getPassword();

        List<Usuario> users = usuarioRepository.findByNombre(nombreUser);

        Usuario u = users
                .stream()
                .filter(user -> user.getNombre().equals(nombreUser) && TokenUtil.checkPassword(passUser, user.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Contraseña incorrecta")); // LANZAR EXCEPCION PROPIA

        // Si coincide -> Insertar una sesión
        // Genero un TOKEN
        String token = "";

        try {
            token = TokenUtil.encrypt(u.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token");
        }

        System.out.println("Token generado: "+token);

        Session s = new Session();
        s.setToken(token);
        s.setUsuario(u);

        s.setExpirationDate(
                LocalDateTime.now().plusMinutes(1)
        );

        sessionRepository.save(s);

        return token;

    }

    public UsuarioInsertDTO insert(UsuarioInsertDTO userDTO) {
        Usuario user = new Usuario();

        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            throw new RuntimeException("Las contraseñas deben de coincidir");
        }

        if (userDTO.getRole().toUpperCase().equals("USER") || userDTO.getRole().toUpperCase().equals("ADMIN")) {
            user.setRole(userDTO.getRole().toUpperCase());
            user.setNombre(userDTO.getNombre());
            user.setPassword(TokenUtil.hashPassword(userDTO.getPassword1()));

            usuarioRepository.save(user);
        } else {
            throw new RuntimeException("El rol debe de ser USER O ADMIN");
        }

        return userDTO;
    }
}
