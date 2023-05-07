package com.adcorreajr.agendaapi.controller.rest;


import com.adcorreajr.agendaapi.model.dto.AtualizacaoContato;
import com.adcorreajr.agendaapi.model.entity.Contato;
import com.adcorreajr.agendaapi.model.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @PostMapping({"","/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save (@RequestBody @Valid Contato contato){
        return contatoRepository.save(contato);
    }

    @GetMapping({"","/"})
    public List<Contato> getAll(){
        return contatoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contato getId(@PathVariable Integer id){
        return contatoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        contatoRepository.findById(id)
                .map( contato -> {
                    contatoRepository.delete(contato);
                    return Void.TYPE;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }



    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid Contato novo){
        contatoRepository.findById(id)
                .map( contato -> {
                    novo.setId(contato.getId());
                    contatoRepository.save(novo);
                    return Void.TYPE;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }


    @PatchMapping("/{id}/favorito")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favoritar(@PathVariable Integer id, @RequestBody boolean favorito){
        contatoRepository.findById(id)
                .map( contato -> {
                    contato.setFavorito(favorito);
                    contatoRepository.save(contato);
                    return Void.TYPE;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }


}
