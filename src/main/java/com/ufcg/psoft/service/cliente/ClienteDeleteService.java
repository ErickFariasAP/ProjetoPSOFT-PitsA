package com.ufcg.psoft.commerce.service.cliente;

@FunctionalInterface
public interface ClienteDeleteService {

    public void delete(Long id, String codInserido);
}
