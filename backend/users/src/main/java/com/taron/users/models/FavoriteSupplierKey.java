package com.taron.users.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteSupplierKey implements Serializable {
    private Integer idEnterprise;
    private Integer idSupplier;
}
