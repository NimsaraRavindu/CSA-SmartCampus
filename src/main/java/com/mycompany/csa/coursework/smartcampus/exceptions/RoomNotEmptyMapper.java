/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.exceptions;

/**
 *
 * @author nimsa
 */



import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        // We return HTTP 409 Conflict because the request cannot be completed 
        // due to the current state of the resource (i.e. existing sensor dependencies).
        return Response.status(Response.Status.CONFLICT) 
                .entity(Map.of("error", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
