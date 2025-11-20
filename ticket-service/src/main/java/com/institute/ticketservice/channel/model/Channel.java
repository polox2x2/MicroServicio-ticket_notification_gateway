package com.institute.ticketservice.channel.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;


}
