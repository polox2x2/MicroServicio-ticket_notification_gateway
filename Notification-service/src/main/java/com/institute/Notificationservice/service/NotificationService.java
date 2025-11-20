package com.institute.Notificationservice.service;


import com.institute.Notificationservice.dto.NotificationCreateRequestDTO;
import com.institute.Notificationservice.dto.NotificationUpdateStatusRequestDTO;
import com.institute.Notificationservice.model.Notification;
import com.institute.Notificationservice.repository.NotificationRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepositorio notificationRepositorio;

    public Notification crear(NotificationCreateRequestDTO dto) {

        Notification notification = Notification.builder()
                .destinatarioId(dto.getDestinatarioId())
                .tipo(dto.getTipo())
                .asunto(dto.getAsunto())
                .contenido(dto.getContenido())
                .estado("pending")
                .fechaCreacion(LocalDateTime.now())
                .build();

        return notificationRepositorio.save(notification);
    }

    public List<Notification> listar(Integer destinatarioId, String estado) {
        if (destinatarioId != null && estado != null) {
            return notificationRepositorio.findByDestinatarioIdAndEstado(destinatarioId, estado);
        }
        if (destinatarioId != null) {
            return notificationRepositorio.findByDestinatarioId(destinatarioId);
        }
        if (estado != null) {
            return notificationRepositorio.findByEstado(estado);
        }
        return notificationRepositorio.findAll();
    }

    public Notification obtenerPorId(Integer id) {
        return notificationRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }

    public Notification actualizarEstado(Integer id, NotificationUpdateStatusRequestDTO dto) {
        Notification notification = obtenerPorId(id);

        notification.setEstado(dto.getEstado());

        if (dto.getEstado().equalsIgnoreCase("sent")) {
            notification.setFechaEnvio(LocalDateTime.now());
        }

        return notificationRepositorio.save(notification);
    }

}
