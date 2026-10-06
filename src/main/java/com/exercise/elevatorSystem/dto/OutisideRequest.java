package com.exercise.elevatorSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record OutisideRequest(@NotBlank
                              Integer floor,
                              @NotBlank
                              String direction) {
}
