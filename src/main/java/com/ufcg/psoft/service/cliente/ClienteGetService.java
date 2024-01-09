package com.ufcg.psoft.commerce.service.cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;

@Service
public interface ClienteGetService {
    public ClientePostPutRequestDTO getById(Long id) throws ClienteInexistenteException;

    public List<ClientePostPutRequestDTO> getAll();
}
