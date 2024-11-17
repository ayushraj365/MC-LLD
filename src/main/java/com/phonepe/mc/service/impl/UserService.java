package com.phonepe.mc.service.impl;

import com.phonepe.mc.dao.InMemoryDatabase;
import com.phonepe.mc.dtos.UserDTO;
import com.phonepe.mc.exceptions.UserException;
import com.phonepe.mc.model.User;
import com.phonepe.mc.repsitory.UserRepository;
import com.phonepe.mc.service.UserTierStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryDatabase inMemoryDatabase;


    public User registerUser(UserDTO userDTO) {

        User user = User.builder()
                .username(userDTO.getUsername())
                .tierStrategy(UserTierStrategyFactory.createStrategy(userDTO.getTier()))
                .remainingBookings(UserTierStrategyFactory.createStrategy(userDTO.getTier()).getBookingLimit())
                .build();

        return inMemoryDatabase.saveUser(user);
    }
}
