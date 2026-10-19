package com.telecomunicaciones.services.interfaces;

import com.telecomunicaciones.persistencia.entity.OrderHistory;

import java.util.List;

public interface IOrderHistory {
    public List<OrderHistory> listarHistorialOrdenes();
}
