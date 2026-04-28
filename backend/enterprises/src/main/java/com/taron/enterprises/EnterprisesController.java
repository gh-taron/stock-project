package com.taron.enterprises;


import com.taron.enterprises.models.Enterprise;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/enterprises")
public class EnterprisesController {

    private static final Set<String> ALLOWED_TYPES = Set.of("buyer", "supplier");

    private final EnterprisesService service;

    public EnterprisesController(EnterprisesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Enterprise enterprise) {
        if (enterprise == null ||
                isNullOrEmpty(enterprise.getName()) ||
                isNullOrEmpty(enterprise.getEmail()) ||
                isNullOrEmpty(enterprise.getPhoneNumber()) ||
                isNullOrEmpty(enterprise.getAddress()) ||
                isNullOrEmpty(enterprise.getType())) {

            return ResponseEntity.badRequest().body("Tous les champs doivent être renseignés.");
        }

        if (!ALLOWED_TYPES.contains(enterprise.getType())) {
            return ResponseEntity.badRequest().body("Type invalide (buyer ou supplier).");
        }

        if (service.existsByEmail(enterprise.getEmail())) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        if (service.existsByPhoneNumber(enterprise.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Ce numéro de téléphone est déjà utilisé.");
        }

        Enterprise created = this.service.createOne(enterprise);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/{id}")
    public Enterprise getById(@PathVariable int id) {
        return this.service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Enterprise> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/getAllSuppliers")
    public List<Enterprise> getAllSuppliers() {
        return this.service.getAllSuppliers();
    }

    @GetMapping("/getAllBuyers")
    public List<Enterprise> getAllBuyers() {
        return this.service.getAllBuyers();
    }

    @PatchMapping("/updateEnterprise/{id}")
    public ResponseEntity<?> updateEnterprise(@PathVariable int id, @RequestBody Enterprise newEnterprise) {
        Enterprise enterprise = getById(id);

        if (!enterprise.getEmail().equals(newEnterprise.getEmail()) && service.existsByEmail(newEnterprise.getEmail())) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        if (!enterprise.getPhoneNumber().equals(newEnterprise.getPhoneNumber()) && service.existsByPhoneNumber(newEnterprise.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Ce numéro de téléphone est déjà utilisé.");
        }

        enterprise.setAddress(newEnterprise.getAddress());
        enterprise.setName(newEnterprise.getName());
        enterprise.setEmail(newEnterprise.getEmail());
        enterprise.setPhoneNumber(newEnterprise.getPhoneNumber());

        Enterprise patched = this.service.createOne(enterprise);

        return ResponseEntity.ok(patched);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
