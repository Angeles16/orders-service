package com.telecomunicaciones.persistencia.repository;

import com.telecomunicaciones.persistencia.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}
