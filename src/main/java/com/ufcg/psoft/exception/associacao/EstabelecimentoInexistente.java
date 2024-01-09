package com.ufcg.psoft.commerce.exception.associacao;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class EstabelecimentoInexistente extends CommerceException{
    public EstabelecimentoInexistente() {
        super("O estabelecimento consultado nao existe!");
    }
}