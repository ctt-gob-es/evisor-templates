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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.IncorrectMessageBarCodeException.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

/** 
 * <p>Class that represents code bar message's exceptions.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class IncorrectMessageBarCodeException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2750990810676940145L;

	/**
	 * Constructor method for the class IncorrectMessageBarCodeException.java.
	 * @param msg message for exception
	 * @param e exception
	 */
	public IncorrectMessageBarCodeException(String msg, Exception e) {
		super(msg, e);
	}

}
