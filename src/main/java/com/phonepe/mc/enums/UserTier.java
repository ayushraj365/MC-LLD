package com.phonepe.mc.enums;

public enum UserTier {
    SILVER(3),
    GOLD(5),
    PLATINUM(10);

    private final int bookingLimit;

    UserTier(int bookingLimit) {
        this.bookingLimit = bookingLimit;
    }

    public int getBookingLimit() {
        return bookingLimit;
    }
}
