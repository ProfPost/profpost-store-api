package com.Profpost.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class PublicationCreateDTO {
    private Integer id;

    @NotNull(message = "El Titulo es obligatorio")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    @URL(message = "Debe ser una URL válida")
    private String video_url;

    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)", message = "El archivo debe ser una imagen con extensión .jpg, .jpeg, .png, .gif, o .bmp")
    private String filePath;

    @Pattern(regexp = "ALL|STANDARD|PREMIUM", message = "El valor debe ser ALL, STANDARD o PREMIUM")
    @NotNull(message = "La visibilidad de contenido es obligatoria")
    private String visibility;

    @NotNull(message = "El ID de la categoria es obligatoria")
    private Integer category_id;

    @NotNull(message = "El ID del creador es obligatorio")
    private Integer creator_id;

    private LocalDateTime schedulePublishAt;
}
