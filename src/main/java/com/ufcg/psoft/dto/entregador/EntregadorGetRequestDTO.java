package com.ufcg.psoft.commerce.dto.entregador;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorGetRequestDTO {
    @JsonProperty("id")
    private Long id;

    //@NotEmpty(message = "Entregador precisa ter um nome")
    @JsonProperty("nome")
    private String nome;

    //@NotEmpty(message = "Entregador precisa da placa do veiculo")
    @JsonProperty("placaVeiculo")
    private String placaVeiculo;

    //@NotEmpty(message = "Entregador precisa da cor do veiculo")
    @JsonProperty("corVeiculo")
    private String corVeiculo;

    //@NotEmpty(message = "Entregador precisa do tipo do veiculo")
    @JsonProperty("tipoVeiculo")
    private String tipoVeiculo;

    @JsonProperty("disponibilidade")
    private boolean disponibilidade;
    @JsonProperty("associacoes")
    private Set<Associacao> associacoes;
    @JsonProperty("statusAprovacao")
    private boolean statusAprovacao;

   public EntregadorGetRequestDTO(Entregador entregador) {
       this.id = entregador.getId();
       this.nome = entregador.getNome();
       this.placaVeiculo = entregador.getPlacaVeiculo();
       this.corVeiculo = entregador.getCorVeiculo();
       this.tipoVeiculo = entregador.getTipoVeiculo();
    }
}
