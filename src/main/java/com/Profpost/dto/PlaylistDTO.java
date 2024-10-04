package com.Profpost.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlaylistDTO {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio ")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String name;

}