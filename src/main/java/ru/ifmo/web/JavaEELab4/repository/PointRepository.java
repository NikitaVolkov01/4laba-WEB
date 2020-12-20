package ru.ifmo.web.JavaEELab4.repository;

import ru.ifmo.web.JavaEELab4.model.Point;

import java.util.List;

public interface PointRepository {
    public Point create(Point point);
    public boolean delete(Point point);
    public void deleteAll(String username);
    public List<Point> findAll(String username);
    public Point findById(Long id);
}
