package com.ufcg.psoft.commerce.controller.pedido;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.pedido.PedidoAssociaEntregadorService;
import com.ufcg.psoft.commerce.service.pedido.PedidoConfirmaEntregaService;
import com.ufcg.psoft.commerce.service.pedido.PedidoConfirmaService;
import com.ufcg.psoft.commerce.service.pedido.PedidoDeleteService;
import com.ufcg.psoft.commerce.service.pedido.PedidoEncaminhaService; 
import com.ufcg.psoft.commerce.service.pedido.PedidoEntregueEstabelecimentoService;
import com.ufcg.psoft.commerce.service.pedido.PedidoGetService;
import com.ufcg.psoft.commerce.service.pedido.PedidoNotificaService;
import com.ufcg.psoft.commerce.service.pedido.PedidoPostService;
import com.ufcg.psoft.commerce.service.pedido.PedidoProntificaService;
import com.ufcg.psoft.commerce.service.pedido.PedidoPutPagamentoService;
import com.ufcg.psoft.commerce.service.pedido.PedidoPutService;

import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE

)
public class PedidoRestController {

    @Autowired
    PedidoPostService pedidoPostService;

    @Autowired
    PedidoPutService pedidoPutService;

    @Autowired
    PedidoPutPagamentoService pedidoPutPagamentoService;

    @Autowired
    PedidoGetService pedidoGetService;

    @Autowired
    PedidoDeleteService pedidoDeleteService;

    @Autowired
    PedidoConfirmaService pedidoConfirmaService;

    @Autowired
    PedidoProntificaService pedidoProntificaService;

    @Autowired
    PedidoNotificaService pedidoNotificaService;

    @Autowired
    PedidoConfirmaEntregaService pedidoConfirmaEntregaService;

    @Autowired
    PedidoAssociaEntregadorService pedidoAssociaEntregadorService;

