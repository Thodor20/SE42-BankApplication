/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.encryption;

import com.Fontys.util.FileControl;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thom van den Akker
 */
public class RSAInputReader {

    public static boolean readFile(String filename) {
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            KeyFactory factory = KeyFactory.getInstance("RSA");
            
            byte[] publicKeyBytes = FileControl.readKey("C:\\users\\thomv\\Desktop\\public.key");
            byte[] inputBytes = FileControl.readKey("C:\\users\\thomv\\Desktop\\" + filename);
            
            PublicKey pub = factory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            sig.initVerify(pub);
            sig.update(inputBytes);
            return sig.verify(inputBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException ex) {
            Logger.getLogger(RSAInputReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
