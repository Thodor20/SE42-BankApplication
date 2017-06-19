/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.main;

import java.io.Serializable;

/**
 *
 * @author Thom van den Akker
 */
public class EncryptionData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final byte[] cipher;
    private final byte[] iv;
    private final byte[] secret;
    
    /**
     * 
     * @param cipher
     * @param iv
     * @param secret 
     */
    public EncryptionData(byte[] cipher, byte[] iv, byte[] secret) {
        this.cipher = cipher;
        this.iv = iv;
        this.secret = secret;
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
    public byte[] getSecret() {
        return secret;
    }
    
}