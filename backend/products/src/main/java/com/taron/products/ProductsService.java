package com.taron.products;

import com.taron.products.models.Product;
import com.taron.products.repositories.ProductsRepository;
import com.taron.products.repositories.StockProxy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    private final StockProxy stockProxy;

    public ProductsService(ProductsRepository repository, StockProxy stockProxy) {
        this.repository = repository;
        this.stockProxy = stockProxy;
    }

    public List<Product> getAll(){
        return this.repository.findAllByActive(true);
    }

    public Product getById(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    public void deleteOne(Integer id) {
        this.repository.deleteById(id);
    }

    @Transactional
    public Product updateProductActive(int idStock, Product product){
        this.stockProxy.deleteBySupplierAndProduct(product.getIdSupplier(), product.getId());
        return this.repository.save(product);
    }

    public List<Product> getAllBySupplier(int id){
        return this.repository.findAllProductsByIdSupplier(id);
    }

    public Product createOne(Product product) {
        return this.repository.save(product);
    }
}
