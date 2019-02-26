/**
 * 
 */
package com.ivoslabs.chef.cook.utils;

import static org.fusesource.jansi.Ansi.ansi;

import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;

/**
 * @author ivoslabs.com
 *
 */
public class Log {

    public static void error(Logger logger, Throwable e) {
	AnsiConsole.systemInstall();
	System.out.print(ansi().fg(Color.RED).a(""));
	logger.error(e.getMessage(), e);
	System.out.print(ansi().fg(Color.BLACK).a("").reset());
	AnsiConsole.systemUninstall();
    }

    public static void error(Logger logger, String message, Object... values) {
	AnsiConsole.systemInstall();
	System.out.print(ansi().fg(Color.RED).a(""));
	logger.error(message, values);
	System.out.print(ansi().fg(Color.BLACK).a("").reset());
	AnsiConsole.systemUninstall();
    }

    public static void info(Logger logger, String message, Object... values) {
	AnsiConsole.systemInstall();
	System.out.print(ansi().fg(Color.GREEN).a(""));
	logger.info(message, values);
	System.out.print(ansi().fg(Color.BLACK).a("").reset());
	AnsiConsole.systemUninstall();
    }

    public static void logDots() {
	AnsiConsole.systemInstall();
	System.out.print(ansi().fg(Color.BLUE).a(""));
	System.out.println("    ...");
	System.out.println("    ...");
	System.out.print(ansi().fg(Color.BLACK).a("").reset());
	AnsiConsole.systemUninstall();
    }

}
