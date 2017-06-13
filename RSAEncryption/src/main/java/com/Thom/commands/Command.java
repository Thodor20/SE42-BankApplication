/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Thom.commands;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
public class Command {

    private final String command;
    private final List<String> arguments;

    /**
     * Creates a new command object used in conjunction with a scanner.
     *
     * @param command The full string command. Arguments separated by delimeter
     * @param delimeter The set delimeter for arguments to function
     * @param type The conversiontype to handle which part of the command will
     * be converted to lowercase
     * @throws IllegalArgumentException Thrown when there are no words found in
     * the argument (empty string)
     */
    Command(String input, String delimeter, ConversionType type) throws IllegalArgumentException {
        // Split the string on every space
        String[] words = input.split(delimeter);

        // In case there are no words found, throw the exception
        if (words.length < 1) {
            throw new IllegalArgumentException("Parsed command does not contain a single word, therefore not a valid command.");
        }

        // Set the initial command to the first found word
        // and initialize the arguments list
        command = type == ConversionType.ALL || type == ConversionType.COMMAND_ONLY ? words[0].toLowerCase() : words[0];
        arguments = new ArrayList<>();

        // If there is more then one word, loop over the remaining
        // arguments and add them to the list
        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                arguments.add(type == ConversionType.ALL || type == ConversionType.ARGS_ONLY ? words[i].toLowerCase() : words[0]);
            }
        }
    }

    /**
     * Gets the initial command from the input string.
     *
     * @return The initial command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets an unmodifiable list of all arguments.
     *
     * @return An unmodifiable list
     */
    public List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    /**
     * Checks if the command has a certain amount of variables.
     *
     * @param amount The (minimum) amount of arguments needed for this command
     * @param extended False if the set amount must <b>exactly</b> match the
     * amount of arguments. True if more arguments are allowed, for example for
     * unknown sizes of lists.
     * @return True if the minimum amount of arguments is met
     */
    public boolean hasRequiredArguments(int amount, boolean extended) {
        if (arguments.size() < amount) {
            return false;
        }

        if (!extended) {
            return arguments.size() == amount;
        }

        return true;
    }

}
