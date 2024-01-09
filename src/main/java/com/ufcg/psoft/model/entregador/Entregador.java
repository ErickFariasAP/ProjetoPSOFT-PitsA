package com.ufcg.psoft.commerce.model.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.associacao.Associacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "entregadores")
public class Entregador {

    //@JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @JsonProperty("placaVeiculo")
    @NotBlank(message = "Placa do veiculo e obrigatoria")
    private String placaVeiculo;

    @NotBlank(message = "Cor do veiculo e obrigatoria")
    @JsonProperty("corVeiculo")
    private String corVeiculo;

    @JsonProperty("tipoVeiculo")
    @NotBlank(message = "Tipo do veiculo e obrigatorio")
    @Pattern(regexp = "^(moto|carro)$", message = "Tipo do veiculo deve ser moto ou carro")
    private String tipoVeiculo;

    @JsonProperty("codigoAcesso")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    //COISA NOVA ABAIXO
    @OneToMany(mappedBy = "entregadorId")
    private List<Associacao> associacoes;

    @JsonProperty("disponibilidade")
    private boolean disponivel;

    @JsonProperty("statusAprovacao")
    private boolean statusAprovacao;
}
