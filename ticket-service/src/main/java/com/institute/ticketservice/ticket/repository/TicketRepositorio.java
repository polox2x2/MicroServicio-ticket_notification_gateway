package com.institute.ticketservice.ticket.repository;

import com.institute.ticketservice.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepositorio extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByEstudianteId(Integer estudianteId);

    List<Ticket> findByEstado(String estado);

    List<Ticket> findByEstudianteIdAndEstado(Integer estudianteId, String estado);

    List<Ticket> findByEstadoOrderByPriorityDescFechaCreacionAsc(String estado);

    List<Ticket> findAllByOrderByPriorityDescFechaCreacionAsc();

    List<Ticket> findByFechaCreacionBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @org.springframework.data.jpa.repository.Query("SELECT t FROM Ticket t WHERE (:estado IS NULL OR t.estado = :estado) "
            +
            "ORDER BY CASE t.priority " +
            "WHEN 'CRITICAL' THEN 4 " +
            "WHEN 'HIGH' THEN 3 " +
            "WHEN 'MEDIUM' THEN 2 " +
            "WHEN 'LOW' THEN 1 ELSE 0 END DESC, t.fechaCreacion ASC")
    List<Ticket> findAllWithCustomPrioritySorting(
            @org.springframework.data.repository.query.Param("estado") String estado);
}
