package com.ufcg.psoft.commerce.service.entregador;


@FunctionalInterface
public interface EntregadorDeleteService {
    void delete(Long id, String codigoAcesso);
}
