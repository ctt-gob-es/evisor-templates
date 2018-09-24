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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.clickPanel.ClickPanelConnector.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.clickPanel;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;

import es.gob.afirma.evisortemplates.components.impl.ClickPanel;

/** 
 * <p>Class connector for click panel component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
@Connect(ClickPanel.class)
public class ClickPanelConnector extends AbstractExtensionConnector {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7290706456777629724L;

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.extensions.AbstractExtensionConnector#extend(com.vaadin.client.ServerConnector)
	 */
	@Override
	protected final void extend(final ServerConnector target) {
		// Get the extended widget
		final Widget pw = ((ComponentConnector) target).getWidget();

		pw.addDomHandler(new MouseDownHandler() {

			/**
			 * {@inheritDoc}
			 * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
			 */
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if (event.getNativeButton() == (NativeEvent.BUTTON_RIGHT)) {
					ClickPanelServerRpc rpc = getRpcProxy(ClickPanelServerRpc.class);
					rpc.mouseClick(pw.getOffsetWidth(), pw.getOffsetHeight());
				}
			}
		}, MouseDownEvent.getType());

		pw.addAttachHandler(new AttachEvent.Handler() {

			/**
			 * {@inheritDoc}
			 * @see com.google.gwt.event.logical.shared.AttachEvent.Handler#onAttachOrDetach(com.google.gwt.event.logical.shared.AttachEvent)
			 */
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				ClickPanelServerRpc rpc = getRpcProxy(ClickPanelServerRpc.class);
				rpc.resize(pw.getOffsetWidth(), pw.getOffsetHeight());

			}
		});

	}
}
