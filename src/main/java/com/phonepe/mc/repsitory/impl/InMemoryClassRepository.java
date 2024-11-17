package com.phonepe.mc.repsitory.impl;

import com.phonepe.mc.model.FitnessClass;
import com.phonepe.mc.repsitory.ClassRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryClassRepository implements ClassRepository {
    private final Map<UUID, FitnessClass> classes = new ConcurrentHashMap<>();

    @Override
    public FitnessClass save(FitnessClass fitnessClass) {
        classes.put(fitnessClass.getId(), fitnessClass);
        return fitnessClass;
    }

    @Override
    public Optional<FitnessClass> findById(UUID id) {
        return Optional.ofNullable(classes.get(id));
    }

    @Override
    public List<FitnessClass> findAll() {
        return new ArrayList<>(classes.values());
    }
}
