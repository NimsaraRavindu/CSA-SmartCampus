package com.mycompany.csa.coursework.smartcampus.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        // 1. Let JAX-RS handle its own specific web exceptions (like 404 or 405)
        // and your custom exceptions if they extend WebApplicationException.
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        // 2. Log the actual error internally for the developer (Observability)
        LOG.log(Level.SEVERE, "Internal server error caught: ", exception);

        // 3. Task 5.4: The "Safety Net" - Return a generic 500 status
        // This ensures no stack trace is visible to the user/attacker.
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                    "status", 500,
                    "error", "Internal Server Error",
                    "message", "An unexpected error occurred. Please contact admin."
                ))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}