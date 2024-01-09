package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;

@FunctionalInterface
public interface ClientePostService {

    public Cliente create(ClientePostPutDTO clientePostPutDTO);

}
