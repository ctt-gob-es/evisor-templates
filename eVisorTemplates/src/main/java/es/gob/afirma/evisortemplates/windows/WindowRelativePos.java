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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.WindowRelativePos.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Window;

/** 
 * <p>Class that represents positioned windows´s common methods.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class WindowRelativePos extends Window {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1058569950299580713L;

	/**
	 * Attribute that represents x coordinate. 
	 */
	private float posX;
	
	/**
	 * Attribute that represents y coordinate. 
	 */
	private float posY;

	/**
	 * Constructor method for the class WindowRelativePos.java.
	 * @param string window caption
	 */
	public WindowRelativePos(String string) {
		super(string);
	}

	/**
	 * Constructor method for the class WindowRelativePos.java. 
	 */
	public WindowRelativePos() {
		super();
	}

	/**
	 * Method that gets x coordinate.
	 * @return x coordinate
	 */
	public final float getPosX() {
		return posX;
	}

	/**
	 * Method that sets x coordinate.
	 * @param posXParam new x coordinate
	 */
	public final void setPosX(float posXParam) {
		this.posX = posXParam;
	}

	/**
	 * Method that gets y coordinate.
	 * @return y coordinate
	 */
	public final float getPosY() {
		return posY;
	}

	/**
	 * Method that sets y coordinate.
	 * @param posYParam new y coordinate
	 */
	public final void setPosY(float posYParam) {
		this.posY = posYParam;
	}

}