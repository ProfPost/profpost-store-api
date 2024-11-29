package com.Profpost.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionCreateDTO {
    private Integer id;

    @NotNull(message = "El valor de la reacción no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo de una reacción es 0")
    @Max(value = 3, message = "El valor máximo de una reacción es 3")
    private Integer value;

    @NotNull(message = "La publicación es obligatoria")
    private Integer publicationId;

}
