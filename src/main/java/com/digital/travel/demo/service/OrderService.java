package com.digital.travel.demo.service;

import com.digital.travel.demo.entity.Order;
import com.digital.travel.demo.entity.OrderStatus;
import com.digital.travel.demo.entity.Product;
import com.digital.travel.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);  // Логгер

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Cacheable(value = "orders", key = "#id")
    public Order getOrderById(Long id) {
        logger.info("Fetching order by ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Cacheable(value = "ordersList", key = "{#status, #minPrice, #maxPrice}")
    public List<Order> getOrdersWithFilters(OrderStatus status, Double minPrice, Double maxPrice) {
        logger.info("Fetching orders with filters: status={}, minPrice={}, maxPrice={}", status, minPrice, maxPrice);
        return orderRepository.findByFilters(status, minPrice, maxPrice);
    }

    @CacheEvict(value = "orders", key = "#id")
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

        logger.info("Updated order with ID: {}", id);
        return orderRepository.save(existingOrder);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void deleteOrder(Long id) {
        logger.info("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);
    }

    @CacheEvict(value = {"orders", "ordersList"}, allEntries = true)
    public Order createOrder(Order order) {
        logger.info("Creating new order: {}", order);
        return orderRepository.save(order);
    }

}
