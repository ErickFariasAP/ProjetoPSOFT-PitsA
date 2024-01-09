package com.ufcg.psoft.commerce.exception.associacao;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class AssociacaoExistente extends CommerceException{
    public AssociacaoExistente() {
        super("Associacao ja existe");
    }
    
}