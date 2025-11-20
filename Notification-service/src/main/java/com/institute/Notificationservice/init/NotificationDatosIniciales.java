package com.institute.Notificationservice.init;


import com.institute.Notificationservice.model.Notification;
import com.institute.Notificationservice.repository.NotificationRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class NotificationDatosIniciales   implements CommandLineRunner {

    private final NotificationRepositorio notificationRepositorio;

    @Override
    public void run(String... args) throws Exception {

        if (notificationRepositorio.count() > 0) {
            log.info(">>> (Init) Notificaciones ya existen, se omite carga inicial.");
            return;
        }

        log.info(">>> (Init) Creando notificaciones de prueba...");

        Notification n1 = Notification.builder()
                .destinatarioId(2001)
                .tipo("email")
                .asunto("Bienvenido al instituto")
                .contenido("Hola, gracias por registrarte en el sistema del instituto.")
                .estado("sent")
                .fechaCreacion(LocalDateTime.now().minusDays(2))
                .fechaEnvio(LocalDateTime.now().minusDays(2))
                .build();

        Notification n2 = Notification.builder()
                .destinatarioId(2002)
                .tipo("whatsapp")
                .contenido("Tienes una nueva notificación sobre tu matrícula.")
                .estado("pending")
                .fechaCreacion(LocalDateTime.now().minusHours(5))
                .build();

        Notification n3 = Notification.builder()
                .destinatarioId(2003)
                .tipo("email")
                .asunto("Error en procesamiento de pago")
                .contenido("Tu pago no pudo ser procesado. Por favor intenta nuevamente.")
                .estado("failed")
                .fechaCreacion(LocalDateTime.now().minusHours(10))
                .build();

        notificationRepositorio.saveAll(List.of(n1, n2, n3));

        log.info(">>> (Init) Notificaciones de prueba creadas correctamente.");
    }


}
