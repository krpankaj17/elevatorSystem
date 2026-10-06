package com.exercise.elevatorSystem.service;

import com.exercise.elevatorSystem.dto.StepRequest;import com.exercise.elevatorSystem.entity.Elevator;
import com.exercise.elevatorSystem.dto.InsideRequest;
import com.exercise.elevatorSystem.dto.OutisideRequest;
import com.exercise.elevatorSystem.entity.Floor;
import com.exercise.elevatorSystem.enums.Direction;
import com.exercise.elevatorSystem.repository.ElevatorRepository;
import com.exercise.elevatorSystem.repository.FloorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;

@Service
public class ElevatorService {
    private  final ElevatorRepository elevatorRepository ;
    private final FloorRepository floorRepository;
    public ElevatorService(ElevatorRepository elevatorRepository,FloorRepository floorRepository){
        this.elevatorRepository = elevatorRepository;
        this.floorRepository = floorRepository;
    }
    Deque<Integer> upPriority = new ArrayDeque<>();
    Deque<Integer> downPriority = new ArrayDeque<>();
    HashSet<Integer> requestedFloor = new HashSet<>();

    public String requestFloor(OutisideRequest request){
        if (request.direction()==null){
            return "Give the Direction";
        }
        requestedFloor.add(request.floor());
        return "Elevator will come in some time.";
    }
    public void goToFloor(InsideRequest request){

        Elevator elevator = elevatorRepository.getReferenceById(request.elevatorId());
        if (Objects.equals(elevator.getCurrentFloor(), request.floor())){
            return;
        }
        requestedFloor.add(request.floor());
        if (elevator.getDirection().equals(Direction.UP)&&elevator.getCurrentFloor()<request.floor()){
            if (!upPriority.isEmpty()&&upPriority.peek()<request.floor()){
               upPriority.push(request.floor());
            }
        }
        if (elevator.getDirection().equals(Direction.DOWN)&&elevator.getCurrentFloor()>request.floor()){
            if (!downPriority.isEmpty()&&downPriority.peek()<request.floor()){
                downPriority.push(request.floor());
            }
        }

    }
    // stepping to next floor according to direction
    public String step(StepRequest request){

        Elevator elevator = elevatorRepository.getReferenceById(request.elevatorId());
        //condition for going up
        if (elevator.getDirection().equals(Direction.UP)&&elevator.getCurrentFloor()!=9){
            if (Objects.equals(upPriority.peek(), elevator.getCurrentFloor())){
                upPriority.clear();
            }
            if (Objects.equals(downPriority.peek(), elevator.getCurrentFloor())){
                downPriority.clear();
            }
            Floor nextFloor = floorRepository.getReferenceById(elevator.getCurrentFloor()+1);
            elevator.setFloor(nextFloor);
            elevatorRepository.save(elevator);

        }
        //Condition for going down
        if (elevator.getDirection().equals(Direction.DOWN)&&elevator.getCurrentFloor()!=1){
            if (Objects.equals(upPriority.peek(), elevator.getCurrentFloor())){
                upPriority.clear();
            }
            if (Objects.equals(downPriority.peek(), elevator.getCurrentFloor())){
                downPriority.clear();
            }
            Floor nextFloor = floorRepository.getReferenceById(elevator.getCurrentFloor()-1);
            elevator.setFloor(nextFloor);
            elevatorRepository.save(elevator);

        }
        //Condition for checking if current floor is in the queue
        if (requestedFloor.contains(elevator.getCurrentFloor())){
            if (Objects.equals(upPriority.peek(), elevator.getCurrentFloor())){
                upPriority.clear();
            }
            if (Objects.equals(downPriority.peek(), elevator.getCurrentFloor())){
                downPriority.clear();
            }
            requestedFloor.remove(elevator.getCurrentFloor());
            return openGate()+" Current floor is "+elevator.getCurrentFloor();


        }
        if (elevator.getCurrentFloor()==9){
            elevator.setMovingDirection(Direction.DOWN);
        }
        if (elevator.getCurrentFloor()==0){
            elevator.setMovingDirection(Direction.UP);
        }
        return " Current floor is "+elevator.getCurrentFloor();
    }

    public String openGate(){
        return "Gate is open ";
    }
    
    public String closeGate(){
        return "Gate is Closed";
    }

}
