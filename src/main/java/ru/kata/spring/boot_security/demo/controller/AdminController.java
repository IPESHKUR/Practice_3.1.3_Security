package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.Dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        List<User> listOfUsers = userService.getAllUsers();
        model.addAttribute("admin", listOfUsers);
        return "admin";
    }

    //    @GetMapping("/admin")
//    public String getAllUsers(Model model) {
//        model.addAttribute("admin", userService.getAllUsers());
//        return "admin";
//    }
//    @PostMapping("/")
//    public String addUser(@ModelAttribute("user") @Valid User user) {
//        userService.addUser(user);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(user.getRoles().stream().map(role -> roleService.findById(role.getId())).collect(Collectors.toSet()));
//        userService.updateUser(user);
//        return "redirect:/admin/";
//    }
    @PostMapping()
    public String addUser(@ModelAttribute("userDto") @Valid UserDto userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(userDto.getRoles());
        userService.saveUser(userDto);
        return "redirect:/admin";
    }
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid UserDto user, @PathVariable ("id") Long id) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUsers(id);
        return "redirect:/admin";
    }

//    @GetMapping("/admin")
//    public String addUser(Model model) {
//        model.addAttribute("userDto", new User());
//        return "admin";
//    }
//
//    @PostMapping("/add")
//    public String saveUser(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "add";
//        }
//        userService.saveUser(userDto);
//        return "redirect:/admin";
//    }

//    @PostMapping("/delete")
//    public String deleteUsers(@RequestParam("id") Long id) {
//        userService.deleteUsers(id);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/admin/update")
//    public String editUser(@RequestParam("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @PostMapping("/edit")
//    public String updateUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult, @ModelAttribute("id") Long id) {
//        if (bindingResult.hasErrors()) {
//            return "edit";
//        }
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }

}
