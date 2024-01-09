package com.ufcg.psoft.commerce.service.cliente;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;
import com.ufcg.psoft.commerce.repository.sabor.SaborRepository;

@Service
public class ClientePutV1Service implements ClientePutService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborRepository saborRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Cliente update(Long id, String codInserido, ClientePostPutDTO clientePostPutDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteInexistenteException::new);

        if (!cliente.getCodigoAcesso().equals(codInserido)) {
            throw new CodigoInvalidoException();
        }

        mapper.map(clientePostPutDTO, cliente);
        return clienteRepository.save(cliente);
    }

}
