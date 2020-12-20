package ru.ifmo.web.JavaEELab4.service;

import com.google.common.collect.Lists;
import ru.ifmo.web.JavaEELab4.model.Point;
import ru.ifmo.web.JavaEELab4.repository.PointRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named("service.PointServiceJPA")
@RequestScoped
public class PointServiceJPA implements Serializable, PointService {


    @Inject
    @Named("repository.PointRepositoryJPA")
    private PointRepository pointRepository;

    @Override
    public Point create(Point point) {
        return this.pointRepository.create(point);
    }

    @Override
    public boolean delete(Point point) {
        return this.pointRepository.delete(point);
    }

    @Override
    public void deleteAll(String username) {
        this.pointRepository.deleteAll(username);
    }


    @Override
    public Point findById(Long id) {
        return this.pointRepository.findById(id);
    }

    @Override
    public List<Point> findAll(String username) {
        return Lists.newArrayList(this.pointRepository.findAll(username));
    }
}
