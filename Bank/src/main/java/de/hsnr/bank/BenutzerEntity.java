/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.io.Serializable;

/**
 *
 * @author jannn
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BenutzerEntity implements Serializable {

   private String Benutzername;
   private String  Passwort;
}
