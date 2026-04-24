package com.taron.products.repositories;

import com.taron.products.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllProductsByIdSupplier(int id);

    List<Product> findAllByActive(boolean active);


}
