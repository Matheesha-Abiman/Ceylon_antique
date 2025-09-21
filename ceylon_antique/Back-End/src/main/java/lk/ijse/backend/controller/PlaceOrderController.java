package lk.ijse.backend.controller;

import lk.ijse.backend.dto.OrderDetailDTO;
import lk.ijse.backend.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/placeorder")
public class PlaceOrderController {

    @Autowired
    private PlaceOrderService placeOrderService;

    @GetMapping("/getorderdetails")
    public List<OrderDetailDTO> getOrderDetails() {
        return placeOrderService.getAllOrderDetails();
    }

    @PostMapping("/addorderdetail")
    public OrderDetailDTO saveOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        return placeOrderService.saveOrderDetail(orderDetailDTO);
    }

    @PutMapping("/updateorderdetail")
    public OrderDetailDTO updateOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        return placeOrderService.updateOrderDetail(orderDetailDTO);
    }

    @DeleteMapping("/deleteorderdetail")
    public String deleteOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        return placeOrderService.deleteOrderDetail(orderDetailDTO);
    }
}
