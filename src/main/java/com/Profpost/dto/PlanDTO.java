package com.Profpost.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PlanDTO {
    private Integer id;

    @NotBlank(message = "El nombre del plan es obligatorio ")
    @Size(max = 20, message = "El nombre debe tener 20 caracteres o menos")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser menor que 0")
    @Digits(integer = 6, fraction = 2, message = "El precio no puede tener más de 6 dígitos enteros y 2 decimales")
    private Float price;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 200, message = "La descripcion debe tener menos de 200 caracteres")
    private String description;

    public PlanDTO() {}
    public PlanDTO(Integer id, String name, Float price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
