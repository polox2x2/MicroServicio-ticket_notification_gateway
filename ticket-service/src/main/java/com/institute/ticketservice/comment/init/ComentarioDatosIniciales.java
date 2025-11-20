package com.institute.ticketservice.comment.init;

import com.institute.ticketservice.comment.model.Comment;
import com.institute.ticketservice.comment.repository.CommentRepositorio;
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
@Order(3) // después de tickets
public class ComentarioDatosIniciales implements CommandLineRunner {

    private final CommentRepositorio commentRepositorio;
    private final TicketRepositorio ticketRepositorio;

    @Override
    public void run(String... args) throws Exception {

        if (commentRepositorio.count() > 0) {
            log.info(">>> (Init) Comentarios ya existen, se omite carga inicial de comentarios.");
            return;
        }

        List<Ticket> tickets = ticketRepositorio.findAll();

        if (tickets.isEmpty()) {
            log.warn(">>> (Init) No hay tickets para asociar comentarios. Se omite carga de comentarios.");
            return;
        }

        log.info(">>> (Init) Creando comentarios de prueba...");

        Ticket t1 = tickets.get(0);
        Ticket t2 = tickets.size() > 1 ? tickets.get(1) : tickets.get(0);

        Comment c1 = new Comment();
        c1.setTicketId(t1.getId());
        c1.setAutorId(9001); // staff o estudiante de prueba
        c1.setContenido("Estamos revisando tu incidencia. Te informaremos pronto.");
        c1.setFechaCreacion(LocalDateTime.now().minusHours(5));

        Comment c2 = new Comment();
        c2.setTicketId(t1.getId());
        c2.setAutorId(1001); // mismo estudiante
        c2.setContenido("Gracias por la ayuda, quedo atento.");
        c2.setFechaCreacion(LocalDateTime.now().minusHours(3));

        Comment c3 = new Comment();
        c3.setTicketId(t2.getId());
        c3.setAutorId(9002);
        c3.setContenido("El problema de matrícula ha sido escalado al área académica.");
        c3.setFechaCreacion(LocalDateTime.now().minusHours(2));

        commentRepositorio.saveAll(List.of(c1, c2, c3));

        log.info(">>> (Init) Comentarios de prueba creados correctamente.");
    }
}
