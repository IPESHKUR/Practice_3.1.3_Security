package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> users;
    public Role() {}
    @Override
    public String getAuthority() {
        return null;
    }

    public Long getId() {
        return id;
    }
}
