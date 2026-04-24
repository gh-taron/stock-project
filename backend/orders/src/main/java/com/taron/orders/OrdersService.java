package com.taron.orders;

import com.taron.orders.models.Order;
import com.taron.orders.repositories.OrderDetailsRepository;
import com.taron.orders.repositories.OrdersRepository;
import com.taron.orders.repositories.StockProxy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepository repository;
    private final OrderDetailsRepository orderDetailsRepository;

    private final StockProxy stockProxy;

    public OrdersService(OrdersRepository repository, OrderDetailsRepository orderDetailsRepository, StockProxy stockProxy) {
        this.repository = repository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.stockProxy = stockProxy;
    }

    public Order createOne(Order order){
        return this.repository.save(order);
    }

    public List<Order> getAllByEnterprise(int id){
        return this.repository.findAllByEnterpriseId(id);
    }

    public Order getOneById(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
    }

    public void deleteOne(int id){
        this.repository.deleteById(id);
    }


    public boolean existsBySupplierId(int supplierId) {
        return repository.existsBySupplierId(supplierId);
    }

}
