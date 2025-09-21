package lk.ijse.backend.service;

import lk.ijse.backend.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    OrderDTO saveOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDTO);
    String deleteOrder(Long id);
    List<OrderDTO> getOrdersByUserId(Long userId);
    List<OrderDTO> getOrdersByStatus(String status);
    Long countOrdersByStatus(String status);
}