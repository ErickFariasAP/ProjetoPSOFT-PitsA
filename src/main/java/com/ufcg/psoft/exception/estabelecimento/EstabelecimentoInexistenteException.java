package com.ufcg.psoft.commerce.exception.estabelecimento;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class EstabelecimentoInexistenteException extends CommerceException {
    public EstabelecimentoInexistenteException() {
        super("O estabelecimento consultado nao existe!");
    }
}
