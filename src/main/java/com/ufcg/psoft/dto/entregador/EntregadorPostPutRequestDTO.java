package com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EntregadorPostPutRequestDTO {
    //private Long id;
    @JsonProperty("nome")
    @NotBlank(message = "Entregador precisa ter um nome")
    private String nome;

    @JsonProperty("placaVeiculo")
    @NotBlank(message = "Entregador precisa da placa do veiculo")
    private String placaVeiculo;

    @JsonProperty("corVeiculo")
    @NotBlank(message = "Entregador precisa da cor do veiculo")
    private String corVeiculo;

    @JsonProperty("tipoVeiculo")
    @NotBlank(message = "Entregador precisa do tipo do veiculo")
    private String tipoVeiculo;

    @JsonProperty("codigoAcesso")
    @NotBlank(message = "Entregador precisa ter um codigo")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;
    
    @JsonProperty("disponibilidade")
    private boolean disponibilidade;
    @JsonProperty("statusAprovacao")
    private boolean statusAprovacao;
}
