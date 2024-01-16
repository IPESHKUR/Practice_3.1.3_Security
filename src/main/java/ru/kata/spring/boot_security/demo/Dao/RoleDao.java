package ru.kata.spring.boot_security.demo.Dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> getRolesByNames(List<String> names);
}
