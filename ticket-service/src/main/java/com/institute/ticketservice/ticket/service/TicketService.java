package com.institute.ticketservice.ticket.service;

import com.institute.ticketservice.client.NotificationClient;
import com.institute.ticketservice.ticket.dto.TicketCreateRequestDTO;
import com.institute.ticketservice.ticket.dto.TicketUpdateStatusRequestDTO;
import com.institute.ticketservice.ticket.model.Ticket;
import com.institute.ticketservice.ticket.repository.TicketRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepositorio ticketRepositorio;
    private final com.institute.ticketservice.ticket.repository.TicketInteractionRepository ticketInteractionRepository;
    private final NotificationClient notificationClient;

    public Ticket asignarTicket(Integer ticketId, Integer advisorId) {
        Ticket ticket = obtenerPorId(ticketId);
        ticket.setAdvisorId(advisorId);
        ticket.setEstado("in_process");
        return ticketRepositorio.save(ticket);
    }

    public com.institute.ticketservice.ticket.model.TicketInteraction agregarInteraccion(Integer ticketId,
            com.institute.ticketservice.ticket.dto.TicketInteractionCreateRequestDTO dto) {
        Ticket ticket = obtenerPorId(ticketId);

        com.institute.ticketservice.ticket.model.TicketInteraction interaction = new com.institute.ticketservice.ticket.model.TicketInteraction();
        interaction.setTicketId(ticket.getId());
        interaction.setAuthorId(dto.getAuthorId());
        interaction.setContent(dto.getContent());
        interaction
                .setType(com.institute.ticketservice.ticket.model.InteractionType.valueOf(dto.getType().toUpperCase()));
        interaction.setTimestamp(LocalDateTime.now());

        return ticketInteractionRepository.save(interaction);
    }

    public List<com.institute.ticketservice.ticket.model.TicketInteraction> obtenerInteracciones(Integer ticketId) {
        return ticketInteractionRepository.findByTicketIdOrderByTimestampAsc(ticketId);
    }

    public List<Ticket> obtenerTicketsPorRango(LocalDateTime inicio, LocalDateTime fin) {
        return ticketRepositorio.findByFechaCreacionBetween(inicio, fin);
    }

    public Ticket crearTicket(TicketCreateRequestDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setEstudianteId(dto.getEstudianteId());
        ticket.setTitulo(dto.getTitulo());
        ticket.setDescripcion(dto.getDescripcion());
        ticket.setCanalId(dto.getCanalId());
        ticket.setEstado("open");
        ticket.setFechaCreacion(LocalDateTime.now());

        if (dto.getPriority() != null) {
            try {
                ticket.setPriority(
                        com.institute.ticketservice.ticket.model.Priority.valueOf(dto.getPriority().toUpperCase()));
            } catch (IllegalArgumentException e) {
                ticket.setPriority(com.institute.ticketservice.ticket.model.Priority.LOW);
            }
        } else {
            ticket.setPriority(com.institute.ticketservice.ticket.model.Priority.LOW);
        }

        Ticket savedTicket = ticketRepositorio.save(ticket);

        // Generate Ticket Code: TICKET-[RANDOM]-[ID]
        String randomCode = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String ticketCode = String.format("TICKET-%s-%04d", randomCode, savedTicket.getId());
        savedTicket.setTicketCode(ticketCode);

        savedTicket = ticketRepositorio.save(savedTicket);

        // Send notification asynchronously
        notificationClient.sendTicketCreatedNotification(
                savedTicket.getId(),
                savedTicket.getTitulo(),
                savedTicket.getEstudianteId());

        return savedTicket;
    }

    public List<Ticket> listarTickets(Integer estudianteId, String estado) {
        if (estudianteId != null && estado != null) {
            return ticketRepositorio.findByEstudianteIdAndEstado(estudianteId, estado);
        }
        if (estudianteId != null) {
            return ticketRepositorio.findByEstudianteId(estudianteId);
        }
        // Use the new custom sorting method for general listing or filtering by state
        // only
        return ticketRepositorio.findAllWithCustomPrioritySorting(estado);
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

        Ticket updatedTicket = ticketRepositorio.save(ticket);

        // Send status change notification
        notificationClient.sendTicketStatusChangedNotification(
                updatedTicket.getId(),
                updatedTicket.getEstado(),
                updatedTicket.getEstudianteId());

        return updatedTicket;
    }

    public Ticket actualizarPrioridad(Integer id, String priority) {
        Ticket ticket = obtenerPorId(id);
        try {
            ticket.setPriority(com.institute.ticketservice.ticket.model.Priority.valueOf(priority.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Prioridad inválida: " + priority);
        }
        return ticketRepositorio.save(ticket);
    }

    public void eliminarTicket(Integer id) {
        if (!ticketRepositorio.existsById(id)) {
            throw new RuntimeException("Ticket no encontrado");
        }
        ticketRepositorio.deleteById(id);
    }

}
