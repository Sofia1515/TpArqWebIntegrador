package org.example.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "inscripcion", nullable = false)
    private int inscripcion;

    @Column(name = "graduacion")
    private int graduacion;

    @Column(name = "antiguedad", nullable = false)
    private int antiguedad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudiante", referencedColumnName = "dni", nullable = false)
    private org.example.models.Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera", nullable = false)
    private org.example.models.Carrera carrera;
}