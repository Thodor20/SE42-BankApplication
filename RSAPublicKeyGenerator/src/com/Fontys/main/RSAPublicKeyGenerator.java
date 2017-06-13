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
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
                case "signInputFile":

                    break;
                case "readSignedFile":

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
