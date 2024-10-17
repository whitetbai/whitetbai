package com.museum.ticketsystem.repository;

import com.museum.ticketsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdNumber(String idNumber);
}
