/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonclient;

/**
 *
 * @author aschekelmann
 */

public record EintragTO(
    int eintragid,
    String begriff,
    String definition,
    int autorid,
    String autorname
) {}