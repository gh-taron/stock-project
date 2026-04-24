package com.taron.users.repositories;

import com.taron.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    List<User> findByIdEnterprise(int idEnterprise);

    List<User> findByRole(String role);

}
