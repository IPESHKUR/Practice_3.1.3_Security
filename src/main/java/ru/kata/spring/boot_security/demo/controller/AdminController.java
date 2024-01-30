package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("admin", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String addUser(Model model) {
        model.addAttribute("userDto", new User());
        return "add";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult) {
        if (userService.existsByUsername(userDto.getUsername())) {
            bindingResult.rejectValue("username",
                    "Username already exists", "Username already exists");
        }
        if (bindingResult.hasErrors()) {
            return "add";
        }
        userService.saveUser(userDto);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUsers(@RequestParam("id") Long id) {
        userService.deleteUsers(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update")
    public String editUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        User userByUsername = userService.getUserByUsername(userDto.getUsername());
        if (userService.uniqueUsername(userDto.getUsername()) && !userByUsername.getId().equals(userDto.getId())) {
            bindingResult.rejectValue("username",
                    "Username already exists", "Username already exists");
        }
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(userDto);
        return "redirect:/admin";
    }

}




//        userService.existsByUsername(userByUsername.getUsername())
//        userByUsername.isPresent && !userByUsername.getId().equals(userDto.getId())