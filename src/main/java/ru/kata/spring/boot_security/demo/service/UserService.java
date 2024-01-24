package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserByUsername(String username);

    User getUserById(Long id);

    void saveUser(UserDto userDto);

    void deleteUsers(Long id);

    void updateUser(UserDto userDto);

    boolean existsByUsername(String username);
}
