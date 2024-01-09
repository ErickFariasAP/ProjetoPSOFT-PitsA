package com.ufcg.psoft.commerce.model.sabor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.sabor.Sabor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sabores")
public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("tipo")
    @NotBlank(message = "Tipo obrigatorio")
    @Pattern(regexp = "^(salgado|doce)$", message = "Tipo deve ser salgado ou doce")
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
    @Builder.Default
    private Boolean disponibilidade = true;

    @JsonProperty("clientesInteressados")
    @Builder.Default
    private List<Long> clientesInteressados = new ArrayList<>();

}
