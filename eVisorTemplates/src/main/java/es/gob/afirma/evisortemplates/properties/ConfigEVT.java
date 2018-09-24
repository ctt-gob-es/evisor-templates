/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2015 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.properties.ConfigEVT.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.properties;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/** 
 * <p>Class that solves configEVT.properies and return properties's values.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public final class ConfigEVT {

	/**
	 * Attribute that represents properties object. 
	 */
	private Properties properties = null;

	/** Configuration file name. */
	public static final String CONFIG_FILE_NAME = "configEVT.properties";
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(ConfigEVT.class);

	/**
	 * Constructor method for the class ConfigEVT.java. 
	 */
	private ConfigEVT() {
		this.properties = new Properties();
		try {
			properties.load(ConfigEVT.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException ex) {
			LOG.error(ex);
		}
	}

	/**
	 * Singleton instance of object.
	 * @return Singleton instance of object
	 */
	public static ConfigEVT getInstance() {
		return ConfigurationHolder.INSTANCE;
	}

	/** 
	 * <p>Inner class that represents configuration holder.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	private static class ConfigurationHolder {

		/**
		 * Attribute that represents configuration holder instance. 
		 */
		private static final ConfigEVT INSTANCE = new ConfigEVT();
	}

	/**
	 * Method that gets a properties value.
	 * @param key properties key
	 * @return properties value
	 */
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}
