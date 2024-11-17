package com.phonepe.mc.repsitory;

import com.phonepe.mc.model.FitnessClass;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassRepository {
    FitnessClass save(FitnessClass fitnessClass);
    Optional<FitnessClass> findById(UUID id);
    List<FitnessClass> findAll();
}
