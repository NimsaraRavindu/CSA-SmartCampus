/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.models;

/**
 *
 * @author nimsa
 */


public class Sensor {
    private String id;
    private String type;
    private String status;
    private double currentValue;
    private String roomId;

    public Sensor() {}
    public Sensor(String id, String type, String status, String roomId) {
        this.id = id; this.type = type; this.status = status; this.roomId = roomId;
    }

    // Getters and Setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double value) { this.currentValue = value; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
}
