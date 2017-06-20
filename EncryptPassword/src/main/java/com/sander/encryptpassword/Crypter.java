/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sander.encryptpassword;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author sande
 */
public class Crypter {

    private final SecureRandom random;

    public Crypter() throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstance("SHA1PRNG");
    }

    public MessageData encryptData(char [] password, String data) {
        
        byte[] salt = generateSalt();
        
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
            password = null;
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
                      
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
            
            byte[] cph = cipher.doFinal(data.getBytes("UTF-8"));
            
            return new MessageData(cph, iv, salt);
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidParameterSpecException
                | InvalidKeySpecException
                | UnsupportedEncodingException
                | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Crypter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String decryptData(char [] password, MessageData data) {
        
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, data.getSalt(), 65536, 128);
            password = null;
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(data.getIv()));
            return new String(cipher.doFinal(data.getCipher()), "UTF-8");
        } catch (NoSuchAlgorithmException 
                | NoSuchPaddingException 
                | InvalidKeyException 
                | InvalidAlgorithmParameterException 
                | UnsupportedEncodingException 
                | InvalidKeySpecException 
                | IllegalBlockSizeException 
                | BadPaddingException ex) {
            Logger.getLogger(Crypter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private byte[] generateSalt() {
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

}
