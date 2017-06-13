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
public enum ConversionType {
    /**
     * Specifies that neither the command nor the arguments will be converted to
     * lowercase
     */
    NONE,
    /**
     * Specifies that both the command and the arguments will be converted to
     * lowercase
     */
    ALL,
    /**
     * Specifies that only the command will be converted to lowercase
     */
    COMMAND_ONLY,
    /**
     * Specifies that only the arguments will be converted to lowercase
     */
    ARGS_ONLY
}
