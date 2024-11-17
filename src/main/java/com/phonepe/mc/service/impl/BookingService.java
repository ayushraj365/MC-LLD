package com.phonepe.mc.service.impl;

import com.phonepe.mc.dao.InMemoryDatabase;
import com.phonepe.mc.enums.BookingStatus;
import com.phonepe.mc.exceptions.BookingException;
import com.phonepe.mc.model.Booking;
import com.phonepe.mc.model.FitnessClass;
import com.phonepe.mc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final InMemoryDatabase db;
    private final Object bookingLock = new Object();


    public Booking bookClass(UUID userId, UUID classId) {
        synchronized (bookingLock) {
            User user = db.findUserById(userId)
                    .orElseThrow(() -> new BookingException("User not found"));


            FitnessClass fitnessClass = db.findClassById(classId)
                    .orElseThrow(() -> new BookingException("Class not found"));

            validateBookingConditions(user, fitnessClass);

            if (isClassFull(fitnessClass)) {
                return addToWaitlist(user, fitnessClass);
            }

            Booking booking = new Booking(userId, classId);
            fitnessClass.getBookings().add(booking.getId());
            user.setRemainingBookings(user.getRemainingBookings() - 1);

            db.saveUser(user);
            db.saveClass(fitnessClass);
            return db.saveBooking(booking);
        }
    }

    private void validateBookingConditions(User user, FitnessClass fitnessClass) {
        if (user.getRemainingBookings() <= 0) {
            throw new BookingException("No remaining bookings available");
        }

        if (fitnessClass.getStartTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new BookingException("Cannot book class starting within 30 minutes");
        }

        boolean alreadyBooked = db.findBookingsByUser(user.getId()).stream()
                .anyMatch(b -> b.getClassId().equals(fitnessClass.getId()) &&
                        b.getStatus() == BookingStatus.CONFIRMED);

        if (alreadyBooked) {
            throw new BookingException("Already booked for this class");
        }
    }

    private boolean isClassFull(FitnessClass fitnessClass) {
        return fitnessClass.getBookings().size() >= fitnessClass.getCapacity();
    }

    private Booking addToWaitlist(User user, FitnessClass fitnessClass) {
        Booking booking = new Booking(user.getId(), fitnessClass.getId());
        booking.setStatus(BookingStatus.WAITLISTED);
        fitnessClass.getWaitlist().add(booking.getId());

        db.saveClass(fitnessClass);
        return db.saveBooking(booking);
    }

    public void cancelBooking(UUID bookingId) {
        synchronized (bookingLock) {
            Booking booking = db.findBookingById(bookingId)
                    .orElseThrow(() -> new BookingException("Booking not found"));

            FitnessClass fitnessClass = db.findClassById(booking.getClassId())
                    .orElseThrow(() -> new BookingException("Class not found"));

            User user = db.findUserById(booking.getUserId())
                    .orElseThrow(() -> new BookingException("User not found"));

            if (booking.getStatus() != BookingStatus.CONFIRMED) {
                throw new BookingException("Cannot cancel non-confirmed booking");
            }

            if (fitnessClass.getStartTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
                throw new BookingException("Cannot cancel within 30 minutes of class start");
            }

            booking.setStatus(BookingStatus.CANCELLED);
            fitnessClass.getBookings().remove(booking.getId());
            user.setRemainingBookings(user.getRemainingBookings() + 1);

            db.saveUser(user);
            db.saveBooking(booking);
            db.saveClass(fitnessClass);

            processWaitlist(fitnessClass);
        }
    }
    private void processWaitlist(FitnessClass fitnessClass) {
        if (!fitnessClass.getWaitlist().isEmpty()) {
            UUID nextInLineBookingId = fitnessClass.getWaitlist().removeFirst();
            Booking waitlistedBooking = db.findBookingById(nextInLineBookingId)
                    .orElseThrow(() -> new BookingException("Waitlisted booking not found"));

            User waitlistedUser = db.findUserById(waitlistedBooking.getUserId())
                    .orElseThrow(() -> new BookingException("Waitlisted user not found"));

            if (waitlistedUser.getRemainingBookings() > 0) {
                waitlistedBooking.setStatus(BookingStatus.CONFIRMED);
                fitnessClass.getBookings().add(waitlistedBooking.getId());
                waitlistedUser.setRemainingBookings(waitlistedUser.getRemainingBookings() - 1);

                db.saveUser(waitlistedUser);
                db.saveBooking(waitlistedBooking);
                db.saveClass(fitnessClass);
            }
        }
    }
}