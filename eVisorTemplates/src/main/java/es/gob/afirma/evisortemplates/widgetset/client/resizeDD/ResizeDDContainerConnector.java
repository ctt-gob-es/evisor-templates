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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.resizeDD.ResizeDDContainerConnector.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.resizeDD;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.Util;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.client.ui.ClickEventHandler;
import com.vaadin.client.ui.PostLayoutListener;
import com.vaadin.client.ui.ShortcutActionHandler;
import com.vaadin.client.ui.ShortcutActionHandler.BeforeShortcutActionListener;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.client.ui.layout.MayScrollChildren;
import com.vaadin.client.ui.window.WindowMoveEvent;
import com.vaadin.client.ui.window.WindowMoveHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

import es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer;

/** 
 * <p>Class connector for ResizeDDContainer.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
@Connect(value = ResizeDDContainer.class)
public class ResizeDDContainerConnector extends AbstractSingleComponentContainerConnector implements Paintable, BeforeShortcutActionListener, SimpleManagedLayout, PostLayoutListener, MayScrollChildren, WindowMoveHandler {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3092986722562062430L;

	/**
	 * Attribute that represents a window clone. 
	 */
	private Node windowClone;

	/**
	 * Attribute that represents controls String constant. 
	 */
	private static final String CONTROLS = "controls";

	/**
	 * Attribute that represents click event handler. 
	 */
	private ClickEventHandler clickEventHandler = new ClickEventHandler(this) {

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.client.ui.ClickEventHandler#fireClick(com.google.gwt.dom.client.NativeEvent, com.vaadin.shared.MouseEventDetails)
		 */
		@Override
		protected void fireClick(NativeEvent event, MouseEventDetails mouseDetails) {
		}
	};

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#delegateCaptionHandling()
	 */
	@Override
	public final boolean delegateCaptionHandling() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractConnector#init()
	 */
	@Override
	protected final void init() {
		super.init();

		VResizeDD window = getWidget();
		window.setId(getConnectorId());
		window.setClient(getConnection());

		window.setAngle(getState().getAngle());

		getLayoutManager().registerDependency(this, window.getContentPanel().getElement());
		window.setOwner(getConnection().getUIConnector().getWidget());
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#onUnregister()
	 */
	@Override
	public final void onUnregister() {
		LayoutManager lm = getLayoutManager();
		VResizeDD window = getWidget();
		lm.unregisterDependency(this, window.getContentPanel().getElement());
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.Paintable#updateFromUIDL(com.vaadin.client.UIDL, com.vaadin.client.ApplicationConnection)
	 */
	@Override
	public final void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

		VResizeDD window = getWidget();
		String connectorId = getConnectorId();

		window.setVisibilityChangesDisabled(true);
		if (!isRealUpdate(uidl)) {
			return;
		}
		window.setVisibilityChangesDisabled(false);

		// we may have actions
		for (int i = 0; i < uidl.getChildCount(); i++) {
			UIDL childUidl = uidl.getChildUIDL(i);
			if (childUidl.getTag().equals("actions")) {
				if (window.getShortcutHandler() == null) {
					window.setShortcutHandler(new ShortcutActionHandler(connectorId, client));
				}
				window.getShortcutHandler().updateActionMap(childUidl);
			}

		}

		if (uidl.hasAttribute("bringToFront")) {
			/*
			 * Focus as a side-effect. Will be overridden by
			 * ApplicationConnection if another component was focused by the
			 * server side.
			 */
			window.getContentPanel().focus();
			window.setBringToFrontSequence(uidl.getIntAttribute("bringToFront"));
			VResizeDD.deferOrdering();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.HasComponentsConnector#updateCaption(com.vaadin.client.ComponentConnector)
	 */
	@Override
	public void updateCaption(ComponentConnector component) {
		// NOP, window has own caption, layout caption not rendered
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.ShortcutActionHandler.BeforeShortcutActionListener#onBeforeShortcutAction(com.google.gwt.user.client.Event)
	 */
	@Override
	public void onBeforeShortcutAction(Event e) {
		// NOP, nothing to update just avoid workaround ( causes excess
		// blur/focus )
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#getWidget()
	 */
	@Override
	public final VResizeDD getWidget() {
		return (VResizeDD) super.getWidget();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ConnectorHierarchyChangeEvent.ConnectorHierarchyChangeHandler#onConnectorHierarchyChange(com.vaadin.client.ConnectorHierarchyChangeEvent)
	 */
	@Override
	public final void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
		// We always have 1 child, unless the child is hidden
		getWidget().getContentPanel().setWidget(getContentWidget());

		if (getParent() == null && windowClone != null) {
			// If the window is removed from the UI, add the copy of the
			// contents to the window (in case of an 'out-animation')
			getWidget().getElement().removeAllChildren();
			getWidget().getElement().appendChild(windowClone);

			// Clean reference
			windowClone = null;
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.SimpleManagedLayout#layout()
	 */
	@Override
	public final void layout() {
		LayoutManager lm = getLayoutManager();
		VResizeDD window = getWidget();
		ComponentConnector content = getContent();
		boolean hasContent = (content != null);
		Element contentElement = window.getContentPanel().getElement();

		/*
		 * Must set absolute position if the child has relative height and
		 * there's a chance of horizontal scrolling as some browsers will
		 * otherwise not take the scrollbar into account when calculating the
		 * height.
		 */
		if (hasContent) {
			Element layoutElement = content.getWidget().getElement();
			Style childStyle = layoutElement.getStyle();

			// IE8 needs some hackery to measure its content correctly
			Util.forceIE8Redraw(layoutElement);

			if (content.isRelativeHeight() && !BrowserInfo.get().isIE9()) {
				childStyle.setPosition(Position.ABSOLUTE);

				Style wrapperStyle = contentElement.getStyle();
				if (window.getElement().getStyle().getWidth().length() == 0 && !content.isRelativeWidth()) {
					/*
					 * Need to lock width to make undefined width work even with
					 * absolute positioning
					 */
					int contentWidth = lm.getOuterWidth(layoutElement);
					wrapperStyle.setWidth(contentWidth, Unit.PX);
				} else {
					wrapperStyle.clearWidth();
				}
			} else {
				childStyle.clearPosition();
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.PostLayoutListener#postLayout()
	 */
	@Override
	public final void postLayout() {
		VResizeDD window = getWidget();

		if (!window.isAttached()) {
			return;
		}
		window.positionOrSizeUpdated();

		if (getParent() != null) {
			windowClone = cloneNodeFilteringMedia(getWidget().getElement().getFirstChild());
		}
	}

	/**
	 * Method that clones container window.
	 * @param node window to clone
	 * @return cloned window
	 */
	private Node cloneNodeFilteringMedia(Node node) {
		if (node instanceof Element) {
			Element old = (Element) node;
			if ("audio".equalsIgnoreCase(old.getTagName()) || "video".equalsIgnoreCase(old.getTagName())) {
				if (!old.hasAttribute(CONTROLS) && "audio".equalsIgnoreCase(old.getTagName())) {
					return null; // nothing to animate, so we won't add this to
								 // the clone
				}
				Element newEl = DOM.createElement(old.getTagName());
				if (old.hasAttribute(CONTROLS)) {
					newEl.setAttribute(CONTROLS, old.getAttribute(CONTROLS));
				}
				if (old.hasAttribute("style")) {
					newEl.setAttribute("style", old.getAttribute("style"));
				}
				if (old.hasAttribute("class")) {
					newEl.setAttribute("class", old.getAttribute("class"));
				}
				return newEl;
			}
		}
		return getNodeRes(node);

	}

	/**
	 * Method that clones container window.
	 * @param node  window to clone
	 * @return cloned window
	 */
	private Node getNodeRes(Node node) {
		Node res = node.cloneNode(false);
		if (node.hasChildNodes()) {
			NodeList<Node> nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node clone = cloneNodeFilteringMedia(nl.getItem(i));
				if (clone != null) {
					res.appendChild(clone);
				}
			}
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#getState()
	 */
	@Override
	public final ResizeDDState getState() {
		return (ResizeDDState) super.getState();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#onStateChanged(com.vaadin.client.communication.StateChangeEvent)
	 */
	@Override
	public final void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		VResizeDD window = getWidget();
		ResizeDDState state = getState();

		window.setAngle(state.getAngle());

		window.setResizableWidth(state.isResizableWidth());

		window.setResizableOnlyCorners(state.isResizableOnlyCorners());

		window.setResizableDisable(state.isResizableDisable());

		if (!window.isAttached()) {
			window.setVisible(false); // hide until possible centering
			window.show();
		}

		clickEventHandler.handleEventHandlerRegistration();

		updateWindowPosition();

		window.getContentPanel().setScrollPosition(state.scrollTop);
		window.getContentPanel().setHorizontalScrollPosition(state.scrollLeft);

		window.setVisible(true);

	}

	// Need to override default because of window mode
	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#updateComponentSize()
	 */
	@Override
	protected final void updateComponentSize() {
		super.updateComponentSize();

	}

	/**
	 * Method that updates window position.
	 */
	protected final void updateWindowPosition() {
		VResizeDD window = getWidget();
		ResizeDDState state = getState();
		if (state.getPositionX() >= 0 || state.getPositionY() >= 0) {
			window.setPopupPosition(Math.round(state.getPositionX()), Math.round(state.getPositionY()));
		}

	}

	/**
	 * Gives the WindowConnector an order number. As a side effect, moves the
	 * window according to its order number so the windows are stacked. This
	 * method should be called for each window in the order they should appear.
	 */
	public final void setWindowOrderAndPosition() {
		getWidget().setWindowOrderAndPosition();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.AbstractComponentConnector#hasTooltip()
	 */
	@Override
	public final boolean hasTooltip() {
		/*
		 * Tooltip event handler always needed on the window widget to make sure
		 * tooltips are properly hidden. (#11448)
		 */
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.client.ui.window.WindowMoveHandler#onWindowMove(com.vaadin.client.ui.window.WindowMoveEvent)
	 */
	@Override
	public void onWindowMove(WindowMoveEvent event) {

	}
}