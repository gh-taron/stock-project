package com.taron.users.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enterprises", schema = "stock_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enterprise")
    private int id;

    @Column(name = "phone_number", nullable = false, unique = true, length = 50)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
}
