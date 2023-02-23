package com.gr8erkay.goodybag.service;

import com.gr8erkay.goodybag.dto.request.UserRequestDto;
import com.gr8erkay.goodybag.dto.response.UserResponseDto;
import com.gr8erkay.goodybag.model.User;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto fetchUserById(Long userId);

    List<UserResponseDto> findAll();

    UserResponseDto updateUser(Long userId, UserRequestDto request);

    void deleteUser(Long userId);

    User userLogin(UserRequestDto request);

    void userLogout(UserRequestDto request);
}
