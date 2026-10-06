package com.exercise.elevatorSystem.dto;

import jakarta.validation.constraints.NotBlank;

public record StepRequest(@NotBlank Integer elevatorId) {
}
