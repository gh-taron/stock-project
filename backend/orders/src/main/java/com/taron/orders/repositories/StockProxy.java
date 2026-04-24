package com.taron.orders.repositories;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "stocks", path = "/stocks")
public interface StockProxy {

}
