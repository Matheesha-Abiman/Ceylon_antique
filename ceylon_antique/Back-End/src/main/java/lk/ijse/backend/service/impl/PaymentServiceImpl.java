package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.PaymentDTO;
import lk.ijse.backend.entity.Order;
import lk.ijse.backend.entity.Payment;
import lk.ijse.backend.repository.OrderRepository;
import lk.ijse.backend.repository.PaymentRepository;
import lk.ijse.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        Payment saved = paymentRepository.save(payment);
        return convertToDTO(saved);
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        Payment updated = paymentRepository.save(payment);
        return convertToDTO(updated);
    }

    @Override
    public String deletePayment(PaymentDTO paymentDTO) {
        paymentRepository.deleteById(paymentDTO.getId());
        return "Payment deleted successfully!";
    }

    // === Mapping Helpers ===
    private PaymentDTO convertToDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .paymentDate(payment.getPaymentDate() != null ? payment.getPaymentDate().format(formatter) : null)
                .orderId(payment.getOrder() != null ? payment.getOrder().getId() : null)
                .status(payment.getStatus())
                // Use the customerName from DTO or set a default
                .customerName("Unknown Customer") // This will be set from the frontend
                .build();
    }

    private Payment convertToEntity(PaymentDTO dto) {
        Order order = null;
        if (dto.getOrderId() != null) {
            order = orderRepository.findById(dto.getOrderId()).orElse(null);
        }

        LocalDateTime paymentDate = null;
        if (dto.getPaymentDate() != null) {
            paymentDate = LocalDateTime.parse(dto.getPaymentDate(), formatter);
        }

        return Payment.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .method(dto.getMethod())
                .paymentDate(paymentDate)
                .order(order)
                .status(dto.getStatus())
                .build();
    }
}