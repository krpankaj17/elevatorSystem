package com.exercise.elevatorSystem.service;

import com.exercise.elevatorSystem.dto.FloorCreateRequest;
import com.exercise.elevatorSystem.dto.FloorResponse;
import com.exercise.elevatorSystem.entity.Elevator;
import com.exercise.elevatorSystem.entity.Floor;
import com.exercise.elevatorSystem.repository.FloorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FloorCrudServices {
     private  final FloorRepository repository;
     public FloorCrudServices(FloorRepository repository){
         this.repository= repository;
     }
    public Page<FloorResponse> getAllFloor(Pageable pageable){
        return repository.findAll(pageable).map(this::mapToResponse);
    }
    public FloorResponse createFloor(FloorCreateRequest request){
         Floor floor = Floor.builder()
                 .id(request.id()).name(request.name()).number(request.number()).build();
           Floor savedFloor = repository.save(floor);
           return mapToResponse(savedFloor);
    }
    public FloorResponse mapToResponse(Floor floor){
        return new FloorResponse(floor.getId(),
                floor.getName(),
                floor.getNumber());
    }
}
