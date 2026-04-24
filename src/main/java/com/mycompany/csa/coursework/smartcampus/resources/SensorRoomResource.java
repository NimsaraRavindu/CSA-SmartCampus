/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.resources;

import com.mycompany.csa.coursework.smartcampus.models.Room;
import com.mycompany.csa.coursework.smartcampus.exceptions.RoomNotEmptyException;
import com.mycompany.csa.coursework.smartcampus.config.DataRepository;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.logging.Logger;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {
    private static final Logger LOG = Logger.getLogger(SensorRoomResource.class.getName());

    // 1. GET ALL ROOMS (Task 2.1)
    @GET
    public Response getAllRooms() {
        LOG.info("Fetching all rooms.");
        return Response.ok(new ArrayList<>(DataRepository.rooms.values())).build();
    }

    // 2. GET ROOM BY ID 
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataRepository.rooms.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(Map.of("error", "Room " + roomId + " not found"))
                           .build();
        }
        return Response.ok(room).build();
    }

    // 3. CREATE ROOM 
    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {
        DataRepository.rooms.put(room.getId(), room);
        LOG.info("Room created: " + room.getId());

        // Builds the full URL for the 'Location' header
        java.net.URI location = uriInfo.getAbsolutePathBuilder()
                                       .path(room.getId())
                                       .build();

        // Response.created() automatically sets 201 status and Location header
        return Response.created(location)
                       .entity(room)
                       .build();
    }

    // 4. DELETE ROOM 
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataRepository.rooms.get(roomId);
        
        if (room == null) {
            return Response.noContent().build(); // Idempotent: 204 
        }

        // Check if room has active sensors (The 409 Conflict logic)
        if (!room.getSensorIds().isEmpty()) {
            LOG.warning("Deletion blocked: Room " + roomId + " contains sensors.");
            throw new RoomNotEmptyException("Cannot delete room " + roomId + " because it contains active sensors.");
        }
        
        DataRepository.rooms.remove(roomId);
        LOG.info("Room deleted: " + roomId);
        return Response.noContent().build(); // 204 Success
    }
}