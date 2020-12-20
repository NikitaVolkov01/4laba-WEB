package ru.ifmo.web.JavaEELab4.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private final boolean result = true;
    private Object message;
}
