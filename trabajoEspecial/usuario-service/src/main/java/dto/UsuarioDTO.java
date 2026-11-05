package dto;

import models.Rol;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;

    public UsuarioDTO(){
    }

    public UsuarioDTO(Long id, String nombre, String apellido, String email, Rol rol){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getApellido() {
        return apellido;
    }

    public Rol getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
