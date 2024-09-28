package com.Profpost.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio ")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String name;

    @NotBlank(message = "La descripcion esobligatoria")
    @Size(max = 200, message = "La descripcion debe tener menos de 200 caracteres")
    private String description;

}

