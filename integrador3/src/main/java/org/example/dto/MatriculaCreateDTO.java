package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaCreateDTO {
    private int id;
    private int dniEstudiante;
    private int idCarrera;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
}