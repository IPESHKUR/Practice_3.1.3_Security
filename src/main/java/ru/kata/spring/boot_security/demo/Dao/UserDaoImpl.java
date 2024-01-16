package ru.kata.spring.boot_security.demo.Dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery(
                        "FROM User user WHERE user.username =:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
    @Override
    public User getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("User with this ID not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUsers(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("It is not possible to delete a user with this ID");
        }
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        User userFromDB = entityManager.find(User.class, user.getId());
        if (userFromDB == null) {
            throw new EntityNotFoundException("It is not possible to update this user");
        }
        entityManager.merge(user);
    }
}
