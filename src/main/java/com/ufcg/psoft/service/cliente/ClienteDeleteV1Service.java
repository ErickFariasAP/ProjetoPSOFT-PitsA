package com.ufcg.psoft.commerce.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;

@Service
public class ClienteDeleteV1Service implements ClienteDeleteService {

    @Autowired
    ClienteRepository repository;

    @Override
    public void delete(Long id, String codInserido){
        Cliente cliente = repository.findById(id).orElseThrow(ClienteInexistenteException::new);

        if (!cliente.getCodigoAcesso().equals(codInserido)) {
            throw new CodigoInvalidoException();
        }

        repository.deleteById(id);
    }

}
