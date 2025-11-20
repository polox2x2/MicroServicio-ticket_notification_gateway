package com.institute.ticketservice.comment.repository;


import com.institute.ticketservice.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepositorio  extends JpaRepository<Comment, Integer> {

    List<Comment> findByTicketId(Integer ticketId);

}
