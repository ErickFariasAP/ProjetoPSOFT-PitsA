package com.ufcg.psoft.commerce.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.service.cliente.*;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteRestController {

    @Autowired
    ClientePostService clientePostService;

    @Autowired
    ClientePutService clientePutService;

    @Autowired
    ClienteGetService clienteGetService;

    @Autowired
    ClienteDeleteService clienteDeleteService;

    @Autowired
    ClienteInteresseService clienteInteresseService;

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody ClientePostPutDTO clientePostPutDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientePostService.create(clientePostPutDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCliente(@PathVariable Long id, @RequestParam("codigoAcesso") String codInserido,
            @RequestBody ClientePostPutDTO clientePostPutDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clientePutService.update(id, codInserido, clientePostPutDTO));
    }

    @PutMapping("/{id}/demonstrarInteresse")
    public ResponseEntity<?> putClienteInteresse(@PathVariable Long id,
            @RequestParam("codigoAcesso") String codInserido,
            @RequestParam("saborId") Long saborId) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteInteresseService.updateInteresse(id, codInserido, saborId));
    }

    @GetMapping
    public ResponseEntity<?> getAllCliente() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteGetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteGetService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClienteById(@PathVariable Long id,
            @RequestParam("codigoAcesso") String codInserido) {
        clienteDeleteService.delete(id, codInserido);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
