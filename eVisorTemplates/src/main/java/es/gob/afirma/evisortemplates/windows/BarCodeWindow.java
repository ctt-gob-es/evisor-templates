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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.BarCodeWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.components.impl.ComboCBType;
import es.gob.afirma.evisortemplates.components.impl.FormVariable;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;

/** 
 * <p>Class that represents the window for include bar codes.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class BarCodeWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5644831960149395702L;

	/**
	 * Attribute that represents variable's insert zone. 
	 */
	private FormVariable fv;
	
	/**
	 * Attribute that represents the accept button. 
	 */
	private Button acceptButton;
	
	/**
	 * Attribute that represents the message value for bar code generation. 
	 */
	private TextField codeMessage;
	
	/**
	 * Attribute that represents the combo box with bar code types. 
	 */
	private ComboCBType ctcb;

	/**
	 * Attribute that represents a flag indicating if is a "for each" variable message. 
	 */
	private boolean isForEach;

	/**
	 * Constructor method for the class BarCodeWindow.java.
	 * @param showIndexVars true if want show index in variable's zone
	 */
	public BarCodeWindow(Boolean showIndexVars) {

		super();

		setCaption(MessagesEVT.getInstance().getProperty("codeBarWindowCaption"));
		setModal(true);
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("barcodeWindowWidth")), Unit.PIXELS);

		final VerticalLayout layout = new VerticalLayout();

		layout.setSizeUndefined();

		ctcb = new ComboCBType();

		layout.addComponent(ctcb);

		codeMessage = new TextField("Message:");

		codeMessage.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("barcodeWindowMessageWidth")), Unit.PIXELS);

		layout.addComponent(codeMessage);

		fv = new FormVariable(Format.TYPEBARCODE, showIndexVars, false);

		fv.addClickAcceptListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 7909463955275136724L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (fv.isFullVar()) {
					codeMessage.setValue(fv.getVarFormed());
				}
				isForEach = fv.isForEach();
				if (isForEach) {
					Notification.show(MessagesEVT.getInstance().getProperty("noIndexVarInserted"));
				}

			}
		});

		layout.addComponent(fv);

		acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));

		layout.addComponent(acceptButton);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	/**
	 * Method that adds an accept button click listeener to window.
	 * @param listener accept button click listeener
	 */
	public final void addClickAcceptButtonListener(ClickListener listener) {
		acceptButton.addClickListener(listener);
	}

	/**
	 * Method that gets the message value for bar code generation.
	 * @return message value for bar code generation
	 */
	public final String getTextValue() {
		return codeMessage.getValue();
	}

	/**
	 * Method that gets the bar code type.
	 * @return bar code type
	 */
	public final String getCodeType() {
		return (String) ctcb.getValue();
	}

	/**
	 * Method that checks if is a "for each" variables insertion.
	 * @return true if is a "for each" variables insertion
	 */
	public final boolean isForEach() {
		return isForEach;
	}

}
