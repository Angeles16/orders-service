package com.telecomunicaciones.services.interfaces;

import com.telecomunicaciones.persistencia.dto.OrderDto;
import com.telecomunicaciones.persistencia.dto.OrderStatus;
import com.telecomunicaciones.persistencia.entity.Order;

import java.util.List;
import java.util.Optional;

public interface IOrder {
    public Order registrOrden(OrderDto orderDto);
    public Order consultarOrden(Long id);
    public List<Order> listarOrdenes();
    public Order actualizarEstado(Long id, OrderStatus newStatus);
}
