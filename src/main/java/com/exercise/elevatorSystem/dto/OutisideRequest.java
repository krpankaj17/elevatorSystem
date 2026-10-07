package com.exercise.elevatorSystem.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OutisideRequest(@NotNull
                              Integer floor,
                              @NotNull
                              Integer elevatorId) {
}
