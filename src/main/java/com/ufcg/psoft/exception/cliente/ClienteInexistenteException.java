package com.ufcg.psoft.commerce.exception.cliente;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class ClienteInexistenteException extends CommerceException {
    public ClienteInexistenteException() {
        super("O cliente consultado nao existe!");
    }
}
