/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.exception;

/**
 *
 * @author marin
 */
public class PaiementIntrouvableException extends Exception {

    /**
     * Creates a new instance of <code>PaiementIntrouvableException</code>
     * without detail message.
     */
    public PaiementIntrouvableException() {
    }

    /**
     * Constructs an instance of <code>PaiementIntrouvableException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PaiementIntrouvableException(String msg) {
        super(msg);
    }
}
