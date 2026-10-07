package com.exercise.elevatorSystem.service;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElevatorRequestState {
    private Integer upPriority = -1;
    private Integer downPriority = -1;
    private Set<Integer> requestedFloors = new HashSet<>();


}