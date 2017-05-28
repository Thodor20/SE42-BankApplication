/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.auctionwebservice;

import javax.xml.ws.Endpoint;
import web.Registration;

/**
 *
 * @author sande
 */
public class PublishWeb {
    private final static String REGISTRATION = "http://localhost:8080/Registration";
    
    public static void main(String[] args) {
        Endpoint.publish(REGISTRATION, new Registration());
    }
    
}
