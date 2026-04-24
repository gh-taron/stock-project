package com.taron.orders.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stocks", schema = "stock_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private int id;

    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @Column(name = "owner_type", nullable = false)
    private String ownerType; // "supplier" ou "enterprise"

    @Column(name = "id_owner", nullable = false)
    private Integer idOwner;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
