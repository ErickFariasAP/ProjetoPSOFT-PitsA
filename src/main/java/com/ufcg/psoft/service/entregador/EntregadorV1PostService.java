package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1PostService implements EntregadorPostService{
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Entregador post(EntregadorPostPutRequestDTO entregadorPostDTO) {
        Entregador entregador = modelMapper.map(entregadorPostDTO, Entregador.class);
        return entregadorRepository.save(entregador);
    }
}
