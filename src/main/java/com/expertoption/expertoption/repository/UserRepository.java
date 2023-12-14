package com.expertoption.expertoption.repository;

import com.expertoption.expertoption.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findUsersByUsedRefToken(String refToken);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
