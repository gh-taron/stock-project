package com.taron.users;

import com.taron.users.models.FavoriteSupplier;
import com.taron.users.models.FavoriteSupplierKey;
import com.taron.users.models.User;
import com.taron.users.repositories.FavoriteSupplierRepository;
import com.taron.users.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository repository;

    private final FavoriteSupplierRepository supplierRepository;

    public UsersService(UsersRepository repository, FavoriteSupplierRepository supplierRepository){
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    public User createOne(User user){
        return this.repository.save(user);
    }

    public boolean existsByPhoneNumber(String phone){
        return this.repository.existsByPhoneNumber(phone);
    }
    public Optional<User> findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    public Optional<User> findById(int id){

        return this.repository.findById(id);
    }

    public List<User> getAll(){
        return this.repository.findAll();
    }

    public void deleteOne(int id){
        repository.deleteById(id);
    }
    
    public List<User> getAllByEnterprise(int idEnterprise){
        return this.repository.findByIdEnterprise(idEnterprise);
    }


    public List<User> getAllSuppliers(){
        return this.repository.findByRole("supplier");
    }

    public List<User> getFavoriteSuppliers(Integer idEnterprise) {
        return supplierRepository.findByIdEnterprise(idEnterprise).stream()
                .map(FavoriteSupplier::getIdSupplier)
                .map(id -> repository.findById(id).orElse(null))
                .toList();
    }

    public void addFavorite(Integer idEnterprise, Integer idSupplier) {
        supplierRepository.save(new FavoriteSupplier(idEnterprise, idSupplier));
    }


    @Transactional
    public void removeFavorite(Integer idEnterprise, Integer idSupplier) {
        supplierRepository.deleteByIdEnterpriseAndIdSupplier(idEnterprise, idSupplier);
    }

}
