package com.phonepe.mc.repsitory.impl;

import com.phonepe.mc.dtos.BookingDTO;
import com.phonepe.mc.model.Booking;
import com.phonepe.mc.repsitory.BookingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {
    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();

    @Override
    public Booking save(Booking booking) {
        bookings.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        return Optional.ofNullable(bookings.get(id));
    }

    @Override
    public List<Booking> findByUserId(UUID userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
