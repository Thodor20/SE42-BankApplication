/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Thom.commands;

/**
 *
 * @author Thom van den Akker
 */
public class CommandBuilder {

    private final String input;

    private String delimeter;
    private ConversionType type;

    /**
     * Creates a new command builder with the given string command.
     *
     * @param input The full string command
     */
    public CommandBuilder(String input) {
        this.input = input;
        delimeter = " ";
        type = ConversionType.NONE;
    }

    /**
     * Sets the delimeter used to determine arguments. Defaults to space.
     *
     * @param delimeter The new delimeter
     * @return the builder object to chain commands.
     */
    public CommandBuilder setDelimeter(String delimeter) {
        this.delimeter = delimeter;
        return this;
    }

    /**
     * Sets the lowercase conversion type for all words in the command. Defaults
     * to ConversionType.NONE.
     *
     * @param type The new conversion type.
     * @return the builder object to chain commands.
     */
    public CommandBuilder setConversionType(ConversionType type) {
        this.type = type;
        return this;
    }

    /**
     * Builds the command object with the given settings.
     *
     * @return the generated command object.
     * @throws IllegalArgumentException If the initial input string was empty.
     */
    public Command build() throws IllegalArgumentException {
        return new Command(input, delimeter, type);
    }

}
