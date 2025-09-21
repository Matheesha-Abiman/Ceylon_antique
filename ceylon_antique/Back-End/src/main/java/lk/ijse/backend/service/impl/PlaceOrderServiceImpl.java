package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.OrderDetailDTO;
import lk.ijse.backend.entity.Order;
import lk.ijse.backend.entity.OrderDetail;
import lk.ijse.backend.entity.Product;
import lk.ijse.backend.repository.OrderDetailRepository;
import lk.ijse.backend.repository.OrderRepository;
import lk.ijse.backend.repository.ProductRepository;
import lk.ijse.backend.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO saveOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        OrderDetail saved = orderDetailRepository.save(orderDetail);
        return convertToDTO(saved);
    }

    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        OrderDetail updated = orderDetailRepository.save(orderDetail);
        return convertToDTO(updated);
    }

    @Override
    public String deleteOrderDetail(OrderDetailDTO orderDetailDTO) {
        orderDetailRepository.deleteById(orderDetailDTO.getId());
        return "OrderDetail deleted successfully!";
    }

    // === Helper methods ===
    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .subtotal(orderDetail.getSubtotal())
                .orderId(orderDetail.getOrder() != null ? orderDetail.getOrder().getId() : null)
                .productId(orderDetail.getProduct() != null ? orderDetail.getProduct().getId() : null)
                .build();
    }

    private OrderDetail convertToEntity(OrderDetailDTO dto) {
        Order order = null;
        if (dto.getOrderId() != null) {
            order = orderRepository.findById(dto.getOrderId()).orElse(null);
        }

        Product product = null;
        if (dto.getProductId() != null) {
            product = productRepository.findById(dto.getProductId()).orElse(null);
        }

        return OrderDetail.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .subtotal(dto.getSubtotal())
                .order(order)
                .product(product)
                .build();
    }
}
