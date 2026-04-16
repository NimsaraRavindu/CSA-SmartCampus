/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.exceptions;

/**
 *
 * @author nimsa
 */

import com.mycompany.csa.coursework.smartcampus.exceptions.SensorUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Task 5.3: Exception Mapper for Sensor Maintenance states.
 * Returns a 403 Forbidden status to prevent data corruption on unavailable sensors.
 */
@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException> {
    
    @Override
    public Response toResponse(SensorUnavailableException exception) {
        return Response.status(Response.Status.FORBIDDEN) // HTTP 403
                .entity(Map.of(
                    "status", 403,
                    "error", "Forbidden",
                    "message", exception.getMessage()
                ))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}