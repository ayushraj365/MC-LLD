package com.phonepe.mc.repsitory;

import com.phonepe.mc.dtos.BookingDTO;
import com.phonepe.mc.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(UUID id);
    List<Booking> findByUserId(UUID userId);
}
