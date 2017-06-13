/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.util;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class FileControl {

    private static final Logger LOGGER = Logger.getLogger(FileControl.class.getName());
    
    /**
     *
     * @param filename
     * @param encodedKey
     * @return 
     */
    public static boolean writeKey(String filename, byte[] encodedKey){
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(encodedKey);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param filename
     * @return
     */
    public static byte[] readKey(String filename) {
        File f = new File(filename);

        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            return keyBytes;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
