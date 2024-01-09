package com.ufcg.psoft.commerce.exception.pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class PedidoInexistenteException extends CommerceException {
    public PedidoInexistenteException() {
        super("O pedido consultado nao existe!");
    }
}
