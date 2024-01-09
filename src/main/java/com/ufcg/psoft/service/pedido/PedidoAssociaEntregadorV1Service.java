package com.ufcg.psoft.commerce.service.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.associacao.EstabelecimentoInexistente;
import com.ufcg.psoft.commerce.exception.pedido.PedidoInexistenteException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoStatusException;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.estabelecimento.Estabelecimento;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.estabelecimento.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.pedido.PedidoRepository;

@Service
public class PedidoAssociaEntregadorV1Service implements PedidoAssociaEntregadorService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Pedido associaEntregador(Long pedidoId, Long estId, String estCodAcesso) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(PedidoInexistenteException::new);
        Estabelecimento est = estabelecimentoRepository.findById(estId).orElseThrow(EstabelecimentoInexistente::new);
        if (!est.getCodigoAcesso().equals(estCodAcesso)) {
            throw new CodigoInvalidoException();
        }
        if (!pedido.getStatusEntrega().equals("Pedido pronto")) {
            throw new PedidoStatusException();
        }
        List<Entregador> entregadoresDisponiveis = est.getEntregadoresDisponiveis();
        if (entregadoresDisponiveis.isEmpty()) {
            String notif = "PEDIDO #" + pedido.getId() + " NAO PODE SER ENTREGUE";
            notif += "\n Estamos sem entregador no momento";
            notif += "\n Cliente: " + pedido.getClienteId();
            System.out.print(notif);
        } else {
            pedido.setEntregadorId(entregadoresDisponiveis.get(0).getId());
            pedido.setStatusEntrega("Pedido em rota");
        }
        return pedido;
    }
    
}
