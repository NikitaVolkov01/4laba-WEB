package ru.ifmo.web.JavaEELab4.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.ifmo.web.JavaEELab4.filter.JWTTokenNeeded;
import ru.ifmo.web.JavaEELab4.model.User;
import ru.ifmo.web.JavaEELab4.payload.request.LoginRequest;
import ru.ifmo.web.JavaEELab4.payload.request.RegisterRequest;
import ru.ifmo.web.JavaEELab4.payload.response.AuthResponse;
import ru.ifmo.web.JavaEELab4.payload.response.ErrorResponse;
import ru.ifmo.web.JavaEELab4.service.UserService;
import ru.ifmo.web.JavaEELab4.util.KeyGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Path("/")
public class UserController {


    @Inject
    @Named("util.KeyGenerator")
    private KeyGenerator keyGenerator;

    @Inject
    @Named("service.UserServiceJPA")
    private UserService userService;

    @POST
    @Path("login")
    @Consumes(APPLICATION_JSON)
    public Response login(@Valid LoginRequest request) {
        if(userService.findByUsernameAndPassword(request.getUsername(), request.getPassword()) != null)
            return Response.ok(new AuthResponse(issueToken(request.getUsername()))).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Incorrect username or password")).build();
    }


    @POST
    @Path("register")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response register(@Valid RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        if(userService.create(user) != null){
            return Response.ok(new AuthResponse(issueToken(user.getUsername()))).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("User already exists")).build();
    }

    @GET
    @Path("current")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @JWTTokenNeeded
    public Response current(@Context SecurityContext security){
        User user = userService.findByUsername(security.getUserPrincipal().getName());
        return Response.ok(user).build();
    }



    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(15)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
