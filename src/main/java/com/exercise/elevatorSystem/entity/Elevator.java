package com.exercise.elevatorSystem.entity;

import com.exercise.elevatorSystem.ElevatorFactory;
import com.exercise.elevatorSystem.enums.Direction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;

@Entity
@Data
@Table(name = "elevator")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Elevator implements ElevatorFactory {
    @Id
    private Integer id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
    private Direction movingDirection;
    private Float currentWeight;



    @Override
    public Integer getCurrentFloor() {
        return floor.getId();
    }

    @Override
    public Direction getDirection() {
        return movingDirection;
    }
    @Override
    public  Integer getId(){
        return id;
    }
    @Override
    public Float getCurrentWeight(){
        return currentWeight;
    }
    @Transient
    public Integer upPriority = -1;
    @Transient
   public Integer downPriority = -1;
    @Transient
    public HashSet<Integer> requestedFloor = new HashSet<>();


}
