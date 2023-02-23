package com.gr8erkay.goodybag.service.serviceimplentaion;

import com.gr8erkay.goodybag.dto.request.UserRequestDto;
import com.gr8erkay.goodybag.dto.response.UserResponseDto;
import com.gr8erkay.goodybag.exception.UserNotFoundException;
import com.gr8erkay.goodybag.model.User;
import com.gr8erkay.goodybag.repository.UserRepository;
import com.gr8erkay.goodybag.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponseDto createUser(UserRequestDto request) {
        log.info("service:: about setting");
        User user = new User();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        log.info("about saving");
        User saveUser = userRepository.save(user);
        log.info("saved user");
        return new UserResponseDto(saveUser.getFirstName(),saveUser.getLastName(),
                saveUser.getUserName(), saveUser.getEmail(),
                saveUser.getPhoneNumber());
    }

    @Override
    public UserResponseDto fetchUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);
            UserResponseDto userResponseDto = new UserResponseDto();
        if (user.isPresent()) {
            User u = user.get();
            userResponseDto.setFirstName(u.getFirstName());
            userResponseDto.setLastName(u.getLastName());
            userResponseDto.setUserName(u.getUserName());
            userResponseDto.setPhoneNumber(u.getPhoneNumber());
        }

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> requests = new ArrayList<>();
        UserResponseDto userResponseDto = new UserResponseDto();
        for (User user : users) {
            userResponseDto.setFirstName(user.getFirstName());
            userResponseDto.setLastName(user.getLastName());
            userResponseDto.setUserName(user.getUserName());
            userResponseDto.setPhoneNumber(user.getPhoneNumber());
            requests.add(userResponseDto);
        }
        return requests;
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto request) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = new User();
       if (user.isPresent()){
           user1 = user.get();
           user1.setFirstName(request.getFirstname());
           user1.setLastName(request.getLastName());
           user1.setUserName(request.getUserName());
           user1.setEmail(request.getEmail());
           user1.setPhoneNumber(request.getPhoneNumber());
       }
        User updatedUser = userRepository.save(user1);

        return new UserResponseDto(updatedUser.getFirstName(),updatedUser.getLastName(),
                updatedUser.getUserName(),updatedUser.getEmail(), updatedUser.getPhoneNumber());
    }


    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User user1 = user.get();
            userRepository.delete(user1);
        }
    }

    @Override
    public User userLogin(UserRequestDto request) {
        String email = request.getEmail();
        String password = request.getPassword();
        User user =  userRepository.findUserByEmailAndPassword(email,password);
        if (user == null) {
            throw new UserNotFoundException("Invalid email or password");
        }
        return user;
    }

    @Override
    public void userLogout(UserRequestDto request) {
        User loggedOutUser = new User();
        loggedOutUser.setUserName(request.getUserName());


    }
}
