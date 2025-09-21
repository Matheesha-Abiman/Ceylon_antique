package lk.ijse.backend.service;

import lk.ijse.backend.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayments();
    PaymentDTO savePayment(PaymentDTO paymentDTO);
    PaymentDTO updatePayment(PaymentDTO paymentDTO);
    String deletePayment(PaymentDTO paymentDTO);
}