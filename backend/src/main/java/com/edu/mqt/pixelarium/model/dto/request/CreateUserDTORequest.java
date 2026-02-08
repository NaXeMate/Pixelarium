package com.edu.mqt.pixelarium.model.dto.request;

import java.time.LocalDate;

public record CreateUserDTORequest(
    String email,
    String password,
    String firstName,
    String lastName,
    String userName,
    LocalDate registerTime
) {}

