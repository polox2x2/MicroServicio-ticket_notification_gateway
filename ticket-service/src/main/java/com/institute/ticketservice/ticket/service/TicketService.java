package com.institute.ticketservice.ticket.service;


import com.institute.ticketservice.ticket.dto.TicketCreateRequestDTO;
import com.institute.ticketservice.ticket.dto.TicketUpdateStatusRequestDTO;
import com.institute.ticketservice.ticket.model.Ticket;
import com.institute.ticketservice.ticket.repository.TicketRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepositorio ticketRepositorio;

    public Ticket crearTicket(TicketCreateRequestDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setEstudianteId(dto.getEstudianteId());
        ticket.setTitulo(dto.getTitulo());
        ticket.setDescripcion(dto.getDescripcion());
        ticket.setCanalId(dto.getCanalId());
        ticket.setEstado("open");
        ticket.setFechaCreacion(LocalDateTime.now());

        return ticketRepositorio.save(ticket);
    }

    public List<Ticket> listarTickets(Integer estudianteId, String estado) {
        if (estudianteId != null && estado != null) {
            return ticketRepositorio.findByEstudianteIdAndEstado(estudianteId, estado);
        }
        if (estudianteId != null) {
            return ticketRepositorio.findByEstudianteId(estudianteId);
        }
        if (estado != null) {
            return ticketRepositorio.findByEstado(estado);
        }
        return ticketRepositorio.findAll();
    }

    public Ticket obtenerPorId(Integer id) {
        return ticketRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }

    public Ticket actualizarEstado(Integer id, TicketUpdateStatusRequestDTO dto) {
        Ticket ticket = obtenerPorId(id);

        ticket.setEstado(dto.getEstado());

        if (dto.getEstado().equalsIgnoreCase("resolved")
                || dto.getEstado().equalsIgnoreCase("closed")) {
            ticket.setFechaResolucion(LocalDateTime.now());
        }

        return ticketRepositorio.save(ticket);
    }


}
