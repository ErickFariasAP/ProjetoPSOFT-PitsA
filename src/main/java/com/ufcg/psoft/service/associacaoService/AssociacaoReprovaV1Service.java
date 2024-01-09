package com.ufcg.psoft.commerce.service.associacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.associacao.AssociacaoInexistente;
import com.ufcg.psoft.commerce.exception.associacao.AssociacaoInvalida;
import com.ufcg.psoft.commerce.exception.associacao.EntregadorInexistente;
import com.ufcg.psoft.commerce.exception.associacao.EstabelecimentoInexistente;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.repository.associacao.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

@Service
public class AssociacaoReprovaV1Service implements AssociacaoReprovaService{

    @Autowired
    private AssociacaoRepository associacaoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private EntregadorRepository entregadorRepository;

    
    
    @Override
    public void reprovaAssociacao(Long id, String codigoAcesso, AssociacaoDTO associacaoReprovaDTO) {
        Long estabelecimentoId = associacaoReprovaDTO.getEstabelecimentoId();
        Long entregadorId = associacaoReprovaDTO.getEntregadorId(); 

        // verifica se a associação existe
        if(!this.associacaoRepository.existsById(id)) {
            throw new AssociacaoInexistente();
        }
        Associacao associacao = this.associacaoRepository.findById(id).get();

         // verifica se o estabelecimento existe
        if(!this.estabelecimentoRepository.existsById(estabelecimentoId)){
            throw new EstabelecimentoInexistente();
        }

        // verifica se o entregador existe
        if(!this.entregadorRepository.existsById(entregadorId)){
            throw new EntregadorInexistente();
        }

        // verifica se a associação é válida
        if(associacao.getEstabelecimentoId() != estabelecimentoId || associacao.getEntregadorId() != entregadorId) {
            throw new AssociacaoInvalida();
        }

        String codigoDeAcesso = this.estabelecimentoRepository.findById(estabelecimentoId).get().getCodigoAcesso();
        if(!codigoDeAcesso.equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }

        this.associacaoRepository.deleteById(id);

    }
    
}