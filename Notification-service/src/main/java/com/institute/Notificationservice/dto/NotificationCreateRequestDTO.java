package com.institute.Notificationservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationCreateRequestDTO {
    @NotNull
    private Integer destinatarioId;

    @NotBlank
    private String tipo;


    private String asunto;

    @NotBlank
    private String contenido;
}
