package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.UserDto;
import com.devmountain.noteApp.entities.User;
import com.devmountain.noteApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDto(user.getId(), user.getUsername(), user.getPassword());
        }
        return null;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        User savedUser = userRepository.save(user);

        return new UserDto(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            userRepository.save(user);
            return new UserDto(user.getId(), user.getUsername(), user.getPassword());
        }
        return null;
    }

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    @Override
    public List<String> userLogin(UserDto userDto) {
        List<String> response = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(userDto.getPassword())) {
                response.add("Login successful.");
            } else {
                response.add("Invalid password.");
            }
        } else {
            response.add("User not found.");
        }
        return response;
    }


    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            User updatedUser = userRepository.save(user);
            return new UserDto(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getPassword());
        }
        return null;
    }
}
