package com.expertoption.expertoption.service;

import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.repository.UserRepository;
import com.expertoption.expertoption.service.impl.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
        return UserDetailsImpl.build(user);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findUsersByUsedRefToken(String refToken) {
        return userRepository.findUsersByUsedRefToken(refToken);
    }

    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
