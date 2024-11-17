package com.phonepe.mc.dao;

import com.phonepe.mc.model.Booking;
import com.phonepe.mc.model.FitnessClass;
import com.phonepe.mc.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class InMemoryDatabase {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Map<UUID, FitnessClass> classes = new ConcurrentHashMap<>();
    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();

    public User saveUser(User user) {
        users.put(user.getId(), user);
        usersByUsername.put(user.getUsername(), user);
        return user;
    }

    public FitnessClass saveClass(FitnessClass fitnessClass) {
        classes.put(fitnessClass.getId(), fitnessClass);
        return fitnessClass;
    }

    public Booking saveBooking(Booking booking) {
        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Optional<User> findUserById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    public Optional<FitnessClass> findClassById(UUID id) {
        return Optional.ofNullable(classes.get(id));
    }

    public Optional<Booking> findBookingById(UUID id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public List<Booking> findBookingsByUser(UUID userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<FitnessClass> findAllClasses() {
        return new ArrayList<>(classes.values());
    }
}
