package com.ufcg.psoft.commerce.service.entregador;


import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInexistenteException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1DeleteService implements EntregadorDeleteService {
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Override
    public void delete(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorInexistenteException::new);
        if(!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }

        entregadorRepository.delete(entregador);
    }
}
