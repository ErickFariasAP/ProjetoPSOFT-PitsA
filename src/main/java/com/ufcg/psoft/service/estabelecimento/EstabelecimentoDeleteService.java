package com.ufcg.psoft.commerce.service.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoDeleteService {
    public void delete(Long id, String codigo);
}
