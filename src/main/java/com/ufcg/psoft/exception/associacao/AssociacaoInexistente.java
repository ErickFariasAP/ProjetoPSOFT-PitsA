package com.ufcg.psoft.commerce.exception.associacao;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class AssociacaoInexistente extends CommerceException{
    public AssociacaoInexistente() {
        super("Id da associacao inexistente!");
    }
}