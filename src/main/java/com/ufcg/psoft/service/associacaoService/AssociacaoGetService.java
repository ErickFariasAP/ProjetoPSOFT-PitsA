package com.ufcg.psoft.commerce.service.associacaoService;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoGetDTO;

@FunctionalInterface
public interface AssociacaoGetService {
    public AssociacaoGetDTO getAssociacao(Long id);
}