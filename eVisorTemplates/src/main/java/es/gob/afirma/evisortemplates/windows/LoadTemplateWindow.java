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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.ImageWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.FormLayout;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents the image input window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class LoadTemplateWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8654254823252228110L;

	/**
	 * Constructor method for the class ImageWindow.java.
	 * @param upload Upload component to show in window. 
	 */
	public LoadTemplateWindow(Upload upload) {

		super();

		setCaption(MessagesEVT.getInstance().getProperty("openTemplateUploadLabel"));
		setModal(true);
		// Put the components in a panel
		Panel panelUpload = new Panel("");
		panelUpload.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("fileUploadPanelWidth")), Unit.PIXELS);
		VerticalLayout panelContent = new VerticalLayout();
		panelContent.addComponents(upload, new Label(""));
		panelContent.setSpacing(true);
		panelUpload.setContent(panelContent);

		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("loadTemplateWindowWidth")), Unit.PIXELS);
		FormLayout fl = new FormLayout();

		fl.addComponent(panelUpload);

		setContent(fl);

	}

}
