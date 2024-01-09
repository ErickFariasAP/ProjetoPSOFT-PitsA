package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.entregador.Entregador;

//@Service
//USA FUNCTIONAL INTERFACE NO LUGAR DE SERVICE
@FunctionalInterface
public interface EntregadorPostService {
    Entregador post(EntregadorPostPutRequestDTO entregadorPostDTO);
}
