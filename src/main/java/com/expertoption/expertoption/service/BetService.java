package com.expertoption.expertoption.service;

import com.expertoption.expertoption.component.RandomNumberGenerator;
import com.expertoption.expertoption.model.Bet;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.repository.BetRepository;
import com.expertoption.expertoption.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public Bet makeBet(Principal principal, Double betAmount) {
        Bet bet = new Bet();
        User user = getUserByPrincipal(principal);

        if (user.getBalance() < betAmount) { return null; }
        bet.setUser(user);
        bet.setAmountOfBet(betAmount);

        bet.setStatus(user.getChance() >= new RandomNumberGenerator().generateNumber());

        if (bet.getStatus()) { user.setBalance(user.getBalance() + betAmount); }
        else { user.setBalance(user.getBalance() - betAmount); }

        userRepository.save(user);
        betRepository.save(bet);
        return bet;
    }

    public List<Bet> findBetByStatusAndUser(Boolean status, User user) {
        return betRepository.findBetByStatusAndUser(status, user);
    }

    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Not found user by principal!"));
    }
}
