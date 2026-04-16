/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus;

/**
 *
 * @author nimsa
 */


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Path("/")
public class DiscoveryResource {
    private static final Logger LOG = Logger.getLogger(DiscoveryResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscoveryInfo() {
        LOG.info("Discovery endpoint accessed.");
        
        Map<String, Object> discovery = new HashMap<>();
        discovery.put("version", "v1.0");
        discovery.put("description", "Smart Campus Sensor & Room Management API");
        discovery.put("admin_contact", "nimsara@iit.ac.lk");
        
        // HATEOAS: Providing links to primary resources
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        
        discovery.put("links", links);
        
        return Response.ok(discovery).build();
    }
}