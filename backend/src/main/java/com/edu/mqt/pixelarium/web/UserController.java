package com.edu.mqt.pixelarium.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.mqt.pixelarium.model.dto.request.CreateUserDTORequest;
import com.edu.mqt.pixelarium.model.dto.request.LoginDTORequest;
import com.edu.mqt.pixelarium.model.dto.response.UserDTOResponse;
import com.edu.mqt.pixelarium.model.entities.User;
import com.edu.mqt.pixelarium.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Exposes user-related endpoints under {@code /api/users}.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Creates a controller backed by the given user service.
     *
     * @param userService service used to handle user operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns all registered users.
     *
     * @return the list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Returns a user by its identifier.
     *
     * @param id user identifier
     * @return the user with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Creates a new user from the provided payload.
     *
     * @param userDTO request payload with user attributes
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTORequest userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Updates an existing user using the provided details.
     *
     * @param id          user identifier to update
     * @param userDetails updated user data
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails) {
        userDetails.setId(id);
        User updatedUser = userService.updateUser(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by its identifier.
     *
     * @param id user identifier
     * @return an empty response with {@code 204 No Content}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Logs in a user.
     *
     * @param loginDTO request payload with user credentials
     * @return the logged in user
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTOResponse> login(@Valid @RequestBody LoginDTORequest loginDTO) {
        UserDTOResponse user = userService.login(loginDTO.email(), loginDTO.password());
        return ResponseEntity.ok(user);
    }

    /**
     * Returns a user by email address.
     *
     * @param email user email address
     * @return the matching user
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
