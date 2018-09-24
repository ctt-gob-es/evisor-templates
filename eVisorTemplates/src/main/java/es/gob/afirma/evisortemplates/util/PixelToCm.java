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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.util.PixelToCm.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;

/** 
 * <p>Class to convert pixels to centimeters and centimeters to pixels.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public final class PixelToCm {

	/**
	 * Constructor method for the class PixelToCm.java. 
	 */
	private PixelToCm() {
		super();
	}

	/**
	 * Convert pixels to centimeters.
	 * @param pixels pixels value
	 * @return centimeters value
	 */
	public static double pxToCm(double pixels) {
		return pxToMm(pixels) / NumberConstants.DNUM10;
	}

	/**
	 * Convert pixels to millimeters.
	 * @param pixels pixels value
	 * @return millimeters value
	 */
	public static double pxToMm(double pixels) {
		// 210mm = 840px
		return (pixels * Double.parseDouble(ConfigEVT.getInstance().getProperty("relMillimeters"))) / Double.parseDouble(ConfigEVT.getInstance().getProperty("relPixels"));
	}

	/**
	 * Convert millimeters to pixels. 
	 * @param mm millimeters value
	 * @return pixels value
	 */
	public static double mmToPx(double mm) {
		// 210mm = 840px
		return (mm * Double.parseDouble(ConfigEVT.getInstance().getProperty("relPixels"))) / Double.parseDouble(ConfigEVT.getInstance().getProperty("relMillimeters"));
	}

	/**
	 * Convert centimeters to pixels.
	 * @param cm centimeters value
	 * @return pixels value
	 */
	public static double cmToPx(double cm) {

		return mmToPx(cm * NumberConstants.DNUM10);
	}

}
