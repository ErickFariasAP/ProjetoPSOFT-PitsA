package com.ufcg.psoft.commerce.service.associacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.commerce.dto.associacao.AssociacaoDTO;
import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.associacao.*;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.associacao.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;

import java.util.List;

@Service
public class AssociacaoAprovaV1Service implements AssociacaoAprovaService{
    @Autowired
    private AssociacaoRepository associacaoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private EntregadorRepository entregadorRepository;
    

    public Associacao aprovaAssociacao(Long id, String codigoAcesso, AssociacaoDTO associacaoAprovaDTO) {
        Long estabelecimentoId = associacaoAprovaDTO.getEstabelecimentoId();
        Long entregadorId = associacaoAprovaDTO.getEntregadorId(); 
        
        
        // verifica se o estabelecimento existe
        if(!this.estabelecimentoRepository.existsById(estabelecimentoId)){
            throw new EstabelecimentoInexistente();
        }
        
        // verifica se o entregador existe
        if(!this.entregadorRepository.existsById(entregadorId)){
            throw new EntregadorInexistente();
        }
        
        String codigoDeAcesso = this.estabelecimentoRepository.findById(estabelecimentoId).get().getCodigoAcesso();
        // verifica se o código de acesso está correto
        if(!codigoDeAcesso.equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }
        // verifica se a associação existe
        if(!this.associacaoRepository.existsById(id)) {
            throw new AssociacaoInexistente();
        }

        Associacao associacao = this.associacaoRepository.findById(id).get();
        // verifica se a associação é válida
        if(associacao.getEstabelecimentoId() != estabelecimentoId || associacao.getEntregadorId() != entregadorId) {
            throw new AssociacaoInvalida();
        }

        // muda o status e da associação 
        Entregador entregador = entregadorRepository.findById(entregadorId).get();
        associacao.setStatus(true);
        entregador.setDisponivel(false);
        // atualiza o entregador para guardar a nova associação que foi aprovada
        List<Associacao> associacoes = entregador.getAssociacoes();
        associacoes.add(associacao);
        entregador.setAssociacoes(associacoes);
        return this.associacaoRepository.save(associacao);
    }
}