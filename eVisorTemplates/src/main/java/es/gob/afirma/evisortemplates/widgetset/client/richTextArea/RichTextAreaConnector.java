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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.richTextArea.RichTextAreaConnector.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.richTextArea;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.Event;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.client.ui.ShortcutActionHandler.BeforeShortcutActionListener;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.Connect.LoadStyle;
import com.vaadin.shared.util.SharedUtil;

import es.gob.afirma.evisortemplates.components.impl.RichTextArea;

/** 
 * <p>Class connector for RichTextArea.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
@Connect(value = RichTextArea.class, loadStyle = LoadStyle.LAZY)
public class RichTextAreaConnector extends AbstractFieldConnector implements Paintable, BeforeShortcutActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5911651771025729551L;
	
	/**
	 * Last value received from the server.
	 */
	private String cachedValue = "";

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractConnector#init()
	 */
	@Override
	protected final void init() {
		getWidget().addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				flush();
			}
		});
	}

	/**
	 * Method that gets the max length attribute from component.
	 * @param uidl uidl from view
	 * @return max length attribute from component
	 */
	private int getNewMaxLength(UIDL uidl) {
		return uidl.hasAttribute("maxLength") ? uidl.getIntAttribute("maxLength") : -1;
	}

	/**
	 * Method that gets the auto grow width attribute from component.
	 * @param uidl uidl from view
	 * @return auto grow width attribute from component
	 */
	private boolean getAutoGrowWidth(UIDL uidl) {
		return uidl.hasAttribute("autoGrowWidth") ? uidl.getBooleanAttribute("autoGrowWidth") : false;
	}

	/**
	 * Method that gets the auto grow height attribute from component.
	 * @param uidl uidl from view
	 * @return auto grow height attribute from component
	 */
	private boolean getAutoGrowHeight(UIDL uidl) {
		return uidl.hasAttribute("autoGrowHeight") ? uidl.getBooleanAttribute("autoGrowHeight") : false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.Paintable#updateFromUIDL(com.vaadin.client.UIDL, com.vaadin.client.ApplicationConnection)
	 */
	@Override
	public final void updateFromUIDL(final UIDL uidl, ApplicationConnection client) {
		getWidget().setClient(client);
		getWidget().setId(uidl.getId());

		if (uidl.hasAttribute("internalFormatter")) {
			getWidget().setInternalFormatter(uidl.getBooleanAttribute("internalFormatter"));
		}
		if (uidl.hasAttribute("toolbar")) {
			RichTextToolbarConnector rttc = (RichTextToolbarConnector) uidl.getPaintableAttribute("toolbar", client);
			getWidget().setFormatter(rttc.getWidget());
		}

		int newMaxLength = getNewMaxLength(uidl);
		boolean autoGrowWidth = getAutoGrowWidth(uidl);
		boolean autoGrowHeight = getAutoGrowHeight(uidl);
		if (newMaxLength >= 0 || autoGrowHeight || autoGrowWidth) {
			if (getWidget().getKeyPressHandler() == null) {
				getWidget().setKeyPressHandler(getWidget().getRta().addKeyPressHandler(getWidget()));
			}
			getWidget().setMaxLength(newMaxLength);
		} else if (getWidget().getKeyPressHandler() != null) {
			getWidget().getElement().setAttribute("maxlength", "");
			getWidget().getKeyPressHandler().removeHandler();
			getWidget().setKeyPressHandler(null);
		}
		getWidget().setMaxLength(newMaxLength);
		getWidget().setAutoGrowWidth(autoGrowWidth);
		getWidget().setAutoGrowHeight(autoGrowHeight);

		evalAtributes(uidl);

		if (!isRealUpdate(uidl)) {
			return;
		}
		getWidget().setEnabled(isEnabled());
		getWidget().setReadOnly(isReadOnly());
		getWidget().setImmediate(getState().immediate);

	}

	
	/**
	 * Method that evaluates attributes from view.
	 * @param uidl uidl from view
	 */
	private void evalAtributes(UIDL uidl) {
		if (uidl.hasAttribute("selectAll")) {
			getWidget().selectAll();
		}

		if (uidl.hasVariable("text")) {
			String newValue = uidl.getStringVariable("text");
			if (!SharedUtil.equals(newValue, cachedValue)) {
				getWidget().setValue(newValue);
				cachedValue = newValue;
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.ShortcutActionHandler.BeforeShortcutActionListener#onBeforeShortcutAction(com.google.gwt.user.client.Event)
	 */
	@Override
	public final void onBeforeShortcutAction(Event e) {
		flush();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#getWidget()
	 */
	@Override
	public final VRichTextArea getWidget() {
		return (VRichTextArea) super.getWidget();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#flush()
	 */
	@Override
	public final void flush() {
		if (getConnection() != null && getConnectorId() != null) {
			final String html = getWidget().getSanitizedValue();
			if (!html.equals(cachedValue)) {
				cachedValue = html;
				getConnection().updateVariable(getConnectorId(), "text", html, getState().immediate);
			}
		}
	};

}
