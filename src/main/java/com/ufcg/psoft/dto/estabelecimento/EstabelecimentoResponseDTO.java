package com.ufcg.psoft.commerce.dto.estabelecimento;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.sabor.Sabor;

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
public class EstabelecimentoResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("sabores")
    private Set<Sabor> sabores;

    @NotBlank(message = "Estabelecimento precisa ter um codigo")
    @JsonProperty("codigoAcesso")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    @JsonProperty("associacoes")
    private Set<Associacao> associacoes;

    @JsonProperty("entregadoresDisponiveis")
    private List<Entregador> entregadoresDisponiveis;
}
