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

/**
 * Mapper for Task 5.2: Returns 422 Unprocessable Entity.
 */
@Provider
public class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        // We use 422 here as it is semantically more accurate for dependency issues
        return Response.status(422) 
                .entity(Map.of(
                    "status", 422,
                    "error", "Unprocessable Entity",
                    "message", ex.getMessage()
                ))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
