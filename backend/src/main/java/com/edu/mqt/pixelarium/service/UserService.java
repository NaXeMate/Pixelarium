package com.edu.mqt.pixelarium.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.dto.request.CreateUserDTORequest;
import com.edu.mqt.pixelarium.model.entities.User;
import com.edu.mqt.pixelarium.model.vo.Email;
import com.edu.mqt.pixelarium.repositories.UserRepository;

/**
 * Provides user-related business operations.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    /**
     * Creates a service backed by the given repository.
     *
     * @param userRepo repository used to persist users
     */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ========= CRRUD =========

    /**
     * Returns all users.
     *
     * @return the list of users
     */
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    /**
     * Returns a user by id, or {@code null} if it does not exist.
     *
     * @param id user identifier
     * @return the user if found, otherwise {@code null}
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        Optional<User> op = userRepo.findById(id);

        if (op.isPresent()) {
            return op.get();
        }

        return null;
    }

    /**
     * Updates an existing user if it already exists.
     *
     * @param user user data to persist
     * @return the saved user, or {@code null} if the user does not exist
     */
    public User updateUser(User user) {
        if (userRepo.existsById(user.getId())) {
            System.out.println("User updated in the database.");
            return userRepo.save(user);
        } else {
            System.out.println("An error has occurred and the database has not been updated.");
            return null;
        }
    }

    /**
     * Deletes a user by id.
     *
     * @param id user identifier
     * @throws java.util.NoSuchElementException if the user does not exist
     */
    public void deleteUser(Long id) {
        userRepo.findById(id).orElseThrow();
        userRepo.deleteById(id);
        System.out.println("User successfully deleted from the database.");
    }

    // ========= CUSTOM METHODS =========

    /**
     * Creates a new user after validating uniqueness constraints.
     *
     * @param userDTO request payload with user data
     * @return the created user
     * @throws IllegalArgumentException if the email is already used
     * @throws IllegalArgumentException if the user name is already used
     */
    public User createUser(CreateUserDTORequest userDTO) {
        String email = userDTO.email();
    if (userRepo.existsByEmail(email)) {
        throw new IllegalArgumentException("Email already exists: " + email);
    }
    
    String userName = userDTO.userName();
    if (userRepo.existsByUserName(userName)) {
        throw new IllegalArgumentException("Username already exists: " + userName);
    }
    
    User newUser = new User();
    
    newUser.setUserName(userName);
    newUser.setPassword(encodePassword(userDTO.password())); // Codificar password
    newUser.setRealName(userDTO.firstName());
    newUser.setSurname(userDTO.lastName());
    newUser.setEmail(new Email(userDTO.email()));
    newUser.setRegisterTime(LocalDate.now());
    
    return userRepo.save(newUser);
}

    /**
     * Encodes the raw password.
     *
     * @param rawPassword password in plain text
     * @return the encoded password
     */
    private String encodePassword(String rawPassword) {
        // TODO: Inyectar BCryptPasswordEncoder y usar
        // return passwordEncoder.encode(rawPassword);
        return rawPassword;
    }

    /**
     * Returns a user by user name.
     *
     * @param userName user name to look up
     * @return the matching user, or {@code null} if not found
     */
    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    /**
     * Returns a user by email address.
     *
     * @param emailValue email address to look up
     * @return the matching user
     * @throws java.util.NoSuchElementException if the user does not exist
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String emailValue) {
    return userRepo.findByEmail(emailValue)
            .orElseThrow();
}


    /**
     * Checks whether a user exists with the given email.
     *
     * @param email email address to check
     * @return {@code true} if the email exists; otherwise {@code false}
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    /**
     * Checks whether a user exists with the given user name.
     *
     * @param userName user name to check
     * @return {@code true} if the user name exists; otherwise {@code false}
     */
    @Transactional(readOnly = true)
    public boolean existsByUserName(String userName) {
        return userRepo.existsByUserName(userName);
    }

    /**
     * Returns users registered on the given date.
     *
     * @param registerTime registration date to match
     * @return users registered on the provided date
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTime(LocalDate registerTime) {
        return userRepo.findByRegisterTime(registerTime);
    }

    /**
     * Returns users registered after the given date.
     *
     * @param date lower bound registration date (exclusive)
     * @return users registered after the provided date
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeAfter(LocalDate date) {
        return userRepo.findByRegisterTimeAfter(date);
    }

    /**
     * Returns users registered before the given date.
     *
     * @param date upper bound registration date (exclusive)
     * @return users registered before the provided date
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeBefore(LocalDate date) {
        return userRepo.findByRegisterTimeBefore(date);
    }

    /**
     * Returns users registered between the given dates.
     *
     * @param initialDate start date (inclusive)
     * @param finalDate end date (inclusive)
     * @return users registered within the given date range
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeBetween(LocalDate initialDate, LocalDate finalDate) {
        return userRepo.findByRegisterTimeBetween(initialDate, finalDate);
    }
}
