/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sander.encryptpassword;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 *
 * @author Sande
 */
public class FileControl {

    private static final Logger LOGGER = Logger.getLogger(FileControl.class.getName());

    /**
     *
     * @param filename
     * @param data
     * @return
     */
    public static boolean writeData(String filename, MessageData data) {
        try (FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
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
    public static MessageData readData(File file) {

        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            MessageData data = (MessageData)ois.readObject();
            return data;
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
