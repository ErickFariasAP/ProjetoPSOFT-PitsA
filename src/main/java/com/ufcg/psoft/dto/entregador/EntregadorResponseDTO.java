package com.ufcg.psoft.commerce.dto.entregador;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.associacao.Associacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("placaVeiculo")
    private String placaVeiculo;
    @JsonProperty("corVeiculo")
    private String corVeiculo;
    @JsonProperty("tipoVeiculo")
    private String tipoVeiculo;
    @JsonProperty("disponibilidade")
    private boolean disponibilidade;
    @JsonProperty("associacoes")
    private Set<Associacao> associacoes;
    @JsonProperty("codigoAcesso")
    @NotBlank(message = "Entregador precisa ter um codigo")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;
    @JsonProperty("statusAprovacao")
    private boolean statusAprovacao;
}
