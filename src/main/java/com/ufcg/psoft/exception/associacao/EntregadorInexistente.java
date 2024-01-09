package com.ufcg.psoft.commerce.exception.associacao;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class EntregadorInexistente extends CommerceException{
    public EntregadorInexistente() {
        super("O entregador consultado nao existe!");
    }
}