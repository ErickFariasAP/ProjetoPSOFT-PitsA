package com.ufcg.psoft.commerce.model.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("endereco")
    @NotBlank(message = "Endereco obrigatorio")
    private String endereco;

    @JsonProperty("codigo")
    @NotBlank(message = "Codigo de acesso obrigatorio")
    @Pattern(regexp = "^[0-9]{6}$", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

}
