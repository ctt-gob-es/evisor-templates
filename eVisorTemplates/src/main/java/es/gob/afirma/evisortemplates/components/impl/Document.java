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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.Document.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.PixelToCm;

/** 
 * <p>Class that represents Document template's component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class Document extends PositionAngleAbsCustom {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8744658984570210396L;

	/**
	 * Attribute that represents layout that contains structure. 
	 */
	private VerticalLayout panel;

	/**
	 * Attribute that represents the template in which is inserted.
	 */
	private Template template;

	/**
	 * Attribute that represents if document is in front o back mode. 
	 */
	private boolean front;

	/**
	 * Constructor method for the class Document.java.
	 * @param templateParam the template in which is inserted
	 */
	public Document(Template templateParam) {
		this.template = templateParam;
		panel = new VerticalLayout();
		panel.setStyleName("bordePunteado");
		panel.addComponent(new Label(MessagesEVT.getInstance().getProperty("documentFront")));
		setWidth(NumberConstants.FNUM400, Unit.PIXELS);
		setHeight(Math.round(panel.getWidth() * Math.sqrt(2)), Unit.PIXELS);
		setCompositionRoot(panel);
		front = true;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isImage()
	 */
	@Override
	public final boolean isImage() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isText()
	 */
	@Override
	public final boolean isText() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isTable()
	 */
	@Override
	public final boolean isTable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isBarCode()
	 */
	@Override
	public final boolean isBarCode() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isDocument()
	 */
	@Override
	public final boolean isDocument() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#setWidth(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setWidth(float w, Unit unit) {
		super.setWidth(w, unit);
		if (panel != null) {
			panel.setWidth(w, unit);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#setHeight(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setHeight(float h, Unit unit) {
		super.setHeight(h, unit);
		if (panel != null) {
			panel.setHeight(h, unit);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCode(es.gob.afirma.evisortemplates.components.impl.CodeEditor)
	 */
	public final StringBuffer getCode(CodeEditor editorCodigo) {
		StringBuffer resultado = new StringBuffer();
		resultado.append(editorCodigo.addLine("<ri:IncludePage Ypos=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(template.getHeight() - (getPosY() + getHeight())) + "", this, CodeEditorInput.POSYDOC, false) + editorCodigo.addText("\"  Xpos=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(getPosX()) + "", this, CodeEditorInput.POSXDOC, false) + editorCodigo.addText("\" \n") + editorCodigo.addLine(" Width=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(getWidth()) + "", this, CodeEditorInput.WIDTHDOC, false) + editorCodigo.addText("\"  Height=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(getHeight()) + "", this, CodeEditorInput.HEIGHTDOC, false));
		resultado.append(editorCodigo.addText("\""));
		if (!front) {
			resultado.append(editorCodigo.addLine(" Layout=\"back\" "));
		}
		resultado.append(editorCodigo.addText(">\n"));

		resultado.append(editorCodigo.addLine("<ri:DocumentPage>\n"));
		resultado.append(editorCodigo.addLine("<xsl:value-of select=\"$currentPage + 1\"/>\n"));
		resultado.append(editorCodigo.addLine("</ri:DocumentPage>\n"));

		resultado.append(editorCodigo.addLine("<ri:ReportPage>\n"));
		if (((Template) getParent()).getFrontTemplate() != null && !((Template) getParent()).getFrontTemplate().isEmpty()) {
			resultado.append(editorCodigo.addLine("<xsl:value-of select=\"$currentPage + 2\"/>\n"));
		} else {
			resultado.append(editorCodigo.addLine("<xsl:value-of select=\"$currentPage + 1\"/>\n"));
		}
		resultado.append(editorCodigo.addLine("</ri:ReportPage>\n"));

		resultado.append(editorCodigo.addLine("</ri:IncludePage>\n"));

		return resultado;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.impl.PositionAngleAbsCustom#newInstance()
	 */
	public final Document newInstance() {
		// no implementado
		return this;
	}

	/**
	 * Method that changes document mode (front/back).
	 */
	public final void changeFrontBack() {
		setFront(!front);

	}
	
	/**
	 * Method that indicates if the document is in front or back of others components.
	 * @return boolean that indicates if the document is in front or back of others components. 
	 * True if docucument is in front, False otherwise.
	 */
	public final boolean isFront() {
		return front;
	}

	
	/**
	 * Method that sets the variable that indicate if the document is in front or back of others components.
	 * @param frontParam boolean value that sets the variable that indicate if the document is in front or back of others components.
	 */
	public final void setFront(boolean frontParam) {
		this.front = frontParam;
		panel.removeAllComponents();
		if (this.front) {
			this.panel.addComponent(new Label(MessagesEVT.getInstance().getProperty("documentFront")));
		} else {
			this.panel.addComponent(new Label(MessagesEVT.getInstance().getProperty("documentBack")));
		}
	}

}
