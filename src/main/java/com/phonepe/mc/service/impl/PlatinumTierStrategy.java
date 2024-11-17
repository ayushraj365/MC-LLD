package com.phonepe.mc.service.impl;

import com.phonepe.mc.service.UserTierStrategy;
import lombok.Value;

@Value
public class PlatinumTierStrategy implements UserTierStrategy {
    @Override
    public int getBookingLimit() {
        return 10;
    }

    @Override
    public String getTierName() {
        return "PLATINUM";
    }
}
