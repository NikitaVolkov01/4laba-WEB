package ru.ifmo.web.JavaEELab4.repository.JPA;

import ru.ifmo.web.JavaEELab4.model.User;
import ru.ifmo.web.JavaEELab4.repository.UserRepository;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Named("repository.UserRepositoryJPA")
@RequestScoped
public class UserRepositoryJPA implements UserRepository, Serializable {
//    private final EntityManagerFactory entityManagerFactory =
//            Persistence.createEntityManagerFactory("persist");


    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Resource
    private UserTransaction transaction;

    @Override
    public User create(User user) {
        if(findByUsername(user.getUsername()) != null) return null;

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            transaction.begin();

            MessageDigest md = MessageDigest.getInstance("MD5");
            user.setPassword((new HexBinaryAdapter()).marshal(md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8))));
            em.persist(user);

            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public boolean delete(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            transaction.begin();
            User removePoint = em.find(User.class, user.getId());

            if(removePoint == null){ return false; }

            em.remove(em.contains(removePoint) ? removePoint : em.merge(removePoint));
            transaction.commit();
            return true;
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }


    @Override
    public void deleteAll() {
        try {
            final List<User> userList = findAll();
            for (User user : userList) {
                delete(user);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public List<User> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT p FROM User p", User.class).getResultList();
    }


    @Override
    public User findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        if(id != null)
            return em.find(User.class, id);

        return null;
    }


    @Override
    public User findByUsername(String username) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("select p from User p where p.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String hashPassword = (new HexBinaryAdapter()).marshal(md.digest(password.getBytes(StandardCharsets.UTF_8)));

            return em.createQuery("select p from User p where p.username = :username and p.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", hashPassword)
                    .getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }
}
