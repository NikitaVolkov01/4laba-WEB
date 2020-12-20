package ru.ifmo.web.JavaEELab4.service;

import ru.ifmo.web.JavaEELab4.model.Point;

import java.util.List;

public interface PointService {
    public Point create(Point point);
    public boolean delete(Point point);
    public void deleteAll(String username);
    public Point findById(Long id);
    public List<Point> findAll(String username);
}
