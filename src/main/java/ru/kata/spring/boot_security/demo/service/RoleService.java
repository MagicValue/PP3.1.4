package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleByName(String name);
    void saveRole(Role role);
    Role getRoleById(Long id);

    Set<Role> getSetRoles(String[] role);

    void deleteRoleById(Long id);

}
