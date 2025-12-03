package com.example.repaso_examen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taller {
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private BigDecimal precio;

}
