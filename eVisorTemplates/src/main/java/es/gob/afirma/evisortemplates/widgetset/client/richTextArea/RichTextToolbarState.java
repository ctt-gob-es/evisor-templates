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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.richTextArea.RichTextToolbarState.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.richTextArea;

/** 
 * <p>Class that represents RichTextToolbar state to communicate with widget.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
public class RichTextToolbarState extends com.vaadin.shared.AbstractComponentState {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -569729016880898240L;

	/**
	 * Attribute that represents if the panel shows content in one line. 
	 */
	private boolean singleLinePanel = false;

	/**
	 * Method that checks if the panel shows content in one line.
	 * @return true if the panel shows content in one line, false otherwise
	 */
	public final boolean isSingleLinePanel() {
		return singleLinePanel;
	}

	/**
	 * Method that sets if the panel shows content in one line.
	 * @param singleLinePanelParam true if the panel shows content in one line
	 */
	public final void setSingleLinePanel(boolean singleLinePanelParam) {
		this.singleLinePanel = singleLinePanelParam;
	}

}
