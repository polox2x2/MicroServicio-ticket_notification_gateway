package com.institute.Notificationservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "recipient_id", nullable = false)
    private Integer destinatarioId;  // users_db.user.id

    @Column(name = "type", nullable = false, length = 20)
    private String tipo; // 'email' o 'whatsapp'

    @Column(name = "subject", length = 255)
    private String asunto; // solo para email

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "status", nullable = false, length = 20)
    private String estado = "pending"; // 'pending', 'sent', 'failed'

    @Column(name = "sent_at")
    private LocalDateTime fechaEnvio;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();


}
