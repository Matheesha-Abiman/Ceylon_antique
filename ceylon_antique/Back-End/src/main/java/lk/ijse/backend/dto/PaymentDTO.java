package lk.ijse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private Double amount;
    private String method;
    private String paymentDate;
    private Long orderId;
    private String status; // Added status field for frontend compatibility
    private String customerName; // Added for frontend compatibility
}