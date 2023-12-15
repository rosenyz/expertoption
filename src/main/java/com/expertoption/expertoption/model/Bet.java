package com.expertoption.expertoption.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bets")
@AllArgsConstructor
@NoArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "bet_amount")
    private Double amountOfBet;

    @Column(name = "success")
    private Boolean status;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(name = "date_of_create_bet")
    private LocalDateTime dateOfCreateBet;

    @PrePersist
    public void init() {
        this.dateOfCreateBet = LocalDateTime.now();
    }
}
