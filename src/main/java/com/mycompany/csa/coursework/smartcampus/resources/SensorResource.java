package com.mycompany.csa.coursework.smartcampus.resources;

import com.mycompany.csa.coursework.smartcampus.exceptions.LinkedResourceNotFoundException;
import com.mycompany.csa.coursework.smartcampus.models.Room;
import com.mycompany.csa.coursework.smartcampus.models.Sensor;
import com.mycompany.csa.coursework.smartcampus.config.DataRepository;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {
    private static final Logger LOG = Logger.getLogger(SensorResource.class.getName());

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> sensorList = new ArrayList<>(DataRepository.sensors.values());
        
        if (type != null && !type.isEmpty()) {
            sensorList = sensorList.stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
        }
        return Response.ok(sensorList).build();
    }

    // ADD THIS METHOD: Needed to verify Task 4.2 in your Video Demo
    @GET
    @Path("/{sensorId}")
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataRepository.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(Map.of("error", "Sensor " + sensorId + " not found"))
                           .build();
        }
        return Response.ok(sensor).build();
    }

    @POST
    public Response registerSensor(Sensor sensor) {
        // Task 3.1: Verify Room existence 
        if (!DataRepository.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room ID " + sensor.getRoomId() + " does not exist.");
        }
        
        DataRepository.sensors.put(sensor.getId(), sensor);
        
        // Link sensor to room 
        Room room = DataRepository.rooms.get(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().add(sensor.getId());
        }
        
        LOG.info("Sensor registered: " + sensor.getId());
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // Task 4.1: Sub-Resource Locator 
    @Path("/{sensorId}/read")
    public SensorReadingResource getReadings(@PathParam("sensorId") String sensorId) {
        // Best practice: verify the sensor exists before delegating
        if (!DataRepository.sensors.containsKey(sensorId)) {
             throw new WebApplicationException(
                 Response.status(404).entity(Map.of("error", "Sensor not found")).build()
             );
        }
        return new SensorReadingResource(sensorId);
    }
}