/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus;

/**
 *
 * @author nimsa
 */
/**
 * Task 5.3: Custom Exception for State-Based Constraints.
 * Thrown when an operation is attempted on a sensor in MAINTENANCE status.
 */
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String message) {
        super(message);
    }
}