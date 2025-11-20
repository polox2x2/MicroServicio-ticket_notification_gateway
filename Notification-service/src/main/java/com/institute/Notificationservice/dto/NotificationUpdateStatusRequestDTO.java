package com.institute.Notificationservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationUpdateStatusRequestDTO {

    @NotBlank
    private String estado;

}
