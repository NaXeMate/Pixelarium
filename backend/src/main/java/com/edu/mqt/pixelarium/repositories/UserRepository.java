package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Provides persistence operations for {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by exact user name.
     *
     * @param userName user name to match
     * @return the matching user or {@code null} if none exists
     */
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

    /**
     * Checks whether a user exists with the given email value.
     *
     * @param emailValue email address value to match
     * @return {@code true} if a user with the email exists; otherwise {@code false}
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email.value = :emailValue")
    boolean existsByEmail(@Param("emailValue") String emailValue);
    
    /**
     * Finds a user by email address value.
     *
     * @param emailValue email address value to match
     * @return an optional containing the matching user, if present
     */
    @Query("SELECT u FROM User u WHERE u.email.value = :emailValue")
    Optional<User> findByEmail(@Param("emailValue") String emailValue);

    /**
     * Checks whether a user exists with the given user name.
     *
     * @param userName user name to match
     * @return {@code true} if a user with the name exists; otherwise {@code false}
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
           "FROM User u WHERE u.userName = :userName")
    boolean existsByUserName(@Param("userName") String userName);

    /**
     * Finds users registered on the given date.
     *
     * @param registerTime registration date to match
     * @return users registered on the provided date
     */
    @Query("SELECT u FROM User u WHERE u.registerTime = :registerTime")
    List<User> findByRegisterTime(@Param("registerTime") LocalDate registerTime);

    /**
     * Finds users registered after the given date.
     *
     * @param date lower bound registration date (exclusive)
     * @return users registered after the provided date
     */
    @Query("SELECT u FROM User u WHERE u.registerTime > :date")
    List<User> findByRegisterTimeAfter(@Param("date") LocalDate date);

    /**
     * Finds users registered before the given date.
     *
     * @param date upper bound registration date (exclusive)
     * @return users registered before the provided date
     */
    @Query("SELECT u FROM User u WHERE u.registerTime < :date")
    List<User> findByRegisterTimeBefore(@Param("date") LocalDate date);

    /**
     * Finds users registered between the given dates.
     *
     * @param initialDate start date (inclusive)
     * @param finalDate end date (inclusive)
     * @return users registered within the provided date range
     */
    @Query("SELECT u FROM User u " +
           "WHERE u.registerTime BETWEEN :initialDate AND :finalDate")
    List<User> findByRegisterTimeBetween(@Param("initialDate") LocalDate initialDate,
                                         @Param("finalDate") LocalDate finalDate);
}
