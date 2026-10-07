package com.exercise.elevatorSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InsideRequest(@NotNull Integer elevatorId,
                      @NotNull
                      Integer floor
                      ) {
}
