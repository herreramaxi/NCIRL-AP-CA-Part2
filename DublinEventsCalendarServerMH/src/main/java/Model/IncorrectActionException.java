/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Maximiliano Herrera
 */
public class IncorrectActionException extends Exception {

    public IncorrectActionException(String errorMessage) {
        super(errorMessage);
    }
    
}
