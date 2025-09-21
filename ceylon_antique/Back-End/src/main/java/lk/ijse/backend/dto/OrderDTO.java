package lk.ijse.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private String orderId;
    private String transactionId;
    private Long userId;

    // Customer info
    private String customerName;
    private String customerEmail;
    private String phone;
    private String address;
    private String city;
    private String country;

    // Delivery info
    private String deliveryAddress;
    private String deliveryCity;
    private String deliveryCountry;

    // Order amounts
    private Double totalAmount;
    private String currency;

    // Products
    private List<Long> productIds;
    private List<ProductDTO> products;
}
