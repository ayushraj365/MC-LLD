package com.phonepe.mc.service.impl;

import com.phonepe.mc.service.UserTierStrategy;
import lombok.Value;

@Value
public class SilverTierStrategy implements UserTierStrategy {
    @Override
    public int getBookingLimit() {
        return 3;
    }

    @Override
    public String getTierName() {
        return "SILVER";
    }
}
