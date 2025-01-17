package com.digital.travel.demo.repository;


import com.digital.travel.demo.entity.Order;
import com.digital.travel.demo.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE " +
            "(:status IS NULL OR o.status = :status) AND " +
            "(:minPrice IS NULL OR o.totalPrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR o.totalPrice <= :maxPrice)")
    List<Order> findByFilters(
            @Param("status") OrderStatus status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
