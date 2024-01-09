package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
@FunctionalInterface
public interface ClientePutService {
    public Cliente update(Long id, String codInserido, ClientePostPutDTO clientePostPutDTO);
}
