package com.adcorreajr.agendaapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class TesteConfig {

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            System.out.println("Sessão de teste iniciada!");
        };
    }
}
