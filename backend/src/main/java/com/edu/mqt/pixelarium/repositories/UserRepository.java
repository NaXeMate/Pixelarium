package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    List<User> findByRegisterTime(LocalDate registerTime);
    List<User> findByRegisterTimeAfter(LocalDate date);
}
