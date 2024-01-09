package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.model.entregador.Entregador;

public interface EntregadorV1IndisponibilizaService {
    public Entregador indisponibiliza(Long id, String codigoAcesso);
}