package com.expertoption.expertoption.service;

import com.expertoption.expertoption.dto.response.UserInfoResponse;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.repository.UserRepository;
import com.expertoption.expertoption.service.impl.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<UserInfoResponse> findUsersByUsedRefToken(String refToken) {
        List<User> users = userRepository.findUsersByUsedRefToken(refToken);
        List<UserInfoResponse> usersInfo = new ArrayList<>();

        for (User user : users)
        {
            UserInfoResponse userInfoResponse = new UserInfoResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getActive(),
                    user.getVerification(),
                    user.getUsedRefToken(),
                    user.getChance(),
                    user.getBalance(),
                    user.getDateOfCreate()
            );
            usersInfo.add(userInfoResponse);
        }

        return usersInfo;
    }

    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Not found user by principal!"));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
