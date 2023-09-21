package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJwtService {
    private final UserRepository userRepository;

    public User getUserByUserId(String userId) {
        return userRepository.getReferenceById(userId);
    }
}