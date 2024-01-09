package com.ufcg.psoft.commerce.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.cliente.ClienteInexistenteException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoInexistenteException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoStatusException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;
import com.ufcg.psoft.commerce.repository.pedido.PedidoRepository;

@Service
public class PedidoConfirmaEntregaV1Service implements PedidoConfirmaEntregaService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Pedido confirmaEntrega(Long pedidoId, Long clienteId,String cliCodAcesso) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(PedidoInexistenteException::new);
        //Estabelecimento pedidoEstab = estabelecimentoRepository.findById(pedido.getEstabelecimentoId()).get();
        Cliente pedidoCliente = clienteRepository.findById(clienteId).orElseThrow(ClienteInexistenteException::new);
        if (!pedidoCliente.getCodigoAcesso().equals(cliCodAcesso)) {
            throw new CodigoInvalidoException();
        }
        if (!pedido.getStatusEntrega().equals("Pedido em rota")) {
            throw new PedidoStatusException();
        }
        pedido.setStatusEntrega("Pedido entregue");

        // String notif = "PEDIDO #" + pedido.getId() + " ENTREGUE";
        // notif += "\n Estabelecimento: " + pedidoEstab.getId() + " - " + pedidoEstab.getNome();
        // System.out.println(notif);
        
        return pedido;
    }
}
