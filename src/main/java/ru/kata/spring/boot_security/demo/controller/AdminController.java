package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPage() {
        return "redirect:/admin/user-list";
    }

    @GetMapping("user-list")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping(value = "user-create/new")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-create";
    }

    @PostMapping(value = "user-create/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/user-list";
    }
    @GetMapping(value = "/user-list/{id}/user-update")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findById(id));
        return "user-update";
    }

    @PostMapping(value = "/user-list/{id}/user-update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles)); //set roles (str[] r)
        userService.saveUser(user);
        return "redirect:/admin/user-list";
    }
    @DeleteMapping("user-list/{id}/delete")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user-list";
    }
}
