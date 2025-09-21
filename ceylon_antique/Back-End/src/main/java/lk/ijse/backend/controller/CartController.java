package lk.ijse.backend.controller;

import lk.ijse.backend.dto.CartDTO;
import lk.ijse.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getcarts")
    public List<CartDTO> getCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/addcart")
    public CartDTO saveCart(@RequestBody CartDTO cartDTO) {
        return cartService.saveCart(cartDTO);
    }

    @PutMapping("/updatecart")
    public CartDTO updateCart(@RequestBody CartDTO cartDTO) {
        return cartService.updateCart(cartDTO);
    }

    @DeleteMapping("/deletecart")
    public String deleteCart(@RequestBody CartDTO cartDTO) {
        return cartService.deleteCart(cartDTO);
    }
}
