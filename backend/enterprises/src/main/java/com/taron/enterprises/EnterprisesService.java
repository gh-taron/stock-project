package com.taron.enterprises;

import com.taron.enterprises.models.Enterprise;
import com.taron.enterprises.repositories.EnterprisesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterprisesService {

    private final EnterprisesRepository repository;

    public EnterprisesService(EnterprisesRepository repository) {
        this.repository = repository;
    }


    public Enterprise createOne(Enterprise enterprise){
        return this.repository.save(enterprise);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return repository.existsByPhoneNumber(phoneNumber);
    }

    public Enterprise getById(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
    }

    public List<Enterprise> getAll(){
        return this.repository.findAll();
    }

}
