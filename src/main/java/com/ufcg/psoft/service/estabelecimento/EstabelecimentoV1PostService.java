package com.ufcg.psoft.commerce.service.estabelecimento;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1PostService implements EstabelecimentoPostService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento post(EstabelecimentoPostPutRequestDTO estabelecimento){
        return this.estabelecimentoRepository.save(modelMapper.map(estabelecimento, Estabelecimento.class));
    }
}
