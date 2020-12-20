package ru.ifmo.web.JavaEELab4.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointAddRequest {

    @NotNull(message = "Enter X value")
    @Min(value = -2, message = "Must be from -2 to 2") @Max(value = 2, message = "Must be from -2 to 2")
    private Double x;

    @NotNull(message = "Enter Y value")
    @Min(value = -3, message = "Must be from -3 to 3") @Max(value = 3, message = "Must be from -3 to 3")
    private Double y;

    @NotNull(message = "Enter R value")
    @Min(value = 0, message = "Must be from 1 to 2") @Max(value = 2, message = "Must be from 1 to 2")
    private Double r;
}
