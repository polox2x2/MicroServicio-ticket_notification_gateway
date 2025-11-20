package com.institute.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketUpdateStatusRequestDTO {


    @NotBlank
    private String estado;
}
