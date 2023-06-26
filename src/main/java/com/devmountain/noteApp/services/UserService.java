package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    String hashPassword(String password);

    List<String> userLogin(UserDto userDto);

    UserDto updateUser(UserDto userDto);
}
