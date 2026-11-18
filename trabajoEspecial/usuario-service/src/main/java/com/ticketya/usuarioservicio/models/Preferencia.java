
package com.ticketya.usuarioservicio.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "preferencias")
public class Preferencia {

    @Id
    private String id;

    private Long usuarioId;
    private List<String> generos;
    private List<String> categorias;
    private List<String> artistasFavoritos;

    public Preferencia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<String> getArtistasFavoritos() {
        return artistasFavoritos;
    }

    public void setArtistasFavoritos(List<String> artistasFavoritos) {
        this.artistasFavoritos = artistasFavoritos;
    }
}
