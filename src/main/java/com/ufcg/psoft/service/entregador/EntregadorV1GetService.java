package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInexistenteException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregadorV1GetService implements EntregadorGetService {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntregadorGetRequestDTO get(Long id) {
        if (!entregadorRepository.existsById(id)) {
            throw new EntregadorInexistenteException();
        }
        EntregadorGetRequestDTO entregadorGetRequestDTO = modelMapper.map(entregadorRepository.findById(id), EntregadorGetRequestDTO.class);
        return entregadorGetRequestDTO;
    }

    @Override
    public List<EntregadorGetRequestDTO> getAll() {
        List<Entregador> todosEntregadores = entregadorRepository.findAll();
        return todosEntregadores.stream()
                .map(entregador -> modelMapper.map(entregador, EntregadorGetRequestDTO.class))
                .collect(Collectors.toList());
    }
}
