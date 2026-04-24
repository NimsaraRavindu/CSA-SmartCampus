/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.exceptions;

// Custom business logic exception to prevent deleting rooms that still contain active sensors.
public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message) {
        super(message);
    }
}
