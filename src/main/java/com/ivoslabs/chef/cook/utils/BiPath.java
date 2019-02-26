/**
 * 
 */
package com.ivoslabs.chef.cook.utils;

/**
 * @author ivoslabs.com
 *
 */
public class BiPath {

    /** */
    private String pathOrigin;

    /** */
    private String pathDest;
    
    
    

    /**
     * @param pathOrigin
     * @param pathDest
     */
    public BiPath(String pathOrigin, String pathDest) {
	super();
	this.pathOrigin = pathOrigin;
	this.pathDest = pathDest;
    }

    /**
     * Gets the String
     *
     * @return {@code String} The pathOrigin
     */
    public String getPathOrigin() {
	return this.pathOrigin;
    }

    /**
     * Sets the pathOrigin
     *
     * @param pathOrigin {@code String} The pathOrigin to set
     */
    public void setPathOrigin(String pathOrigin) {
	this.pathOrigin = pathOrigin;
    }

    /**
     * Gets the String
     *
     * @return {@code String} The pathDest
     */
    public String getPathDest() {
	return this.pathDest;
    }

    /**
     * Sets the pathDest
     *
     * @param pathDest {@code String} The pathDest to set
     */
    public void setPathDest(String pathDest) {
	this.pathDest = pathDest;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "BiPath [pathOrigin=" + pathOrigin + ", pathDest=" + pathDest + "]";
    }
    
    

}
