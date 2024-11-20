package com.es.seguridadsession.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private String password;
    private String role;

    public Usuario(Long id, String nombre, String password, String role) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.role = role;
    }

    public Usuario(String nombre, String password, String role) {
        this.nombre = nombre;
        this.password = password;
        this.role = role;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
