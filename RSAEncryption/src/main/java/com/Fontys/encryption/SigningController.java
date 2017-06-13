/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.encryption;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.Fontys.util.FileControl;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class SigningController {

    private static final Logger LOGGER = Logger.getLogger(SigningController.class.getName());

    public final String ALGORITHM = "RSA";
    public final String SIGNATURE = "SHA1withRSA";
    public final int KEYSIZE = 1024;

    /**
     *
     * @return
     */
    public KeyPair generateKeyPair() {
        try {
            System.out.println("Generating public/private key ...");
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
            keygen.initialize(KEYSIZE);
            return keygen.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param priv
     * @param input
     * @return
     */
    public byte[] sign(PrivateKey priv, byte[] input) {
        try {
            Signature sign = Signature.getInstance(SIGNATURE);
            sign.initSign(priv);
            sign.update(input);
            return sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param pub
     * @param input
     * @param signed
     * @return
     */
    public boolean verify(PublicKey pub, byte[] input, byte[] signed) {
        try {
            Signature sign = Signature.getInstance(SIGNATURE);
            sign.initVerify(pub);
            sign.update(input);
            return sign.verify(signed);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            Logger.getLogger(SigningController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
