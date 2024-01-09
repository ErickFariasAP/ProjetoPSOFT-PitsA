package com.ufcg.psoft.commerce.model.estabelecimento;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.associacao.Associacao;
import com.ufcg.psoft.commerce.model.entregador.Entregador;
import com.ufcg.psoft.commerce.model.sabor.Sabor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "estabelecimentos")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;

    @OneToMany(mappedBy = "estabelecimentoId")
    private Set<Associacao> associacoes;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Sabor> sabores;

    //TO DO
    @OneToMany(mappedBy = "placaVeiculo")
    private List<Entregador> entregadoresDisponiveis;
}
