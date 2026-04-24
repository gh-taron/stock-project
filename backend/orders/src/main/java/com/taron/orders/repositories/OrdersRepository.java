package com.taron.orders.repositories;


import com.taron.orders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByEnterpriseId(int id);

    boolean existsBySupplierId(int supplierId);

}
