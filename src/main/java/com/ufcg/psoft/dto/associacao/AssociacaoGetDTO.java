package com.ufcg.psoft.commerce.dto.associacao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoGetDTO {
    
    @JsonProperty("id")
    private Long id;

    @JsonProperty("entregadorId")
    private Long entregadorId;

    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @JsonProperty("status")
    private boolean status;
}
