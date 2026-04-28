package com.taron.stocks.repositories;

import com.taron.stocks.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByIdEnterprise(int idEnterprise);

    Stock findByIdEnterpriseAndIdProduct(int idEnterprise, int idProduct);
}
