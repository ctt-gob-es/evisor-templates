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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.LayoutTemplateClickListener.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import org.apache.log4j.Logger;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

import es.gob.afirma.evisortemplates.components.impl.RichTable.TableAbsoluteLayout;
import es.gob.afirma.evisortemplates.components.impl.RichTable.TableHorizontalSplitPanel;
import es.gob.afirma.evisortemplates.components.impl.RichTable.TableVerticalSplitPanel;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.windows.BarCodeWindow;
import es.gob.afirma.evisortemplates.windows.ForEachWindow;
import es.gob.afirma.evisortemplates.windows.ImageWindow;
import es.gob.afirma.evisortemplates.windows.TableWindow;
import es.gob.afirma.evisortemplates.windows.TextWindow;

/** 
 * <p>Class needed to support template's clicks.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class LayoutTemplateClickListener implements LayoutClickListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6932319288843850709L;

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(LayoutTemplateClickListener.class);

	/**
	 * Attribute that represents the template instance. 
	 */
	private Template template;

	/**
	 * Constructor method for the class LayoutTemplateClickListener.java.
	 * @param templateParam template's instance to be listened
	 */
	public LayoutTemplateClickListener(Template templateParam) {
		this.template = templateParam;
	}

	/**
	 * Method that checks if template's layout are clicked.
	 * @param event event to be checked
	 * @return true if template's layout are clicked, false otherwise
	 */
	private boolean layoutClicked(final LayoutClickEvent event) {
		boolean tableInstance = (event.getClickedComponent() instanceof TableAbsoluteLayout || event.getClickedComponent() instanceof TableHorizontalSplitPanel || event.getClickedComponent() instanceof TableVerticalSplitPanel || event.getClickedComponent() instanceof RichTableCel);
		boolean templateClicked = (event.getClickedComponent() instanceof AbsoluteLayout || event.getClickedComponent() instanceof HorizontalSplitPanel || event.getClickedComponent() instanceof VerticalSplitPanel) && !tableInstance;
		return event.getButton().equals(MouseButton.LEFT) && templateClicked;
	}

	/**
	 * {@inheritDoc}.
	 * @see com.vaadin.event.LayoutEvents.LayoutClickListener#layoutClick(com.vaadin.event.LayoutEvents.LayoutClickEvent)
	 */
	@Override
	public final void layoutClick(final LayoutClickEvent event) {

		// El evento solo actua si se ha clicado en el layout o
		// bien en los
		// splitpanels y no en uno de los componentes que hay en
		// el
		if (layoutClicked(event)) {
			// Si la opción seleccionada es introducir texto
			if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTTEXT)) {

				final TextWindow window = new TextWindow("", MessagesEVT.getInstance().getProperty("textWindowCaption"), false, true, true, false);

				window.setPosX(event.getRelativeX());
				window.setPosY(event.getRelativeY());

				// CHECKSTYLE:OFF To allow anonymous inner class size
				// greater than 20 lines.
				window.addClickBotonAceptarListener(new Button.ClickListener() {

					// CHECKSTYLE:ON
					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -6043712498687285970L;

					@Override
					public void buttonClick(ClickEvent event) {
						if (window.getTextValue() != null && !window.getTextValue().isEmpty()) {
							FormVariable.addGlobalVarUsed(window.getOriginalTextValue());
							RichLabel label = new RichLabel();
							label.setPosX(window.getPosX());
							label.setPosY(window.getPosY());
							label.setOriginalValue(window.getOriginalTextValue());
							label.setValue(window.getTextValue());
							if (window.isForEach()) {
								label.setIsForEach(window.isForEach());
								label.setForEachType(ForEachWindow.OPORIZONTAL);
							}
							insertComponent(window.getPosX(), window.getPosY(), label);
							LOG.info(MessagesEVT.getInstance().getProperty("insertingTextInTemplate"));
							window.close();
						}

					}
				});

				UI.getCurrent().addWindow(window);
			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTIMAGE)) {
				// Si está seleccionado insertar imagen

				final ImageWindow window = new ImageWindow();

				window.setPosX(event.getRelativeX());
				window.setPosY(event.getRelativeY());

				window.addClickAcceptButtonListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = 2221029224002987006L;

					@Override
					public void buttonClick(ClickEvent event) {
						if (window.isImageIn()) {
							if (window.getVariable() != null) {
								FormVariable.addGlobalVarUsed(window.getVariable());
							}
							window.getImage().setPosX(window.getPosX());
							window.getImage().setPosY(window.getPosY());
							insertComponent(window.getPosX(), window.getPosY(), window.getImage());
							LOG.info(MessagesEVT.getInstance().getProperty("insertingImageInTemplate"));
							window.close();
						} else {
							Notification notification = new Notification(MessagesEVT.getInstance().getProperty("noInsertedImage"));
							notification.show(Page.getCurrent());
						}
					}
				});
				UI.getCurrent().addWindow(window);
			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTTABLE)) {
				final TableWindow window = new TableWindow(false);
				window.setPosX(event.getRelativeX());
				window.setPosY(event.getRelativeY());

				window.addClickAcceptButtonListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -7080047553982765564L;

					@Override
					public void buttonClick(ClickEvent event) {
						RichTable table = new RichTable((Integer) window.getColsValue(), (Integer) window.getRowsValue(), null, null, false, true, window.isForEachRows(), window.getHeadValue());
						table.setPosX(window.getPosX());
						table.setPosY(window.getPosY());
						insertComponent(window.getPosX(), window.getPosY(), table);
						LOG.info(MessagesEVT.getInstance().getProperty("insertingTableInTemplate"));
						window.close();

					}
				});

				UI.getCurrent().addWindow(window);

			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTCODEBAR)) {
				final BarCodeWindow window = new BarCodeWindow(true);

				window.setPosX(event.getRelativeX());
				window.setPosY(event.getRelativeY());

				// CHECKSTYLE:OFF To allow anonymous inner class size
				// greater than 20 lines.
				window.addClickAcceptButtonListener(new Button.ClickListener() {

					// CHECKSTYLE:ON

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = 6418771626414785553L;

					@Override
					public void buttonClick(ClickEvent event) {
						if (window.getTextValue() != null && !window.getTextValue().isEmpty()) {
							try {
								BarCode codigo = new BarCode();
								FormVariable.addGlobalVarUsed(window.getTextValue());
								codigo.setMessage(window.getTextValue());
								codigo.setType((String) window.getCodeType());
								codigo.setPosX(window.getPosX());
								codigo.setPosY(window.getPosY());
								if (window.isForEach()) {
									codigo.setIsForEach(window.isForEach());
									codigo.setForEachType(ForEachWindow.OPORIZONTAL);
								}
								insertComponent(window.getPosX(), window.getPosY(), codigo);
								LOG.info(MessagesEVT.getInstance().getProperty("insertingCodeBarInTemplate"));
								window.close();
							} catch (IncorrectMessageBarCodeException e) {
								LOG.error(MessagesEVT.getInstance().getProperty("barcodeIncorrectMessage"), e);
								Notification notification = new Notification("Error", MessagesEVT.getInstance().getProperty("barcodeIncorrectMessage"));
								notification.show(Page.getCurrent());
							}

						}
					}
				});
				UI.getCurrent().addWindow(window);
			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTDOCUMENT)) {
				if (!template.isLast() && !template.isFront()) {
					if (!template.isHasDocument()) {
						Document doc = new Document(template);
						doc.setPosX(event.getRelativeX());
						doc.setPosY(event.getRelativeY());
						insertComponent(event.getRelativeX(), event.getRelativeY(), doc);
						LOG.info(MessagesEVT.getInstance().getProperty("insertingDocInTemplate"));
					}
				} else {
					Notification notification = new Notification(MessagesEVT.getInstance().getProperty("incorrectDocZone"));
					notification.show(Page.getCurrent());
				}
			}

		}
	}

	/**
	 * Method that inserts a component in template's instance.
	 * @param posX x coordinate
	 * @param posY y coordinate
	 * @param component component to be inserted
	 */
	private void insertComponent(float posX, float posY, Component component) {
		template.insertComponent(posX, posY, component);

	}

}
