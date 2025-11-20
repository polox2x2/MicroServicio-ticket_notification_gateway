package com.institute.ticketservice.comment.service;


import com.institute.ticketservice.comment.dto.CrearComentarioDTO;
import com.institute.ticketservice.comment.model.Comment;
import com.institute.ticketservice.comment.repository.CommentRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServicio {

    private final CommentRepositorio commentRepositorio;

    public Comment crear(CrearComentarioDTO dto) {
        Comment c = new Comment();
        c.setTicketId(dto.getTicketId());
        c.setAutorId(dto.getAutorId());
        c.setContenido(dto.getContenido());
        return commentRepositorio.save(c);
    }

    public List<Comment> listarPorTicket(Integer ticketId) {
        return commentRepositorio.findByTicketId(ticketId);
    }

}
