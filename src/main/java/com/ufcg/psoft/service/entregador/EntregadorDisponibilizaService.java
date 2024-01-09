package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.model.entregador.Entregador;

public interface EntregadorDisponibilizaService {
    public Entregador disponibiliza(Long id, String codigoAcesso);
}
