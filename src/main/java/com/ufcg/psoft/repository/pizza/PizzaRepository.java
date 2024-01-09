package com.ufcg.psoft.commerce.repository.pizza;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.commerce.model.pizza.Pizza;

public interface  PizzaRepository extends JpaRepository<Pizza, Long> {
    
}
