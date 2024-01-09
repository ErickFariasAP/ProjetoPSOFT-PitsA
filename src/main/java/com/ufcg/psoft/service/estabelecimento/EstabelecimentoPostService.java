package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPostService {
    public Estabelecimento post(EstabelecimentoPostPutRequestDTO estabelecimento);
}
