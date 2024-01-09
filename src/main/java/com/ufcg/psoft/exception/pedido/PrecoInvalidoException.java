package com.ufcg.psoft.commerce.exception.pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class PrecoInvalidoException extends CommerceException {
    public PrecoInvalidoException() {
        super("O preco nao esta batendo");
    }
    
}
