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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.BlurNotifier;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.FieldEvents.FocusNotifier;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.ui.Panel;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.widgetset.client.resizeDD.ResizeDDState;

/** 
 * <p>Class that represents resize drag and drop container to modify template's components.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class ResizeDDContainer extends Panel implements FocusNotifier, BlurNotifier, LegacyComponent {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = -4793207832092039539L;

	/**
	 * Attribute that represents layout that contains the structure. 
	 */
	private AbsoluteLayout layout;
	
	/**
	 * Attribute that represents the float menu. 
	 */
	private FloatMenu menu;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(ResizeDDContainer.class);

	
	/**
	 * Constructor method for the class ResizeDDContainer.java.
	 * @param layoutParam layout that contains the structure
	 * @param component component to be wrapped
	 */
	public ResizeDDContainer(AbsoluteLayout layoutParam, PositionAngle component) {
		super();
		this.layout = layoutParam;

		setAngle(component.getAngle());

		this.layout.addComponent(this);

		this.menu = new FloatMenu(this, component, this.layout);
		addMenuElement(component.getPosX(), component.getPosY(), component.getIntHeight());
	}

	/**
	 * Method that adds menu to template.
	 * @param posx x coordinate
	 * @param posy y coordinate
	 * @param height height value for menu
	 */
	private void addMenuElement(float posx, float posy, int height) {
		layout.addComponent(menu);
		resetMenuPosition(posx, posy, height);
	}

	/**
	 * Method that resets the menu's position in template.
	 * @param posx x coordinate
	 * @param posy y coordinate
	 * @param height height value for menu
	 */
	public final void resetMenuPosition(float posx, float posy, float height) {
		ComponentPosition pos = layout.getPosition(menu);
		if (posy >= NumberConstants.NUM40) {
			pos.setTopValue(posy - NumberConstants.NUM40);
		} else if (posy + height + NumberConstants.NUM40 >= layout.getHeight()) {
			pos.setTopValue(0f);
		} else {
			pos.setTopValue(posy + height);
		}

		float posxVar = posx;
		if (posx < 0) {
			posxVar = 0;
		} else if (posx > layout.getWidth() - menu.getSizeAprox()) {
			posxVar = layout.getWidth() - menu.getSizeAprox();
		}
		pos.setLeftValue(posxVar);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.Panel#paintContent(com.vaadin.server.PaintTarget)
	 */
	@Override
	public final synchronized void paintContent(PaintTarget target) throws PaintException {
		if (bringToFront != null) {
			target.addAttribute("bringToFront", bringToFront.intValue());
			bringToFront = null;
		}

		// Contents of the window panel is painted
		super.paintContent(target);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#setParent(com.vaadin.ui.HasComponents)
	 */
	@Override
	public final void setParent(HasComponents parent) {
		super.setParent(parent);

	}

	/**
	 * Method that checks if any size property are modified.
	 * @param variables properties to checked
	 * @return true if any size property are modified, false otherwise
	 */
	private boolean sizeHasChanged(Map<String, Object> variables) {
		if (variables.containsKey("height") && (getHeightUnits() != Unit.PIXELS || (Integer) variables.get("height") != getHeight())) {
			return true;
		}
		if (variables.containsKey("width") && (getWidthUnits() != Unit.PIXELS || (Integer) variables.get("width") != getWidth())) {
			return true;
		}
		return false;
	}

	/**
	 * Method that checks if any position property are modified.
	 * @param variables properties to checked
	 * @return true if any position property are modified, false otherwise
	 */
	private boolean positionHasChanged(Map<String, Object> variables) {
		if (variables.containsKey("positionx") && (Integer) variables.get("positionx") != getPositionX()) {
			return true;
		}
		if (variables.containsKey("positiony") && (Integer) variables.get("positiony") != getPositionY()) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.Panel#changeVariables(java.lang.Object, java.util.Map)
	 */
	@Override
	public final void changeVariables(Object source, Map<String, Object> variables) {

		boolean sizeHasChanged = sizeHasChanged(variables);

		boolean positionHasChanged = positionHasChanged(variables);

		super.changeVariables(source, variables);

		// Positioning
		final Integer positionx = (Integer) variables.get("positionx");
		if (positionx != null) {
			final int x = positionx.intValue();
			setPositionX(x);
		}
		final Integer positiony = (Integer) variables.get("positiony");
		if (positiony != null) {
			final int y = positiony.intValue();
			setPositionY(y);
		}

		// Closing
		final Boolean close = (Boolean) variables.get("close");
		if (close != null && close.booleanValue()) {
			close();
		}

		// fire event if size has really changed
		if (sizeHasChanged) {
			fireResize();
		}

		if (positionHasChanged) {
			fireMove();
		}

		if (variables.containsKey(FocusEvent.EVENT_ID)) {
			fireEvent(new FocusEvent(this));
		} else if (variables.containsKey(BlurEvent.EVENT_ID)) {
			fireEvent(new BlurEvent(this));
		}

	}

	/**
	 * Close the edition mode's container and menu.
	 */
	public final void close() {
		layout.removeComponent(this);
		layout.removeComponent(menu);
	}

	/**
	 * Method that gets the x coordinate of resizeDD panel in pixels.
	 * @return the x coordinate of resizeDD panel
	 */
	public final float getPositionX() {
		return getState(false).getPositionX();
	}

	/**
	 * Method that sets the x coordinate of resizeDD panel.
	 * @param positionX the new x coordinate of resizeDD panel in pixels.
	 */
	public final void setPositionX(float positionX) {
		getState().setPositionX(positionX);
		if (layout != null) {
			ComponentPosition pos = layout.getPosition(this);

			if (pos != null) {
				pos.setLeftValue(Float.parseFloat(positionX + ""));
			}
		}
	}

	/**
	 * Method that gets the y coordinate of resizeDD panel in pixels.
	 * @return the y coordinate of resizeDD panel
	 */
	public final float getPositionY() {
		return getState(false).getPositionY();
	}

	/**
	 * Method that sets the y coordinate of resizeDD panel.
	 * @param positionY the new y coordinate of resizeDD panel in pixels.
	 */
	public final void setPositionY(float positionY) {
		getState().setPositionY(positionY);
		if (layout != null) {
			ComponentPosition pos = layout.getPosition(this);

			if (pos != null) {
				pos.setTopValue(Float.parseFloat(positionY + ""));
			}
		}
	}

	/**
	 * Method that sets if the container is resizable dragging and dropping left and right edges.
	 * @param resizableWidth true if the container is resizable dragging and dropping left and right edges
	 */
	public final void setResizableWidth(boolean resizableWidth) {
		getState().setResizableWidth(resizableWidth);
	}

	/**
	 * Method that sets if the container is resizable dragging and dropping corners.
	 * @param resizableOnlyCorners true if the container is resizable dragging and dropping corners
	 */
	public final void setResizableOnlyCorners(boolean resizableOnlyCorners) {
		getState().setResizableOnlyCorners(resizableOnlyCorners);
	}

	/**
	 * Method that sets if the container is not resizable.
	 * @param resizableDisable true if the container is not resizable
	 */
	public final void setResizableDisable(boolean resizableDisable) {
		getState().setResizableDisable(resizableDisable);
	}

	/**
	 * Attribute that represents window move method. 
	 */
	private static final java.lang.reflect.Method WINDOW_MOVE_METHOD;
	static {
		try {
			WINDOW_MOVE_METHOD = MoveListener.class.getDeclaredMethod("windowMoved", new Class[ ] { MoveEvent.class });
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			LOG.error("Internal error, window moved method not found",e);
			throw new java.lang.RuntimeException("Internal error, window moved method not found", e);
		}
	}

	/** 
	 * <p>Class that represents move event for resize drag and drop container.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	public static class MoveEvent extends Component.Event {

		/**
		 * Attribute that represents . 
		 */
		private static final long serialVersionUID = -6803313483202487417L;

		/**
		 * Constructor method for the class ResizeDDContainer.java.
		 * @param source component
		 */
		public MoveEvent(Component source) {
			super(source);
		}

		/**
		 * Method that gets the container.
		 * @return the container
		 */
		public final ResizeDDContainer getWindow() {
			return (ResizeDDContainer) getSource();
		}
	}

	/** 
	 * <p>Interface that describe MoveListener methods.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	public interface MoveListener extends Serializable {

		/**
		 * Method that manages move events.
		 * @param e move event
		 */
		void windowMoved(MoveEvent e);
	}

	/**
	 * Method that adds move listener to container.
	 * @param listener new move listener to add
	 */
	public final void addMoveListener(MoveListener listener) {
		addListener(MoveEvent.class, listener, WINDOW_MOVE_METHOD);
	}

	/**
	 * Method that fires move event.
	 */
	protected final void fireMove() {
		fireEvent(new MoveEvent(this));
	}

	/**
	 * Attribute that represents window resize method. 
	 */
	private static final java.lang.reflect.Method WINDOW_RESIZE_METHOD;
	static {
		try {
			WINDOW_RESIZE_METHOD = ResizeListener.class.getDeclaredMethod("windowResized", new Class[ ] { ResizeEvent.class });
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			LOG.error("Internal error, window resized method not found",e);
			throw new java.lang.RuntimeException("Internal error, window resized method not found", e);
		}
	}

	/** 
	 * <p>Class that represents resize event for resize drag and drop container.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	public static class ResizeEvent extends Component.Event {

		/**
		 * Attribute that represents . 
		 */
		private static final long serialVersionUID = -6304188464990853895L;

		/**
		 * Constructor method for the class ResizeDDContainer.java.
		 * @param source component
		 */
		public ResizeEvent(Component source) {
			super(source);
		}

		/**
		 * Method that gets the container.
		 * @return the container
		 */
		public final ResizeDDContainer getWindow() {
			return (ResizeDDContainer) getSource();
		}
	}

	/** 
	 * <p>Interface that describe ResizeListener methods.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	public interface ResizeListener extends Serializable {

		/**
		 * Method that manages resize events.
		 * @param e resize event
		 */
		void windowResized(ResizeEvent e);
	}

	/**
	 * Method that adds resize listener to container.
	 * @param listener new resize listener to add
	 */
	public final void addResizeListener(ResizeListener listener) {
		addListener(ResizeEvent.class, listener, WINDOW_RESIZE_METHOD);
	}

	/**
	 * Method that fires resize event.
	 */
	protected final void fireResize() {
		fireEvent(new ResizeEvent(this));
	}

	/**
	 * Attribute that represents if componente bring to front . 
	 */
	private Integer bringToFront = null;

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.FocusNotifier#addFocusListener(com.vaadin.event.FieldEvents.FocusListener)
	 */
	@Override
	public final void addFocusListener(FocusListener listener) {
		addListener(FocusEvent.EVENT_ID, FocusEvent.class, listener, FocusListener.focusMethod);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.FocusNotifier#addListener(com.vaadin.event.FieldEvents.FocusListener)
	 */
	@Override
	@Deprecated
	public final void addListener(FocusListener listener) {
		addFocusListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.FocusNotifier#removeFocusListener(com.vaadin.event.FieldEvents.FocusListener)
	 */
	@Override
	public final void removeFocusListener(FocusListener listener) {
		removeListener(FocusEvent.EVENT_ID, FocusEvent.class, listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.FocusNotifier#removeListener(com.vaadin.event.FieldEvents.FocusListener)
	 */
	@Override
	@Deprecated
	public final void removeListener(FocusListener listener) {
		removeFocusListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.BlurNotifier#addBlurListener(com.vaadin.event.FieldEvents.BlurListener)
	 */
	@Override
	public final void addBlurListener(BlurListener listener) {
		addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener, BlurListener.blurMethod);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.BlurNotifier#addListener(com.vaadin.event.FieldEvents.BlurListener)
	 */
	@Override
	@Deprecated
	public final void addListener(BlurListener listener) {
		addBlurListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.BlurNotifier#removeBlurListener(com.vaadin.event.FieldEvents.BlurListener)
	 */
	@Override
	public final void removeBlurListener(BlurListener listener) {
		removeListener(BlurEvent.EVENT_ID, BlurEvent.class, listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.event.FieldEvents.BlurNotifier#removeListener(com.vaadin.event.FieldEvents.BlurListener)
	 */
	@Override
	@Deprecated
	public final void removeListener(BlurListener listener) {
		removeBlurListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.Panel#focus()
	 */
	@Override
	public final void focus() {
		/*
		 * When focusing a window it basically means it should be brought to the
		 * front. Instead of just moving the keyboard focus we focus the window
		 * and bring it top-most.
		 */
		super.focus();
		// bringToFront();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.Panel#getState()
	 */
	@Override
	protected final ResizeDDState getState() {
		return (ResizeDDState) super.getState();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.Panel#getState(boolean)
	 */
	@Override
	protected final ResizeDDState getState(boolean markAsDirty) {
		return (ResizeDDState) super.getState(markAsDirty);
	}

	/**
	 * Method that sets rotation angle for container.
	 * @param angle new rotation angle
	 */
	public final void setAngle(int angle) {
		getState().setAngle(angle);
	}

	/**
	 * Method that gets rotation angle for container.
	 * @return rotation angle
	 */
	public final FloatMenu getMenu() {
		return menu;
	}

}