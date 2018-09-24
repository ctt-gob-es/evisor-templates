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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.PositionAngleAbsImage.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.StyleUtil;

/** 
 * <p>Class that represents commons methods for image positionAngle objects.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 12/03/2015.
 */
public abstract class PositionAngleAbsImage extends Image implements PositionAngle {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2349939179414728984L;

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
	 * Constructor method for the class PositionAngleAbsImage.java. 
	 */
	public PositionAngleAbsImage() {
		super();
	}

	/**
	 * Constructor method for the class PositionAngleAbsImage.java.
	 * @param caption caption for component
	 * @param themeResource for image representation
	 */
	public PositionAngleAbsImage(String caption, ThemeResource themeResource) {
		super(caption, themeResource);
	}

	/**
	 * Constructor method for the class PositionAngleAbsImage.java.
	 * @param caption caption for component
	 * @param source for image representation
	 */
	public PositionAngleAbsImage(String caption, Resource source) {
		super(caption, source);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosX()
	 */
	@Override
	public final float getPosX() {
		return posX;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosX(float)
	 */
	@Override
	public final void setPosX(float posXParam) {
		this.posX = posXParam;

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getPosY()
	 */
	@Override
	public final float getPosY() {
		return posY;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#setPosY(float)
	 */
	@Override
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
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#newInstance()
	 */
	public abstract PositionAngle newInstance();

	/**
	 * Method that sets the component's width scaling component's height. 
	 * @param w new width value
	 */
	public final void setScaleWidth(float w) {
		if (!(this instanceof BarCode)) {
			float lastW = ((RichImage) this).getOriginalWidth();
			float lastH = ((RichImage) this).getOriginalHeight();
			setWidth(w, Unit.PIXELS);

			setHeight(lastH * w / lastW, Unit.PIXELS);

		}
	}

	/**
	 * Method that sets the component's height scaling component's width. 
	 * @param h new height value
	 */
	public final void setScaleHeight(float h) {
		if (!(this instanceof BarCode)) {
			float lastW = ((RichImage) this).getOriginalWidth();
			float lastH = ((RichImage) this).getOriginalHeight();
			setHeight(h, Unit.PIXELS);

			setWidth(lastW * h / lastH, Unit.PIXELS);

		}
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
