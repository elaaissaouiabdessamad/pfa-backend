package com.example.pfabackend.Controllers;

import com.example.pfabackend.dto.OrderRequestDto;
import com.example.pfabackend.entities.Order;
import com.example.pfabackend.entities.Waiter;
import com.example.pfabackend.service.OrderService;
import com.example.pfabackend.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WaiterService waiterService;

    @GetMapping("/client/{clientId}")
    public List<Order> getOrdersByClientId(@PathVariable Long clientId) {
        return orderService.getOrdersByClientId(clientId);
    }

    @GetMapping("/waiter/{waiterId}")
    public List<Order> getOrdersByWaiterId(@PathVariable Long waiterId) {
        return orderService.getOrdersByWaiterId(waiterId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable Long restaurantId) {
        List<Order> allOrders = new ArrayList<>();
        List<Waiter> waiters = waiterService.getWaitersByRestaurantId(restaurantId);

        for (Waiter waiter : waiters) {
            List<Order> orders = orderService.getOrdersByWaiterId(waiter.getId());
            allOrders.addAll(orders);
        }

        return ResponseEntity.ok().body(allOrders);
    }

    @PostMapping("/client/{clientId}/waiter/{waiterId}")
    public Order createOrder(@PathVariable Long clientId, @PathVariable Long waiterId,
                             @RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(clientId, waiterId, requestDto.getProducts(),
                 requestDto.getDiscounts(),
                requestDto.getRewards(), requestDto.getTotalPrice());
    }
}
