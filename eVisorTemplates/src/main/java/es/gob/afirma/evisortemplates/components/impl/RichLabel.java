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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichLabel.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.StyleUtil;

/** 
 * <p>Class that represents template's text blocks.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class RichLabel extends VerticalLayout implements PositionAngle {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3232744417405600915L;

	/**
	 * Attribute that represents text block content. 
	 */
	private Label content;

	/**
	 * Attribute that represents original value of text block content. 
	 */
	private String originalValue;
	
	/**
	 * Attribute that represents the component's position x value in pixels. 
	 */
	private float posX;
	
	/**
	 * Attribute that represents the component's position y value in pixels. 
	 */
	private float posY;
	
	/**
	 * Attribute that represents the component's position x value for XSLT template in pixels.  
	 */
	private float posXXLST;
	
	/**
	 * Attribute that represents the component's position y value for XSLT template in pixels.  
	 */
	private float posYXSLT;

	/**
	 * Attribute that represents the component's center position x value in pixels.  
	 */
	private float posXCenter;
	
	/**
	 * Attribute that represents the component's center position y value in pixels. 
	 */
	private float posYCenter;

	/**
	 * Attribute that represents the component's rotation angle. 
	 */
	private int angle;

	/**
	 * Attribute that represents the component's visibility condition part 1.
	 */
	private String condition1;
	
	/**
	 * Attribute that represents the component's visibility condition part 1.
	 */
	private String condition2;
	
	/**
	 * Attribute that represents the component's visibility condition operator. 
	 */
	private String conditionOp;

	/**
	 * Attribute that represents the component's container in edit mode. 
	 */
	private ResizeDDContainer container;

	/**
	 * Attribute that represents if the component is or not a "for each" component. 
	 */
	private boolean isForEach;
	
	/**
	 * Attribute that represents component's "for each" condition. 
	 */
	private String conditionForEach;

	/**
	 * Attribute that represents component's "for each" type condition. 
	 */
	private String forEachType;

	/**
	 * Attribute that represents xsl:value-of select=\" String constant. 
	 */
	private static final String VALUEOFBLOCK = "<xsl:value-of select=\"";
	
	/**
	 * Attribute that represents \"/> String constant. 
	 */
	private static final String FINALLABEL = "\"/>";

	/**
	 * Constructor method for the class RichLabel.java. 
	 */
	public RichLabel() {
		super();
		content = new Label();
		content.setContentMode(Label.CONTENT_XHTML);
		content.setStyleName("TextoAlineado");
		this.setHeightUndefined();
		this.addComponent(content);

	}

	/**
	 * Constructor method for the class RichLabel.java.
	 * @param transparent true if is transparent container text
	 */
	public RichLabel(boolean transparent) {
		this();
		if (transparent) {
			content.setStyleName("textoTransparente");
		}
	}

	/**
	 * Constructor method for the class RichLabel.java.
	 * @param value text to show
	 * @param transparent true if is transparent container text
	 */
	public RichLabel(String value, boolean transparent) {
		this(transparent);
		setValue(value);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosX()
	 */
	public final float getPosX() {
		return posX;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosX(float)
	 */
	public final void setPosX(float posXParam) {
		this.posX = posXParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosY()
	 */
	public final float getPosY() {
		return posY;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosY(float)
	 */
	public final void setPosY(float posYParam) {
		this.posY = posYParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosXXSLT()
	 */
	@Override
	public final float getPosXXSLT() {
		return posXXLST;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosXXSLT(float)
	 */
	@Override
	public final void setPosXXSLT(float posXParam) {
		this.posXXLST = posXParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosYXSLT()
	 */
	@Override
	public final float getPosYXSLT() {
		return posYXSLT;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosYXSLT(float)
	 */
	@Override
	public final void setPosYXSLT(float posYParam) {
		this.posYXSLT = posYParam;
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
		return true;
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
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getIntWidth()
	 */
	@Override
	public final int getIntWidth() {
		return Math.round(this.getWidth());
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getIntHeight()
	 */
	@Override
	public final int getIntHeight() {
		return Math.round(this.getHeight());
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getAngle()
	 */
	@Override
	public final int getAngle() {
		return angle;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setAngle(int)
	 */
	@Override
	public final void setAngle(int inAngle) {
		if (inAngle != this.angle) {
			this.angle = inAngle;
			if (container != null) {
				container.setAngle(this.angle);
				if (container.getMenu() != null) {

					container.getMenu().setAngle(this.angle);
				}
			}
			StyleUtil.rotaryStyle(this);
		}
	}

	/**
	 * Method that gets label that contains text value.
	 * @return text value
	 */
	public final Label getContent() {
		return content;
	}

	/**
	 * Method that sets label that contains text value. 
	 * @param contentParam  label that contains text value
	 */
	public final void setContent(Label contentParam) {
		this.content = contentParam;
	}

	/**
	 * Method that sets value from text block. 
	 * @param value new  value from text block
	 */
	public final void setValue(String value) {
		content.setValue(value);
	}

	/**
	 * Method that gets value from text block.  
	 * @return value from text block
	 */
	public final String getValue() {
		return content.getValue();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCondition1()
	 */
	@Override
	public final String getCondition1() {
		return condition1;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCondition2()
	 */
	@Override
	public final String getCondition2() {
		return condition2;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getConditionOp()
	 */
	@Override
	public final String getConditionOp() {
		return conditionOp;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setCondition1(java.lang.String)
	 */
	@Override
	public final void setCondition1(String condicion) {
		this.condition1 = condicion;

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setCondition2(java.lang.String)
	 */
	@Override
	public final void setCondition2(String condicion) {
		this.condition2 = condicion;

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setConditionOp(java.lang.String)
	 */
	@Override
	public final void setConditionOp(String condicion) {
		this.conditionOp = condicion;

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getContainer()
	 */
	@Override
	public final ResizeDDContainer getContainer() {
		return container;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setContainer(es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer)
	 */
	@Override
	public final void setContainer(ResizeDDContainer containerParam) {
		this.container = containerParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosXCenter()
	 */
	@Override
	public final float getPosXCenter() {
		return posXCenter;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosYCenter()
	 */
	@Override
	public final float getPosYCenter() {
		return posYCenter;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#calcCentralPoint()
	 */
	@Override
	public final void calcCentralPoint() {
		posXCenter = posX + (getWidth() / 2);
		posYCenter = posY + (getHeight() / 2);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCode(es.gob.afirma.evisortemplates.components.impl.CodeEditor)
	 */
	public final StringBuffer getCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();

		if (originalValue != null && !originalValue.isEmpty()) {

			String[ ] textArray = originalValue.split("¬");

			boolean par = false;
			boolean first = true;
			for (String var: textArray) {
				String varAux;
				varAux = var.replace("&nbsp;", " ");
				if (varAux.trim().length() != 0) {
					if (par) {

						if (first) {
							result.append(codeEditor.addLine(VALUEOFBLOCK + Format.formatVariable("¬" + varAux + "¬", this) + FINALLABEL));
							first = false;
						} else {
							result.append(codeEditor.addText(VALUEOFBLOCK + Format.formatVariable("¬" + varAux + "¬", this) + FINALLABEL));
						}
					} else {
						if (first) {
							result.append(codeEditor.addLine(Format.formatText(varAux)));
							first = false;
						} else {
							result.append(codeEditor.addText(Format.formatText(varAux)));
						}

					}
				} else {
					result.append(codeEditor.addText("<xsl:value-of select=\"' '\"/>"));
				}
				par = !par;
			}

		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#mountCondition()
	 */
	@Override
	public final String mountCondition() {
		String parte1;
		String parte2;

		if (condition1.contains("¬")) {
			parte1 = Format.formatVariable(condition1, this);
		} else {
			parte1 = "'"+condition1+"'";
			if ("''".equals(condition1)) {
				parte1 = condition1;
			} 
		}

		if (condition2.contains("¬")) {
			parte2 = Format.formatVariable(condition2, this);
		} else {
			parte2 = "'"+condition2+"'";
			if ("''".equals(condition2)) {
				parte2 = condition2;
			} 
		}

		return parte1 + conditionOp + parte2;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#newInstance()
	 */
	public final RichLabel newInstance() {
		RichLabel aux = new RichLabel();
		aux.setAngle(getAngle());
		aux.setConditionOp(getConditionOp());
		aux.setCondition1(getCondition1());
		aux.setCondition2(getCondition2());
		aux.setContainer(container);
		aux.setHeight(getHeight(), getHeightUnits());
		aux.setPosX(getPosX());
		aux.setPosY(getPosY());
		aux.setPosXXSLT(getPosXXSLT());
		aux.setPosYXSLT(getPosYXSLT());
		aux.setPosXCenter(getPosXCenter());
		aux.setPosYCenter(getPosYCenter());
		aux.setWidth(getWidth(), getWidthUnits());
		aux.setValue(getValue());
		aux.setConditionForEach(getConditionForEach());
		aux.setIsForEach(isForEach());
		aux.setOriginalValue(getOriginalValue());
		aux.setForEachType(getForEachType());
		return aux;
	}

	/**
	 * Method to set a new position x for center position of component.
	 * @param posXCenterParam new value for center position x
	 */
	public final void setPosXCenter(float posXCenterParam) {
		this.posXCenter = posXCenterParam;
	}

	/**
	 * Method to set a new position y for center position of component.
	 * @param posYCenterParam new value for center position y
	 */
	public final void setPosYCenter(float posYCenterParam) {
		this.posYCenter = posYCenterParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isForEach()
	 */
	public final boolean isForEach() {
		return isForEach;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setIsForEach(boolean)
	 */
	public final void setIsForEach(boolean forEach) {
		this.isForEach = forEach;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setConditionForEach(java.lang.String)
	 */
	public final void setConditionForEach(String condition) {
		this.conditionForEach = condition;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getConditionForEach()
	 */
	public final String getConditionForEach() {
		return conditionForEach;
	}

	/**
	 * Method that sets original value of text block (without VAR literal).
	 * @param originalTextValue original value of text block (without VAR literal)
	 */
	public final void setOriginalValue(String originalTextValue) {
		originalValue = originalTextValue;

	}

	/**
	 * Method that gets original value of text block (without VAR literal).
	 * @return original value of text block (without VAR literal)
	 */
	public final String getOriginalValue() {
		return originalValue;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getForEachType()
	 */
	public final String getForEachType() {
		return forEachType;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setForEachType(java.lang.String)
	 */
	public final void setForEachType(String forEachTypeParam) {
		this.forEachType = forEachTypeParam;
	}

}
