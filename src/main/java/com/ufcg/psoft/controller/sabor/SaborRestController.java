package com.ufcg.psoft.commerce.controller.sabor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutDTO;
import com.ufcg.psoft.commerce.model.sabor.Sabor;
import com.ufcg.psoft.commerce.service.sabor.SaborDeleteService;
import com.ufcg.psoft.commerce.service.sabor.SaborDisponibilidadeService;
import com.ufcg.psoft.commerce.service.sabor.SaborGetService;
import com.ufcg.psoft.commerce.service.sabor.SaborPostService;
import com.ufcg.psoft.commerce.service.sabor.SaborPutService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(
    value = "/sabores", produces = MediaType.APPLICATION_JSON_VALUE
)

public class SaborRestController {
    @Autowired
    SaborPostService saborPostService;
    @Autowired
    SaborGetService saborGetService;
    @Autowired
    SaborDeleteService saborDeleteService;
    @Autowired
    SaborPutService saborPutService;
    @Autowired
    SaborDisponibilidadeService saborDisponibilidadeService;

   @PostMapping
   public ResponseEntity<Sabor> post(@RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso, @RequestBody SaborPostPutDTO sabor){
        Sabor saborCriado = this.saborPostService.post(estId, codAcesso, sabor);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(saborCriado);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.saborGetService.getAll(estId, codAcesso));
    } 

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.saborGetService.get(id, estId, codAcesso));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("saborId") Long id, @RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso){
        this.saborDeleteService.delete(id, estId, codAcesso);
        return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(""); 
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestParam(value = "saborId") Long id, @RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso, @RequestBody @Valid SaborPostPutDTO newSabor) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.saborPutService.put(id, estId, codAcesso, newSabor)); 
    }

    @PutMapping("/{id}/{disponibilidade}")
    public ResponseEntity<?> putDisponivel(@PathVariable Long id, @PathVariable Boolean disponibilidade, @RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String codAcesso) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.saborDisponibilidadeService.putDisponivel(id, estId, codAcesso, disponibilidade)); 
    }
}
