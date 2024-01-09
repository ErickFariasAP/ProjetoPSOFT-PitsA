package com.ufcg.psoft.commerce.exception.sabor;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class SaborInexistenteException extends CommerceException {
    public SaborInexistenteException() {
        super("O sabor consultado nao existe!");
    }
}
