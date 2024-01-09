package com.ufcg.psoft.commerce.dto.sabor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborPostPutDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("tipo")
    @NotBlank(message = "Tipo obrigatorio")
    private String tipo;

    @JsonProperty("precoM")
    @NotNull(message = "PrecoM obrigatorio")
    @Positive(message = "PrecoM deve ser maior que zero")
    private Double precoM;

    @JsonProperty("precoG")
    @NotNull(message = "PrecoG obrigatorio")
    @Positive(message = "PrecoG deve ser maior que zero")
    private Double precoG;

    @JsonProperty("disponibilidade")
    @NotNull(message = "Disponibilidade obrigatoria")
    private Boolean disponibilidade;

    @JsonProperty("clientesInteressados")
    private List<Long> clientesInteressados;
}
