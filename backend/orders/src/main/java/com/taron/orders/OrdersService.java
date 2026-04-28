package com.taron.orders;

import com.taron.orders.models.Order;
import com.taron.orders.repositories.OrderDetailsRepository;
import com.taron.orders.repositories.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository repository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrdersService(OrdersRepository repository, OrderDetailsRepository orderDetailsRepository) {
        this.repository = repository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public Order createOne(Order order){
        return this.repository.save(order);
    }

    public Order updateState(Order order){
        return this.repository.save(order);
    }

    public List<Order> getAllByBuyer(int idBuyer){
        return this.repository.findAllByIdBuyer(idBuyer);
    }

    public List<Order> getAllBySupplier(int idSupplier){
        return this.repository.findAllByIdSupplier(idSupplier);
    }

    public Order getOneById(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
    }

    public void deleteOne(int id){
        this.repository.deleteById(id);
    }

    public boolean existsBySupplierId(int idSupplier) {
        return repository.existsByIdSupplier(idSupplier);
    }
}
