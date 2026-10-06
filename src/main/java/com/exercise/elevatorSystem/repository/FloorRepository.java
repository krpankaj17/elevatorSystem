package com.exercise.elevatorSystem.repository;

import com.exercise.elevatorSystem.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Integer> {
}
