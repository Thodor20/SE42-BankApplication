/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.passwordencryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sande
 */
public class FileControl {
    
    private static final Logger LOGGER = Logger.getLogger(FileControl.class.getName());

    /**
     *
     * @param filename
     * @param data
     * @return
     */
    public boolean writeData(String filename, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param file
     * @return
     */
    public byte[] readData(File file) {
        

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] keyBytes = new byte[(int) file.length()];
            fis.read(keyBytes);
            return keyBytes;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
