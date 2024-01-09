package com.ufcg.psoft.commerce.service.associacaoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.associacao.EntregadorInexistente;
import com.ufcg.psoft.commerce.exception.associacao.EstabelecimentoInexistente;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.repository.associacao.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

@Service
public class AssociacaoPostV1Service implements AssociacaoPostService{

    @Autowired
    private AssociacaoRepository associacaoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Associacao criaAssociacao(String codigoAcesso, AssociacaoDTO associacaoDTO) {
        Long estabelecimentoId = associacaoDTO.getEstabelecimentoId();
        Long entregadorId = associacaoDTO.getEntregadorId();   

        
        // verifica se o estabelecimento existe
        if(!this.estabelecimentoRepository.existsById(estabelecimentoId)){
            throw new EstabelecimentoInexistente();
        }   
        
        // verifica se o entregador existe
        if(!this.entregadorRepository.existsById(entregadorId)){
            throw new EntregadorInexistente();
        }
        
        String codigoDeAcesso = this.entregadorRepository.findById(entregadorId).get().getCodigoAcesso();
        // verifica se o código de acesso está correto
        if(!codigoDeAcesso.equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }
        return this.associacaoRepository.save(
            modelMapper.map(associacaoDTO, Associacao.class)
        );

    }

    
}