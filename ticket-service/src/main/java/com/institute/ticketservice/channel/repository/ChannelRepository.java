package com.institute.ticketservice.channel.repository;

import com.institute.ticketservice.channel.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

}
