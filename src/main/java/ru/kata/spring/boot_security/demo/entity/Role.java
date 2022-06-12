package ru.kata.spring.boot_security.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "role_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    @Override
    public String getAuthority() {
        return getName();
    }

}
