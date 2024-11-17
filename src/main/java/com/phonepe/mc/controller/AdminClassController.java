package com.phonepe.mc.controller;

import com.phonepe.mc.dao.InMemoryDatabase;
import com.phonepe.mc.model.FitnessClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/classes")
class AdminClassController {
    private final InMemoryDatabase db;

    public AdminClassController(InMemoryDatabase db) {
        this.db = db;
    }

    @PostMapping
    public ResponseEntity<FitnessClass> createClass(@RequestBody FitnessClass fitnessClass) {
        FitnessClass newClass = new FitnessClass(
                fitnessClass.getType(),
                fitnessClass.getInstructor(),
                fitnessClass.getStartTime(),
                fitnessClass.getDuration(),
                fitnessClass.getCapacity()
        );

        return ResponseEntity.ok(db.saveClass(newClass));
    }

    @GetMapping
    public ResponseEntity<List<FitnessClass>> getAllClasses() {
        return ResponseEntity.ok(db.findAllClasses());
    }
}
