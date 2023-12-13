package com.expertoption.expertoption.model;

import com.expertoption.expertoption.component.RandomKeyGenerator;
import com.expertoption.expertoption.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "verification")
    private Boolean verification;

    @Column(name = "ref_token", unique = true)
    private String generatedRefToken;

    @Column(name = "used_ref_token")
    private String usedRefToken;

    @Column(name = "chance")
    private Double chance;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Bet> bets = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    public void addBetToUser(Bet bet) {
        bet.setUser(this);
        bets.add(bet);
    }

    @PrePersist
    private void init() {
        this.generatedRefToken = new RandomKeyGenerator().generateRandomKey();
        this.chance = 0.5;
        this.balance = 0.0;
        this.verification = false;
        this.dateOfCreate = LocalDateTime.now();
    }
}
