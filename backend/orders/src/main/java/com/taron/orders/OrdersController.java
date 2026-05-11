package com.taron.orders;

import com.taron.orders.models.Order;
import com.taron.orders.models.OrderDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService service;

    public OrdersController(OrdersService service) {
        this.service = service;
    }

    @GetMapping("/getAllByBuyer/{id}")
    public List<Order> getAllByBuyer(@PathVariable int id){
        return this.service.getAllByBuyer(id);
    }

    @GetMapping("/getAllBySupplier/{id}")
    public List<Order> getAllBySupplier(@PathVariable int id){
        return this.service.getAllBySupplier(id);
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Order order){
        if(order.getIdBuyer() == null || order.getIdSupplier() == null
                || order.getState() == null || order.getState().isBlank()){
            return ResponseEntity.badRequest().body("Tous les champs doivent être remplis correctement.");
        }
        Order newOrder = this.service.createOne(order);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable int id){
        return this.service.getOneById(id);
    }

    @GetMapping("/{id}/details")
    public List<OrderDetail> getDetails(@PathVariable int id){
        return this.service.getDetailsByOrderId(id);
    }

    @PatchMapping("/{id}")
    public Order updateOne(@PathVariable int id, @RequestBody Order newOrder){
        Order order = getOne(id);
        order.setState(newOrder.getState());
        return this.service.updateState(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable int id){
        this.service.deleteOne(id);
    }

    @GetMapping("/existsBySupplier")
    public boolean existsBySupplierId(@RequestParam int supplierId) {
        return service.existsBySupplierId(supplierId);
    }
}
