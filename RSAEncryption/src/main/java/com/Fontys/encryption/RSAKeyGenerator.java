/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.encryption;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.Fontys.util.FileControl;
import java.io.IOException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class RSAKeyGenerator {
    
    /**
     *
     */
    public static void generate() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair pair = keyGen.genKeyPair();
            
            byte[] prv = pair.getPrivate().getEncoded();
            byte[] pub = pair.getPublic().getEncoded();
            
            FileControl.writeKey("C:\\users\\thomv\\Desktop\\private.key", prv);
            FileControl.writeKey("C:\\users\\thomv\\Desktop\\public.key", pub);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}