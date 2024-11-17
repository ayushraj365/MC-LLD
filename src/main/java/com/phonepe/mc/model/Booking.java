package com.phonepe.mc.model;

import com.phonepe.mc.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private UUID userId;
    private UUID classId;
    @Builder.Default
    private LocalDateTime bookingTime = LocalDateTime.now();
    private BookingStatus status;

    public Booking(UUID userId, UUID classId) {
        this.userId = userId;
        this.classId = classId;
    }
}
