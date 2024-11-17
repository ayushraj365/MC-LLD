package com.phonepe.mc.service;

import com.phonepe.mc.service.impl.GoldTierStrategy;
import com.phonepe.mc.service.impl.PlatinumTierStrategy;
import com.phonepe.mc.service.impl.SilverTierStrategy;

public class UserTierStrategyFactory {
    public static UserTierStrategy createStrategy(String tierName) {
        return switch (tierName.toUpperCase()) {
            case "SILVER" -> new SilverTierStrategy();
            case "GOLD" -> new GoldTierStrategy();
            case "PLATINUM" -> new PlatinumTierStrategy();
            default -> throw new IllegalArgumentException("Invalid tier: " + tierName);
        };
    }
}

