package ru.ifmo.web.JavaEELab4.repository.JPA;

import ru.ifmo.web.JavaEELab4.model.Point;
import ru.ifmo.web.JavaEELab4.repository.PointRepository;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

@Named("repository.PointRepositoryJPA")
@SessionScoped
public class PointRepositoryJPA implements PointRepository, Serializable {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Resource
    private UserTransaction transaction;


    @Override
    public Point create(Point point) {
        EntityManager em = entityManagerFactory.createEntityManager();
        if(point.getId() == null) {
            try {
                transaction.begin();
                em.persist(point);
                transaction.commit();
            } catch (Exception e) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }else return null;

        return point;
    }


    @Override
    public boolean delete(Point point) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            transaction.begin();
            Point removePoint = em.find(Point.class, point.getId());

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
    public void deleteAll(String username) {
        try {
            final List<Point> pointList = findAll(username);
            for (Point point : pointList) {
                delete(point);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public List<Point> findAll(String username) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT p FROM Point p where p.user.username=:username", Point.class)
                .setParameter("username", username)
                .getResultList();
    }


    @Override
    public Point findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        if(id != null)
            return em.find(Point.class, id);

        return null;
    }
}
