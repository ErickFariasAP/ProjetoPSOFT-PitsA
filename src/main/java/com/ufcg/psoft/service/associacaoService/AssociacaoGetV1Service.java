package com.ufcg.psoft.commerce.service.associacaoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoGetDTO;
import com.ufcg.psoft.commerce.exception.associacao.AssociacaoInexistente;
import com.ufcg.psoft.commerce.repository.associacao.AssociacaoRepository;

@Service
public class AssociacaoGetV1Service implements AssociacaoGetService{
    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AssociacaoGetDTO getAssociacao(Long id) {
        if(!this.associacaoRepository.existsById(id)) {
            throw new AssociacaoInexistente();
        }

        AssociacaoGetDTO associacaoGetDTO = modelMapper.map(associacaoRepository.findById(id), AssociacaoGetDTO.class);
        return associacaoGetDTO;
    }

    
}