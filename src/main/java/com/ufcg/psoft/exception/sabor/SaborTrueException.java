package com.ufcg.psoft.commerce.exception.sabor;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class SaborTrueException extends CommerceException {
    public SaborTrueException() {
        super("O sabor consultado ja esta disponivel!");
    }
}
