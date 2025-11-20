package com.institute.ticketservice.channel.controller;


import com.institute.ticketservice.channel.model.Channel;
import com.institute.ticketservice.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/canales")
@RequiredArgsConstructor
public class ChannelControlador {

    private final ChannelRepository repo;

    @GetMapping
    public List<Channel> listar() {
        return repo.findAll();
    }


}
