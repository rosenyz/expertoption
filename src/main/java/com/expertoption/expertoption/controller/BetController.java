package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.component.RandomNumberGenerator;
import com.expertoption.expertoption.dto.response.BetInfoResponse;
import com.expertoption.expertoption.model.Bet;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.service.BetService;
import com.expertoption.expertoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/bets")
@RequiredArgsConstructor
public class BetController {
    private final UserService userService;
    private final BetService betService;

    @GetMapping("/make")
    public ResponseEntity<?> makeBetByUser(@RequestParam(name = "amount") Double betAmount, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User retrieval error"); }

        Bet bet = betService.makeBet(principal, betAmount);
        if (bet == null) { return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("У Вас недостаточно средств."); }

        BetInfoResponse betInfoResponse = new BetInfoResponse(
                bet.getId(),
                bet.getAmountOfBet(),
                bet.getStatus(),
                betService.findBetByStatusAndUser(true, bet.getUser()).size(),
                betService.findBetByStatusAndUser(false, bet.getUser()).size(),
                bet.getDateOfCreateBet()
        );

        return ResponseEntity.ok(betInfoResponse);
    }
}
