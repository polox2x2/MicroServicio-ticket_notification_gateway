package com.institute.Notificationservice.repository;

import com.institute.Notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepositorio extends JpaRepository<Notification, Integer> {
    List<Notification> findByDestinatarioId(Integer destinatarioId);

    List<Notification> findByEstado(String estado);

    List<Notification> findByDestinatarioIdAndEstado(Integer destinatarioId, String estado);


}
