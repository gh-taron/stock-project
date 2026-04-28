package com.taron.orders.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailKey implements Serializable {
    private Integer idOrder;
    private Integer idProduct;
}
