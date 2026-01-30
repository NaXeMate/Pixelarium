package com.edu.mqt.pixelarium.model.dto.response;

import java.time.LocalDate;

import com.edu.mqt.pixelarium.model.vo.Email;

public record UserDTOResponse(
    Long id,
    String userName,
    Email email,
    LocalDate registerTime
) {}