package com.exercise.elevatorSystem.entity;

import com.exercise.elevatorSystem.ElevatorFactory;
import com.exercise.elevatorSystem.enums.Direction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Elevator implements ElevatorFactory {
    @Id
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
    private Direction movingDirection;



    @Override
    public Integer getCurrentFloor() {
        return floor.getId();
    }

    @Override
    public Direction getDirection() {
        return movingDirection;
    }


}
