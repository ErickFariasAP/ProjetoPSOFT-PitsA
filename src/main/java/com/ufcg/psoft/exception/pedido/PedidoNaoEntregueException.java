package com.ufcg.psoft.commerce.exception.pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class PedidoNaoEntregueException extends CommerceException {
    public PedidoNaoEntregueException() {
        super("O pedido em questao ainda nao foi entregue");
    }
    
}
