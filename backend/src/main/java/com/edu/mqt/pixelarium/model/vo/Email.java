package com.edu.mqt.pixelarium.model.vo;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String value) {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", 
                       Pattern.CASE_INSENSITIVE);
    
    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email can't be null or be empty.");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }

        if (value.length() > 254) {
            throw new IllegalArgumentException("The email is too large; it musn't surpass 255 characters.");
        }
    }
}
