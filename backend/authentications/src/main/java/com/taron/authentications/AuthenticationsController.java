package com.taron.authentications;


import com.taron.authentications.models.LoginRequest;
import com.taron.authentications.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;


@RestController
@RequestMapping("/auth")
public class AuthenticationsController {

    private static final Set<String> ALLOWED_ROLES = Set.of("admin", "employee");

    private final AuthenticationsService service;

    public AuthenticationsController(AuthenticationsService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user == null ||
                isNullOrEmpty(user.getFirstname()) ||
                isNullOrEmpty(user.getLastname()) ||
                isNullOrEmpty(user.getEmail()) ||
                isNullOrEmpty(user.getPassword()) ||
                isNullOrEmpty(user.getPhoneNumber()) ||
                isNullOrEmpty(user.getRole()) ||
                user.getIdEnterprise() == null) {

            return ResponseEntity.badRequest().body("Tous les champs obligatoires doivent être renseignés.");
        }

        if (!ALLOWED_ROLES.contains(user.getRole())) {
            return ResponseEntity.badRequest().body("Rôle invalide (admin ou employee).");
        }

        if (this.service.existsByEmail(user.getEmail()))
            return ResponseEntity.badRequest().body("Cet email a déjà été utilisé");

        if (this.service.existsByPhoneNumber(user.getPhoneNumber()))
            return ResponseEntity.badRequest().body("Ce numéro de téléphone a déjà été utilisé");

        User createdUser = service.register(user);
        return ResponseEntity.ok(createdUser);
    }


    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        String token = service.loginAndReturnToken(login.getEmail(), login.getPassword());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
