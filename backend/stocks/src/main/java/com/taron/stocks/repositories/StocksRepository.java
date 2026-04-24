package com.taron.stocks.repositories;

import com.taron.stocks.models.Stock;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<Stock, Integer> {
    @Query(value = "SELECT s.* FROM stock_project.stocks s " +
            "WHERE s.id_owner = :supplierId AND LOWER(s.owner_type) = 'supplier'",
            nativeQuery = true)
    List<Stock> findStocksBySupplierId(@Param("supplierId") int supplierId);


    @Query(value = "SELECT s.* FROM stock_project.stocks s " +
            "WHERE s.id_owner = :idSupplier AND s.id_product = :idProduct AND s.owner_type = 'supplier'",
            nativeQuery = true)
    Stock findStockByIdProductAndIdOwnerAndOwnerType(@Param("idSupplier") int idSupplier,
                                                     @Param("idProduct") int idProduct,
                                                     @Param("ownerType") String ownerType);


}
