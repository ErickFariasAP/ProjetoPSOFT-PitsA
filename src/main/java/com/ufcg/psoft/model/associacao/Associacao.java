package com.ufcg.psoft.commerce.model.associacao;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "associacoes")
public class Associacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("entregadorId")
    private Long entregadorId;

    @JsonProperty("estabelecimentoId")
    private Long estabelecimentoId;

    @JsonProperty("status")
    @Builder.Default
    private boolean status = false;
}
