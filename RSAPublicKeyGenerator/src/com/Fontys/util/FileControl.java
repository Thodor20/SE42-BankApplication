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
import java.io.FileOutputStream;
import java.io.IOException;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class FileControl {

    /**
     *
     * @param filename
     * @param encodedKey
     * @throws java.io.IOException
     */
    public static void writeKey(String filename, byte[] encodedKey) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(encodedKey);
        }
    }

    /**
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static byte[] readKey(String filename) throws IOException {
        File f = new File(filename);

        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            return keyBytes;
        }
    }

}
