package com.ufcg.psoft.commerce.exception.pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class PedidoJaProntoException extends CommerceException {
    public PedidoJaProntoException() {
        super("Voce nao pode cancelar um pedido pronto!");
    }
}
