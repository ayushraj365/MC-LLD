package com.phonepe.mc.service.impl;

import com.phonepe.mc.service.UserTierStrategy;
import lombok.Value;

@Value
public class GoldTierStrategy implements UserTierStrategy {
    @Override
    public int getBookingLimit() {
        return 5;
    }

    @Override
    public String getTierName() {
        return "GOLD";
    }
}
