package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public AdminController() {
    }


    @GetMapping
    public String findAll(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRoles") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("{id}/edit")
    public String editUserForm(@ModelAttribute("user") User user,
                               ModelMap model,
                               @PathVariable("id") Long id,
                               @RequestParam(value = "editRoles") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findById(id));
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "editRoles") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("{id}/remove")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}
