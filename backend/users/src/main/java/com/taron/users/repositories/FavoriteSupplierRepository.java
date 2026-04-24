package com.taron.users.repositories;

import com.taron.users.models.FavoriteSupplier;
import com.taron.users.models.FavoriteSupplierKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSupplierRepository extends JpaRepository<FavoriteSupplier, FavoriteSupplierKey> {
    List<FavoriteSupplier> findByIdEnterprise(Integer idEnterprise);

    void deleteByIdEnterpriseAndIdSupplier(int idEnterprise, int idSupplier);
}

