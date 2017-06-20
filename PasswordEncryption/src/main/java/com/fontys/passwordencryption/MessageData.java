/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.passwordencryption;

import java.io.Serializable;

/**
 *
 * @author Sande
 */
public class MessageData implements Serializable {
    
    private final byte[] cipher;
    private final byte[] iv;
    private final byte [] salt;
    
    /**
     * 
     * @param cipher
     * @param iv
     * @param salt
     */
    public MessageData(byte[] cipher, byte[] iv, byte [] salt) {
        this.cipher = cipher;
        this.iv = iv;
        this.salt = salt;
    }

    /**
     * 
     * @return 
     */
    public byte[] getCipher() {
        return cipher;
    }

    /**
     * 
     * @return 
     */
    public byte[] getIv() {
        return iv;
    }
    
    /**
     * 
     * @return 
     */
    public byte[] getSalt() {
        return salt;
    }
    
}
