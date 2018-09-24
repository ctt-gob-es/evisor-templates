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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichTextToolbar.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import es.gob.afirma.evisortemplates.widgetset.client.richTextArea.RichTextToolbarState;

/** 
 * <p>Class that represents the server-side UI component that provides public API for RichTextToolbar.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class RichTextToolbar extends com.vaadin.ui.AbstractComponent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1235679784710966253L;

	/**
	 * Constructor method for the class RichTextToolbar.java. 
	 */
	public RichTextToolbar() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#getState()
	 */
	@Override
	public final RichTextToolbarState getState() {
		return (RichTextToolbarState) super.getState();
	}

	/**
	 * Attribute that represents if tool bar is in a single line. 
	 */
	private boolean singleLinePanel = false;

	/**
	 * Method that checks if tool bar is in a single line.
	 * @return true if tool bar is in a single line, false otherwise
	 */
	public final boolean isSingleLinePanel() {
		return singleLinePanel;
	}

	/**
	 * Method that sets if tool bar is in a single line.
	 * @param singleLinePanelParam if tool bar is in a single line
	 */
	public final void setSingleLinePanel(boolean singleLinePanelParam) {
		this.singleLinePanel = singleLinePanelParam;
		getState().setSingleLinePanel(this.singleLinePanel);
	}
}