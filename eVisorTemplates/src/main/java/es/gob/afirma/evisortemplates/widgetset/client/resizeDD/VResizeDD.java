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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.resizeDD.VResizeDD.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.resizeDD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.aria.client.RelevantValue;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Focusable;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.Util;
import com.vaadin.client.ui.FocusableScrollPanel;
import com.vaadin.client.ui.ShortcutActionHandler;
import com.vaadin.client.ui.ShortcutActionHandler.ShortcutActionHandlerOwner;
import com.vaadin.client.ui.VNotification;
import com.vaadin.client.ui.VOverlay;
import com.vaadin.shared.EventId;

import es.gob.afirma.evisortemplates.util.NumberConstants;


/** 
 * <p>Class that represents the resize drag and drop client component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
public class VResizeDD extends VOverlay implements ShortcutActionHandlerOwner, ScrollHandler, KeyDownHandler, KeyUpHandler, FocusHandler, BlurHandler, Focusable {

	/**
	 * Attribute that represents the list of ordered componets. 
	 */
	private static List<VResizeDD> windowOrder = new ArrayList<VResizeDD>();

	/**
	 * Attribute that represents positiony String constant. 
	 */
	private static final String POSITIONY = "positiony";
	
	/**
	 * Attribute that represents positionx String constant. 
	 */
	private static final String POSITIONX = "positionx";

	/**
	 * Attribute that represents if component are ordered. 
	 */
	private static boolean orderingDefered;

	/**
	 * Attribute that represents the CSS style applied. 
	 */
	public static final String CLASSNAME = "v-window";

	/**
	 * Attribute that represents stacking offset pixls. 
	 */
	private static final int STACKING_OFFSET_PIXELS = 15;

	/**
	 * Attribute that represents the z_index defalut value. 
	 */
	public static final int Z_INDEX = 100000;

	/**
	 * Attribute that represents bottom resize zone. 
	 */
	private Element bottomResizeBox;

	/**
	 * Attribute that represents right resize zone. 
	 */
	private Element rightResizeBox;

	/**
	 * Attribute that represents left resize zone. 
	 */
	private Element leftResizeBox;

	/**
	 * Attribute that represents top resize zone. 
	 */
	private Element topResizeBox;

	/**
	 * Attribute that represents top-left corner resize zone. 
	 */
	private Element topLeftCorner;

	/**
	 * Attribute that represents top-right corner resize zone. 
	 */
	private Element topRightCorner;

	/**
	 * Attribute that represents bottom-left corner resize zone. 
	 */
	private Element bottomLeftCorner;

	/**
	 * Attribute that represents bottom-right corner resize zone. 
	 */
	private Element bottomRightCorner;

	/**
	 * Attribute that represents center move zone. 
	 */
	private Element center;

	/**
	 * Attribute that represents the selected element. 
	 */
	private Element selectedElement;

	/** For internal use only. May be removed or replaced in the future. */
	private final FocusableScrollPanel contentPanel = new FocusableScrollPanel();

	/**
	 * Attribute that represents a flag indicating if component are dragging. 
	 */
	private boolean dragging;
	
	/**
	 * Attribute that represents a flag indicating if component are resizing. 
	 */
	private boolean resizing;

	/**
	 * Attribute that represents the start x coordinate of move and resize events. 
	 */
	private int startX;

	/**
	 * Attribute that represents the start y coordinate of move and resize events. 
	 */
	private int startY;

	/**
	 * Attribute that represents the original x coordinate before was moved. 
	 */
	private int origX;

	/**
	 * Attribute that represents the original y coordinate before was moved. 
	 */
	private int origY;

	/**
	 * Attribute that represents the original width before was resized. 
	 */
	private int origW;

	/**
	 * Attribute that represents the original height before was resized. 
	 */
	private int origH;

	/**
	 * Attribute that represents the rotation anble for component. 
	 */
	private int angle;

	/** For internal use only. May be removed or replaced in the future. */
	private ApplicationConnection client;

	/** For internal use only. May be removed or replaced in the future. */
	private String id;

	/** For internal use only. May be removed or replaced in the future. */
	private ShortcutActionHandler shortcutHandler;

	/** Last known positionx read from UIDL or updated to application connection. */
	private int uidlPositionX = -1;

	/** Last known positiony read from UIDL or updated to application connection. */
	private int uidlPositionY = -1;
	
	/**
	 * Attribute that represents the resizing curtain element. 
	 */
	private Element resizingCurtain;

	/**
	 * Attribute that represents a flag indicating if component is focused. 
	 */
	private boolean hasFocus;

	/** For internal use only. May be removed or replaced in the future. */
	private boolean visibilityChangesDisabled;

	/** For internal use only. May be removed or replaced in the future. */
	private int bringToFrontSequence = -1;

	/**
	 * Constructor method for the class VResizeDD.java. 
	 */
	public VResizeDD() {
		super(false, false, true); // no autohide, not modal, shadow
		// Different style of shadow for windows
		setShadowStyle("window");

		Roles.getDialogRole().set(getElement());
		Roles.getDialogRole().setAriaRelevantProperty(getElement(), RelevantValue.ADDITIONS);

		constructDOM();
		contentPanel.addScrollHandler(this);
		contentPanel.addKeyDownHandler(this);
		contentPanel.addKeyUpHandler(this);
		contentPanel.addFocusHandler(this);
		contentPanel.addBlurHandler(this);
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	@Override
	protected final void onAttach() {
		super.onAttach();

		getApplicationConnection().getUIConnector().getWidget().storeFocus();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#onDetach()
	 */
	@Override
	protected final void onDetach() {
		super.onDetach();
		
		getApplicationConnection().getUIConnector().getWidget().focusStoredElement();
	}

	/**
	 * Method that bring to front the component.
	 */
	public final void bringToFront() {
		int curIndex = windowOrder.indexOf(this);
		if (curIndex + 1 < windowOrder.size()) {
			windowOrder.remove(this);
			windowOrder.add(this);
			for (; curIndex < windowOrder.size(); curIndex++) {
				windowOrder.get(curIndex).setWindowOrder(curIndex);
			}
		}
	}

	/**
	 * Returns true if this window is the topmost VContenedorResizable.
	 * @return true if this window is the topmost VContenedorResizable, false otherwise
	 */
	private boolean isActive() {
		return equals(getTopmostWindow());
	}

	/**
	 * Returns the top most component.
	 * @return the top most component
	 */
	private static VResizeDD getTopmostWindow() {
		if (windowOrder.size() > 0) {
			return windowOrder.get(windowOrder.size() - 1);
		}
		return null;
	}

	/** For internal use only. May be removed or replaced in the future. */
	public final void setWindowOrderAndPosition() {
		// This cannot be done in the constructor as the widgets are created in
		// a different order than on they should appear on screen
		if (windowOrder.contains(this)) {
			// Already set
			return;
		}
		final int order = windowOrder.size();
		setWindowOrder(order);
		windowOrder.add(this);
		setPopupPosition(order * STACKING_OFFSET_PIXELS, order * STACKING_OFFSET_PIXELS);

	}

	/**
	 * Sets new order for component in view.
	 * @param order new component order
	 */
	private void setWindowOrder(int order) {
		setZIndex(order + Z_INDEX);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#setZIndex(int)
	 */
	@Override
	protected final void setZIndex(int zIndex) {
		super.setZIndex(zIndex);
	}

	/**
	 * Method that constructs DOM elements.
	 */
	protected final void constructDOM() {
		setStyleName(CLASSNAME);

		String resizebox = "-resizebox";
		
		String right = "right";
		String px10 = "10px";
		
		String width = "width";
		String height = "height";
		String bottom = "bottom";
		String left = "left";
		String top = "top";
		String pC90 = "90%";
		String pC5 = "5%";
		String px0 = "0px";
		String className = "className";

		rightResizeBox = DOM.createDiv();
		DOM.setElementProperty(rightResizeBox, className, CLASSNAME + resizebox);

		bottomResizeBox = DOM.createDiv();
		DOM.setElementProperty(bottomResizeBox, className, CLASSNAME + resizebox);

		leftResizeBox = DOM.createDiv();
		DOM.setElementProperty(leftResizeBox, className, CLASSNAME + resizebox);

		topResizeBox = DOM.createDiv();
		DOM.setElementProperty(topResizeBox, className, CLASSNAME + resizebox);

		topLeftCorner = DOM.createDiv();
		DOM.setElementProperty(topLeftCorner, className, CLASSNAME + resizebox);

		topRightCorner = DOM.createDiv();
		DOM.setElementProperty(topRightCorner, className, CLASSNAME + resizebox);

		bottomRightCorner = DOM.createDiv();
		DOM.setElementProperty(bottomRightCorner, className, CLASSNAME + resizebox);

		bottomLeftCorner = DOM.createDiv();
		DOM.setElementProperty(bottomLeftCorner, className, CLASSNAME + resizebox);

		center = DOM.createDiv();

		DOM.appendChild(super.getContainerElement(), rightResizeBox);
		DOM.appendChild(super.getContainerElement(), bottomResizeBox);
		DOM.appendChild(super.getContainerElement(), leftResizeBox);
		DOM.appendChild(super.getContainerElement(), topResizeBox);

		DOM.appendChild(super.getContainerElement(), topLeftCorner);
		DOM.appendChild(super.getContainerElement(), topRightCorner);
		DOM.appendChild(super.getContainerElement(), bottomLeftCorner);
		DOM.appendChild(super.getContainerElement(), bottomRightCorner);

		DOM.appendChild(super.getContainerElement(), center);

		
		bottomResizeBox.getStyle().setProperty(width, pC90);
		bottomResizeBox.getStyle().setProperty(height, px10);
		bottomResizeBox.getStyle().setProperty(bottom, px0);
		bottomResizeBox.getStyle().setProperty(left, pC5);
		bottomResizeBox.getStyle().setProperty(right, pC5);

		
		rightResizeBox.getStyle().setProperty(height, pC90);
		rightResizeBox.getStyle().setProperty(width, px10);
		rightResizeBox.getStyle().setProperty(right, px0);
		rightResizeBox.getStyle().setProperty(top, pC5);
		rightResizeBox.getStyle().setProperty(bottom, pC5);

		
		leftResizeBox.getStyle().setProperty(height, pC90);
		leftResizeBox.getStyle().setProperty(width, px10);
		leftResizeBox.getStyle().setProperty(left, px0);
		leftResizeBox.getStyle().setProperty(top, pC5);
		leftResizeBox.getStyle().setProperty(bottom, pC5);

		
		topResizeBox.getStyle().setProperty(width, pC90);
		topResizeBox.getStyle().setProperty(height, px10);
		topResizeBox.getStyle().setProperty(top, px0);
		topResizeBox.getStyle().setProperty(left, pC5);
		topResizeBox.getStyle().setProperty(right, pC5);

		
		topLeftCorner.getStyle().setProperty(height, pC5);
		topLeftCorner.getStyle().setProperty(width, pC5);
		topLeftCorner.getStyle().setProperty(top, px0);
		topLeftCorner.getStyle().setProperty(left, px0);

		
		topRightCorner.getStyle().setProperty(height, pC5);
		topRightCorner.getStyle().setProperty(width, pC5);
		topRightCorner.getStyle().setProperty(top, px0);
		topRightCorner.getStyle().setProperty(right, px0);

		
		bottomLeftCorner.getStyle().setProperty(height, pC5);
		bottomLeftCorner.getStyle().setProperty(width, pC5);
		bottomLeftCorner.getStyle().setProperty(bottom, px0);
		bottomLeftCorner.getStyle().setProperty(left, px0);

		
		bottomRightCorner.getStyle().setProperty(height, pC5);
		bottomRightCorner.getStyle().setProperty(width, pC5);
		bottomRightCorner.getStyle().setProperty(bottom, px0);
		bottomRightCorner.getStyle().setProperty(right, px0);

		center.getStyle().setProperty(height, "100%");
		center.getStyle().setProperty(width, "100%");
		
		setCursors();

		// CHECKSTYLE:OFF Needed
		sinkEvents(Event.ONDBLCLICK | Event.MOUSEEVENTS | Event.TOUCHEVENTS | Event.ONCLICK | Event.ONLOSECAPTURE);
		// CHECKSTYLE:ON

		setWidget(contentPanel);
	}
	
	/**
	 * Method that checks if the rotation angle of component are in first sector.
	 * @return true if the rotation angle of component are in first sector, false otherwise
	 */
	private boolean firstSector() {
		return angle < NumberConstants.NUM25 || (angle > NumberConstants.NUM155 && angle < NumberConstants.NUM205) || angle > NumberConstants.NUM335 ;
	}
	
	/**
	 * Method that checks if the rotation angle of component are in second sector.
	 * @return true if the rotation angle of component are in second sector, false otherwise
	 */
	private boolean secondSector() {
		return (angle >= NumberConstants.NUM65 && angle < NumberConstants.NUM115) ||( angle >= NumberConstants.NUM245 && angle < NumberConstants.NUM295);
	}
	
	/**
	 * Method that checks if the rotation angle of component are in third sector.
	 * @return true if the rotation angle of component are in third sector, false otherwise
	 */
	private boolean thirdSector() {
		return (angle >=  NumberConstants.NUM25 && angle <  NumberConstants.NUM65) ||( angle >=  NumberConstants.NUM205 && angle <  NumberConstants.NUM245);
	}

	/**
	 * Sets CSS style cursors.
	 */
	private void setCursors() {
		String cursor = "cursor";
		String move = "move";
		String wresize = "w-resize";
		String nresize = "n-resize";
		String seresize = "se-resize";
		String swresize = "sw-resize";
		
		if (firstSector()) {
    		bottomResizeBox.getStyle().setProperty(cursor, nresize);
    		rightResizeBox.getStyle().setProperty(cursor, wresize);
    		leftResizeBox.getStyle().setProperty(cursor, wresize);
    		topResizeBox.getStyle().setProperty(cursor, nresize);
    		topLeftCorner.getStyle().setProperty(cursor, seresize);
    		topRightCorner.getStyle().setProperty(cursor, swresize);
    		bottomLeftCorner.getStyle().setProperty(cursor, swresize);
    		bottomRightCorner.getStyle().setProperty(cursor, seresize);
		} else if (secondSector()) {
			bottomResizeBox.getStyle().setProperty(cursor, wresize);
			rightResizeBox.getStyle().setProperty(cursor, nresize);
			leftResizeBox.getStyle().setProperty(cursor, nresize);
			topResizeBox.getStyle().setProperty(cursor, wresize);
			topLeftCorner.getStyle().setProperty(cursor, swresize);
			topRightCorner.getStyle().setProperty(cursor, seresize);
			bottomLeftCorner.getStyle().setProperty(cursor, seresize);
			bottomRightCorner.getStyle().setProperty(cursor, swresize);
		} else if (thirdSector()) {
			bottomResizeBox.getStyle().setProperty(cursor, swresize);
			rightResizeBox.getStyle().setProperty(cursor, seresize);
			leftResizeBox.getStyle().setProperty(cursor, seresize);
			topResizeBox.getStyle().setProperty(cursor, swresize);
			topLeftCorner.getStyle().setProperty(cursor, nresize);
			topRightCorner.getStyle().setProperty(cursor, wresize);
			bottomLeftCorner.getStyle().setProperty(cursor, wresize);
			bottomRightCorner.getStyle().setProperty(cursor,nresize);
		} else {
			bottomResizeBox.getStyle().setProperty(cursor, seresize);
			rightResizeBox.getStyle().setProperty(cursor, swresize);
			leftResizeBox.getStyle().setProperty(cursor, swresize);
			topResizeBox.getStyle().setProperty(cursor, seresize);
			topLeftCorner.getStyle().setProperty(cursor, wresize);
			topRightCorner.getStyle().setProperty(cursor, nresize);
			bottomLeftCorner.getStyle().setProperty(cursor, nresize);
			bottomRightCorner.getStyle().setProperty(cursor,wresize);
		}
		
		
		center.getStyle().setProperty(cursor, move);
		
	}

	/**
	 * Calling this method will defer ordering algorithm, to order windows based
	 * on servers bringToFront and modality instructions. Non changed windows
	 * will be left intact.
	 */
	public static void deferOrdering() {
		if (!orderingDefered) {
			orderingDefered = true;
			Scheduler.get().scheduleFinally(new Command() {

				@Override
				public void execute() {
					doServerSideOrdering();
					VNotification.bringNotificationsToFront();
				}
			});
		}
	}

	/**
	 * Method that orders the components server side.
	 */
	private static void doServerSideOrdering() {
		orderingDefered = false;
		VResizeDD[ ] array = windowOrder.toArray(new VResizeDD[windowOrder.size()]);
		Arrays.sort(array, new Comparator<VResizeDD>() {

			@Override
			public int compare(VResizeDD o1, VResizeDD o2) {
				/*
				 * Order by modality, then by bringtofront sequence.
				 */

				if (o1.bringToFrontSequence > o2.bringToFrontSequence) {
					return 1;
				} else if (o1.bringToFrontSequence < o2.bringToFrontSequence) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		for (int i = 0; i < array.length; i++) {
			VResizeDD w = array[i];
			if (w.bringToFrontSequence != -1) {
				w.bringToFront();
				w.bringToFrontSequence = -1;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#setVisible(boolean)
	 */
	@Override
	public final void setVisible(boolean visible) {
		if (!visibilityChangesDisabled) {
			super.setVisible(visible);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#show()
	 */
	@Override
	public final void show() {
		if (!windowOrder.contains(this)) {
			windowOrder.add(this);
		}

		super.show();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#hide()
	 */
	@Override
	public final void hide() {

		if (BrowserInfo.get().isIE8()) {
			fixIE8FocusCaptureIssue();
		}

		super.hide();

		int curIndex = windowOrder.indexOf(this);
		windowOrder.remove(curIndex);
		while (curIndex < windowOrder.size()) {
			windowOrder.get(curIndex).setWindowOrder(curIndex++);
		}
	}

	/**
	 * fixIE8FocusCaptureIssue.
	 */
	private void fixIE8FocusCaptureIssue() {
		Element e = DOM.createInputText();
		Style elemStyle = e.getStyle();
		elemStyle.setPosition(Position.ABSOLUTE);
		elemStyle.setTop(NumberConstants.NUM_NEG_10, Unit.PX);
		elemStyle.setWidth(0, Unit.PX);
		elemStyle.setHeight(0, Unit.PX);

		contentPanel.getElement().appendChild(e);
		e.focus();
		contentPanel.getElement().removeChild(e);
	}

	/**
	 * Shows an empty div on top of all other content; used when resizing, so
	 * that iframes (etc) do not steal event.
	 */
	private void showResizingCurtain() {
		getElement().getParentElement().insertBefore(getResizingCurtain(), getElement());
	}

	/**
	 * Hides the resizing curtain.
	 */
	private void hideResizingCurtain() {
		if (resizingCurtain != null) {
			resizingCurtain.removeFromParent();
		}
	}

	
	/**
	 * Gets the resizing curtain.
	 * @return the resizing curtain
	 */
	private Element getResizingCurtain() {
		if (resizingCurtain == null) {
			resizingCurtain = createCurtain();
			resizingCurtain.setClassName(CLASSNAME + "-resizingCurtain");
		}

		return resizingCurtain;
	}

	/**
	 * Creates the resizing curtain.
	 * @return the resizing curtain
	 */
	private Element createCurtain() {
		Element curtain = DOM.createDiv();

		curtain.getStyle().setPosition(Position.ABSOLUTE);
		curtain.getStyle().setTop(0, Unit.PX);
		curtain.getStyle().setLeft(0, Unit.PX);
		curtain.getStyle().setWidth(NumberConstants.DNUM100, Unit.PCT);
		curtain.getStyle().setHeight(NumberConstants.DNUM100, Unit.PCT);
		curtain.getStyle().setZIndex(VOverlay.Z_INDEX);

		return curtain;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#setPopupPosition(int, int)
	 */
	@Override
	public final void setPopupPosition(int left, int top) {

		super.setPopupPosition(left, top);
		if (left != uidlPositionX && client != null) {
			client.updateVariable(id, POSITIONX, left, false);
			uidlPositionX = left;
		}
		if (top != uidlPositionY && client != null) {
			client.updateVariable(id, POSITIONY, top, false);
			uidlPositionY = top;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.PopupPanel#getContainerElement()
	 */
	@Override
	protected final com.google.gwt.user.client.Element getContainerElement() {
		// in GWT 1.5 this method is used in PopupPanel constructor
		// if (contents == null) {
		return super.getContainerElement();
		// }
		// return DOM.asOld(contents);
	}

	/**
	 * Attribute that represents drag event. 
	 */
	private Event headerDragPending;

	/**
	 * Method that process the left button click event.
	 * @param event event to be processed
	 * @param type type of event
	 * @param bubble bubble of events
	 * @return bubble value
	 */
	private boolean processLeftButton(Event event, int type, boolean bubble) {
		boolean bubleAux = bubble;
		if (event.getButton() == (NativeEvent.BUTTON_LEFT)) {
			// dblclick handled in connector
			if (type != Event.ONDBLCLICK) {
				if (type == Event.ONMOUSEDOWN) {
					event.preventDefault();

					headerDragPending = event;
				} else if (type == Event.ONMOUSEMOVE && headerDragPending != null) {
					// ie won't work unless this is set here
					dragging = true;
					onDragEvent(headerDragPending);
					onDragEvent(event);
					headerDragPending = null;
				} else {
					headerDragPending = null;
				}
				bubleAux = false;
			}
			if (type == Event.ONCLICK) {
				activateOnClick();
			}
		}
		return bubleAux;
	}

	/**
	 * Method that checks if component is resizing.
	 * @param target target element.
	 * @return true if component is resizing, false otherwise
	 */
	private boolean isResizingCondition(Element target) {
		boolean resizingCondition = resizing || bottomResizeBox.equals(target) || rightResizeBox.equals(target) || leftResizeBox.equals(target);
		resizingCondition = resizingCondition || topResizeBox.equals(target) || topLeftCorner.equals(target) || topRightCorner.equals(target);
		resizingCondition = resizingCondition || bottomLeftCorner.equals(target) || bottomRightCorner.equals(target);
		return resizingCondition;
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	@Override
	public final void onBrowserEvent(final Event event) {
		boolean bubble = true;

		final int type = event.getTypeInt();

		final Element target = DOM.eventGetTarget(event);

		if (isResizingCondition(target)) {
			onResizeEvent(event);

			bubble = false;
		} else if (!dragging) {
			bubble = processLeftButton(event, type, bubble);
		} else if (dragging) {
			if (event.getButton() == (NativeEvent.BUTTON_LEFT)) {
				onDragEvent(event);
				bubble = false;
			}
		} else if (type == Event.ONCLICK) {
			activateOnClick();
		}

		if (!bubble) {
			event.stopPropagation();
		} else {
			super.onBrowserEvent(event);
		}
	}

	/**
	 * Activates click event.
	 */
	private void activateOnClick() {
		// clicked inside window or inside header, ensure to be on top
		if (!isActive()) {
			bringToFront();
		}
	}

	// CHECKSTYLE:OFF Needed for cyclomatic complex
	/**
	 * Manages resize events.
	 * @param event event
	 */
	private void onResizeEvent(Event event) {
		// CHECKSTYLE:ON
		if (Util.isTouchEventOrLeftMouseButton(event)) {

			switch (event.getTypeInt()) {
				case Event.ONMOUSEDOWN:
				case Event.ONTOUCHSTART:
					if (!isActive()) {
						bringToFront();
					}
					showResizingCurtain();
					if (BrowserInfo.get().isIE()) {
						bottomResizeBox.getStyle().setVisibility(Visibility.HIDDEN);
					}
					resizing = true;
					startX = Util.getTouchOrMouseClientX(event);
					startY = Util.getTouchOrMouseClientY(event);
					origW = getElement().getClientWidth();
					origH = getElement().getClientHeight();
					origX = Integer.parseInt(getElement().getStyle().getProperty("left").split("px")[0]);
					origY = Integer.parseInt(getElement().getStyle().getProperty("top").split("px")[0]);

					selectedElement = DOM.eventGetTarget(event);

					DOM.setCapture(getElement());
					event.preventDefault();
					break;
				case Event.ONMOUSEUP:
				case Event.ONTOUCHEND:
					setSize(event, true);
				case Event.ONTOUCHCANCEL:
					DOM.releaseCapture(getElement());
				case Event.ONLOSECAPTURE:
					hideResizingCurtain();
					if (BrowserInfo.get().isIE()) {
						bottomResizeBox.getStyle().clearVisibility();
					}
					resizing = false;
					break;
				case Event.ONMOUSEMOVE:
				case Event.ONTOUCHMOVE:
					if (resizing) {
						setSize(event, false);
						event.preventDefault();
					}
					break;
				default:
					event.preventDefault();
					break;
			}
		}
	}

	/**
	 * TODO check if we need to support this with touch based devices.
	 * 
	 * Checks if the cursor was inside the browser content area when the event
	 * happened.
	 * 
	 * @param event
	 *            The event to be checked
	 * @return true, if the cursor is inside the browser content area
	 * 
	 *         false, otherwise
	 */
	private boolean cursorInsideBrowserContentArea(Event event) {
		if (event.getClientX() < 0 || event.getClientY() < 0) {
			// Outside to the left or above
			return false;
		}

		if (event.getClientX() > Window.getClientWidth() || event.getClientY() > Window.getClientHeight()) {
			// Outside to the right or below
			return false;
		}

		return true;
	}

	/**
	 * For internal use.
	 * @return true if zone 1 are selected, false otherwise
	 */
	private boolean isSelecteResizeZone1() {
		boolean selectedResizeZone = selectedElement.equals(bottomResizeBox) || selectedElement.equals(topResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner);
		selectedResizeZone = selectedResizeZone || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner);
		return selectedResizeZone;
	}

	/**
	 * For internal use.
	 * @return true if zone 2 are selected, false otherwise
	 */
	private boolean isSelecteResizeZone2() {
		boolean selectedResizeZone = selectedElement.equals(rightResizeBox) || selectedElement.equals(leftResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner);
		selectedResizeZone = selectedResizeZone || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner);
		return selectedResizeZone;
	}

	/**
	 * For internal use.
	 * @return true if zone 3 are selected, false otherwise
	 */
	private boolean isSelecteResizeZone3() {
		boolean selectedResizeZone = selectedElement.equals(bottomResizeBox) || selectedElement.equals(topResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner);
		selectedResizeZone = selectedResizeZone || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner);
		return selectedResizeZone;
	}

	/**
	 * For internal use.
	 * @return true if zone 4 are selected, false otherwise
	 */
	private boolean isSelecteResizeZone4() {
		boolean selectedResizeZone = selectedElement.equals(rightResizeBox) || selectedElement.equals(leftResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner);
		selectedResizeZone = selectedResizeZone || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner);
		return selectedResizeZone;
	}

	// CHECKSTYLE:OFF Needed for cyclomatic complex
	/**
	 * Method that sets new size through the event listened.
	 * @param event event listened
	 * @param updateVariables indicates if the is necessary update variables
	 */
	private void setSize(Event event, boolean updateVariables) {
		// CHECKSTYLE:ON
		if (!cursorInsideBrowserContentArea(event)) {
			// Only drag while cursor is inside the browser client area
			return;
		}

		int w = 0;
		double d = 0;

		int h = 0;
		double d2 = 0;

		if (isSelecteResizeZone1()) {
			double pendiente = Math.tan(Math.toRadians(NumberConstants.NUM180 - angle));
			double a = pendiente;
			double b = (-1 * startY) - (pendiente * startX);

			d = (a * Util.getTouchOrMouseClientX(event) - (-1 * Util.getTouchOrMouseClientY(event)) + b) / (Math.sqrt(Math.pow(a, 2) + 1));

			if (selectedElement.equals(bottomResizeBox) || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner)) {
				if (angle < NumberConstants.NUM90 || angle > NumberConstants.NUM270) {
					h = (int) Math.round(origH + (d * 2));

					getElement().getParentElement().getStyle().setTop(origY - d, Unit.PX);
				} else {
					h = (int) Math.round(origH - (d * 2));

					getElement().getParentElement().getStyle().setTop(origY + d, Unit.PX);
				}
			} else if (selectedElement.equals(topResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner)) {
				if (angle < NumberConstants.NUM90 || angle > NumberConstants.NUM270) {
					h = (int) Math.round(origH - (d * 2));

					getElement().getParentElement().getStyle().setTop(origY + d, Unit.PX);
				} else {
					h = (int) Math.round(origH + (d * 2));

					getElement().getParentElement().getStyle().setTop(origY - d, Unit.PX);

				}
			}
		}

		if (isSelecteResizeZone2()) {

			if (selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner) || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner)) {
				w = h * origW / origH;

				d2 = (w - origW) / 2;

				getElement().getParentElement().getStyle().setLeft(origX - d2, Unit.PX);

			} else {

				double pendiente2 = Math.tan(Math.toRadians(NumberConstants.NUM180 - angle + NumberConstants.NUM90));
				double a2 = pendiente2;
				double b2 = (-1 * startY) - (pendiente2 * startX);

				d2 = (a2 * Util.getTouchOrMouseClientX(event) - (-1 * Util.getTouchOrMouseClientY(event)) + b2) / (Math.sqrt(Math.pow(a2, 2) + 1));

				if (selectedElement.equals(rightResizeBox) || selectedElement.equals(topRightCorner) || selectedElement.equals(bottomRightCorner)) {
					if (angle < NumberConstants.NUM180) {
						w = (int) Math.round(origW + (d2 * 2));

						getElement().getParentElement().getStyle().setLeft(origX - d2, Unit.PX);
					} else {
						w = (int) Math.round(origW - (d2 * 2));

						getElement().getParentElement().getStyle().setLeft(origX + d2, Unit.PX);
					}
				} else if (selectedElement.equals(leftResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(bottomLeftCorner)) {
					if (angle < NumberConstants.NUM180) {
						w = (int) Math.round(origW - (d2 * 2));

						getElement().getParentElement().getStyle().setLeft(origX + d2, Unit.PX);
					} else {
						w = (int) Math.round(origW + (d2 * 2));

						getElement().getParentElement().getStyle().setLeft(origX - d2, Unit.PX);

					}
				}
			}
		}

		// }

		if (isSelecteResizeZone3()) {
			setHeight(h + "px");
		}

		if (isSelecteResizeZone4()) {
			setWidth(w + "px");
		}

		if (updateVariables) {

			if (isSelecteResizeZone3()) {
				client.updateVariable(id, "height", h, false);

				if (selectedElement.equals(bottomResizeBox) || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner)) {
					if (angle < NumberConstants.NUM90 || angle > NumberConstants.NUM270) {
						client.updateVariable(id, POSITIONY, origY - (int) Math.round(d), true);
						uidlPositionY = origY - (int) Math.round(d);
					} else {
						client.updateVariable(id, POSITIONY, origY + (int) Math.round(d), true);
						uidlPositionY = origY + (int) Math.round(d);
					}
				} else if (selectedElement.equals(topResizeBox) || selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner)) {
					if (angle < NumberConstants.NUM90 || angle > NumberConstants.NUM270) {
						client.updateVariable(id, POSITIONY, origY + (int) Math.round(d), true);
						uidlPositionY = origY + (int) Math.round(d);
					} else {
						client.updateVariable(id, POSITIONY, origY - (int) Math.round(d), true);
						uidlPositionY = origY - (int) Math.round(d);

					}
				}
			}
			if (isSelecteResizeZone4()) {

				client.updateVariable(id, "width", w, false);

				if (selectedElement.equals(topLeftCorner) || selectedElement.equals(topRightCorner) || selectedElement.equals(bottomLeftCorner) || selectedElement.equals(bottomRightCorner)) {
					client.updateVariable(id, POSITIONX, origX - (int) Math.round(d2), true);
					uidlPositionX = origX - (int) Math.round(d2);
				} else if (selectedElement.equals(rightResizeBox)) {
					if (angle < NumberConstants.NUM180) {
						client.updateVariable(id, POSITIONX, origX - (int) Math.round(d2), true);
						uidlPositionX = origX - (int) Math.round(d2);
					} else {
						client.updateVariable(id, POSITIONX, origX + (int) Math.round(d2), true);
						uidlPositionX = origX + (int) Math.round(d2);
					}
				} else if (selectedElement.equals(leftResizeBox)) {
					if (angle < NumberConstants.NUM180) {
						client.updateVariable(id, POSITIONX, origX + (int) Math.round(d2), true);
						uidlPositionX = origX + (int) Math.round(d2);
					} else {
						client.updateVariable(id, POSITIONX, origX - (int) Math.round(d2), true);
						uidlPositionX = origX - (int) Math.round(d2);

					}
				}
			}
		}

		if (updateVariables) {
			// Resize has finished or is not lazy
			updateContentsSize();
		}
	}

	/**
	 * Updates variables.
	 */
	public final void updateContentsSize() {
		LayoutManager layoutManager = getLayoutManager();
		layoutManager.setNeedsMeasure(ConnectorMap.get(client).getConnector(this));
		layoutManager.layoutNow();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#setWidth(java.lang.String)
	 */
	@Override
	public final void setWidth(String width) {
		// Override PopupPanel which sets the width to the contents
		getElement().getStyle().setProperty("width", width);
		// Update v-has-width in case undefined window is resized
		setStyleName("v-has-width", width != null && width.length() > 0);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.VOverlay#setHeight(java.lang.String)
	 */
	@Override
	public final void setHeight(String height) {
		// Override PopupPanel which sets the height to the contents
		getElement().getStyle().setProperty("height", height);
		// Update v-has-height in case undefined window is resized
		setStyleName("v-has-height", height != null && height.length() > 0);
	}

	// CHECKSTYLE:OFF Needed for cyclomatic complex
	/**
	 * Manages drag events
	 * @param event drag event
	 */
	private void onDragEvent(Event event) {
		// CHECKSTYLE:ON

		if (event.getButton() == (NativeEvent.BUTTON_LEFT)) {
			if (!Util.isTouchEventOrLeftMouseButton(event)) {
				return;
			}

			switch (DOM.eventGetType(event)) {
				case Event.ONTOUCHSTART:
					if (event.getTouches().length() > 1) {
						return;
					}
				case Event.ONMOUSEDOWN:
					if (!isActive()) {
						bringToFront();
					}
					beginMovingWindow(event);
					break;
				case Event.ONMOUSEUP:
				case Event.ONTOUCHEND:
				case Event.ONTOUCHCANCEL:
				case Event.ONLOSECAPTURE:
					stopMovingWindow();
					break;
				case Event.ONMOUSEMOVE:
				case Event.ONTOUCHMOVE:
					moveWindow(event);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Actions needed for move component event.
	 * @param event component event
	 */
	private void moveWindow(Event event) {
		if (dragging) {
			// centered = false;
			if (cursorInsideBrowserContentArea(event)) {
				// Only drag while cursor is inside the browser client area
				final int x = Util.getTouchOrMouseClientX(event) - startX + origX;
				final int y = Util.getTouchOrMouseClientY(event) - startY + origY;
				getElement().getParentElement().getStyle().setTop(y, Unit.PX);
				uidlPositionY = y;

				getElement().getParentElement().getStyle().setLeft(x, Unit.PX);
				uidlPositionX = x;
			}
			DOM.eventPreventDefault(event);
		}
	}

	/**
	 * Actions needed for begin move component.
	 * @param event component event
	 */
	private void beginMovingWindow(Event event) {

		dragging = true;
		startX = Util.getTouchOrMouseClientX(event);
		startY = Util.getTouchOrMouseClientY(event);
		origX = Integer.parseInt(getElement().getStyle().getProperty("left").split("px")[0]);
		origY = Integer.parseInt(getElement().getStyle().getProperty("top").split("px")[0]);

		DOM.setCapture(getElement());
		DOM.eventPreventDefault(event);

	}

	/**
	 * Actions needed for stop move component.
	 */
	private void stopMovingWindow() {
		dragging = false;
		// hideDraggingCurtain();
		DOM.releaseCapture(getElement());

		client.updateVariable(id, POSITIONX, Integer.parseInt(getElement().getParentElement().getStyle().getLeft().split("px")[0]), true);
		client.updateVariable(id, POSITIONY, Integer.parseInt(getElement().getParentElement().getStyle().getTop().split("px")[0]), true);

		updateContentsSize();

	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.PopupPanel#onEventPreview(com.google.gwt.user.client.Event)
	 */
	@Override
	public final boolean onEventPreview(Event event) {
		if (dragging) {
			onDragEvent(event);
			return false;
		} else if (resizing) {

			onResizeEvent(event);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.UIObject#addStyleDependentName(java.lang.String)
	 */
	@Override
	public final void addStyleDependentName(String styleSuffix) {
		// VContenedorResizable's getStyleElement() does not return the same
		// element as
		// getElement(), so we need to override this.
		setStyleName(getElement(), getStylePrimaryName() + "-" + styleSuffix, true);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.ShortcutActionHandler.ShortcutActionHandlerOwner#getShortcutActionHandler()
	 */
	@Override
	public final ShortcutActionHandler getShortcutActionHandler() {
		return shortcutHandler;
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.ScrollHandler#onScroll(com.google.gwt.event.dom.client.ScrollEvent)
	 */
	@Override
	public final void onScroll(ScrollEvent event) {
		client.updateVariable(id, "scrollTop", contentPanel.getScrollPosition(), false);
		client.updateVariable(id, "scrollLeft", contentPanel.getHorizontalScrollPosition(), false);

	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.KeyDownHandler#onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent)
	 */
	@Override
	public final void onKeyDown(KeyDownEvent event) {
		if (hasFocus && event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
			event.preventDefault();
		}

		if (shortcutHandler != null) {
			shortcutHandler.handleKeyboardEvent(Event.as(event.getNativeEvent()));
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.KeyUpHandler#onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent)
	 */
	@Override
	public final void onKeyUp(KeyUpEvent event) {

	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.BlurHandler#onBlur(com.google.gwt.event.dom.client.BlurEvent)
	 */
	@Override
	public final void onBlur(BlurEvent event) {
		hasFocus = false;

		if (client.hasEventListeners(this, EventId.BLUR)) {
			client.updateVariable(id, EventId.BLUR, "", true);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.FocusHandler#onFocus(com.google.gwt.event.dom.client.FocusEvent)
	 */
	@Override
	public final void onFocus(FocusEvent event) {
		hasFocus = true;

		if (client.hasEventListeners(this, EventId.FOCUS)) {
			client.updateVariable(id, EventId.FOCUS, "", true);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.Focusable#focus()
	 */
	@Override
	public final void focus() {
		contentPanel.focus();
	}

	/**
	 * Method that gets the layout manager.
	 * @return the layout manager
	 */
	private LayoutManager getLayoutManager() {
		return LayoutManager.get(client);
	}

	/**
	 * Method that gets rotation angle of component.
	 * @return rotation angle of component
	 */
	public final int getAngle() {
		return angle;
	}

	/**
	 * Method that sets rotation angle of component.
	 * @param inAngle new rotation angle
	 */
	public final void setAngle(int inAngle) {
		this.angle = inAngle;
		setCursors();
	}

	/**
	 * Method that says to component that is only resizable in left and right. 
	 * @param resizableWidth true if is only resizable in left and right 
	 */
	public final void setResizableWidth(boolean resizableWidth) {
		if (!resizableWidth) {
			this.topLeftCorner.removeFromParent();
			this.topRightCorner.removeFromParent();
			this.bottomLeftCorner.removeFromParent();
			this.bottomRightCorner.removeFromParent();
			this.topResizeBox.removeFromParent();
			this.bottomResizeBox.removeFromParent();
		}
	}

	/**
	 * Method that says to component that is only resizable in corners. 
	 * @param resizableOnlyCorners true is only resizable in corners
	 */
	public final void setResizableOnlyCorners(boolean resizableOnlyCorners) {
		if (resizableOnlyCorners) {
			this.leftResizeBox.removeFromParent();
			this.rightResizeBox.removeFromParent();
			this.topResizeBox.removeFromParent();
			this.bottomResizeBox.removeFromParent();
		}
	}

	/**
	 * Method that says to component that is no resizable. 
	 * @param resizableDisable true that is no resizable
	 */
	public final void setResizableDisable(boolean resizableDisable) {
		if (resizableDisable) {
			this.topLeftCorner.removeFromParent();
			this.topRightCorner.removeFromParent();
			this.bottomLeftCorner.removeFromParent();
			this.bottomRightCorner.removeFromParent();
			this.topResizeBox.removeFromParent();
			this.bottomResizeBox.removeFromParent();
			this.rightResizeBox.removeFromParent();
			this.leftResizeBox.removeFromParent();
		}

	}

	/**
	 * Method that gets component's content panel.
	 * @return component's content panel
	 */
	public final FocusableScrollPanel getContentPanel() {
		return contentPanel;
	}

	/**
	 * Method that gets client connection.
	 * @return client connection
	 */
	public final ApplicationConnection getClient() {
		return client;
	}

	/**
	 * Method that sets client connection.
	 * @param clientParam new client connection
	 */
	public final void setClient(ApplicationConnection clientParam) {
		this.client = clientParam;
	}

	/**
	 * Method that gets component's id.
	 * @return component's id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Method that sets component's id.
	 * @param idParam component's new id
	 */
	public final void setId(String idParam) {
		this.id = idParam;
	}

	/**
	 * Method that gets shortcut handler.
	 * @return shortcut handler
	 */
	public final ShortcutActionHandler getShortcutHandler() {
		return shortcutHandler;
	}

	/**
	 * Method that sets shortcut handler.
	 * @param shortcutHandlerParam new shortcut handler
	 */
	public final void setShortcutHandler(ShortcutActionHandler shortcutHandlerParam) {
		this.shortcutHandler = shortcutHandlerParam;
	}

	/**
	 * Method that checks if visibility changes are disabled.
	 * @return true if visibility changes are disabled, false otherwise
	 */
	public final boolean isVisibilityChangesDisabled() {
		return visibilityChangesDisabled;
	}

	/**
	 * Method that sets if visibility changes are disabled.
	 * @param visibilityChangesDisabledParam true if visibility changes are disabled
	 */
	public final void setVisibilityChangesDisabled(boolean visibilityChangesDisabledParam) {
		this.visibilityChangesDisabled = visibilityChangesDisabledParam;
	}

	/**
	 * Method that gets bring to front sequence.
	 * @return  bring to front sequence
	 */
	public final int getBringToFrontSequence() {
		return bringToFrontSequence;
	}

	/**
	 * Method that sets bring to front sequence.
	 * @param bringToFrontSequenceParam new bring to front sequence
	 */
	public final void setBringToFrontSequence(int bringToFrontSequenceParam) {
		this.bringToFrontSequence = bringToFrontSequenceParam;
	}

}
