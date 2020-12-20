package ru.ifmo.web.JavaEELab4.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "points")
public class Point implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double x;

    private Double y;

    private Double r;

    private Boolean hit;

    private Double time;

    @ManyToOne
    private User user;

    public Point(){}

    public Point(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;

        long startTime = System.nanoTime();
        this.hit = checkArea(this.x, this.y, this.r);
        long endTime = System.nanoTime();

        this.time = ((double) (endTime - startTime) / 10000000);
    }



    private boolean checkArea(double x, double y, double r){
        // Checks triangle area
        if (x <= 0 && y >= 0 && y <= x/2 + r/2) {
            return true;
        }
        // Checks rectangle area
        if (x >= 0 && y >= 0 && x <= r && y <= r) {
            return true;
        }
        // Checks 1/4 circle area
        if (x >= 0 && y <= 0 && x * x + y * y <= (r * r)/4) {
            return true;
        }
        return false;
    }

    public void calculateArea(){
        long startTime = System.nanoTime();
        this.hit = checkArea(this.x, this.y, this.r);
        long endTime = System.nanoTime();

        this.time = ((double) (endTime - startTime) / 10000000);
    }
}