package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.model.pedido.Pedido;

@FunctionalInterface
public interface PedidoConfirmaService {
    public Pedido confirmaPedido(Long id, String cliCodAcesso);
}
