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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.ClickPanel.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer.MoveEvent;
import es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer.ResizeEvent;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.StyleUtil;
import es.gob.afirma.evisortemplates.widgetset.client.clickPanel.ClickPanelServerRpc;

/** 
 * <p>Class that represents click panel container component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class ClickPanel extends AbstractExtension {

	
	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = -9200380284857669426L;

	/**
	 * Attribute that represents component to be applied click panel functionality. 
	 */
	private PositionAngle componente;
	
	/**
	 * Attribute that represents resize drag and drop container. 
	 */
	private ResizeDDContainer contenedor;
	
	/**
	 * Attribute that represents layout that contains the structure. 
	 */
	private AbsoluteLayout layout;

	// CHECKSTYLE:OFF To allow anonymous inner class size
	// greater than 20 lines.
	/**
	 * Attribute that represents rpc. 
	 */
	private ClickPanelServerRpc rpc = new ClickPanelServerRpc() {

		// CHECKSTYLE:ON

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = -2965011898901535269L;

		/**
		 * {@inheritDoc}
		 * @see es.gob.afirma.evisortemplates.widgetset.client.clickPanel.ClickPanelServerRpc#mouseClick(int, int)
		 */
		@Override
		public void mouseClick(int w, int h) {

			if (layout != null && !(layout instanceof RichTableCel)) {
				createResizeDD(w, h);

				if (componente.isTable() && ((RichTable) componente).getAngle() == 0) {
					((RichTable) componente).visibleSplits(true);

				}
			}

		}

		/**
		 * {@inheritDoc}
		 * @see es.gob.afirma.evisortemplates.widgetset.client.clickPanel.ClickPanelServerRpc#resize(int, int)
		 */
		@Override
		public void resize(int w, int h) {

			if (componente.isImage() && ((RichImage) componente).getWidth() < 0) {
				((RichImage) componente).setWidth(w, Unit.PIXELS);
				((RichImage) componente).setHeight(h, Unit.PIXELS);

				if (((RichImage) componente).getParent() instanceof RichTableCel) {

					float hcel = ((RichImage) componente).getParent().getHeight();
					float wcel = ((RichImage) componente).getParent().getWidth();

					if (((h * wcel) / w) > hcel) {
						((RichImage) componente).setScaleHeight(hcel);
					} else if (((h * wcel) / w) < hcel) {
						((RichImage) componente).setScaleWidth(wcel);
					} else {
						((RichImage) componente).setWidth(wcel, Unit.PIXELS);
						((RichImage) componente).setHeight(hcel, Unit.PIXELS);
					}

					((RichTableCel) ((RichImage) componente).getParent()).focusContent(((RichImage) componente));
				} else {
					((RichImage) componente).setScaleWidth(NumberConstants.FNUM400);
				}

			}

		}

	};

	/**
	 * Constructor method for the class ClickPanel.java.
	 * @param component component who is wrapped.
	 * @param layoutParam layout
	 */
	public ClickPanel(PositionAngle component, AbsoluteLayout layoutParam) {
		this.componente = component;

		this.layout = layoutParam;
		registerRpc(rpc);

		super.extend((AbstractClientConnector) component);

	}

	/**
	 * Method that creates the resize drag and drop container.
	 * @param w width value
	 * @param h height value
	 */
	public final void createResizeDD(int w, int h) {

		if (!componente.isDocument()) {

			// Se obtiene una nueva instancia del componente que será una copia
			// de
			// la original.
			// Esto es necesario para que se posicione al frente de la página al
			// entrar en modo edición
			PositionAngle comp = componente.newInstance();
			new ClickPanel((PositionAngle) comp, layout);
			this.layout.replaceComponent(componente, comp);

			componente = comp;

		}

		contenedor = new ResizeDDContainer(layout, componente);
		if (componente.isText()) {
			//Se suma 4px al width y height para compensar los bordes del contenedor
			contenedor.setWidth(w+ NumberConstants.NUM4, Unit.PIXELS);
		} else {
			contenedor.setWidth(w, Unit.PIXELS);
			contenedor.setHeight(h, Unit.PIXELS);
		}
		contenedor.setPositionY(componente.getPosY());
		contenedor.setPositionX(componente.getPosX());
		contenedor.setImmediate(true);

		if (!componente.isDocument()) {
			if (componente.isBarCode()) {
				contenedor.setResizableDisable(true);
			} else {
				contenedor.setResizableDisable(false);
			}
			contenedor.setResizableOnlyCorners(false);
		} else {
			contenedor.setResizableOnlyCorners(true);
		}

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		contenedor.addMoveListener(new ResizeDDContainer.MoveListener() {

			// CHECKSTYLE:ON
			/**
			 * 
			 */
			private static final long serialVersionUID = -4317537444188893860L;

			@Override
			public void windowMoved(MoveEvent e) {
				componente.setPosX(((ResizeDDContainer) e.getComponent()).getPositionX());
				componente.setPosY(((ResizeDDContainer) e.getComponent()).getPositionY());

				if (componente.getWidthUnits().equals(Unit.PERCENTAGE)) {
					componente.setWidth(((ResizeDDContainer) e.getComponent()).getWidth(), ((ResizeDDContainer) e.getComponent()).getWidthUnits());
				}
				ComponentPosition pos = layout.getPosition(componente);
				pos.setTopValue(Float.parseFloat(componente.getPosY() + ""));
				pos.setLeftValue(Float.parseFloat(componente.getPosX() + ""));
				layout.setPosition(componente, pos);
				contenedor.resetMenuPosition(componente.getPosX(), componente.getPosY(), componente.getHeight());
			}
		});

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		contenedor.addResizeListener(new ResizeDDContainer.ResizeListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -619756272656158071L;

			@Override
			public void windowResized(ResizeEvent e) {
				

				if (!componente.isText()) {

						componente.setWidth(((ResizeDDContainer) e.getComponent()).getWidth(), Unit.PIXELS);
					

						componente.setHeight(((ResizeDDContainer) e.getComponent()).getHeight(), Unit.PIXELS);

					
				} else {
					if (e.getComponent().getWidth() + ((ResizeDDContainer) e.getComponent()).getPositionX() < layout.getWidth()) {

						//-4 para compensar los bordes del componente
						componente.setWidth(((ResizeDDContainer) e.getComponent()).getWidth()- NumberConstants.NUM4, Unit.PIXELS);
					} else {
						componente.setWidth(layout.getWidth() - ((ResizeDDContainer) e.getComponent()).getPositionX()- NumberConstants.NUM4, Unit.PIXELS);

						contenedor.setWidth(layout.getWidth() - ((ResizeDDContainer) e.getComponent()).getPositionX(), Unit.PIXELS);
					}
					contenedor.setHeightUndefined();
					// Se inserta un richlabel transparente para que el
					// contenedor pueda ajustarse al tamaño
					contenedor.setContent(new RichLabel(((RichLabel) componente).getValue(), true));

				}

			}
		});

		((PositionAngle) componente).setContainer(contenedor);

		if (componente.isText()) {
			contenedor.setHeightUndefined();
			contenedor.setResizableWidth(false);
			contenedor.setContent(new RichLabel(((RichLabel) componente).getValue(), true));
		}

		//StyleUtil.rotaryStyle(componente, idComp);
		StyleUtil.rotaryStyle(componente);

	}

}