/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa.coursework.smartcampus.exceptions;

// Custom exception thrown during sensor registration if the specified room ID does not exist in the repository.
public class LinkedResourceNotFoundException extends RuntimeException {
    
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}