package com.ivoslabs.chef;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.chef.cook.Chef;
import com.ivoslabs.chef.cook.utils.Log;

/**
 * @author ivoslabs.com
 *
 * 
 */
public class ChefMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChefMain.class);

    public static void main(String[] args) {

	try {

	    if (args != null && args.length == 1 && args[0].equalsIgnoreCase("-help")) {
		ChefMain.printHelp();
	    } else if (args != null && args.length > 0) {
		if (args[0].startsWith("prepare:") && !args[0].endsWith(":")) {
		    String env = args[0].split(":")[1];
		    new Chef().prepare(env);
		} else if (args[0].startsWith("create:") && !args[0].endsWith(":")) {
		    String project = args[0].split(":")[1];
		    new Chef().createRecipe(project);
		} else if (args.length > 1 && args[0].startsWith("cook:") && !args[0].endsWith(":")) {
		    String file = args[0].substring(args[0].indexOf(":") + 1);
		    List<String> kvs = new LinkedList<String>(Arrays.asList(args));
		    kvs.remove(0);
		    new Chef().cookIngrediente(file, kvs);
		} else {
		    Log.error(LOGGER, "Invalid arguments");
		    ChefMain.printHelp();
		}
	    } else {
		Log.error(LOGGER, "Invalid arguments");
		ChefMain.printHelp();
	    }

	} catch (Exception e) {
	    Log.error(LOGGER, e);
	}

    }

    public static void printHelp() {
	System.err.println("        Help");
	System.err.println("         Creating recipe template");
	System.err.println("            chef create:[recipe name]");
	System.err.println("            e.j: chef create:projectX ");
	System.err.println("         Preparing environment");
	System.err.println("            chef prepare:[environment]");
	System.err.println("            e.j: chef prepare:QA ");
	System.err.println("         Cook ingredient");
	System.err.println("            chef cook:[ingredient] \"[key]|[value]\" \"[key]|[value]\" ...");
	System.err.println("            e.j: chef cook:conf/projectX.properties \"proxy.user|zxcvbn\" \"proxy.pass|pwd123\"");

    }
}
