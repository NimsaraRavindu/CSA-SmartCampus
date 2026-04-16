/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus;

/**
 *
 * @author nimsa
 */
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

// The Exception Class
class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) { super(message); }
}

// The Mapper
@Provider
class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        return Response.status(422) // Unprocessable Entity
                .entity(Map.of("error", "Dependency Validation Failed", "message", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
