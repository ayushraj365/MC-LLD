package com.phonepe.mc.model;

import com.phonepe.mc.service.UserTierStrategy;
import com.phonepe.mc.service.UserTierStrategyFactory;
import com.phonepe.mc.service.impl.SilverTierStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String username;
    private String password;
    @Builder.Default
    private UserTierStrategy tierStrategy = new SilverTierStrategy();
    private int remainingBookings;

    public User(String username, String password, String tier) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.tierStrategy = UserTierStrategyFactory.createStrategy(tier);
        this.remainingBookings = this.tierStrategy.getBookingLimit();
    }
}
