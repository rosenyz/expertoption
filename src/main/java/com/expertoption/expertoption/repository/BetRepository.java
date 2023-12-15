package com.expertoption.expertoption.repository;

import com.expertoption.expertoption.model.Bet;
import com.expertoption.expertoption.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findBetByStatusAndUser(Boolean status, User user);
}
