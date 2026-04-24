package com.taron.users.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favorite_suppliers", schema = "stock_project")
@IdClass(FavoriteSupplierKey.class)
public class FavoriteSupplier {

    @Id
    @Column(name = "id_enterprise")
    private Integer idEnterprise;

    @Id
    @Column(name = "id_supplier")
    private Integer idSupplier;
}

