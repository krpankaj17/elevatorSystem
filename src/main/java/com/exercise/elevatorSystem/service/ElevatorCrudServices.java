package com.exercise.elevatorSystem.service;

import com.exercise.elevatorSystem.dto.CreateElevatorRequest;
import com.exercise.elevatorSystem.dto.ElevatorResponse;
import com.exercise.elevatorSystem.entity.Elevator;
import com.exercise.elevatorSystem.entity.Floor;
import com.exercise.elevatorSystem.enums.Direction;
import com.exercise.elevatorSystem.repository.ElevatorRepository;
import com.exercise.elevatorSystem.repository.FloorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ElevatorCrudServices {
    private  final ElevatorRepository repository;
    private final FloorRepository floorRepository;
    public  ElevatorCrudServices(ElevatorRepository repository,FloorRepository floorRepository){
        this.repository = repository;
        this.floorRepository =floorRepository;
    }
    public Page<ElevatorResponse> getAllElevator(Pageable pageable){
        return  repository.findAll(pageable).map(this::mapToResponse);
    }
    public ElevatorResponse mapToResponse(Elevator elevator){
        return new ElevatorResponse(elevator.getId(),
                elevator.getCurrentFloor(),
                elevator.getMovingDirection(),
                elevator.getCurrentWeight());
    }
    public ElevatorResponse createElevator(CreateElevatorRequest request){
        Floor floor = floorRepository.findById(request.floorId()).
                orElseThrow(()->new RuntimeException("Floor not found"));
        Elevator elevator = Elevator.builder()
                .id(request.id())
                .floor(floor)
                .name(request.name())
                .movingDirection(Direction.valueOf(request.direction()))
                .currentWeight(request.weight())
                .build();
       Elevator savedElevator = repository.save(elevator);
       return mapToResponse(savedElevator);
    }
}
