package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EntregadorGetService {
    //public Entregador get(Long id);
    public EntregadorGetRequestDTO get(Long id);

    //public List<Entregador> getAll();
    public List<EntregadorGetRequestDTO> getAll();
}
