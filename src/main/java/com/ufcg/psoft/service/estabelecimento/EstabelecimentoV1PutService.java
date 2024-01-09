package com.ufcg.psoft.commerce.service.estabelecimento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.estabelecimento.EstabelecimentoInexistenteException;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1PutService implements EstabelecimentoPutService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento put(Long id, EstabelecimentoPostPutRequestDTO estabelecimentoPostPut, String codigoAcesso){
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoInexistenteException::new);

        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }

        modelMapper.map(estabelecimentoPostPut, estabelecimento); 
        return estabelecimentoRepository.save(estabelecimento);
    }
}
