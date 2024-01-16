package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

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
        newUser.setPassword(userDto.getPassword());
        newUser.setUsername(userDto.getUsername());
        newUser.setRoles(roles);

        userDao.saveUser(newUser);
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    @Override
    @Transactional
    public void deleteUsers(Long id) {
        userDao.deleteUsers(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userDao.getUserByUsername(username);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}

//    public User findByUsername(String name) {
//        return userRepository.findByUsername(name);
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), rolesToAuthorities(user.getRoles()));
//    }
//
//    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
//    @PostConstruct
//    public void addTestUsers() {
//        roleRepository.save(new Role(1L, "ROLE_ADMIN"));
//        roleRepository.save(new Role(2L, "ROLE_USER"));
//        User newAdmin = new User("name", "surname", 20, "username", "password",
//                (Set<Role>) roleRepository.findByRole("ROLE_ADMIN"));
//        saveUser(newAdmin);
//        User newUser = new User("name", "user", roleService.getRoleByName(new String[]{"ROLE_USER"}), 0, "user");
//        saveUser(newUser);
//    }


