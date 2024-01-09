package com.ufcg.psoft.commerce.service.estabelecimento;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoInexistenteException;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;



@Service
public class EstabelecimentoV1DeleteService implements EstabelecimentoDeleteService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void delete(Long id, String codInserido){
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoInexistenteException::new);

        if (!estabelecimento.getCodigoAcesso().equals(codInserido)) {
            throw new CodigoInvalidoException();
        }

        estabelecimentoRepository.delete(estabelecimento);
    }
}
