package com.es.seguridadsession.controller;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.exception.BadRequestException;
import com.es.seguridadsession.exception.NotFoundException;
import com.es.seguridadsession.exception.UnauthorizedException;
import com.es.seguridadsession.service.ProductoService;
import com.es.seguridadsession.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * CLASE CONTROLLER DE PRODUCTOS
 * ESTOS RECURSOS ESTÁN PROTEGIDOS, Y SÓLO SE PUEDE ACCEDER AQUÍ SI EL USUARIO TIENE UNA SESSION ACTIVA
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private SessionService sessionService;


    /**
     * GET PRODUCTO POR SU ID
     * A este método pueden acceder todo tipo de usuarios
     * tanto los que tengan ROL USER como los que tengan ROL ADMIN
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        if (id == null || id.isBlank()) {
            throw new BadRequestException("La id no puede estar vacía");
        }

        String token = "";

        if (request.getCookies() == null) {
            throw new BadRequestException("Las cookies no pueden ser null");
        }

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("tokenSession")) {
                token = cookie.getValue();
            }
        }

        if (sessionService.checkToken(token)) {
            ProductoDTO producto = productoService.getById(id);

            return new ResponseEntity<>(producto, HttpStatus.OK);
        }

        return null;
    }

    /**
     * INSERTAR PRODUCTO
     * A este método sólo pueden acceder los usuarios que tengan ROL ADMIN
     * @param productoDTO
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> insert(
            @RequestBody ProductoDTO productoDTO,
            HttpServletRequest request
    ) {
        // TODO

        if (productoDTO == null || productoDTO.getNombre() == null) {
            throw new BadRequestException("Producto no puede ser null");
        }

        String token = "";

        if (request.getCookies() == null) {
            throw new BadRequestException("Las cookies no pueden ser null");
        }

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("tokenSession")) {
                token = cookie.getValue();
            }
        }

        if (sessionService.checkToken(token)) {
            ProductoDTO producto = productoService.insert(productoDTO, token);

            return new ResponseEntity<>(producto, HttpStatus.OK);
        }

        return null;
    }


}
