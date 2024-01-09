package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.model.pedido.Pedido;

@FunctionalInterface
public interface PedidoAssociaEntregadorService {
    public Pedido associaEntregador(Long pedidoId, Long estId, String estCodAcesso); 
}
