package com.exercise.elevatorSystem;

import com.exercise.elevatorSystem.entity.Floor;import com.exercise.elevatorSystem.enums.Direction;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorFactory {
    Integer getId();
    Integer getCurrentFloor();
    Direction getDirection();
    Float getCurrentWeight();

}
