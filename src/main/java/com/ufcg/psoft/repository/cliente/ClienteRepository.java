package com.ufcg.psoft.commerce.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.commerce.model.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
