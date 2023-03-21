package com.gr8erkay.goodybag.controller;

import com.gr8erkay.goodybag.dto.request.UserRequestDto;
import com.gr8erkay.goodybag.dto.response.ApiResponse;
import com.gr8erkay.goodybag.dto.response.UserResponseDto;
import com.gr8erkay.goodybag.model.User;
import com.gr8erkay.goodybag.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/users/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto request) {
        log.info("create user call for name: {}", request.getFirstname());
        UserResponseDto response =  userService.createUser(request);
        log.info("response: {}", response.toString());
        System.out.println(response);
//        UserResponseDto user = response;

        //Using constructor
        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatusCode("201");
        apiResponse.setMessage("user created successfully");

//        UserResponseDto newBoy = new UserResponseDto();
//        newBoy.setAccountBalance(100000.00);
//        newBoy.setUserName(request.getUserName());
//        newBoy.setFirstName(request.getFirstname());
//        newBoy.setEmail(request.getEmail());
//        newBoy.setLastName(request.getLastName());
//        newBoy.setPhoneNumber(request.getPhoneNumber());

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "{userId}/user/details")
    public ResponseEntity<Object> fetchUserById(@PathVariable Long userId) {
        UserResponseDto foundUser = userService.fetchUserById(userId);

        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(foundUser);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user fetch");

        return ResponseEntity.status(200).body(apiResponse);
    }

    @GetMapping(path = "/user/listOfUsers")
    public ResponseEntity<Object> fetchAllUsers() {
        UserResponseDto listOfUsers = (UserResponseDto) userService.findAll();

        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(listOfUsers);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user fetch");

        return ResponseEntity.status(200).body(apiResponse);
    }

    @DeleteMapping(path = "{userId}/user/delete")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData("User Deleted");
        apiResponse.setStatusCode("00");

        return ResponseEntity.status(202).body(apiResponse);
    }

    @PutMapping(path = "{userId}/user/update")
    public ResponseEntity<Object> updateUserById(@PathVariable Long userId, @RequestBody UserRequestDto request) {
        UserResponseDto response = userService.updateUser(userId, request);

        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("updated successfully");

        return ResponseEntity.status(202).body(apiResponse);
    }

}
