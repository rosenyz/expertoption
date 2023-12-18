package com.expertoption.expertoption.service;

import com.expertoption.expertoption.dto.response.UserInfoFWResponse;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.model.enums.Role;
import com.expertoption.expertoption.repository.UserRepository;
import com.expertoption.expertoption.service.impl.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

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

    public List<UserInfoFWResponse> findUsersByUsedRefToken(String refToken) {
        List<User> users = userRepository.findUsersByUsedRefToken(refToken);
        return getUserInfoFWResponses(users);
    }

    public List<UserInfoFWResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return getUserInfoFWResponses(users);
    }

    public List<UserInfoFWResponse> getAllUsersByRole(String role) {
        List<User> notSortedUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();

        for (User user : notSortedUsers) {
            if (user.getRoles().contains(Role.valueOf(role))) {
                users.add(user);
            }
        }

        return getUserInfoFWResponses(users);
    }

    private List<UserInfoFWResponse> getUserInfoFWResponses(List<User> users) {
        List<UserInfoFWResponse> usersInfo = new ArrayList<>();

        for (User user : users)
        {
            UserInfoFWResponse userInfoFWResponse = new UserInfoFWResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getActive(),
                    user.getVerification(),
                    user.getIsWithdrawAvailable(),
                    user.getUsedRefToken(),
                    user.getChance(),
                    user.getBalance(),
                    user.getRoles(),
                    user.getDateOfCreate()
            );
            usersInfo.add(userInfoFWResponse);
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
