package com.ufcg.psoft.commerce.controller.associacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.service.associacaoService.AssociacaoAprovaService;
import com.ufcg.psoft.commerce.service.associacaoService.AssociacaoPostService;
import com.ufcg.psoft.commerce.service.associacaoService.AssociacaoReprovaService;

@RestController
@RequestMapping(
        value = "/associacao", produces = MediaType.APPLICATION_JSON_VALUE
)

public class AssociacaoRestController {
    @Autowired
    AssociacaoAprovaService associacaoAprovaservice;
    @Autowired
    AssociacaoReprovaService associacaoReprovaService;
    @Autowired
    AssociacaoPostService associacaoPostService;

    @PostMapping    
    public ResponseEntity<Associacao> solicitarAssociacao(@RequestParam String codigoAcesso, @RequestBody AssociacaoDTO associacaoDTO){
        Associacao associacao = this.associacaoPostService.criaAssociacao(codigoAcesso, associacaoDTO); 
        return ResponseEntity
                .status(HttpStatus.CREATED) 
                .body(associacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> aceitarAssociacao (@PathVariable("id") Long  id, @RequestParam String codigoAcesso, @RequestBody AssociacaoDTO associacaoDTO){ 
        Associacao associacao = this.associacaoAprovaservice.aprovaAssociacao(id, codigoAcesso, associacaoDTO); 
        return ResponseEntity
                .status(HttpStatus.OK) 
                .body(associacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> recusarAssociacao(@PathVariable("id") Long  id, @RequestParam String codigoAcesso, @RequestBody AssociacaoDTO associacaoDTO){ 
        associacaoReprovaService.reprovaAssociacao(id, codigoAcesso, associacaoDTO);
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .build();
    }
}
