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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.clickPanel.ClickPanelServerRpc.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.clickPanel;

import com.vaadin.shared.communication.ServerRpc;

/** 
 * <p>Interface that defines ClickPanelServerRpc methods .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public interface ClickPanelServerRpc extends ServerRpc {

	/**
	 * Method that manages mouse clicks.
	 * @param w width value
	 * @param h height value
	 */
	void mouseClick(int w, int h);

	/**
	 * Method that manages resize operations.
	 * @param w width value
	 * @param h height value
	 */
	void resize(int w, int h);

}
