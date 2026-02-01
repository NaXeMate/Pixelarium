package com.edu.mqt.pixelarium.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.User;
import com.edu.mqt.pixelarium.repositories.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ========= CRRUD =========

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        Optional<User> op = userRepo.findById(id);

        if (op.isPresent()) {
            return op.get();
        }

        return null;
    }

    public User updateUser(User user) {
        if (userRepo.existsById(user.getId())) {
            System.out.println("User updated in the database.");
            return userRepo.save(user);
        } else {
            System.out.println("An error has occurred and the database has not been updated.");
            return null;
        }
    }

    public void deleteUser(Long id) {
        userRepo.findById(id).orElseThrow();
        userRepo.deleteById(id);
        System.out.println("User successfully deleted from the database.");
    }

    // ========= CUSTOM METHODS =========

    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String userName) {
        return userRepo.existsByUserName(userName);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTime(LocalDate registerTime) {
        return userRepo.findByRegisterTime(registerTime);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeAfter(LocalDate date) {
        return userRepo.findByRegisterTimeAfter(date);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeBefore(LocalDate date) {
        return userRepo.findByRegisterTimeBefore(date);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRegisterTimeBetween(LocalDate initialDate, LocalDate finalDate) {
        return userRepo.findByRegisterTimeBetween(initialDate, finalDate);
    }
}
