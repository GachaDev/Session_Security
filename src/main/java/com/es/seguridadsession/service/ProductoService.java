package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.exception.NotFoundException;
import com.es.seguridadsession.exception.UnauthorizedException;
import com.es.seguridadsession.model.Producto;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.model.Usuario;
import com.es.seguridadsession.repository.ProductoRepository;
import com.es.seguridadsession.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Obtiene un producto buscándolo por su ID
     * @param id
     * @return
     */
    public ProductoDTO getById(String id) {
        Long idL = 0L;

        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException("La id debe de ser un número correcto");
        }

        Producto producto = productoRepository.findById(idL).orElse(null);

        if (producto == null) {
            throw new NotFoundException("El producto con la id " + id + " no existe");
        }

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.isPrecio());
        productoDTO.setStock(producto.getStock());

        return productoDTO;
    }

    /**
     * Inserta un producto dentro la tabla productos
     * @param productoDTO
     * @return
     */
    public ProductoDTO insert(ProductoDTO productoDTO, String token) {
        Session session = sessionRepository.findByToken(token).orElse(null);
        Usuario usuario = session.getUsuario();

        if (usuario.getRole().equalsIgnoreCase("USER")) {
            throw new UnauthorizedException("DEBES DE SER ADMIN PARA PODER INSERTAR PRODUCTOS");
        }

        Producto producto = new Producto();

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.isPrecio());
        producto.setStock(productoDTO.getStock());

        productoRepository.save(producto);

        return productoDTO;
    }

}
