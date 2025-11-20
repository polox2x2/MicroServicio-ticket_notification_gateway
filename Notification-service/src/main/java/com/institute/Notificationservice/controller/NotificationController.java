package com.institute.Notificationservice.controller;


import com.institute.Notificationservice.dto.NotificationCreateRequestDTO;
import com.institute.Notificationservice.dto.NotificationUpdateStatusRequestDTO;
import com.institute.Notificationservice.model.Notification;
import com.institute.Notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> crear(@Valid @RequestBody NotificationCreateRequestDTO dto) {
        Notification guardada = notificationService.crear(dto);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping
    public List<Notification> listar(
            @RequestParam(required = false) Integer destinatarioId,
            @RequestParam(required = false) String estado
    ) {
        return notificationService.listar(destinatarioId, estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> obtenerPorId(@PathVariable Integer id) {
        Notification notification = notificationService.obtenerPorId(id);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Notification> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody NotificationUpdateStatusRequestDTO dto
    ) {
        Notification actualizada = notificationService.actualizarEstado(id, dto);
        return ResponseEntity.ok(actualizada);
    }
}
