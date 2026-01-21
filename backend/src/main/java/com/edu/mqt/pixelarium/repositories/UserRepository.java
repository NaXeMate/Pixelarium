package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserame(String userame);
    List<User> findByRegisterDate(LocalDate registerTime);
}
