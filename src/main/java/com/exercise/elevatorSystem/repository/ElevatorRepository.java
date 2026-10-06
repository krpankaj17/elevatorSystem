package com.exercise.elevatorSystem.repository;

import com.exercise.elevatorSystem.entity.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorRepository extends JpaRepository<Elevator,Integer> {
}
