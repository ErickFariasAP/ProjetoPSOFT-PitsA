package com.ufcg.psoft.commerce.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientePostPutRequestDTO {

    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    String nome;

    @JsonProperty("endereco")
    @NotBlank(message = "Endereco obrigatorio")
    String endereco;

    @JsonProperty("codigo")
    @NotBlank(message = "Codigo de acesso obrigatorio")
    @Pattern(regexp = "^[0-9]{6}$", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    String codigoAcesso;
    
}
