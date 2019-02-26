/**
 * 
 */
package com.ivoslabs.chef.cook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.chef.cook.utils.Log;
import com.ivoslabs.chef.cook.utils.Util;
import com.ivoslabs.chef.exceptions.ChefException;

/**
 * @author ivoslabs.com
 *
 */
public class Chef {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Chef.class);

    /**
     * 
     * @param environment
     */
    public void prepare(String environment) {

	Log.info(LOGGER, "Start preparation of  {}", environment);
	CookLine cookLine = new CookLine();
	Recipe recipe = cookLine.readRecipe(environment);

	cookLine.unzip(recipe.getToUnzip());

	cookLine.cookIngredients(recipe, environment);

	cookLine.copy(recipe.getToCopy());
	cookLine.move(recipe.getToMove());

	Log.info(LOGGER, "{} is ready", environment);
    }

    public void createRecipe(String project) {
	Log.info(LOGGER, "Start creation of  {}.recipe", project);
	Log.logDots();
	String recipe = Util.readFromInputStream(Chef.class.getResourceAsStream("/demo/demo.recipe"));
	recipe = recipe.replaceAll("\\{\\{project\\}\\}", project);
	Util.writeFile(recipe, project + CookLine.EXT_RECIPE);

	String xml = Util.readFromInputStream(Chef.class.getResourceAsStream("/demo/demo.xml.ingr"));
	Util.writeFile(xml, project + ".xml" + CookLine.EXT_INGR);

	String props = Util.readFromInputStream(Chef.class.getResourceAsStream("/demo/demo.properties.ingr"));
	Util.writeFile(props, project + ".properties" + CookLine.EXT_INGR);

	Log.info(LOGGER, "Recipe {}.recipe is ready", project);
    }

    /**
     * 
     * @param ingredient
     * @param keyValues
     */
    public void cookIngrediente(String ingredient, List<String> keyValues) {
	Log.info(LOGGER, "Start cook of  ingredient {} ", ingredient);
	Log.logDots();
	CookLine cookLine = new CookLine();

	Map<String, String> values = new HashMap<String, String>();

	for (String keyValue : keyValues) {

	    String key;
	    String value;

	    try {
		key = Util.splitByPipe(keyValue, 0);
		value = Util.splitByPipe(keyValue, 1);
	    } catch (Exception e) {
		throw new ChefException("Invalid argument '" + keyValue + "' ", e);
	    }

	    values.put(key, value);
	}

	cookLine.cookIngredient(ingredient, values);
	Log.info(LOGGER, "Ingredient {} is ready", ingredient);
    }

}
