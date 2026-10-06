package com.exercise.elevatorSystem.controller;

import com.exercise.elevatorSystem.dto.InsideRequest;
import com.exercise.elevatorSystem.dto.OutisideRequest;
import com.exercise.elevatorSystem.dto.StepRequest;
import com.exercise.elevatorSystem.service.ElevatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elevator")
public class ElevatorController {
    private  final ElevatorService service;
    public ElevatorController(ElevatorService service){
        this.service = service;
    }
    @PostMapping("/request")
    public ResponseEntity<String> requestFloor(OutisideRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.requestFloor(request));
    }
    @PostMapping("/floor-request")
    public ResponseEntity<Void>  goToFloor(InsideRequest request){
        service.goToFloor(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/step")
    public ResponseEntity<String> step(StepRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(service.step(request));
    }

}
