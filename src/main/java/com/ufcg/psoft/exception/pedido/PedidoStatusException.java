package com.ufcg.psoft.commerce.exception.pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class PedidoStatusException extends CommerceException {
    public PedidoStatusException() {
        super("O status atual do pedido nao permite realizar essa operacao");
    }
    
}
