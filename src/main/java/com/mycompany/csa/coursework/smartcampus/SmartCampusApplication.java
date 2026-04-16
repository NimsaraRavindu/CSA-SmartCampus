/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus;

/**
 *
 * @author nimsa
 */


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Task 1.1: Project & Application Configuration
 * Establish the API's versioned entry point at /api/v1
 */
@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {
    // The runtime will automatically scan for resources and providers in this package.
}
