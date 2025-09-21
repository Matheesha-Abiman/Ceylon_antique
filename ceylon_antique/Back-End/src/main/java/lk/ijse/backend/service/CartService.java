package lk.ijse.backend.service;

import lk.ijse.backend.dto.CartDTO;
import java.util.List;

public interface CartService {
    List<CartDTO> getAllCarts();
    CartDTO saveCart(CartDTO cartDTO);
    CartDTO updateCart(CartDTO cartDTO);
    String deleteCart(CartDTO cartDTO);
}
