package com.example.pfabackend.service;

import com.example.pfabackend.entities.*;
import com.example.pfabackend.repository.ClientRepository;
import com.example.pfabackend.repository.OrderRepository;
import com.example.pfabackend.repository.WaiterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WaiterRepository waiterRepository;

    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<Order> getOrdersByWaiterId(Long waiterId) {
        return orderRepository.findByWaiterId(waiterId);
    }

    @Transactional
    public Order createOrder(Long clientId, Long waiterId, Set<Product> products,
                             Set<Discount> discounts,
                             Set<Reward> rewards, double totalPrice) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        Waiter waiter = waiterRepository.findById(waiterId)
                .orElseThrow(() -> new EntityNotFoundException("Waiter not found with id: " + waiterId));

        // Create the order
        Order order = new Order();
        order.setClient(client);
        order.setWaiter(waiter);
        order.setProducts(products);
        order.setDiscounts(discounts);
        order.setRewards(rewards);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }
}
