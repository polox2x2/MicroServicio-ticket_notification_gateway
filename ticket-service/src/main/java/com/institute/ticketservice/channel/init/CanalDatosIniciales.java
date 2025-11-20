package com.institute.ticketservice.channel.init;

import com.institute.ticketservice.channel.model.Channel;

import com.institute.ticketservice.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1) // se ejecuta antes que tickets y comentarios
public class CanalDatosIniciales implements CommandLineRunner {

    private final ChannelRepository canalRepositorio;

    @Override
    public void run(String... args) throws Exception {
        if (canalRepositorio.count() > 0) {
            log.info(">>> (Init) Canales ya existen, se omite carga inicial de canales.");
            return;
        }

        log.info(">>> (Init) Creando canales por defecto: whatsapp, email, web_form...");

        Channel whatsapp = new Channel();
        whatsapp.setName("whatsapp");

        Channel email = new Channel();
        email.setName("email");

        Channel webForm = new Channel();
        webForm.setName("web_form");

        canalRepositorio.save(whatsapp);
        canalRepositorio.save(email);
        canalRepositorio.save(webForm);

        log.info(">>> (Init) Canales creados correctamente.");
    }
}
