package com.exercise.elevatorSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record InsideRequest(@NotBlank Integer elevatorId,
                      @NotBlank
                      Integer floor
                      ) {
}
