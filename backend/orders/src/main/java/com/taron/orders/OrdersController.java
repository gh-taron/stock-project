package com.taron.orders;

import com.taron.orders.models.Order;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService service;

    public OrdersController(OrdersService service) {
        this.service = service;
    }


    @GetMapping("/getAllByEnterprise/{id}")
    public List<Order> getAllByEnterprise(@PathVariable int id){
        return this.service.getAllByEnterprise(id);
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Order order){
        if(order.getEnterpriseId() == null || order.getSupplierId() == null || order.getState().isBlank() || order.getState() == null ){
            return ResponseEntity.badRequest().body("Tous les champs doivent être remplis correctement (longueurs respectées et non vides).");
        }
        Order newOrder = this.service.createOne(order);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable int id){
        return this.service.getOneById(id);
    }

    @PatchMapping("/{id}")
    public Order updateOne(@PathVariable int id,@RequestBody Order newOrder){
        Order order = getOne(id);
        order.setState(newOrder.getState());
        return this.service.createOne(order);
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















