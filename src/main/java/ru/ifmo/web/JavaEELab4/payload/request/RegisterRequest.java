package ru.ifmo.web.JavaEELab4.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Enter username")
    @Size(min = 4, message = "Must be greater than 4")
    private String username;

    @NotEmpty(message = "Enter password")
    @Size(min = 6, message = "Must be greater than 6")
    private String password;
}
