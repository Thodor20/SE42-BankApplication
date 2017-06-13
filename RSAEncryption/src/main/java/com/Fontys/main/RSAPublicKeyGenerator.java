/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Fontys.main;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.Fontys.encryption.*;
import com.Fontys.util.FileControl;
import com.Thom.commands.Command;
import com.Thom.commands.CommandBuilder;
import com.Thom.commands.ConversionType;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class RSAPublicKeyGenerator {

    private static boolean isRunning = true;
    private static final SigningController controller = new SigningController();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            Command cmd = new CommandBuilder(sc.nextLine()).setConversionType(ConversionType.COMMAND_ONLY).build();

            switch (cmd.getCommand()) {
                case "generatekey":
                    if (cmd.hasRequiredArguments(1, false)) {
                        KeyPair pair = controller.generateKeyPair();

                        boolean prv = FileControl.writeKey(cmd.getArguments().get(0) + "private.key", pair.getPrivate().getEncoded());
                        boolean pub = FileControl.writeKey(cmd.getArguments().get(0) + "public.key", pair.getPublic().getEncoded());

                        if (prv && pub) {
                            System.out.println("Both private and public key have been generated successfully!");
                        } else {
                            System.out.println("Failure when writing private and public keys!");
                        }
                    } else {
                        System.out.println("Wrong amount of parameters for command. Required 1:");
                        System.out.println(" - Folder: path defining where the private and public key will be stored");
                    }
                    break;
                case "signinputfile":
                    if (cmd.hasRequiredArguments(4, false)) {
                        byte[] privateBytes = FileControl.readKey(cmd.getArguments().get(0) + "private.key");
                        byte[] inputBytes = FileControl.readKey(cmd.getArguments().get(1));

                        if (privateBytes != null && inputBytes != null) {
                            try {
                                KeyFactory factory = KeyFactory.getInstance(controller.ALGORITHM);
                                PrivateKey prv = factory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));

                                byte[] sign = controller.sign(prv, inputBytes);
                                FileControl.writeKey(cmd.getArguments().get(2) + "output(SignedBy" + cmd.getArguments().get(3) + ").txt", sign);
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                                Logger.getLogger(RSAPublicKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        System.out.println("Wrong amount of parameters for command. Required 3:");
                        System.out.println(" - Private: path defining where the private key is stored");
                        System.out.println(" - Input: file defining where the input file is stored");
                        System.out.println(" - Output: folder defining where the output file is stored");
                        System.out.println(" - Signer: the name of the person who is signing the file");
                    }
                    break;
                case "readsignedfile":
                    if (cmd.hasRequiredArguments(3, false)) {
                        byte[] publicBytes = FileControl.readKey(cmd.getArguments().get(0) + "public.key");
                        byte[] inputBytes = FileControl.readKey(cmd.getArguments().get(1));
                        byte[] signedBytes = FileControl.readKey(cmd.getArguments().get(2));

                        if (publicBytes != null && inputBytes != null) {
                            try {
                                KeyFactory factory = KeyFactory.getInstance(controller.ALGORITHM);
                                PublicKey pub = factory.generatePublic(new X509EncodedKeySpec(publicBytes));

                                System.out.println("Verification of the signed file " + (controller.verify(pub, inputBytes, signedBytes) ? "SUCCEEDED!" : "FAILED!"));
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                                Logger.getLogger(RSAPublicKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        System.out.println("Wrong amount of parameters for command. Required 2:");
                        System.out.println(" - Public: path defining where the public key is stored");
                        System.out.println(" - Input: file defining where the input file is stored");
                        System.out.println(" - Signed: file defining where the signed input file is stored");
                    }
                    break;
                case "exit":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Command not found!");
            }
        }

    }

    /**
     *
     * @param args
     * @return
     */
    public static Pair<String, List<String>> extractCommandArgument(String[] args) {
        if (args.length > 0) {
            String command = args[0];
            List<String> arguments = new ArrayList<>();

            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    arguments.add(args[i]);
                }
            }

            return new Pair<>(command, arguments);
        }

        return null;
    }

}
