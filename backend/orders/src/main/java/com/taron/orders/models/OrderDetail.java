package com.taron.orders.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders_details", schema = "stock_project")
@IdClass(OrderDetailKey.class)
public class OrderDetail {
    @Id
    @Column(name="id_order")
    private Integer idOrder;

    @Id
    @Column(name="id_product")
    private Integer idProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
