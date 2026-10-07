package com.exercise.elevatorSystem.service;

import com.exercise.elevatorSystem.dto.InsideRequest;
import com.exercise.elevatorSystem.dto.OutisideRequest;
import com.exercise.elevatorSystem.entity.Elevator;
import com.exercise.elevatorSystem.entity.Floor;
import com.exercise.elevatorSystem.enums.Direction;
import com.exercise.elevatorSystem.repository.ElevatorRepository;
import com.exercise.elevatorSystem.repository.FloorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElevatorServiceTest {

    @Mock
    private ElevatorRepository elevatorRepository;
    @Mock
    private FloorRepository floorRepository;
    @InjectMocks
    private ElevatorService elevatorService;

    @Test
    void shouldOpenGate() {

        String result = elevatorService.openGate();

        assertEquals("Gate is open ", result);
    }
    @Test
    void shouldRequestElevator() {

        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();

        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();

        OutisideRequest request = new OutisideRequest(5, 1);

        when(elevatorRepository.findById(1))
                .thenReturn(Optional.of(elevator));

        String result = elevatorService.requestElevator(request);

        assertEquals("Wait for sometime", result);
    }
    @Test
    void shouldRejectInvalidFloor() {
        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();

        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();


        OutisideRequest request = new OutisideRequest(10, 1);
        when(elevatorRepository.findById(1))
                .thenReturn(Optional.of(elevator));

        String result = elevatorService.requestElevator(request);

        assertEquals("Enter Valid floor", result);
    }
    @Test
    void shouldThrowExceptionWhenElevatorNotFound() {

        OutisideRequest request = new OutisideRequest(5, 99);

        when(elevatorRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> elevatorService.requestElevator(request)
        );
    }
    @Test
    void shouldAcceptFloorZeroForRequestFloor() {

        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();

        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();

        OutisideRequest request = new OutisideRequest(0, 1);

        when(elevatorRepository.findById(1))
                .thenReturn(Optional.of(elevator));

        String result = elevatorService.requestElevator(request);

        assertEquals("Wait for sometime", result);
    }
    @Test
    void shouldAcceptNineForRequestFloor(){
        Floor floor = Floor.builder().id(3).name("Third floor").number(3).build();
        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator one")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();
        OutisideRequest request = new OutisideRequest(9,1);
        when(elevatorRepository.findById(1)).thenReturn(Optional.of(elevator));
        String result = elevatorService.requestElevator(request);
        assertEquals("Wait for sometime",result);
    }
    @Test
    void shouldOpenGateForRequestedFloors(){
        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();

        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();
        InsideRequest request = new InsideRequest(1,3);
        when(elevatorRepository.findById(1)).thenReturn(Optional.of(elevator));
        String result = elevatorService.goToFloor(request);
        assertEquals("Gate is open ",result);
        
    }
    @Test
  void shouldThrowExceptionForGoToFloor(){
        InsideRequest request= new InsideRequest(1,3);
        when(elevatorRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()->elevatorService.goToFloor(request));
    }
    @Test
    void shouldRejectInvalidRequestForGoTOFloor(){
        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();
        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();
        InsideRequest request = new InsideRequest(1,10);
        when(elevatorRepository.findById(1)).thenReturn(Optional.of(elevator));
        String result = elevatorService.goToFloor(request);
        assertEquals("Enter Valid floor",result);
    }
    @Test
    void ShouldTakeRequestForHigherFloorForGoToFloor(){
        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();
        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();
        InsideRequest request = new InsideRequest(1,5);
        when(elevatorRepository.findById(1)).thenReturn(Optional.of(elevator));
        String result = elevatorService.goToFloor(request);
        assertEquals("Wait for your floor.",result);
    }
    @Test
    void ShouldTakeRequestForLowerFloorForGoToFloor(){
        Floor floor = Floor.builder()
                .id(3)
                .name("Floor 3")
                .number(3)
                .build();
        Elevator elevator = Elevator.builder()
                .id(1)
                .name("Elevator 1")
                .floor(floor)
                .movingDirection(Direction.UP)
                .build();
        InsideRequest request = new InsideRequest(1,2);
        when(elevatorRepository.findById(1)).thenReturn(Optional.of(elevator));
        String result = elevatorService.goToFloor(request);
        assertEquals("Wait for your floor.",result);
    }
    
}
