package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;

@FunctionalInterface
public interface ClienteInteresseService {
public SaborPostPutRequestDTO updateInteresse(Long id, String codInserido, Long saborId);
}
