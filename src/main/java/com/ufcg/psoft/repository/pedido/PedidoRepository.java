package com.ufcg.psoft.commerce.repository.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufcg.psoft.commerce.model.pedido.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
