package com.ufcg.psoft.commerce.controller.estabelecimento;

import java.util.List;

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

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoDeleteService;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoGetService;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoPostService;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoPutService;

import jakarta.validation.Valid;
@RestController
@RequestMapping(
    value = "/estabelecimentos", produces = MediaType.APPLICATION_JSON_VALUE
)

public class EstabelecimentoRestController {
    @Autowired
    EstabelecimentoPostService estabelecimentoPostService;
    @Autowired
    EstabelecimentoGetService estabelecimentoGetService;
    @Autowired
    EstabelecimentoDeleteService estabelecimentoDeleteService;
    @Autowired
    EstabelecimentoPutService estabelecimentoPutService;

//   public EstabelecimentoRestController() {
//        this.estabelecimentoPostService = new EstabelecimentoV1PostService();
//        this.estabelecimentoGetService = new EstabelecimentoV1GetService();
//        this.estabelecimentoDeleteService = new EstabelecimentoV1DeleteService();
//        this.estabelecimentoPutService = new EstabelecimentoV1PutService();
//    }

   @PostMapping
   public ResponseEntity<Estabelecimento> post(@RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimento){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.estabelecimentoPostService.post(estabelecimento));
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoResponseDTO>> getAll() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.estabelecimentoGetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.estabelecimentoGetService.get(id));
    }
    
    @GetMapping("/{id}/sabores")
    public ResponseEntity<?> getCardapio(@PathVariable Long id) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.estabelecimentoGetService.getCardapio(id));
    }

    @GetMapping("/{id}/sabores/{tipo}")
    public ResponseEntity<?> getCardapioPorTipo(@PathVariable Long id, @RequestParam(value = "tipo") String tipo) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.estabelecimentoGetService.getCardapioPorTipo(id, tipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestParam(value = "codigoAcesso") String codigoAcesso){
        this.estabelecimentoDeleteService.delete(id, codigoAcesso);
        return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body("");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody EstabelecimentoPostPutRequestDTO newEstabelecimento, @RequestParam(value = "codigoAcesso") String codigoAcesso) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.estabelecimentoPutService.put(id, newEstabelecimento, codigoAcesso));
    }

    
}
