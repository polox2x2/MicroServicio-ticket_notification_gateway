package com.institute.ticketservice.comment.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;

    @Column(name = "author_id", nullable = false)
    private Integer autorId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "created_at")
    private LocalDateTime fechaCreacion = LocalDateTime.now();


}
