package com.ufcg.psoft.commerce.service.cliente;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;
import com.ufcg.psoft.commerce.exception.sabor.SaborInexistenteException;
import com.ufcg.psoft.commerce.exception.sabor.SaborTrueException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.sabor.Sabor;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;
import com.ufcg.psoft.commerce.repository.sabor.SaborRepository;

@Service
public class ClienteInteresseV1Service implements ClienteInteresseService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper mapper;
    
     @Override
    public SaborPostPutRequestDTO updateInteresse(Long id, String codInserido, Long saborId) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteInexistenteException::new);

        if (!cliente.getCodigoAcesso().equals(codInserido)) {
            throw new CodigoInvalidoException();
        }

        Sabor sabor = saborRepository.findById(saborId).orElseThrow(SaborInexistenteException::new);
        Boolean saborDispo = sabor.getDisponibilidade();

        if (saborDispo.equals(true)) {
            throw new SaborTrueException();
        }

        sabor.getClientesInteressados().add(saborId);

        return mapper.map(sabor, SaborPostPutRequestDTO.class);
    }
}
