/**
 * 
 */
package com.ivoslabs.chef.cook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivoslabs.chef.cook.utils.BiPath;

/**
 * @author ivoslabs.com
 *
 */
public class Recipe {

    /** */
    private String fileName;

    /** */
    private String environments;

    /** */
    private List<String> ingredients = new ArrayList<String>();

    /** */
    private List<BiPath> toMove = new ArrayList<BiPath>();

    /** */
    private List<BiPath> toCopy = new ArrayList<BiPath>();

    /** */
    private List<BiPath> toUnzip = new ArrayList<BiPath>();

    /** */
    private Map<String, String> values = new HashMap<String, String>();

    /**
     * Gets the String
     *
     * @return {@code String} The fileName
     */
    public String getFileName() {
	return this.fileName;
    }

    /**
     * Sets the fileName
     *
     * @param fileName {@code String} The fileName to set
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /**
     * Gets the String
     *
     * @return {@code String} The environments
     */
    public String getEnvironments() {
	return this.environments;
    }

    /**
     * Sets the environments
     *
     * @param environments {@code String} The environments to set
     */
    public void setEnvironments(String environments) {
	this.environments = environments;
    }

    /**
     * Gets the List<String>
     *
     * @return {@code List<String>} The ingredients
     */
    public List<String> getIngredients() {
	return this.ingredients;
    }

    /**
     * Sets the ingredients
     *
     * @param ingredients {@code List<String>} The ingredients to set
     */
    public void setIngredients(List<String> ingredients) {
	this.ingredients = ingredients;
    }

    /**
     * Gets the List<BiPath>
     *
     * @return {@code List<BiPath>} The toMove
     */
    public List<BiPath> getToMove() {
	return this.toMove;
    }

    /**
     * Sets the toMove
     *
     * @param toMove {@code List<BiPath>} The toMove to set
     */
    public void setToMove(List<BiPath> toMove) {
	this.toMove = toMove;
    }

    /**
     * Gets the List<BiPath>
     *
     * @return {@code List<BiPath>} The toCopy
     */
    public List<BiPath> getToCopy() {
	return this.toCopy;
    }

    /**
     * Sets the toCopy
     *
     * @param toCopy {@code List<BiPath>} The toCopy to set
     */
    public void setToCopy(List<BiPath> toCopy) {
	this.toCopy = toCopy;
    }

    /**
     * Gets the List<BiPath>
     *
     * @return {@code List<BiPath>} The toUnzip
     */
    public List<BiPath> getToUnzip() {
	return this.toUnzip;
    }

    /**
     * Sets the toUnzip
     *
     * @param toUnzip {@code List<BiPath>} The toUnzip to set
     */
    public void setToUnzip(List<BiPath> toUnzip) {
	this.toUnzip = toUnzip;
    }

    /**
     * Gets the Map<String,String>
     *
     * @return {@code Map<String,String>} The values
     */
    public Map<String, String> getValues() {
	return this.values;
    }

    /**
     * Sets the values
     *
     * @param values {@code Map<String,String>} The values to set
     */
    public void setValues(Map<String, String> values) {
	this.values = values;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Recipe [environments=" + environments + ", ingredients=" + ingredients + ", toMove=" + toMove + ", toCopy=" + toCopy + ", toUnzip=" + toUnzip + ", values=" + values + "]";
    }

}
