package com.taron.authentications;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.taron.authentications.models.User;
import com.taron.authentications.repositories.UsersProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationsService {

    private final UsersProxy usersProxy;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Algorithm algorithm = Algorithm.HMAC256("secret123");

    public AuthenticationsService(UsersProxy usersProxy){
        this.usersProxy = usersProxy;
    }


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersProxy.createOne(user);
    }

    public boolean existsByEmail(String email){
        return this.usersProxy.findByEmail(email).isPresent();
    }

    public boolean existsByPhoneNumber(String phone){
        return this.usersProxy.existsByPhoneNumber(phone);
    }

    public String loginAndReturnToken(String email, String password) {
        User user = usersProxy.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email non trouvé"));



        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }
}
