package com.taron.orders;

import com.taron.orders.models.Order;
import com.taron.orders.models.OrderDetail;
import com.taron.orders.repositories.OrderDetailsRepository;
import com.taron.orders.repositories.OrdersRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Order createOne(Order order) {
        List<OrderDetail> products = order.getProducts();
        Order saved = this.repository.save(order);
        if (products != null) {
            for (OrderDetail detail : products) {
                detail.setIdOrder(saved.getId());
                orderDetailsRepository.save(detail);
            }
        }
        return saved;
    }

    public List<OrderDetail> getDetailsByOrderId(int orderId) {
        return orderDetailsRepository.findByIdOrder(orderId);
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
