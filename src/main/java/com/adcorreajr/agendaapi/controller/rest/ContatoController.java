package com.adcorreajr.agendaapi.controller.rest;


import com.adcorreajr.agendaapi.model.dto.AtualizacaoContato;
import com.adcorreajr.agendaapi.model.entity.Contato;
import com.adcorreajr.agendaapi.model.repository.ContatoRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin("http://localhost:4200")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @PostMapping({"","/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save (@RequestBody @Valid Contato contato){
        return contatoRepository.save(contato);
    }

    @GetMapping({"","/"})
    public Page<Contato> getAll( @RequestParam(value = "page", defaultValue = "0") Integer pagina,
                        @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina){

        return contatoRepository.findAll(PageRequest.of(pagina, tamanhoPagina));
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
    public void favoritar(@PathVariable Integer id){
        contatoRepository.findById(id)
                .map( contato -> {
                    boolean favorito = contato.getFavorito() == Boolean.TRUE;
                    contato.setFavorito(!favorito);
                    contatoRepository.save(contato);
                    return Void.TYPE;
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }


    @PatchMapping("/{id}/foto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public byte[] addFoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo){

        return contatoRepository.findById(id)
                .map( contato -> {
                    try {
                        InputStream is = arquivo.getInputStream();
                        byte[] bytes = new byte[(int) arquivo.getSize()];
                        IOUtils.readFully(is, bytes);
                        contato.setFoto(bytes);
                        contatoRepository.save(contato);
                        is.close();
                        return bytes;
                    }catch (IOException ex){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível fazer o upload do arquivo.");
                    }
                })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }


}
