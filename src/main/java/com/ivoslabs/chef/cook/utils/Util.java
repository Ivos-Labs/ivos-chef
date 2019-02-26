/**
 * 
 */
package com.ivoslabs.chef.cook.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.chef.cook.CookLine;
import com.ivoslabs.chef.exceptions.ChefException;

/**
 * @author ivoslabs.com
 *
 * 
 */
public class Util {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    /**
     * 
     * @param content
     * @param key
     * @param val
     * @return
     */
    public static String replaceKey(String content, String key, String val) {
	try {
	    String m = Matcher.quoteReplacement(val);
	    content = content.replaceAll("\\{\\{" + key + "\\}\\}", m);
	} catch (Exception e) {
	    throw new ChefException(e.getMessage(), e);
	}
	return content;
    }

    /**
     * 
     * @param fullValue
     * @param indx
     * @return
     */
    public static String splitByPipe(String fullValue, int indx) {
	boolean containsPipeEsc = fullValue.contains("\\|");
	String cmdn = "&vert;&#124;";
	if (containsPipeEsc) {
	    fullValue = fullValue.replaceAll("[\\\\][|]", cmdn);
	}

	String ar[] = fullValue.split("\\|", -1);
	String val = ar[indx];

	if (containsPipeEsc) {
	    val = val.replaceAll(cmdn, "|");
	}

	val = val.trim();
	return val;
    }

    /**
     * 
     * @param content
     * @param path
     */
    public static void writeFile(String content, String path) {

	BufferedWriter bw = null;
	FileWriter fw = null;

	try {

	    fw = new FileWriter(path);
	    bw = new BufferedWriter(fw);
	    bw.write(content);

	} catch (IOException e) {

	    throw new ChefException(e.getMessage(), e);

	} finally {

	    try {

		if (bw != null)
		    bw.close();

		if (fw != null)
		    fw.close();

	    } catch (IOException ex) {

		throw new ChefException(ex.getMessage(), ex);

	    }

	}
    }

    /**
     * 
     * @param path
     * @return
     */
    public static List<String> readFile(String path) {

	List<String> recipe = new ArrayList<String>();
	BufferedReader br = null;
	FileReader fr = null;

	try {

	    try {
		fr = new FileReader(path);
	    } catch (FileNotFoundException e) {
		if (path.endsWith(CookLine.EXT_INGR)) {
		    throw new ChefException("   The ingredient '" + path + "' was not found");
		} else {
		    throw new ChefException(e.getMessage(), e);
		}
	    }

	    br = new BufferedReader(fr);

	    String sCurrentLine;

	    while ((sCurrentLine = br.readLine()) != null) {
		recipe.add(sCurrentLine);
	    }

	} catch (IOException e) {
	    throw new ChefException(e.getMessage(), e);
	} finally {

	    try {

		if (br != null)
		    br.close();

		if (fr != null)
		    fr.close();

	    } catch (IOException ex) {

		throw new ChefException(ex.getMessage(), ex);

	    }

	}
	return recipe;
    }

    /**
     * 
     * @param pathOrigin
     * @param pathDes
     */
    public static void copy(String pathOrigin, String pathDes) {

	LOGGER.info("  Copy: '{}' to  '{}'", pathOrigin, pathDes);

	InputStream inStream = null;
	OutputStream outStream = null;

	try {

	    File afile = new File(pathOrigin);
	    File bfile = new File(pathDes);

	    new File(bfile.getParent()).mkdirs();

	    inStream = new FileInputStream(afile);
	    outStream = new FileOutputStream(bfile);

	    byte[] buffer = new byte[1024];

	    int length;
	    // copy the file content in bytes
	    while ((length = inStream.read(buffer)) > 0) {

		outStream.write(buffer, 0, length);

	    }

	    inStream.close();
	    outStream.close();

	} catch (IOException e) {
	    throw new ChefException(e.getMessage(), e);
	}
    }

    /**
     * 
     * @param pathOrigin
     * @param pathDes
     */
    public static void move(String pathOrigin, String pathDes) {
	LOGGER.info("  Move: '{}' to  '{}'", pathOrigin, pathDes);
	new File(new File(pathDes).getParent()).mkdirs();
	if (new File(pathDes).exists()) {
	    new File(pathDes).delete();
	}
	new File(pathOrigin).renameTo(new File(pathDes));
    }

    /**
     * Method that unzip files
     * 
     * @param zipFilePath
     * @param destDir
     */
    public static void unzip(String zipFilePath, String destDir) {

	LOGGER.info("  Unzip: '{}' into '{}'", zipFilePath, destDir);

	File dir = new File(destDir);
	// create output directory if it doesn't exist
	if (!dir.exists())
	    dir.mkdirs();
	FileInputStream fis;
	// buffer for read and write data to file
	byte[] buffer = new byte[1024];
	try {
	    fis = new FileInputStream(zipFilePath);
	    ZipInputStream zis = new ZipInputStream(fis);
	    ZipEntry ze = zis.getNextEntry();
	    while (ze != null) {
		String fileName = ze.getName();
		File newFile = new File(destDir + File.separator + fileName);

		// create directories for sub directories in zip
		new File(newFile.getParent()).mkdirs();
		FileOutputStream fos = new FileOutputStream(newFile);
		int len;
		while ((len = zis.read(buffer)) > 0) {
		    fos.write(buffer, 0, len);
		}
		fos.close();
		// close this ZipEntry
		zis.closeEntry();
		ze = zis.getNextEntry();
	    }
	    // close last ZipEntry
	    zis.closeEntry();
	    zis.close();
	    fis.close();
	} catch (IOException e) {
	    throw new ChefException(e.getMessage(), e);
	}

    }

    public static String readFromInputStream(InputStream inputStream) {
	StringBuilder resultStringBuilder = new StringBuilder();
	BufferedReader br = null;
	try {
	    br = new BufferedReader(new InputStreamReader(inputStream));

	    String line;
	    while ((line = br.readLine()) != null) {
		resultStringBuilder.append(line).append("\r\n");
	    }
	} catch (Exception e) {
	    throw new ChefException(e.getMessage(), e);
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (Exception e) {
		    throw new ChefException(e.getMessage(), e);
		}
	    }
	}
	return resultStringBuilder.toString();
    }
}
