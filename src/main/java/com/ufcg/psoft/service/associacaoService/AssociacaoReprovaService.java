package com.ufcg.psoft.commerce.service.associacaoService;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;

@FunctionalInterface
public interface AssociacaoReprovaService {
    public void reprovaAssociacao(Long id, String codigoAcesso, AssociacaoDTO associacaoReprovaDTO);
}