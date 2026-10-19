package com.telecomunicaciones.services.implement;

import com.telecomunicaciones.persistencia.dto.OrderDto;
import com.telecomunicaciones.persistencia.dto.OrderStatus;
import com.telecomunicaciones.persistencia.entity.Order;
import com.telecomunicaciones.persistencia.entity.OrderHistory;
import com.telecomunicaciones.persistencia.repository.OrderRepository;
import com.telecomunicaciones.services.interfaces.IOrder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrder {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order registrOrden(OrderDto orderDto) {
        Order order = Order.builder()
                .orderNumber(orderDto.getOrderNumber())
                .customerId(orderDto.getCustomerId())
                .serviceType(orderDto.getServiceType())
                .status(OrderStatus.CREATED)
                .build();

        // Registrar estado inicial en el historial
        OrderHistory history = OrderHistory.builder()
                .order(order)
                .previousStatus(null)
                .newStatus(OrderStatus.CREATED)
                .build();

        order.getHistory().add(history);
        return orderRepository.save(order);
    }

    public Order consultarOrden(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));
    }

    public List<Order> listarOrdenes() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order actualizarEstado(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + id));

        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.COMPLETED || currentStatus == OrderStatus.CANCELLED) {
            throw new IllegalStateException("No se puede modificar una orden que se encuentra en estado " + currentStatus);
        }

        if (currentStatus == OrderStatus.CREATED && newStatus == OrderStatus.APPROVED) {
            throw new IllegalArgumentException("Regla de negocio violada: Una orden en estado CREATED debe pasar por VALIDATED antes de ser aprobada.");
        }

        order.setStatus(newStatus);

        OrderHistory history = OrderHistory.builder()
                .order(order)
                .previousStatus(currentStatus)
                .newStatus(newStatus)
                .build();

        order.getHistory().add(history);
        return orderRepository.save(order);
    }

    public List<OrderHistory> getOrderHistory(Long id) {
        Order order = consultarOrden(id);
        return order.getHistory();
    }
}
