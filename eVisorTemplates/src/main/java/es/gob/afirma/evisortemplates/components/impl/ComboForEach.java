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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.ComboForEach.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import com.vaadin.ui.ComboBox;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents "for each" combo box.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class ComboForEach extends ComboBox {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7449730322930154989L;

	/**
	 * Attribute that represents IndividualSignature String constant. 
	 */
	public static final String INDIVIDUALSIGNATURE = "IndividualSignature";
	
	/**
	 * Attribute that represents Barcode String constant. 
	 */
	public static final String BARCODE = "Barcode";

	/**
	 * Constructor method for the class ComboForEach.java. 
	 */
	public ComboForEach() {
		super();
		setCaption(MessagesEVT.getInstance().getProperty("comboForEachMessage"));
		setNullSelectionAllowed(false);
		addItem(INDIVIDUALSIGNATURE);
		addItem(BARCODE);
		setValue(INDIVIDUALSIGNATURE);

	}

}
