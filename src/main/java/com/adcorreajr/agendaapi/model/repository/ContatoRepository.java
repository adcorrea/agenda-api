package com.adcorreajr.agendaapi.model.repository;

import com.adcorreajr.agendaapi.model.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
