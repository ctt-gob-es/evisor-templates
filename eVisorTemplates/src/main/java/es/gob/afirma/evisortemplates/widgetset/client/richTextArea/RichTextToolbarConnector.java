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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.richTextArea.RichTextToolbarConnector.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.richTextArea;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import es.gob.afirma.evisortemplates.components.impl.RichTextToolbar;

/** 
 * <p>Class connector for RichTextToolbar.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
@Connect(RichTextToolbar.class)
public class RichTextToolbarConnector extends AbstractComponentConnector {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8514369094621292101L;

	/**
	 * Constructor method for the class RichTextToolbarConnector.java. 
	 */
	public RichTextToolbarConnector() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#createWidget()
	 */
	@Override
	protected final Widget createWidget() {
		return GWT.create(VRichTextToolbar.class);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#getWidget()
	 */
	@Override
	public final VRichTextToolbar getWidget() {
		return (VRichTextToolbar) super.getWidget();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#getState()
	 */
	@Override
	public final RichTextToolbarState getState() {
		return (RichTextToolbarState) super.getState();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#onStateChanged(com.vaadin.client.communication.StateChangeEvent)
	 */
	@Override
	public final void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		final boolean singleLine = getState().isSingleLinePanel();
		getWidget().setSingleLinePanel(singleLine);
	}
}
