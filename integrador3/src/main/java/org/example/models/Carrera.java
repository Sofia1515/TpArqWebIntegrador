package org.example.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrera")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrera {

    @Id
    @Column(name = "id_carrera", nullable = false, unique = true)
    private int id;

    @Column(name = "carrera", nullable = false)
    private String nombre;

    @Column(name = "duracion", nullable = false)
    private int duracion;
}