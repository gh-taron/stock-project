package com.taron.stocks;

import com.taron.stocks.models.Stock;
import com.taron.stocks.repositories.StocksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksService {

    private final StocksRepository repository;

    public StocksService(StocksRepository repository) {
        this.repository = repository;
    }

    public List<Stock> getStocksByEnterprise(int idEnterprise) {
        return repository.findByIdEnterprise(idEnterprise);
    }

    public Stock createOne(Stock stock){
        return this.repository.save(stock);
    }

    public void deleteOne(int id){
        this.repository.deleteById(id);
    }

    public Stock getOne(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock introuvable"));
    }

    public Stock getStockByEnterpriseAndProduct(int idEnterprise, int idProduct){
        return this.repository.findByIdEnterpriseAndIdProduct(idEnterprise, idProduct);
    }
}
