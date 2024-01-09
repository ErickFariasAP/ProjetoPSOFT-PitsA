package com.ufcg.psoft.commerce.exception.sabor;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class SaborFalseException extends CommerceException {
    public SaborFalseException() {
        super("O sabor consultado ja esta indisponivel!");
    }
}
