package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.model.pedido.Pedido;

@FunctionalInterface
public interface PedidoConfirmaEntregaService {
    public Pedido confirmaEntrega(Long pedidoId, Long clienteId, String cliCodAcesso);
    
}
