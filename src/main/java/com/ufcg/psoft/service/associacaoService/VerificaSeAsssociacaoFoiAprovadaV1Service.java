package com.ufcg.psoft.commerce.service.associacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoGetDTO;

@Service
public class VerificaSeAsssociacaoFoiAprovadaV1Service implements VerificaSeAssociacaoFoiAprovadaService{


    @Autowired
    AssociacaoGetService associacaoGetService;

    @Override
    public boolean verificaSeAssociacaoFoiAprovada(Long id) {
        AssociacaoGetDTO associacaoGetDTO = this.associacaoGetService.getAssociacao(id);
    
        return associacaoGetDTO.isStatus();
    }
    
}