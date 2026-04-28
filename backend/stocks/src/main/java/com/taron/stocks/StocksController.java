package com.taron.stocks;

import com.taron.stocks.models.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    private final StocksService service;

    public StocksController(StocksService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Stock getOne(@PathVariable int id){
        return this.service.getOne(id);
    }

    @GetMapping("/getStocksByEnterprise/{id}")
    public List<Stock> getStocksByEnterprise(@PathVariable int id) {
        return service.getStocksByEnterprise(id);
    }

    @GetMapping("/getStockByEnterpriseAndProduct/{idEnterprise}/{idProduct}")
    public Stock getStockByEnterpriseAndProduct(@PathVariable int idEnterprise, @PathVariable int idProduct){
        return this.service.getStockByEnterpriseAndProduct(idEnterprise, idProduct);
    }

    @PatchMapping("/updateStock/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable int id, @RequestBody Stock newStockData) {
        Stock stock = this.service.getOne(id);
        stock.setQuantity(newStockData.getQuantity());

        Stock updated = service.createOne(stock);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("")
    public ResponseEntity<?> createOne(@RequestBody Stock stock) {
        if (stock.getIdProduct() == null ||
                stock.getIdEnterprise() == null ||
                stock.getQuantity() == null || stock.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("Tous les champs requis doivent être valides.");
        }

        Stock s = this.service.createOne(stock);
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id){
        this.service.deleteOne(id);
    }
}
