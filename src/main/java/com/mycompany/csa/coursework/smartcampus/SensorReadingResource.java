/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus;

/**
 *
 * @author nimsa
 */


import com.mycompany.csa.coursework.smartcampus.config.DataRepository;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.logging.Logger;

// Ensure these are here at the class level
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    private static final Logger LOG = Logger.getLogger(SensorReadingResource.class.getName());
    private String sensorId;

    // The constructor used by the Sub-Resource Locator
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getHistory() {
        LOG.info("Fetching history for sensor: " + sensorId);
        List<SensorReading> readings = DataRepository.history.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(readings).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor s = DataRepository.sensors.get(sensorId);
        
        // Task 5.3: Maintenance Check (403 Forbidden)
        if (s != null && "MAINTENANCE".equalsIgnoreCase(s.getStatus())) {
            LOG.warning("Update rejected: Sensor " + sensorId + " is in maintenance.");
            throw new SensorUnavailableException("Sensor " + sensorId + " is in maintenance.");
        }

        // Add to history
        List<SensorReading> history = DataRepository.history.computeIfAbsent(sensorId, k -> new ArrayList<>());
        history.add(reading);
        
        // Task 4.2: Consistency Side Effect (Update parent)
        if (s != null) {
            s.setCurrentValue(reading.getValue());
            LOG.info("Updated sensor " + sensorId + " current value to: " + reading.getValue());
        }

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}