/**
 * 
 */
package com.ivoslabs.chef.exceptions;

/**
 * @author ivoslabs.com
 *
 */
public class ChefException extends RuntimeException {

    /** */
    private static final long serialVersionUID = -1034897190745766940L;

    /**
     * 
     */
    protected ChefException() {
	super();
    }

    /**
     * 
     * @param message
     */
    public ChefException(String message) {
	super(message);
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public ChefException(String message, Throwable cause) {
	super(message, cause);
    }

}
