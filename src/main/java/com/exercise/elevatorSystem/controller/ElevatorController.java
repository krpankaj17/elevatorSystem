package com.exercise.elevatorSystem.controller;

import com.exercise.elevatorSystem.dto.*;
import com.exercise.elevatorSystem.service.ElevatorCrudServices;
import com.exercise.elevatorSystem.service.ElevatorService;
import jakarta.validation.Valid;import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elevator")
public class ElevatorController {
    private  final ElevatorService service;
    private final  ElevatorCrudServices crudServices;
    public ElevatorController(ElevatorService service,ElevatorCrudServices crudServices){
        this.service = service;
        this.crudServices =crudServices;
    }
    @PostMapping("/request")
    public ResponseEntity<String> requestFloor(@RequestBody OutisideRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.requestElevator(request));
    }
    @PostMapping("/floor-request")
    public ResponseEntity<String>  goToFloor(@RequestBody InsideRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.goToFloor(request));
    }
    @PostMapping("/step")
    public ResponseEntity<String> step(@RequestBody StepRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.step(request));
    }
    @GetMapping
    public ResponseEntity<Page<ElevatorResponse>> getAllElevator(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(crudServices.getAllElevator(pageable));
    }
    @PostMapping
    public ResponseEntity<ElevatorResponse> createElevator(@RequestBody @Valid CreateElevatorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(crudServices.createElevator(request));
    }

}
