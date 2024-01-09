package com.ufcg.psoft.commerce.dto.associacao;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociacaoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "entregador_id")
    private Long entregadorId;
    
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Long estabelecimentoId;
}
