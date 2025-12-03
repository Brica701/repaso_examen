package com.example.repaso_examen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paso1DTO {
    @NotNull(message = "Debes seleccionar un taller")
    private Integer tallerId;

    @NotNull(message = "Debes indicar el número de asistentes")
    @Min(value = 1, message = "Debe ser al menos 1 asistente")
    private Integer numeroAsistentes;
}
