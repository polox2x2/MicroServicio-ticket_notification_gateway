package com.institute.ticketservice.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@org.hibernate.annotations.SQLDelete(sql = "UPDATE ticket SET deleted = true WHERE id = ?")
@org.hibernate.annotations.Where(clause = "deleted = false")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id", nullable = false)
    private Integer estudianteId;

    @Column(name = "advisor_id")
    private Integer advisorId;

    @Column(name = "ticket_code")
    private String ticketCode;

    @Column(name = "title", nullable = false, length = 255)
    private String titulo;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "channel_id", nullable = false)
    private Integer canalId;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String estado = "open";

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Priority priority = Priority.LOW;

    @Column(name = "resolved_at")
    private LocalDateTime fechaResolucion;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;
}
