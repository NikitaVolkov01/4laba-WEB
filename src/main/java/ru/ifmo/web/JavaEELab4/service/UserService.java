package ru.ifmo.web.JavaEELab4.service;

import ru.ifmo.web.JavaEELab4.model.User;

import java.util.List;

public interface UserService {
    public User create(User user);
    public boolean delete(User user);
    public void deleteAll();
    public User findById(Long id);
    public List<User> findAll();
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
}
