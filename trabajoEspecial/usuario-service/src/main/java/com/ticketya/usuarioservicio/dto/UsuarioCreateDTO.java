package com.ticketya.usuarioservicio.dto;

import com.ticketya.usuarioservicio.models.Rol;

public class UsuarioCreateDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;

    public UsuarioCreateDTO(String nombre, String apellido, String email, String password, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    //getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
