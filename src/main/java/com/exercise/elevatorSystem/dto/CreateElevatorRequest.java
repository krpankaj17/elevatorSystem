package com.exercise.elevatorSystem.dto;

public record CreateElevatorRequest(Integer id,
                                    Integer floorId,
                                    String name,
                                    String direction,
                                    Float weight) {
}
