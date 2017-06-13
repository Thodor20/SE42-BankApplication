/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.encryption;

import com.Fontys.util.FileControl;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thom van den Akker
 */
public class RSAInputSigner {

    /**
     *
     * @param signer
     */
    public static void signFile(String signer) {
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            KeyFactory factory = KeyFactory.getInstance("RSA");

            byte[] privateKeyBytes = FileControl.readKey("C:\\users\\thomv\\Desktop\\private.key");
            byte[] inputBytes = FileControl.readKey("C:\\users\\thomv\\Desktop\\input.txt");

            PrivateKey prv = factory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            sig.initSign(prv);
            sig.update(inputBytes);

            FileControl.writeKey("C:\\users\\thomv\\Desktop\\input(SignedBy" + signer + ").txt", sig.sign());
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException | InvalidKeyException | SignatureException ex) {
            Logger.getLogger(RSAInputSigner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
