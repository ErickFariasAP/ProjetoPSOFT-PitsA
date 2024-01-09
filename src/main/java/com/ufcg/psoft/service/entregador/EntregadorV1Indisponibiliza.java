package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInexistenteException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorV1Indisponibiliza implements EntregadorV1IndisponibilizaService{
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Entregador indisponibiliza(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorInexistenteException::new);

        if(!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }
        entregador.setDisponivel(false);

        List<Associacao> associacoes = entregador.getAssociacoes();
        for (Associacao ass : associacoes) {
            Long estabelecimentoId = ass.getEstabelecimentoId();
            List<Entregador> entregadoresDisponiveis = this.estabelecimentoRepository.findById(estabelecimentoId).get().getEntregadoresDisponiveis();
            entregadoresDisponiveis.remove(entregador);
            this.estabelecimentoRepository.findById(estabelecimentoId).get().setEntregadoresDisponiveis(entregadoresDisponiveis);

        }
        return entregador;

    }
    
}
