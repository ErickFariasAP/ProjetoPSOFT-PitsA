package com.ufcg.psoft.commerce.dto.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pizza.Pizza;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PedidoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("pizzas")
    @NotNull(message = "A listagem de pedidos nao pode ser null.")
    @NotEmpty(message = "A listagem de pedidos nao pode estar vazia.")
    private List<Pizza> pizzas;

    @JsonProperty("endereco")
    private String enderecoEntrega;

    @JsonProperty("estabelecimento")
    private Long estabelecimentoId;

    @JsonProperty("cliente")
    private Long clienteId;

    @JsonProperty("entregador")
    private Long entregadorId;

    @JsonProperty("statusEntrega")
    private String statusEntrega;

    @JsonProperty("meioPagamento")
    private String meioPagamento;

    @JsonProperty("statusPagamento")
    private boolean statusPagamento;

    @JsonProperty("preco")
    private Double preco;
}
