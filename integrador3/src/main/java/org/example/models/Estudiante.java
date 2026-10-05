package org.example.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private int dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "ciudad_residencia", nullable = false)
    private String ciudadResidencia;

    @Column(name = "libreta_universitaria", nullable = false, unique = true)
    private int libretaUniversitaria;
}