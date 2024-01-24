package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User getUserById(Long id);

    List<User> getAllUsers();

    boolean existsByUsername(String username);
    void saveUser(User user);

    void deleteUsers(Long id);

    void updateUser(User user);

    Optional<User> getUserByUsername(String username);

}