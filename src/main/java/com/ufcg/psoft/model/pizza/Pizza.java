package com.ufcg.psoft.commerce.model.pizza;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.pedido.Pedido;
import com.ufcg.psoft.commerce.model.sabor.Sabor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pizzas")
public class Pizza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("pedido")
    @ManyToOne
    @JoinColumn(name="id_pedido")
    @JsonBackReference
    private Pedido pedido;

    @JsonProperty("sabor1")
    @ManyToOne()
    @JoinColumn(name = "id_sabor1")
    private Sabor sabor1;

    @JsonProperty("sabor2")
    @ManyToOne()
    @JoinColumn(name = "id_sabor2")
    private Sabor sabor2;

    @JsonProperty("tamanho")
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(media|grande)$", message = "Tamanho deve ser media ou grande")
    private String tamanho;
}
