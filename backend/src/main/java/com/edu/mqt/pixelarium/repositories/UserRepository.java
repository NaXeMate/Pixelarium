package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
           "FROM User u WHERE u.userName = :userName")
    boolean existsByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE u.registerTime = :registerTime")
    List<User> findByRegisterTime(@Param("registerTime") LocalDate registerTime);

    @Query("SELECT u FROM User u WHERE u.registerTime > :date")
    List<User> findByRegisterTimeAfter(@Param("date") LocalDate date);

    @Query("SELECT u FROM User u WHERE u.registerTime < :date")
    List<User> findByRegisterTimeBefore(@Param("date") LocalDate date);

    @Query("SELECT u FROM User u " +
           "WHERE u.registerTime BETWEEN :initialDate AND :finalDate")
    List<User> findByRegisterTimeBetween(@Param("initialDate") LocalDate initialDate,
                                         @Param("finalDate") LocalDate finalDate);
}