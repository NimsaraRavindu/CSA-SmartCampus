/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.config;

import com.mycompany.csa.coursework.smartcampus.models.Room;
import com.mycompany.csa.coursework.smartcampus.models.Sensor;
import com.mycompany.csa.coursework.smartcampus.models.SensorReading;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author nimsa
 */
public class DataRepository {
    // Stores Room ID -> Room object 
    public static Map<String, Room> rooms = new ConcurrentHashMap<>();
    
    // Stores Sensor ID -> Sensor object 
    public static Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    
    // Stores Sensor ID -> List of historical readings 
    public static Map<String, List<SensorReading>> history = new ConcurrentHashMap<>();
}