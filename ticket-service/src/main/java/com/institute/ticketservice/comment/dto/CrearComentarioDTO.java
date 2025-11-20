package com.institute.ticketservice.comment.dto;


import lombok.Data;

@Data
public class CrearComentarioDTO {

    private Integer ticketId;
    private Integer autorId;
    private String contenido;

}
