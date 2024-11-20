package com.es.seguridadsession.dto;

public class UsuarioInsertDTO {

    private String nombre;
    private String password1;
    private String password2;
    private String role;

    public UsuarioInsertDTO(String nombre, String password1, String password2, String role) {
        this.nombre = nombre;
        this.password1 = password1;
        this.password2 = password2;
        this.role = role;
    }

    public UsuarioInsertDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
