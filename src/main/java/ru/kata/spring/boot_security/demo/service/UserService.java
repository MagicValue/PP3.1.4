package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

@Service
public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User saveUser(User user);
    User update (User user, Long id);
    void deleteById(Long id);
    User getUserByUsername(String username);
}
