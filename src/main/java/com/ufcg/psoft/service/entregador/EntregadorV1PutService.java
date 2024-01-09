package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInexistenteException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1PutService implements EntregadorPutService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Entregador put(Long id, String codigoAcesso, EntregadorPostPutRequestDTO entregadorPostPutDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorInexistenteException::new);

        if(!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }

        modelMapper.map(entregadorPostPutDTO, entregador);
        return entregadorRepository.save(entregador);

//        Entregador newEntregador = this.entregadorRepository.findById(id).get()
//        newEntregador.setNome(entregadorPutDTO.getNome());
//        newEntregador.setPlacaVeiculo(entregadorPutDTO.getPlacaVeiculo());
//        newEntregador.setTipoVeiculo(entregadorPutDTO.getTipoVeiculo());
//        newEntregador.setCorVeiculo(entregadorPutDTO.getCorVeiculo());
//        return this.entregadorRepository.save(newEntregador);
    }
}
