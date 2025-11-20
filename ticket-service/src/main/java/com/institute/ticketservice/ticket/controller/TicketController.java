package com.institute.ticketservice.ticket.controller;

import com.institute.ticketservice.ticket.dto.TicketCreateRequestDTO;
import com.institute.ticketservice.ticket.dto.TicketUpdateStatusRequestDTO;
import com.institute.ticketservice.ticket.model.Ticket;
import com.institute.ticketservice.ticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {


    private final TicketService ticketServicio;

    @PostMapping
    public ResponseEntity<Ticket> crear(@Valid @RequestBody TicketCreateRequestDTO dto) {
        Ticket guardado = ticketServicio.crearTicket(dto);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping
    public List<Ticket> listar(
            @RequestParam(required = false) Integer estudianteId,
            @RequestParam(required = false) String estado
    ) {
        return ticketServicio.listarTickets(estudianteId, estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerPorId(@PathVariable Integer id) {
        Ticket ticket = ticketServicio.obtenerPorId(id);
        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody TicketUpdateStatusRequestDTO dto
    ) {
        Ticket actualizado = ticketServicio.actualizarEstado(id, dto);
        return ResponseEntity.ok(actualizado);
    }

}
