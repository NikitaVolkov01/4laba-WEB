package ru.ifmo.web.JavaEELab4.service;

import com.google.common.collect.Lists;
import ru.ifmo.web.JavaEELab4.model.User;
import ru.ifmo.web.JavaEELab4.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("service.UserServiceJPA")
@RequestScoped
public class UserServiceJPA implements Serializable, UserService {

    @Inject
    @Named("repository.UserRepositoryJPA")
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return this.userRepository.create(user);
    }

    @Override
    public boolean delete(User user) {
        return this.userRepository.delete(user);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }


    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(this.userRepository.findAll());
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password);
    }
}
