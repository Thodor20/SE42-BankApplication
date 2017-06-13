/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.passwordencryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author sande
 */
public class FileControl {
    
    public void writeFile(File file, byte [] data){
         try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException ex) {
            
        }
    }
}
