package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.CartDTO;
import lk.ijse.backend.entity.Cart;
import lk.ijse.backend.entity.Product;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.CartRepository;
import lk.ijse.backend.repository.ProductRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private CartDTO mapToDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .productId(cart.getProduct() != null ? cart.getProduct().getId() : null)
                .build();
    }

    private Cart mapToEntity(CartDTO cartDTO) {
        User user = null;
        if (cartDTO.getUserId() != null) {
            user = userRepository.findById(cartDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Product product = null;
        if (cartDTO.getProductId() != null) {
            product = productRepository.findById(cartDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        return Cart.builder()
                .id(cartDTO.getId())
                .quantity(cartDTO.getQuantity())
                .user(user)
                .product(product)
                .build();
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartDTO saveCart(CartDTO cartDTO) {
        Cart cart = mapToEntity(cartDTO);
        Cart savedCart = cartRepository.save(cart);
        return mapToDTO(savedCart);
    }

    @Override
    public CartDTO updateCart(CartDTO cartDTO) {
        if (cartDTO.getId() == null) throw new RuntimeException("Cart ID is required for update");

        Cart existingCart = cartRepository.findById(cartDTO.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        existingCart.setQuantity(cartDTO.getQuantity());

        if (cartDTO.getUserId() != null) {
            User user = userRepository.findById(cartDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingCart.setUser(user);
        }

        if (cartDTO.getProductId() != null) {
            Product product = productRepository.findById(cartDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existingCart.setProduct(product);
        }

        Cart updatedCart = cartRepository.save(existingCart);
        return mapToDTO(updatedCart);
    }

    @Override
    public String deleteCart(CartDTO cartDTO) {
        if (cartDTO.getId() == null) throw new RuntimeException("Cart ID is required for delete");
        cartRepository.deleteById(cartDTO.getId());
        return "Cart deleted successfully";
    }
}
