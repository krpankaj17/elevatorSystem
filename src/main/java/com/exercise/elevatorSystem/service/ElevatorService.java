package com.exercise.elevatorSystem.service;

import com.exercise.elevatorSystem.dto.InsideRequest;
import com.exercise.elevatorSystem.dto.OutisideRequest;
import com.exercise.elevatorSystem.dto.StepRequest;
import com.exercise.elevatorSystem.entity.Elevator;
import com.exercise.elevatorSystem.entity.Floor;
import com.exercise.elevatorSystem.enums.Direction;
import com.exercise.elevatorSystem.repository.ElevatorRepository;
import com.exercise.elevatorSystem.repository.FloorRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ElevatorService {

    private final ElevatorRepository elevatorRepository;
    private final FloorRepository floorRepository;

    private final Map<Integer, ElevatorRequestState> elevatorStates = new HashMap<>();

    public ElevatorService(ElevatorRepository elevatorRepository,
                           FloorRepository floorRepository) {
        this.elevatorRepository = elevatorRepository;
        this.floorRepository = floorRepository;
    }

    public String requestElevator(OutisideRequest request) {

        Elevator elevator = elevatorRepository.findById(request.elevatorId())
                .orElseThrow(() -> new RuntimeException("Elevator not found"));

        if (request.floor() > 9 || request.floor() < 0) {
            return "Enter Valid floor";
        }

        ElevatorRequestState state = elevatorStates.computeIfAbsent(
                elevator.getId(),
                id -> new ElevatorRequestState()
        );

        state.getRequestedFloors().add(request.floor());

        if (state.getUpPriority() < request.floor()) {
            state.setUpPriority(request.floor());
        }

        if (state.getDownPriority() == -1 ||
                state.getDownPriority() > request.floor()) {
            state.setDownPriority(request.floor());
        }

        return "Wait for sometime";
    }

    public String goToFloor(InsideRequest request) {

        Elevator elevator = elevatorRepository.findById(request.elevatorId())
                .orElseThrow(() -> new RuntimeException("Elevator not found"));

        if (request.floor() > 9 || request.floor() < 0) {
            return "Enter Valid floor";
        }

        if (Objects.equals(elevator.getCurrentFloor(), request.floor())) {
            return openGate();
        }

        ElevatorRequestState state = elevatorStates.computeIfAbsent(
                elevator.getId(),
                id -> new ElevatorRequestState()
        );

        state.getRequestedFloors().add(request.floor());

        if (elevator.getDirection().equals(Direction.UP)
                && elevator.getCurrentFloor() < request.floor()) {

            if (state.getUpPriority() < request.floor()) {
                state.setUpPriority(request.floor());
            }
        }

        if (elevator.getDirection().equals(Direction.DOWN)
                && elevator.getCurrentFloor() > request.floor()) {

            if (state.getDownPriority() == -1 ||
                    request.floor() < state.getDownPriority()) {
                state.setDownPriority(request.floor());
            }
        }

        return "Wait for your floor.";
    }

    public String step(StepRequest request) {

        Elevator elevator = elevatorRepository.findById(request.elevatorId())
                .orElseThrow(() -> new RuntimeException("Elevator not found"));

        ElevatorRequestState state = elevatorStates.computeIfAbsent(
                elevator.getId(),
                id -> new ElevatorRequestState()
        );

        if (elevator.getCurrentFloor() == 9) {
            elevator.setMovingDirection(Direction.DOWN);
        } else if (elevator.getCurrentFloor() == 0) {
            elevator.setMovingDirection(Direction.UP);
        }

        if (elevator.getDirection().equals(Direction.UP)) {

            Floor nextFloor = floorRepository.getReferenceById(
                    elevator.getCurrentFloor() + 1
            );

            elevator.setFloor(nextFloor);

        } else if (elevator.getDirection().equals(Direction.DOWN)) {

            Floor nextFloor = floorRepository.getReferenceById(
                    elevator.getCurrentFloor() - 1
            );

            elevator.setFloor(nextFloor);
        }

        if (Objects.equals(
                state.getUpPriority(),
                elevator.getCurrentFloor())) {

            state.setUpPriority(-1);
            elevator.setMovingDirection(Direction.DOWN);
        }

        if (Objects.equals(
                state.getDownPriority(),
                elevator.getCurrentFloor())) {

            state.setDownPriority(-1);
            elevator.setMovingDirection(Direction.UP);
        }

        elevatorRepository.save(elevator);

        if (state.getRequestedFloors()
                .contains(elevator.getCurrentFloor())) {

            state.getRequestedFloors()
                    .remove(elevator.getCurrentFloor());

            return openGate()
                    + " Current floor is "
                    + elevator.getCurrentFloor();
        }

        return "Current floor is " + elevator.getCurrentFloor();
    }

    public String openGate() {
        return "Gate is open ";
    }
}