    @Autowired
    PedidoEntregueEstabelecimentoService pedidoEntregueEstabelecimentoService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestParam(value = "clienteId") Long cliId, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoDeAcesso, @RequestParam(value = "estabelecimentoId") Long estId, @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.pedidoPostService.post(cliId, cliCodigoDeAcesso, estId, pedidoPostPutRequestDTO));
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestParam Long pedidoId, @RequestParam String codigoAcesso, @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(pedidoPutService.put(pedidoId, codigoAcesso, pedidoPostPutRequestDTO));
    }

    //PUT DE CONFIRMA PAGAMENTO COM LOGICA DE DESCONTO
    @PutMapping("/{cliId}/confirmar-pagamento")
    public ResponseEntity<?> confirmaPagamento(@PathVariable Long cliId, @RequestParam(value = "codigoAcessoCliente") String cliCodAcesso, @RequestParam(value = "pedidoId") Long pedidoId, @RequestParam(value = "metodoPagamento") String metodoPagamento, @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(pedidoPutPagamentoService.putPagamento(cliId, cliCodAcesso, pedidoId, metodoPagamento, pedidoPostPutRequestDTO));
    }
    

    //DELETE DE QUANDO O CLIENTE EXCLUI PEDIDO FEITO POR ELE SALVO
    @DeleteMapping("/{pedidoId}/{cliId}")
    public ResponseEntity<?> deleteCli(@PathVariable Long pedidoId, @PathVariable Long cliId, @RequestParam String codigoAcesso) {
        this.pedidoDeleteService.deleteCli(pedidoId, cliId, codigoAcesso);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("");
    }

    //DELETE DE QUANDO O ESTABELECIMETNO EXCLUI UM PEDIDO NELE SALVO
    @DeleteMapping("/{pedidoId}/{estId}/{estCodAcesso}")
    public ResponseEntity<?> deleteEst(@PathVariable Long pedidoId, @PathVariable Long estId, @PathVariable String estCodAcesso) {
        this.pedidoDeleteService.deleteEst(pedidoId, estId, estCodAcesso);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("");
    }

    //DELETE DE QUANDO UM CLIENTE CANCELA UM PEDIDO
    @DeleteMapping("/{pedidoId}/cancelar-pedido")
    public ResponseEntity<?> cancelaPedido(@PathVariable Long pedidoId, @RequestParam(value = "clienteCodigoAcesso") String cliCodAcesso) {
        this.pedidoDeleteService.cliCancelaPedido(pedidoId, cliCodAcesso);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("");
    }

    //DELETE DE TODOS OS PEDIDOS DE UM CLIENTE
    @DeleteMapping
    public ResponseEntity<?> deleteAllCliente(@RequestParam(value = "clienteId") Long cliId) {
        this.pedidoDeleteService.deleteAllCliente(cliId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("");
    }

    //DELTE DE TODOS OS PEDIDOS DE UM ESTABELECIMENTO
    @DeleteMapping("/{estId}")
    public ResponseEntity<?> deleteAllEst(@PathVariable Long estId) {
        this.pedidoDeleteService.deleteAllEst(estId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("");
    }

    //GET PARA PEGAR TODOS OS PEDIDOS DE CLIENTE E/OU ESTABELECIMENTO
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAll(@RequestParam(value = "clienteId",required = false) Long cliId, @RequestParam(value = "estabelecimentoId", required = false) Long estId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getAll(cliId, estId));
    }

    //GET PARA PEGAR UM PEDIDO DE UM CLIENTE
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> get(@PathVariable Long id, @RequestParam(value = "clienteId") Long cliId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getFromClient(id, cliId));
    }

    //GET PARA PEGAR UM PEDIDO DE UM ESTABELECIMENTO
    @GetMapping("/{id}/{estId}/{codigoAcesso}")
    public ResponseEntity<PedidoResponseDTO> get(@PathVariable Long id, @PathVariable Long estId, @PathVariable String codigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getFromEstabelecimento(id, estId, codigoAcesso));
    }
    
    //GET PARA O CLIENTE PEGAR UM PEDIDO DELE EM UM ESTABELECIMENTO
    @GetMapping("/pedido-cliente-estabelecimento/{cliId}/{estId}/{pedidoId}")
    public ResponseEntity<List<PedidoResponseDTO>> getFromClienteAndEstabelecimento(@PathVariable Long cliId, @PathVariable Long estId, @PathVariable Long pedidoId, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getFromClienteAndEstabelecimento(pedidoId, cliId, estId, cliCodigoAcesso));
    }

    //GET PARA O CLIENTE PEGAR OS PEDIDOS DELE EM UM ESTABELECIMENTO
    @GetMapping("/pedidos-cliente-estabelecimento/{cliId}/{estId}")
    public ResponseEntity<List<PedidoResponseDTO>> getFromClienteAndEstabelecimento(@PathVariable Long cliId, @PathVariable Long estId, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getAllFromClient(cliId, estId, cliCodigoAcesso));
    }

    //GET PARA O CLIENTE PEGAR OS PEDIDOS DELE EM UM ESTABELECIMENTO COM UM DETERMINADO STATUS DE ENTREGA
    @GetMapping("/pedidos-cliente-estabelecimento/{cliId}/{estId}/{statusEntrega}")
    public ResponseEntity<List<PedidoResponseDTO>> getFromClienteAndEstabelecimento(@PathVariable Long cliId, @PathVariable Long estId, @PathVariable String statusEntrega, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoGetService.getAllWithStatus(cliId, estId, statusEntrega, cliCodigoAcesso));
    }

    //PATCH PARA O CLIENTE CONFIRMAR UM PEDIDO
    @PutMapping("/{pedidoId}/confirmar-pedido")
    public ResponseEntity<?> confirmarPedido(@PathVariable Long pedidoId, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoConfirmaService.confirmaPedido(pedidoId, cliCodigoAcesso));
    }

    //PATCH PARA O ESTABELECIMENTO PRONTIFICAR PEDIDO
    @PutMapping("/{pedidoId}/prontificar-pedido")
    public ResponseEntity<?> prontificarPedido(@PathVariable Long pedidoId, @RequestParam(value = "estabelecimentoCodigoAcesso") String estCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoProntificaService.prontificaPedido(pedidoId, estCodigoAcesso));
    }

    //PATCH PARA O ENTREGADOR ENCAMINHAR PEDIDO
    @PutMapping("/{pedidoId}/encaminhar-pedido")
    public ResponseEntity<?> encaminharPedido(@PathVariable Long pedidoId, @RequestParam(value = "estabelecimentoCodigoAcesso") String estCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoNotificaService.notificaPedido(pedidoId, estCodigoAcesso, "A CAMINHO"));
    }

    //PATCH PARA O CLIENTE CONFIRMAR ENTREGA
    @PutMapping("/{pedidoId}/{clienteId}/cliente-confirmar-entrega")
    public ResponseEntity<?> confirmarEntrega(@PathVariable Long pedidoId, @PathVariable Long clienteId, @RequestParam(value = "clienteCodigoAcesso") String cliCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoConfirmaEntregaService.confirmaEntrega(pedidoId, clienteId, cliCodigoAcesso));
    }

    //put para associar entregador a pedido
    @PutMapping("/{pedidoId}/associar-pedido-entregador")
    public ResponseEntity<?> associaPedidoEntregador(@PathVariable Long pedidoId, @RequestParam(value = "estabelecimentoId") Long estId, @RequestParam(value = "estabelecimentoCodigoAcesso") String estCodigoAcesso) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.pedidoAssociaEntregadorService.associaEntregador(pedidoId, estId, estCodigoAcesso));
    }

    @PutMapping("/{pedidoId}/notificar-estabelecimento")
        public ResponseEntity<?> notificaPedidoEntregueEstabelecimento (@PathVariable Long pedidoId) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pedidoEntregueEstabelecimentoService.notificaEntregaEstabelecimento(pedidoId));
        }
    }

