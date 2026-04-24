package com.taron.orders.repositories;

import com.taron.orders.models.OrderDetail;
import com.taron.orders.models.OrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, OrderDetailKey> {


}
