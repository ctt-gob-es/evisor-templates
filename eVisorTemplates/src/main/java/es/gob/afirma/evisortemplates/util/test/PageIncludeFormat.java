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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.PageIncludeFormat.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

/** 
 * <p>Class that contains the format of a PDF page.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 10/03/2011.
 */
public class PageIncludeFormat {

	/**
	 * Attribute that represents the constant used to indicate the layout is front. 
	 */
	public static final String FRONT_LAYOUT = "front";
	/**
	 * Attribute that represents the constant used to indicate the layout is back. 
	 */
	public static final String BACK_LAYOUT = "back";

	/**
	 * Attribute that represents the Y position in millimeters. 
	 */
	private double ypos = 0;

	/**
	 * Attribute that represents the X position in millimeters. 
	 */
	private double xpos = 0;

	/**
	 * Attribute that represents the page width in millimeters. 
	 */
	private double width = 0;

	/**
	 * Attribute that represents the page height in millimeters. 
	 */
	private double height = 0;

	/**
	 * Attribute that represents the page layout. 
	 */
	private String layout = FRONT_LAYOUT;

	/**
	 * Gets the Y position.
	 * @return	Position in millimeters.
	 */
	public final double getYpos() {
		return ypos;
	}

	/**
	 * Gets the value of the page layout.
	 * @return the value of the page layout.
	 */
	public final String getLayout() {
		return layout;
	}

	/**
	 * Sets the value of the page layout.
	 * @param layer The value for the page layout.
	 */
	public final void setLayout(String layer) {
		this.layout = layer;
	}

	/**
	 * Sets the Y position in millimeters.
	 * @param pos	Y position in millimeters.
	 */
	public final void setYpos(double pos) {
		this.ypos = pos;
	}

	/**
	 * Gets the X position.
	 * @return	Position in millimeters.
	 */
	public final double getXpos() {
		return xpos;
	}

	/**
	 * Sets the X position.
	 * @param pos	Position in millimeters.
	 */
	public final void setXpos(double pos) {
		this.xpos = pos;
	}

	/**
	 * Gets the page width. 
	 * @return	Width in millimeters.
	 */
	public final double getWidth() {
		return width;
	}

	/**
	 * Sets the page width.
	 * @param pageWidth	Width in millimeters.
	 */
	public final void setWidth(double pageWidth) {
		this.width = pageWidth;
	}

	/**
	 * Gets the page height.
	 * @return	Height in millimeters.
	 */
	public final double getHeight() {
		return height;
	}

	/**
	 * Sets the page height.
	 * @param pageHeight	Height in millimeters.
	 */
	public final void setHeight(double pageHeight) {
		this.height = pageHeight;
	}

}
