package com.digital.travel.demo.service;

import com.digital.travel.demo.entity.Order;
import com.digital.travel.demo.entity.OrderStatus;
import com.digital.travel.demo.entity.Product;
import com.digital.travel.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrdersWithFilters(OrderStatus status, Double minPrice, Double maxPrice) {
        return orderRepository.findByFilters(status, minPrice, maxPrice);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        for (Product updatedProduct : updatedOrder.getProducts()) {
            if (!existingOrder.getProducts().contains(updatedProduct)) {
                existingOrder.addProduct(updatedProduct);
            }
        }

        for (Product existingProduct : existingOrder.getProducts()) {
            if (!updatedOrder.getProducts().contains(existingProduct)) {
                existingOrder.removeProduct(existingProduct);
            }
        }

        try {
            return orderRepository.save(existingOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving updated order", e);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
