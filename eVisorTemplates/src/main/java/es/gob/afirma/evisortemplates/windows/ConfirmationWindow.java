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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.ConfirmationWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that represents a confirmation window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class ConfirmationWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6019106024566010710L;
	
	/**
	 * Attribute that represents yes button. 
	 */
	private Button yes;

	/**
	 * Constructor method for the class ConfirmationWindow.java.
	 * @param value message to show
	 */
	public ConfirmationWindow(String value) {
		super();
		Button no;
		// valores por defecto
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("confirmationWindowWidth")), Unit.PIXELS);
		setModal(true);
		setResizable(false);

		VerticalLayout contenedor = new VerticalLayout();
		Label text = new Label(value);
		contenedor.addComponent(text);
		HorizontalLayout botonera = new HorizontalLayout();
		botonera.setWidth(NumberConstants.FNUM60, Unit.PERCENTAGE);
		botonera.setHeight(NumberConstants.FNUM50, Unit.PIXELS);

		yes = new Button(MessagesEVT.getInstance().getProperty("yesButton"));
		botonera.addComponent(yes);
		botonera.setComponentAlignment(yes, Alignment.BOTTOM_LEFT);
		contenedor.setMargin(true);

		no = new Button(MessagesEVT.getInstance().getProperty("noButton"));
		no.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -8965842092135602279L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();

			}
		});
		botonera.addComponent(no);
		botonera.setComponentAlignment(no, Alignment.BOTTOM_RIGHT);

		contenedor.addComponent(botonera);

		contenedor.setComponentAlignment(text, Alignment.MIDDLE_CENTER);
		contenedor.setComponentAlignment(botonera, Alignment.BOTTOM_CENTER);
		setContent(contenedor);
	}

	/**
	 * Method that adds a yes click button listener.
	 * @param listener  yes click button listener
	 */
	public final void addClickYesButtonListener(ClickListener listener) {
		yes.addClickListener(listener);
	}

}