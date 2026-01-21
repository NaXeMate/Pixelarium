package com.edu.mqt.pixelarium.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.edu.mqt.pixelarium.model.vo.Email;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userId", 
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Column(nullable = false, length = 55, unique = true)
    private String userName;

    @Column(nullable = false, length = 55)
    private String password;

    @Column(nullable = false, length = 55)
    private String realName;

    @Column(nullable = false, length = 100)
    private String surname;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, length = 255))
    private Email email;

    @Column(nullable = false)
    private LocalDate registerTime;

    public User() {}

    public User(Long id, String userName, String password, String realName, String surname, Email email,
            LocalDate registerTime) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.surname = surname;
        this.email = email;
        this.registerTime = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public LocalDate getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDate registerTime) {
        this.registerTime = registerTime;
    }
}
