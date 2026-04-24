package com.taron.stocks;

import com.taron.stocks.models.Stock;
import com.taron.stocks.repositories.StocksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StocksService {

    private final StocksRepository repository;

    public StocksService(StocksRepository repository) {
        this.repository = repository;
    }

    public List<Stock> getStocksBySupplierId(int supplierId) {
        return repository.findStocksBySupplierId(supplierId);
    }


    public Stock createOne(Stock stock){
        return this.repository.save(stock);
    }

    public void deleteOne(int id){
        this.repository.deleteById(id);
    }

    public Stock getOne(int id){
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    public Stock getStockBySupplierAndProduct(int idSupplier, int idProduct){
        return this.repository.findStockByIdProductAndIdOwnerAndOwnerType(idSupplier, idProduct, "supplier");
    }
}
