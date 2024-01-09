package com.ufcg.psoft.commerce.exception.entregador;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class EntregadorInexistenteException extends CommerceException {
    public EntregadorInexistenteException() {
        super("O entregador consultado nao existe!");
    }
}
