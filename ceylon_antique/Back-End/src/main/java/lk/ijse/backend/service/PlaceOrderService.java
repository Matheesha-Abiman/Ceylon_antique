package lk.ijse.backend.service;

import lk.ijse.backend.dto.OrderDetailDTO;

import java.util.List;

public interface PlaceOrderService {
    List<OrderDetailDTO> getAllOrderDetails();
    OrderDetailDTO saveOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO);
    String deleteOrderDetail(OrderDetailDTO orderDetailDTO);
}
