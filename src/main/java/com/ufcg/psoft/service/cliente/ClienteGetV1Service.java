package com.ufcg.psoft.commerce.service.cliente;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;

@Service
public class ClienteGetV1Service implements ClienteGetService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ClientePostPutRequestDTO getById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteInexistenteException();
        }
        ClientePostPutRequestDTO clienteGetRequestDTO = mapper.map(clienteRepository.findById(id),
                ClientePostPutRequestDTO.class);
        return clienteGetRequestDTO;
    }

    @Override
    public List<ClientePostPutRequestDTO> getAll() {
        List<Cliente> todosClientees = clienteRepository.findAll();
        return todosClientees.stream()
                .map(cliente -> mapper.map(cliente, ClientePostPutRequestDTO.class))
                .collect(Collectors.toList());
    }
}
