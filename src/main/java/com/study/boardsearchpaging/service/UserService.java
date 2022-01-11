package com.study.boardsearchpaging.service;

import com.study.boardsearchpaging.entity.User;
import com.study.boardsearchpaging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(int id) {
        return userRepository.findById(id);
    }
}
