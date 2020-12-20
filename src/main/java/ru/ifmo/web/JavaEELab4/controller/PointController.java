package ru.ifmo.web.JavaEELab4.controller;

import ru.ifmo.web.JavaEELab4.filter.JWTTokenNeeded;
import ru.ifmo.web.JavaEELab4.model.Point;
import ru.ifmo.web.JavaEELab4.model.User;
import ru.ifmo.web.JavaEELab4.payload.request.PointAddRequest;
import ru.ifmo.web.JavaEELab4.payload.response.MessageResponse;
import ru.ifmo.web.JavaEELab4.service.PointService;
import ru.ifmo.web.JavaEELab4.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Path("points")
public class PointController {

    @Inject
    @Named("service.PointServiceJPA")
    private PointService pointService;

    @Inject
    @Named("service.UserServiceJPA")
    private UserService userService;


    @GET
    @Path("get-all")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @JWTTokenNeeded
    public Response get(@Context SecurityContext security){
        String user = security.getUserPrincipal().getName();
        List<Point> pointList = pointService.findAll(user);
        if(pointList != null) {
            pointList.sort((left, right) -> (int) (right.getId() - left.getId()));
            Map<String, Object> out = new HashMap<>();
            out.put("total", pointList.size());
            out.put("points", pointList);

            return Response.ok(out).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @GET
    @Path("remove-all")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @JWTTokenNeeded
    public Response remove(@Context SecurityContext security){
        String user = security.getUserPrincipal().getName();
        pointService.deleteAll(user);
        return Response.ok(new MessageResponse("All points removed")).build();
    }

    @POST
    @Path("add")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @JWTTokenNeeded
    public Response add(@Context SecurityContext security, @Valid PointAddRequest request){
        User user = userService.findByUsername(security.getUserPrincipal().getName());

        Point point = new Point(request.getX(), request.getY(), request.getR());
        point.setUser(user);
        pointService.create(point);

        Map<String, Object> out = new HashMap<>();
        out.put("result", true);
        out.put("data", point);

        return Response.ok(out).build();
    }
}
