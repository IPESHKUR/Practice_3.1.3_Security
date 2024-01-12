package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class UsersController {

    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("admin", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUsers(@RequestParam("id") Long id) {
        userService.deleteUsers(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String editUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @ModelAttribute("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
