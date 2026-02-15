package com.edu.mqt.pixelarium.exception;

public record ErrorResponse(
        int status,
        String message,
        String detail) {
}
