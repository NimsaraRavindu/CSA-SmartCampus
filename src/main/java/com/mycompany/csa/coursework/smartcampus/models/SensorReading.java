package com.mycompany.csa.coursework.smartcampus.models;

import java.util.UUID;

public class SensorReading {
    private String id;
    private long timestamp;
    private double value;

    // sets the time.
    public SensorReading() { 
        this.id = UUID.randomUUID().toString(); 
        this.timestamp = System.currentTimeMillis(); 
    } 

    public SensorReading(double value) {
        this(); // Calls the constructor above
        this.value = value;
    }
    
    // Getters and Setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long ts) { this.timestamp = ts; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}