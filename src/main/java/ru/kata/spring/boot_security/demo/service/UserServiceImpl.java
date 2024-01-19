package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Dao.RoleDao;
import ru.kata.spring.boot_security.demo.Dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {

        return userDao.getAllUsers();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        Set<Role> roles = roleDao.getRolesByNames(Arrays.asList(userDto.getRoles()));

        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setSurname(userDto.getSurname());
        newUser.setAge(userDto.getAge());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        newUser.setRoles(roles);

        userDao.saveUser(newUser);
    }
    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        Set<Role> roles = roleDao.getRolesByNames(Arrays.asList(userDto.getRoles()));

        User newUser = new User();
        newUser.setId(userDto.getId());
        newUser.setName(userDto.getName());
        newUser.setSurname(userDto.getSurname());
        newUser.setAge(userDto.getAge());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        newUser.setRoles(roles);

        userDao.updateUser(newUser);
    }

    @Override
    @Transactional
    public void deleteUsers(Long id) {
        userDao.deleteUsers(id);
    }
//
//    @Override
//    @Transactional
//    public void updateUser(User user) {
//        userDao.updateUser(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with this name: " + username);
        }
        return user;
    }

}



