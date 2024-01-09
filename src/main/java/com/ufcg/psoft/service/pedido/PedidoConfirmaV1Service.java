package com.ufcg.psoft.commerce.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoInvalidoException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoInexistenteException;
import com.ufcg.psoft.commerce.exception.pedido.PedidoStatusException;
import com.ufcg.psoft.commerce.model.cliente.Cliente;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.repository.cliente.ClienteRepository;
import com.ufcg.psoft.commerce.repository.pedido.PedidoRepository;

@Service
public class PedidoConfirmaV1Service implements PedidoConfirmaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Override
    public Pedido confirmaPedido(Long id, String cliCodAcesso) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(PedidoInexistenteException::new);
        Cliente pedidoCliente = clienteRepository.findById(pedido.getClienteId()).get();
        if (!pedidoCliente.getCodigoAcesso().equals(cliCodAcesso)) {
            throw new CodigoInvalidoException();
        }
        if (!pedido.getStatusEntrega().equals("Pedido recebido")) {
            throw new PedidoStatusException();
        }
        pedido.setStatusEntrega("Pedido em preparo");
        return pedido;
    }
    
}
