package com.ufcg.psoft.commerce.controller.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.service.associacaoService.AssociacaoPostV1Service;
import com.ufcg.psoft.commerce.service.entregador.EntregadorDeleteService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorDisponibilizaService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorGetService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorV1IndisponibilizaService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorPostService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorPutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/entregadores", produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorRestController {
    @Autowired
    EntregadorPostService entregadorPostService;
    @Autowired
    EntregadorGetService entregadorGetService;
    @Autowired
    EntregadorDeleteService entregadorDeleteService;
    @Autowired
    EntregadorPutService entregadorPutService;
    @Autowired
    AssociacaoPostV1Service associacaoPostService;
    @Autowired
    EntregadorDisponibilizaService entregadorDisponibilizaService;
    @Autowired
    EntregadorV1IndisponibilizaService entregadorInDisponibilizaService;

    @PostMapping
    public ResponseEntity<?> cadastrarEntregador(@RequestBody EntregadorPostPutRequestDTO EntregadorPostDTO) {
        Entregador entregadorCriado = this.entregadorPostService.post(EntregadorPostDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(@PathVariable("id") Long id, @RequestParam(value = "codigoAcesso") String codigoAcesso, @RequestBody EntregadorPostPutRequestDTO EntregadorPutDTO) {
        Entregador entregadorAtualizado = this.entregadorPutService.put(id, codigoAcesso,EntregadorPutDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAtualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEntregador(@PathVariable("id") Long id) {
        EntregadorGetRequestDTO entregadorBuscado = entregadorGetService.get(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorBuscado);
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        List<EntregadorGetRequestDTO> todosOsEntregadores = entregadorGetService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todosOsEntregadores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEntregador(@PathVariable("id") Long id, @RequestParam(value = "codigoAcesso") String codigoAcesso) {
        entregadorDeleteService.delete(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // seta a disponibilidade do entregador para poder realizar entregas
    @PatchMapping("/{id}/disponibilizarEntregador")
    public ResponseEntity<?> disponibilizarEntregador(@PathVariable("id") Long id, @RequestParam(value = "codigoAcesso") String codigoAcesso) {
        Entregador entregadorDisponibilizado = entregadorDisponibilizaService.disponibiliza(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorDisponibilizado);
    }

    // seta a disponibilidade do entregador para não poder realizar entregas
    @PatchMapping("/{id}/indisponibilizarEntregador")
    public ResponseEntity<?> indisponibilizarEntregador(@PathVariable("id") Long id, @RequestParam(value = "codigoAcesso") String codigoAcesso) {
        Entregador entregadorIndisponibilizado = entregadorInDisponibilizaService.indisponibiliza(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorIndisponibilizado);
    }
}
