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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.resizeDD.ResizeDDState.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.resizeDD;

import com.vaadin.shared.ui.panel.PanelState;

/** 
 * <p>Class that represents ResizeDD state to communicate with widget.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class ResizeDDState extends PanelState {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6399096497429103107L;

	{
		primaryStyleName = "v-window";
	}

	/**
	 * Attribute that represents x coordinate. 
	 */
	private float positionX = -1;
	
	/**
	 * Attribute that represents y coordinate. 
	 */
	private float positionY = -1;

	/**
	 * Attribute flag that represents if the container is resizable dragging and dropping left and right edges. 
	 */
	private boolean resizableWidth = true;

	/**
	 * Attribute flag that represents if the container is resizable dragging and dropping corners. 
	 */
	private boolean resizableOnlyCorners = true;

	/**
	 * Attribute flag that represents if the container is not resizable. 
	 */
	private boolean resizableDisable = false;

	/**
	 * Attribute that represents rotation anble for container. 
	 */
	private int angle = 0;

	/**
	 * Method that gets x coordinate of the container.
	 * @return x coordinate of the container
	 */
	public final float getPositionX() {
		return positionX;
	}

	/**
	 * Method that sets x coordinate of the container.
	 * @param positionXParam new x coordinate for container
	 */
	public final void setPositionX(float positionXParam) {
		this.positionX = positionXParam;
	}

	/**
	 * Method that gets y coordinate of the container.
	 * @return y coordinate of the container
	 */
	public final float getPositionY() {
		return positionY;
	}

	/**
	 * Method that sets y coordinate of the container.
	 * @param positionYParam new y coordinate for container
	 */
	public final void setPositionY(float positionYParam) {
		this.positionY = positionYParam;
	}

	/**
	 * Method that checks if the container is resizable dragging and dropping left and right edges.
	 * @return true if the container is resizable dragging and dropping left and right edges, false otherwise
	 */
	public final boolean isResizableWidth() {
		return resizableWidth;
	}

	/**
	 * Method that sets if the container is resizable dragging and dropping left and right edges.
	 * @param resizableWidthParam true if the container is resizable dragging and dropping left and right edges
	 */
	public final void setResizableWidth(boolean resizableWidthParam) {
		this.resizableWidth = resizableWidthParam;
	}

	/**
	 * Method that checks if the container is resizable dragging and dropping corners.
	 * @return true if the container is resizable dragging and dropping corners, false otherwise
	 */
	public final boolean isResizableOnlyCorners() {
		return resizableOnlyCorners;
	}

	/**
	 * Method that sets if the container is resizable dragging and dropping corners.
	 * @param resizableOnlyCornersParam true if the container is resizable dragging and dropping corners
	 */
	public final void setResizableOnlyCorners(boolean resizableOnlyCornersParam) {
		this.resizableOnlyCorners = resizableOnlyCornersParam;
	}

	/**
	 * Method that checks if the container is not resizable.
	 * @return true if the container is not resizable, false otherwise
	 */
	public final boolean isResizableDisable() {
		return resizableDisable;
	}

	/**
	 * Method that sets if the container is not resizable.
	 * @param resizableDisableParam true if the container is not resizable
	 */
	public final void setResizableDisable(boolean resizableDisableParam) {
		this.resizableDisable = resizableDisableParam;
	}

	/**
	 * Method that gets rotation angle of the container.
	 * @return rotation angle of the container
	 */
	public final int getAngle() {
		return angle;
	}

	/**
	 *  Method that sets rotation angle of the container.
	 * @param angleParam new rotation angle
	 */
	public final void setAngle(int angleParam) {
		this.angle = angleParam;
	}

}
