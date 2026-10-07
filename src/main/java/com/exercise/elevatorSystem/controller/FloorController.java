package com.exercise.elevatorSystem.controller;

import com.exercise.elevatorSystem.dto.FloorCreateRequest;
import com.exercise.elevatorSystem.dto.FloorResponse;
import com.exercise.elevatorSystem.service.FloorCrudServices;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloorController {
    private final FloorCrudServices service;
    public FloorController(FloorCrudServices service){
        this.service = service;
    }
    @GetMapping("/floors")
    public ResponseEntity<Page<FloorResponse>> getAllFloor(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllFloor(pageable));
    }
    @PostMapping ("floor")
    public ResponseEntity<FloorResponse> createFloor(@RequestBody @Valid FloorCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createFloor(request));
    }

}
