package com.institute.ticketservice.comment.controller;


import com.institute.ticketservice.comment.dto.CrearComentarioDTO;
import com.institute.ticketservice.comment.model.Comment;
import com.institute.ticketservice.comment.service.CommentServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentControlador {

    private final CommentServicio commentServicio;

    @PostMapping
    public ResponseEntity<Comment> crear(@RequestBody CrearComentarioDTO dto) {
        return ResponseEntity.ok(commentServicio.crear(dto));
    }

    @GetMapping("/ticket/{ticketId}")
    public List<Comment> listarPorTicket(@PathVariable Integer ticketId) {
        return commentServicio.listarPorTicket(ticketId);
    }

}
