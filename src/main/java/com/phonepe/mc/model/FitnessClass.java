package com.phonepe.mc.model;

import com.phonepe.mc.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FitnessClass {
    private UUID id = UUID.randomUUID();
    private ClassType type;
    private String instructor;
    private LocalDateTime startTime;
    private Integer duration;
    private Integer capacity;
    private Set<UUID> bookings = new HashSet<>();
    private LinkedList<UUID> waitlist = new LinkedList<>();
    public FitnessClass(ClassType type, String instructor, LocalDateTime startTime, int duration, int capacity) {
        this.type = type;
        this.instructor = instructor;
        this.startTime = startTime;
        this.duration = duration;
        this.capacity = capacity;
    }
}
