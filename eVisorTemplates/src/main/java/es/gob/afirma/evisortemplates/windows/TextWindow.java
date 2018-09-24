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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.TextWindow.java.</p>
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.components.impl.FormVariable;
import es.gob.afirma.evisortemplates.components.impl.RichTextArea;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that represents the text block input window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class TextWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5644831960149395702L;

	/**
	 * Attribute that represents variable's insert zone. 
	 */
	private FormVariable fv;
	
	/**
	 * Attribute that represents remove button. 
	 */
	private Button removeButton;
	
	/**
	 * Attribute that represents accept button. 
	 */
	private Button acceptButton;
	
	/**
	 * Attribute that represents the rich text area component. 
	 */
	private RichTextArea rta;
	
	/**
	 * Attribute that represents a flag that indicates if block are repeat for each occurrence of individual signature. 
	 */
	private boolean isForEach;

	/**
	 * Constructor method for the class TextWindow.java.
	 * @param value initial content of text area
	 * @param caption window caption
	 * @param showEliminar true if want show remove button
	 * @param vars Indicates if text have any variable
	 * @param showIndexVars indicates if variable's zone have the index combo box
	 * @param hasVarIndex indicates if exists a variable with index inserted
	 */
	public TextWindow(String value, String caption, boolean showEliminar, boolean vars, boolean showIndexVars, boolean hasVarIndex) {

		super();

		rta = new RichTextArea("", value);
		rta.setSizeFull();
		setCaption(caption);
		setContent(rta);
		setModal(true);
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("textWindowWidth")), Unit.PIXELS);

		final VerticalLayout container = new VerticalLayout();

		container.addComponent(rta);

		if (vars) {
			fv = new FormVariable(Format.TYPEALL, showIndexVars, hasVarIndex);
			// CHECKSTYLE:OFF To allow anonymous inner class size
			// greater than 20 lines.
			fv.addClickAcceptListener(new Button.ClickListener() {

				// CHECKSTYLE:ON

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 4425435237483747572L;

				@Override
				public void buttonClick(ClickEvent event) {
					if (fv.isFullVar()) {
						rta.setValue(rta.getValue() + fv.getVarFormed());
						// Se bloquea el formVariable para que el resto de
						// variables sean igual que la primera (con [] o sin [])
						fv.blockForEach();
						if (!isForEach) {
							isForEach = fv.isForEach();

							if (isForEach) {
								Notification.show(MessagesEVT.getInstance().getProperty("noIndexVarInserted"));
							}
						}
					}

				}
			});

			container.addComponent(fv);
		}
		if (showEliminar) {
			HorizontalLayout botonera = new HorizontalLayout();
			botonera.setWidth(NumberConstants.FNUM60, Unit.PERCENTAGE);
			botonera.setHeight(NumberConstants.FNUM50, Unit.PIXELS);

			removeButton = new Button(MessagesEVT.getInstance().getProperty("removeButton"));
			botonera.addComponent(removeButton);
			botonera.setComponentAlignment(removeButton, Alignment.BOTTOM_LEFT);
			acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));
			botonera.addComponent(acceptButton);
			botonera.setComponentAlignment(acceptButton, Alignment.BOTTOM_RIGHT);
			container.addComponent(botonera);
			container.setComponentAlignment(botonera, Alignment.BOTTOM_CENTER);
		} else {
			acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));
			container.addComponent(acceptButton);
		}

		container.setMargin(true);
		container.setSpacing(true);

		container.setComponentAlignment(rta, Alignment.MIDDLE_CENTER);

		setContent(container);
	}

	/**
	 * Method that adds an remove button click listener to window.
	 * @param listener remove button click listener
	 */
	public final void addClickBotonEliminarListener(ClickListener listener) {
		if (removeButton != null) {
			removeButton.addClickListener(listener);
		}
	}

	/**
	 * Method that adds an accept button click listener to window.
	 * @param listener accept button click listener
	 */
	public final void addClickBotonAceptarListener(ClickListener listener) {
		if (acceptButton != null) {
			acceptButton.addClickListener(listener);
		}
	}

	/**
	 * Method that returns String representation content replacing variables with VAR literal.
	 * @return String representation content replacing variables with VAR literal
	 */
	public final String getTextValue() {
		String resultado = "";

		String[ ] textArray = rta.getValue().split("¬");

		boolean par = false;

		for (String var: textArray) {
			String varAux;
			varAux = var.replace("&nbsp;", " ");
			if (par) {
				resultado = resultado + ConfigEVT.getInstance().getProperty("widlcardVarWord");
			} else {
				resultado = resultado + varAux;
			}

			par = !par;
		}
		return resultado;
	}

	/**
	 * Method that returns the original value of text area.
	 * @return the original value of text area
	 */
	public final String getOriginalTextValue() {
		return rta.getValue();
	}

	/**
	 * Method that checks if the variable is a "for each" variable.
	 * @return true if the variable is a "for each" variable, false otherwise
	 */
	public final boolean isForEach() {
		return isForEach;
	}

	
	/**
	 * Method that sets if the variable is a "for each" variable.
	 * @param isForEachParam true if the variable is a "for each" variable, false otherwise
	 */
	public final void setForEach(boolean isForEachParam) {
		this.isForEach = isForEachParam;
	}

}
