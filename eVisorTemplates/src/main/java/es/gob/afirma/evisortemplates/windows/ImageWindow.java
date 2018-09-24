/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2018 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.ImageWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;

import es.gob.afirma.evisortemplates.components.impl.ImageUpload;
import es.gob.afirma.evisortemplates.components.impl.RichImage;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents the image input window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class ImageWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7833305254001414741L;
	
	/**
	 * Attribute that represents accept button. 
	 */
	private Button acceptButton;
	
	/**
	 * Attribute that represents Image upload component. 
	 */
	private ImageUpload imageUp;

	/**
	 * Constructor method for the class ImageWindow.java. 
	 */
	public ImageWindow() {

		super();
		setCaption(MessagesEVT.getInstance().getProperty("imageWindowCaption"));

		setModal(true);
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("imageWindowWidth")), Unit.PIXELS);
		setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("imageWindowHeight")), Unit.PIXELS);
		FormLayout fl = new FormLayout();
		imageUp = new ImageUpload();

		acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));

		fl.addComponent(imageUp);
		fl.addComponent(acceptButton);

		setContent(fl);

	}

	/**
	 * Method that adds an accept button click listeener to window.
	 * @param listener accept button click listeener
	 */
	public final void addClickAcceptButtonListener(ClickListener listener) {
		acceptButton.addClickListener(listener);
	}

	/**
	 * Method that checks if the image was inserted.
	 * @return true if the image was inserted, false otherwise
	 */
	public final boolean isImageIn() {
		// si la imagen es visible es que se ha introducido
		// una
		if (((RichImage) imageUp.getImage()).isVisible()) {
			return true;
		}
		return false;
	}

	/**
	 * Method that gets image component.
	 * @return image component
	 */
	public final RichImage getImage() {
		return (RichImage) imageUp.getImage();
	}
	
	/**
	 * Method that gets the variable name of the images.
	 * @return the string representation of the variable.
	 */
	public final String getVariable() {
		return ((RichImage) imageUp.getImage()).getVariable();
	}

}
