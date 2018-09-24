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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.util.UtilsResources.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tika.Tika;

import com.vaadin.server.VaadinSession;

/** 
 * <p>Class that provides functionality to control resources..</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public final class UtilsResources {

	/**
	 * Constructor method for the class UtilsResources.java. 
	 */
	private UtilsResources() {
		super();
	}

	/**
	 * Method that handles the closing of a {@link OutputStream} resource.
	 * 
	 * @param os
	 *            Parameter that represents a {@link OutputStream} resource.
	 */
	public static void safeCloseOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * Method that handles the closing of a {@link InputStream} resource.
	 * 
	 * @param is
	 *            Parameter that represents a {@link InputStream} resource.
	 */
	public static void safeCloseInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * Method that returns mime type form byte [].
	 * @param data file representation
	 * @return mime type
	 */
	public static String getMimeType(byte[ ] data) {
		Tika t = new Tika();
		InputStream is = new ByteArrayInputStream(data);
		try {
			return t.detect(is);
		} catch (IOException e) {
			// LOGGER.error(
			// Language.getFormatResCoreGeneral("logUR02", new Object[0]),
			// e);
		} finally {
			safeCloseInputStream(is);
		}
		return "text/plain";
	}

	/**
	 * Method that creates a session temporal directory in disk.
	 * @return path of new directory
	 */
	public static String createSessionTempDir() {
		String tmppath = System.getProperty("java.io.tmpdir");
		File folder = new File(tmppath, VaadinSession.getCurrent().getSession().getId());
		if (!folder.exists()) {
			folder.mkdir();
		}
		return tmppath +File.separator+ VaadinSession.getCurrent().getSession().getId() + File.separator;
	}

}
