package com.ufcg.psoft.commerce.service.associacaoService;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.model.associacao.Associacao;

@FunctionalInterface
public interface AssociacaoAprovaService {
    public Associacao aprovaAssociacao(Long id, String codigoAcesso, AssociacaoDTO associacaoAprovaDTO);
}