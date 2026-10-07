package com.exercise.elevatorSystem.dto;

public record ElevatorResponse(Integer id,
                               Integer floorId,
                               com.exercise.elevatorSystem.enums.Direction direction,
                               Float weight) {
}
