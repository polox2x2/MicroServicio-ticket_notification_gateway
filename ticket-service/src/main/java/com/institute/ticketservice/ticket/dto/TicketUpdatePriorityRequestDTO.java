package com.institute.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketUpdatePriorityRequestDTO {
    @NotBlank(message = "La prioridad es obligatoria")
    private String priority;
}
