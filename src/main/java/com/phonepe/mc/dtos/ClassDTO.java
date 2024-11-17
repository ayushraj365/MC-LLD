package com.phonepe.mc.dtos;

import com.phonepe.mc.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private ClassType type;
    private String instructor;
    private LocalDateTime startTime;
    private Integer duration;
    private Integer capacity;
}
