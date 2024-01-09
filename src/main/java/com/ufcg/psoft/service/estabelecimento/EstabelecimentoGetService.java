package com.ufcg.psoft.commerce.service.estabelecimento;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.model.sabor.Sabor;

@Service
public interface EstabelecimentoGetService {
    public EstabelecimentoResponseDTO get(Long id);

    public Set<Sabor> getCardapio(Long id);
    
    public List<EstabelecimentoResponseDTO> getAll();

    public Set<Sabor> getCardapioPorTipo(Long id, String tipo);
}
