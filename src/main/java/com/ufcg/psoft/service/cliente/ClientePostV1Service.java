package com.ufcg.psoft.commerce.service.cliente;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;

@Service
public class ClientePostV1Service implements ClientePostService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Cliente create(ClientePostPutDTO clientePostPutDTO) {
        return repository.save(mapper.map(clientePostPutDTO, Cliente.class));
    }

}
