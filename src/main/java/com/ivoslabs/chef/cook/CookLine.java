/**
 * 
 */
package com.ivoslabs.chef.cook;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.chef.cook.utils.BiPath;
import com.ivoslabs.chef.cook.utils.Constants;
import com.ivoslabs.chef.cook.utils.Log;
import com.ivoslabs.chef.cook.utils.Util;
import com.ivoslabs.chef.exceptions.ChefException;

/**
 * @author ivoslabs.com
 *
 */
public class CookLine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookLine.class);

    /** Property separator */
    private static final String EQ = "=";

    /** Paths separator */
    private static final String TO = " to ";

    /** */
    public static final String EXT_INGR = ".ingr";

    /** */
    public static final String EXT_RECIPE = ".recipe";

    private Map<String, String> envValues;

    /**
     * 
     * @param environment
     * @return
     */
    public Recipe readRecipe(String environment) {
	Recipe recipe = new Recipe();

	List<String> content = this.readRecipeFile(recipe);

	char section = ' ';

	for (String line : content) {

	    if (line.trim().isEmpty() || line.startsWith("#")) {
		// do nothing
	    } else if (line.startsWith("environments=") && !line.endsWith("=")) {
		recipe.setEnvironments(line.split(EQ)[1]);
	    } else if (line.startsWith(";ingredients")) {
		section = 'I';
	    } else if (line.startsWith(";values")) {
		section = 'V';
	    } else if (line.startsWith(";unzip")) {
		section = 'U';
	    } else if (line.startsWith(";copy")) {
		section = 'C';
	    } else if (line.startsWith(";move")) {
		section = 'M';
	    } else {
		String prop[];
		switch (section) {
		case 'I':
		    recipe.getIngredients().add(line);
		    break;
		case 'V':
		    // values
		    int idx = line.indexOf(EQ);
		    String k = line.substring(0, idx).trim();
		    String v = line.substring(idx + 1);
		    prop = line.split(EQ);
		    recipe.getValues().put(k, v);
		    break;
		case 'U':
		    // to unzip
		    prop = line.split(TO);
		    recipe.getToUnzip().add(new BiPath(prop[0].trim(), prop[1].trim()));
		    break;
		case 'C':
		    // to copy
		    prop = line.split(TO);
		    recipe.getToCopy().add(new BiPath(prop[0].trim(), prop[1].trim()));
		    break;
		case 'M':
		    // to move
		    prop = line.split(TO);
		    recipe.getToMove().add(new BiPath(prop[0].trim(), prop[1].trim()));
		    break;
		default:
		    break;
		}
	    }

	}

	if (recipe.getEnvironments() == null || recipe.getEnvironments().trim().isEmpty()) {
	    throw new ChefException("The recipe " + recipe.getFileName() + " has no environments configured.");
	}

	Log.info(LOGGER, " using recipe: {}", recipe.getFileName());
	LOGGER.debug("  recipe: {}", recipe);

	this.envValues = this.getEnvValues(recipe, environment);

	for (BiPath p : recipe.getToUnzip()) {
	    this.updateVarsPath(p);
	}

	for (BiPath p : recipe.getToCopy()) {
	    this.updateVarsPath(p);
	}

	for (BiPath p : recipe.getToMove()) {
	    this.updateVarsPath(p);
	}

	return recipe;
    }

    private void updateVarsPath(BiPath p) {
	String orig = p.getPathOrigin();
	String dest = p.getPathDest();
	// replace vals
	Set<String> keys = this.envValues.keySet();
	for (String key : keys) {
	    String val = this.envValues.get(key);
	    orig = Util.replaceKey(orig, key, val);
	    dest = Util.replaceKey(dest, key, val);
	}
	p.setPathDest(dest);
	p.setPathOrigin(orig);
    }

    /**
     * 
     * @param toUnzip
     */
    public void unzip(List<BiPath> toUnzip) {
	LOGGER.info("Start unzips");
	if (!toUnzip.isEmpty()) {
	    for (BiPath p : toUnzip) {
		Util.unzip(p.getPathOrigin(), p.getPathDest());
	    }
	} else {
	    LOGGER.info("  No files to unzip");
	}
	LOGGER.info(" End unzips");
    }

    /**
     * 
     * @param toCopy
     */
    public void copy(List<BiPath> toCopy) {
	LOGGER.info("Start copies");
	if (!toCopy.isEmpty()) {
	    for (BiPath p : toCopy) {
		Util.copy(p.getPathOrigin(), p.getPathDest());
	    }
	} else {
	    LOGGER.info("  No files to copy");
	}
	LOGGER.info(" End copies");
    }

    /**
     * 
     * @param toMove
     */
    public void move(List<BiPath> toMove) {
	LOGGER.info("Start moves");
	if (!toMove.isEmpty()) {
	    for (BiPath p : toMove) {
		Util.move(p.getPathOrigin(), p.getPathDest());
	    }
	} else {
	    LOGGER.info("  No files to move");
	}

	LOGGER.info(" End moves");
    }

    /**
     * 
     * @param recipe
     * @param environment
     */
    public void cookIngredients(Recipe recipe, String environment) {
	LOGGER.info("Start cooking the ingredients  ");

	List<String> ingredientes = recipe.getIngredients();

	if (!ingredientes.isEmpty()) {
	    for (String ing : ingredientes) {
		String ingredient = ing;
		LOGGER.info("  Cooking '{}'", ing);
		this.cookIngredient(ingredient, this.envValues);

	    }
	} else {
	    LOGGER.info("  No ingredients to cook");
	}

	LOGGER.info(" End cooking the ingredients  ");

    }

    /**
     * 
     * @param ingredient
     * @param needed
     * @param replacement
     */
    public void cookIngredient(String ingredient, Map<String, String> values) {

	List<String> fileContent = Util.readFile(ingredient + EXT_INGR);
	StringBuilder c = new StringBuilder();
	for (String line : fileContent) {
	    c.append(line).append(Constants.LINE_SEPARATOR);
	}

	String content = c.toString();
	// replace vals
	Set<String> keys = values.keySet();
	for (String key : keys) {
	    String val = values.get(key);
	    content = Util.replaceKey(content, key, val);
	}

	Util.writeFile(content, ingredient);
    }

    /**
     * 
     */

    /*************************
     * Start private methods *
     *************************/

    /**
     * 
     * @param environment
     * @return
     */
    private Map<String, String> getEnvValues(Recipe recipe, String environment) {

	Map<String, String> vals = new HashMap<String, String>();

	int indx = this.getEnvironmentIndex(recipe, environment);

	Set<String> keys = recipe.getValues().keySet();
	for (String key : keys) {
	    String fullValue = recipe.getValues().get(key);
	    String val;
	    try {
		val = Util.splitByPipe(fullValue, indx);
	    } catch (Exception e) {
		throw new ChefException("value for '" + key + "' in environment :'" + environment + "' not configured", e);
	    }
	    vals.put(key, val);
	}

	return vals;
    }

    /**
     * 
     * @param recipe
     * @param environment
     * @return
     */
    private int getEnvironmentIndex(Recipe recipe, String environment) {
	String envs[] = recipe.getEnvironments().split("\\|");
	int indx = Arrays.asList(envs).indexOf(environment);
	if (indx == -1) {
	    throw new ChefException("environment :'" + environment + "' not configured in '" + recipe.getFileName() + "'");
	}
	return indx;
    }

    /**
     * 
     */

    /**
     * 
     * @return
     */
    private List<String> readRecipeFile(Recipe recipe) {
	String files[] = new File(".").list();
	String recipeFile = null;
	if (files != null) {
	    for (String string : files) {
		LOGGER.trace("files: {}", string);
		if (string.endsWith(EXT_RECIPE)) {
		    recipeFile = string;
		    break;
		}
	    }

	}
	if (recipeFile == null) {
	    throw new ChefException("No was found any .recipe file");
	}

	recipe.setFileName(recipeFile);

	return Util.readFile(recipeFile);
    }
}
