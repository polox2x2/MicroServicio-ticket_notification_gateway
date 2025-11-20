package com.institute.ticketservice.ticket.init;

import com.institute.ticketservice.ticket.model.Ticket;
import com.institute.ticketservice.ticket.repository.TicketRepositorio;
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
@Order(2) // después de canales
public class TicketDatosIniciales implements CommandLineRunner {

    private final TicketRepositorio ticketRepositorio;

    @Override
    public void run(String... args) throws Exception {

        if (ticketRepositorio.count() > 0) {
            log.info(">>> (Init) Tickets ya existen, se omite carga inicial de tickets.");
            return;
        }

        log.info(">>> (Init) Creando tickets de prueba...");

        Ticket t1 = new Ticket();
        t1.setEstudianteId(1001);
        t1.setTitulo("Problema con acceso al aula virtual");
        t1.setDescripcion("No puedo ingresar a la plataforma con mi usuario institucional.");
        t1.setCanalId(3); // web_form
        t1.setEstado("open");
        t1.setFechaCreacion(LocalDateTime.now().minusDays(2));

        Ticket t2 = new Ticket();
        t2.setEstudianteId(1002);
        t2.setTitulo("Error en matrícula de curso");
        t2.setDescripcion("El sistema no me permite matricularme en Programación I.");
        t2.setCanalId(2); // email
        t2.setEstado("in_progress");
        t2.setFechaCreacion(LocalDateTime.now().minusDays(1));

        Ticket t3 = new Ticket();
        t3.setEstudianteId(1003);
        t3.setTitulo("Actualización de datos personales");
        t3.setDescripcion("Necesito cambiar mi número de celular en el sistema.");
        t3.setCanalId(1); // whatsapp
        t3.setEstado("resolved");
        t3.setFechaCreacion(LocalDateTime.now().minusDays(3));
        t3.setFechaResolucion(LocalDateTime.now().minusDays(1));

        Ticket t4 = new Ticket();
        t4.setEstudianteId(1004);
        t4.setTitulo("Problema con certificado de estudios");
        t4.setDescripcion("Solicité mi certificado hace una semana y aún no está disponible.");
        t4.setCanalId(2); // email
        t4.setEstado("open");
        t4.setFechaCreacion(LocalDateTime.now().minusHours(10));

        ticketRepositorio.saveAll(List.of(t1, t2, t3, t4));

        log.info(">>> (Init) Tickets de prueba creados correctamente.");
    }
}
