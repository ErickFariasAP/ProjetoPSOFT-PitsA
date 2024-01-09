package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.model.associacao.Associacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInexistenteException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorV1DisponibilizaService implements EntregadorDisponibilizaService{

    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    // seta a disponibilidade do entregador para poder realizar entregas
    @Override
    public Entregador disponibiliza(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorInexistenteException::new);
        // TODO 
        // ENTREGADOR TEM QUE ITERAR POR SEUS ESTABELECIMENTOS E IR COLOCANDO ELE COMO DISPONIVEL NAS
        // LISTAS DE ENTREGADORES DISPONIVEIS EM CADA UM DOS ESTABELECIMENTOS
        //Long estabelecimentoId = associacaoAprovaDTO.getEstabelecimentoId();
        //Long entregadorId = associacaoAprovaDTO.getEntregadorId();

        if(!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoInvalidoException();
        }
        entregador.setDisponivel(true);

        List<Associacao> associacoes = entregador.getAssociacoes();
        for (Associacao ass : associacoes) {
            Long estabelecimentoId = ass.getEstabelecimentoId();
            List<Entregador> entregadoresDisponiveis = this.estabelecimentoRepository.findById(estabelecimentoId).get().getEntregadoresDisponiveis();
            entregadoresDisponiveis.add(entregador);
            this.estabelecimentoRepository.findById(estabelecimentoId).get().setEntregadoresDisponiveis(entregadoresDisponiveis);

        }
        return entregador;

    }
    
}
