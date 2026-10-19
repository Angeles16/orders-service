package com.telecomunicaciones.persistencia.repository;

import com.telecomunicaciones.persistencia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrdersById(Long id);
}
