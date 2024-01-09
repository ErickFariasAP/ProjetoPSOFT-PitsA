package com.ufcg.psoft.commerce.service.pedido;

import org.springframework.stereotype.Service;

@Service
public interface PedidoDeleteService {
    public void deleteCli(Long pedidoId, Long cliId, String codigoAcesso);
    public void deleteEst(Long pedidoId, Long estId, String codigoAcesso);
    public void cliCancelaPedido(Long pedidoId, String codigoAcesso);
    public void deleteAllCliente(Long cliId);
    public void deleteAllEst(Long estId);
}
