package ru.ifmo.web.JavaEELab4.repository;

import ru.ifmo.web.JavaEELab4.model.User;

import java.util.List;

public interface UserRepository {
    public User create(User user);
    public boolean delete(User user);
    public void deleteAll();
    public List<User> findAll();
    public User findById(Long id);
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
}
