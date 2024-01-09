package com.ufcg.psoft.commerce.model.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pizza.Pizza;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pedidos")
public class Pedido {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("preco")
    @JoinColumn(name = "preco")
    private Double preco;

    @JsonProperty("endereco")
    @JoinColumn(name = "endereco")
    private String enderecoEntrega;

    @JsonProperty("estabelecimento")
    @NotNull
    private Long estabelecimentoId;

    @JsonProperty("cliente")
    @NotNull
    private Long clienteId;

    @JsonProperty("entregador")
    private Long entregadorId;

    @JsonProperty("pizzas")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonManagedReference
    @NotEmpty
    @NotNull
    private List<Pizza> pizzas;

    @JsonProperty("statusEntrega")
    private String statusEntrega;

    @JsonProperty("statusPagamento")
    private boolean statusPagamento;

    @JsonProperty("meioPagamento")
    @Pattern(regexp = "^(pix|cartao de credito|cartao de debito)$", message = "Tipo de pagamento deve ser valido")
    private String meioPagamento;
}