package com.taron.stocks.models;

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

    @Column(name = "id_enterprise", nullable = false)
    private Integer idEnterprise;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
