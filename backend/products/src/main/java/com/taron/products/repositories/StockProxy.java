package com.taron.products.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stocks", path = "/stocks")
public interface StockProxy {

    @DeleteMapping("/deleteBySupplierAndProduct/{idSupplier}/{idProduct}")
    void deleteBySupplierAndProduct(@PathVariable int idSupplier, @PathVariable int idProduct);
}
