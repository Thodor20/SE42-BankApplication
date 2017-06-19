/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.main;

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

    public EncryptionData encryptData(String password, String data) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), generateSalt(), 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] cph = cipher.doFinal(data.getBytes("UTF-8"));

            return new EncryptionData(cph, iv, secret.getEncoded());
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidParameterSpecException
                | InvalidKeySpecException
                | UnsupportedEncodingException ex) {
            Logger.getLogger(Crypter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String decryptData(String password, EncryptionData data) {
        try {
            SecretKey secret = new SecretKeySpec(data.getSecret(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(data.getIv()));
            return new String(cipher.doFinal(data.getCipher()), "UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
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
