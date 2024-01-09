package com.ufcg.psoft.commerce.exception.associacao;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class AssociacaoInvalida extends CommerceException{
    public AssociacaoInvalida() {
        super("Associação passada não é entre esse entregador e esse estabelecimento!");
    }
}