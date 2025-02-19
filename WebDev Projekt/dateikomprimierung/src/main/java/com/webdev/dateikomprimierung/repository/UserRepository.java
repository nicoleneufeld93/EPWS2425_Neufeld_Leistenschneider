package com.webdev.dateikomprimierung.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webdev.dateikomprimierung.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByZugriffslink(String zugriffslink);
}
