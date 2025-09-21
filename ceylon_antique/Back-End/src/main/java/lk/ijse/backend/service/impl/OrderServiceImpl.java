package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.OrderDTO;
import lk.ijse.backend.dto.ProductDTO;
import lk.ijse.backend.entity.Order;
import lk.ijse.backend.entity.Product;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.OrderRepository;
import lk.ijse.backend.repository.ProductRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.OrderService;
import lk.ijse.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);

        // Set order date if not provided
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Set default status if not provided
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDING");
        }

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        // Check if order exists
        if (!orderRepository.existsById(orderDTO.getId())) {
            return null;
        }

        Order order = convertToEntity(orderDTO);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Override
    public String deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return "Order deleted successfully!";
        } else {
            return "Order not found!";
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countOrdersByStatus(String status) {
        return orderRepository.countByStatus(status);
    }

    // === Mappers ===
    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .orderId(order.getOrderId())
                .transactionId(order.getTransactionId())

                // Customer info
                .customerName(order.getFirstName() + " " + order.getLastName())
                .customerEmail(order.getEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .city(order.getCity())
                .country(order.getCountry())

                // Delivery info
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryCity(order.getDeliveryCity())
                .deliveryCountry(order.getDeliveryCountry())

                // Order amounts
                .totalAmount(order.getAmount())
                .currency(order.getCurrency())

                // Products (map to IDs for DTO, can expand to full DTO if needed)
                .productIds(order.getProducts() != null
                        ? order.getProducts().stream().map(Product::getId).toList()
                        : null)
                .products(order.getProducts() != null
                        ? order.getProducts().stream().map(this::mapToDTO).toList()
                        : null)

                .build();
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        User user = null;
        if (orderDTO.getUserId() != null) {
            user = userRepository.findById(orderDTO.getUserId()).orElse(null);
        }

        List<Product> products = null;
        if (orderDTO.getProductIds() != null) {
            products = productRepository.findAllById(orderDTO.getProductIds());
        }

        return Order.builder()
                .id(orderDTO.getId())
                .orderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : LocalDateTime.now())
                .status(orderDTO.getStatus())
                .user(user)
                .orderId(orderDTO.getOrderId())
                .transactionId(orderDTO.getTransactionId())

                // Customer info
                .firstName(extractFirstName(orderDTO.getCustomerName()))
                .lastName(extractLastName(orderDTO.getCustomerName()))
                .email(orderDTO.getCustomerEmail())
                .phone(orderDTO.getPhone())
                .address(orderDTO.getAddress())
                .city(orderDTO.getCity())
                .country(orderDTO.getCountry())

                // Delivery info
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .deliveryCity(orderDTO.getDeliveryCity())
                .deliveryCountry(orderDTO.getDeliveryCountry())

                // Amount and currency
                .amount(orderDTO.getTotalAmount())
                .currency(orderDTO.getCurrency())

                // Products
                .products(products)
                .build();
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategoryName())
                .build();
    }

    // Helper to split full name into first and last
    private String extractFirstName(String fullName) {
        if (fullName == null || fullName.isBlank()) return null;
        String[] parts = fullName.trim().split(" ", 2);
        return parts[0];
    }

    private String extractLastName(String fullName) {
        if (fullName == null || fullName.isBlank()) return null;
        String[] parts = fullName.trim().split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

}