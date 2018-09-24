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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.FloatMenu.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import org.apache.log4j.Logger;
import org.vaadin.mikael.rotaryknob.RotaryKnob;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.windows.ConditionWindow;
import es.gob.afirma.evisortemplates.windows.ConfirmationWindow;
import es.gob.afirma.evisortemplates.windows.ForEachWindow;
import es.gob.afirma.evisortemplates.windows.TextWindow;

/** 
 * <p>Class that represents a float menu from components in edit mode.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class FloatMenu extends Panel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5345638376667104734L;

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(FloatMenu.class);

	/**
	 * Attribute that represents rotary knob button. 
	 */
	private RotaryKnob rotary;

	/**
	 * Attribute that represents rotary 90 button. 
	 */
	private Button rotary90Button;

	/**
	 * Attribute that represents aprox size of menu. 
	 */
	private int sizeAprox;

	/**
	 * Constructor method for the class FloatMenu.java.
	 * @param container resize drag and drop container
	 * @param component component for menu options
	 * @param layout layout from component
	 */
	public FloatMenu(final ResizeDDContainer container, final PositionAngle component, final AbsoluteLayout layout) {

		super();

		rotary = new RotaryKnob(NumberConstants.DNUM0, NumberConstants.DNUM359, NumberConstants.NUM0);

		HorizontalLayout menuContainer = new HorizontalLayout();

		Button removeButton = new Button();
		removeButton.setIcon(FontAwesome.TRASH_O);
		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		removeButton.addClickListener(new Button.ClickListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5700066755274362258L;

			/**
			 * {@inheritDoc}
			 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				final ConfirmationWindow window = new ConfirmationWindow(MessagesEVT.getInstance().getProperty("removeComponent"));

				window.setClosable(false);

				window.addClickYesButtonListener(new Button.ClickListener() {

					/**
					 * Attribute that represents serialVersionUID. 
					 */
					private static final long serialVersionUID = -7255714059527411466L;

					/**
					 * {@inheritDoc}
					 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
					 */
					@Override
					public void buttonClick(ClickEvent event) {
						LOG.info(MessagesEVT.getInstance().getProperty("menuClickRemove"));
						if (component instanceof RichLabel) {
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getOriginalValue());
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getCondition1());
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getCondition2());
						} else if (component instanceof RichImage) {
							FormVariable.removeGlobalVarUsed(((RichImage) component).getVariable());
							FormVariable.removeGlobalVarUsed(((RichImage) component).getCondition1());
							FormVariable.removeGlobalVarUsed(((RichImage) component).getCondition2());
						} else if (component instanceof BarCode) {
							FormVariable.removeGlobalVarUsed(((BarCode) component).getMessage());
							FormVariable.removeGlobalVarUsed(((BarCode) component).getCondition1());
							FormVariable.removeGlobalVarUsed(((BarCode) component).getCondition2());
						} else if (component instanceof RichTable){
							FormVariable.removeGlobalVarUsed(((RichTable) component).getCondition1());
							FormVariable.removeGlobalVarUsed(((RichTable) component).getCondition2());
						}
						layout.removeComponent(component);
						container.close();
						window.close();

					}
				});

				UI.getCurrent().addWindow(window);

			}
		});

		menuContainer.addComponent(removeButton);

		if (component.isText()) {
			Button botonEditar = new Button();
			botonEditar.setIcon(FontAwesome.EDIT);
			// CHECKSTYLE:OFF To allow anonymous inner class size
			// greater than 20 lines.
			botonEditar.addClickListener(new Button.ClickListener() {

				// CHECKSTYLE:ON

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 5509123949183285854L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {
					LOG.info(MessagesEVT.getInstance().getProperty("menuClickEditText"));
					container.close();
					((PositionAngle) component).setContainer(null);

					final TextWindow eidtWindow = new TextWindow(((RichLabel) component).getOriginalValue(), MessagesEVT.getInstance().getProperty("textWindowCaptionEdition"), false, true, !((PositionAngle) component).isForEach(), Format.hasVarIndex(((RichLabel) component).getOriginalValue()));

					eidtWindow.addClickBotonAceptarListener(new Button.ClickListener() {

						/**
						 * serialVersionUID
						 */
						private static final long serialVersionUID = -5446231273947179373L;

						/**
						 * {@inheritDoc}
						 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
						 */
						@Override
						public void buttonClick(ClickEvent event) {
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getOriginalValue());
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getCondition1());
							FormVariable.removeGlobalVarUsed(((RichLabel) component).getCondition2());
							((RichLabel) component).setOriginalValue(eidtWindow.getOriginalTextValue());
							((RichLabel) component).setValue(eidtWindow.getTextValue());
							FormVariable.addGlobalVarUsed(((RichLabel) component).getOriginalValue());
							FormVariable.addGlobalVarConditionUsed(((RichLabel) component).getCondition1());
							FormVariable.addGlobalVarConditionUsed(((RichLabel) component).getCondition2());
							eidtWindow.close();
						}
					});

					UI.getCurrent().addWindow(eidtWindow);
				}
			});

			menuContainer.addComponent(botonEditar);
		}

		if (component.isTable()) {

			Button borderButton = new Button();
			borderButton.setIcon(FontAwesome.TABLE);
			borderButton.addClickListener(new Button.ClickListener() {

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 1728103540586226113L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {
					LOG.info(MessagesEVT.getInstance().getProperty("menuClickChangeBorderTable"));
					((RichTable) component).changeBorderStyle();

				}
			});

			menuContainer.addComponent(borderButton);

		} else if (!component.isTable() && !component.isDocument()) {
			rotary.setImmediate(true);
			rotary.setValue(Double.parseDouble(component.getAngle() + ""));
			rotary.addValueChangeListener(new ValueChangeListener() {

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 174287905618307164L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
				 */
				@Override
				public void valueChange(ValueChangeEvent event) {

					component.setAngle(Integer.parseInt(Math.round(rotary.getValue()) + ""));

				}
			});
			menuContainer.addComponent(rotary);
		}

		if (!component.isDocument()) {
			rotary90Button = new Button();
			rotary90Button.setIcon(FontAwesome.ROTATE_RIGHT);
			// CHECKSTYLE:OFF To allow anonymous inner class size
			// greater than 20 lines.
			rotary90Button.addClickListener(new Button.ClickListener() {

				// CHECKSTYLE:ON

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 2876775110256625147L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {

					LOG.info(MessagesEVT.getInstance().getProperty("menuClickRotate90"));

					int nuevoAngulo = (component.getAngle() + NumberConstants.NUM90) % NumberConstants.NUM360;

					component.setAngle(nuevoAngulo);

					if (component.getAngle() != 0) {

						if (component.isTable()) {

							((RichTable) component).visibleSplits(false);
						}
					} else {
						if (component.isTable()) {
							((RichTable) component).visibleSplits(true);
						}
					}

					if (rotary != null) {
						rotary.setValue(Double.parseDouble(component.getAngle() + ""));
					}
				}
			});

			menuContainer.addComponent(rotary90Button);

			Button conditionButton = new Button();
			conditionButton.setIcon(FontAwesome.EYE);
			conditionButton.addClickListener(new Button.ClickListener() {

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = -5159161999559727649L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {

					LOG.info(MessagesEVT.getInstance().getProperty("menuClickCondition"));

					ConditionWindow window = new ConditionWindow(component);

					UI.getCurrent().addWindow(window);
				}
			});

			menuContainer.addComponent(conditionButton);

		} else {
			Button frontBackButton = new Button();
			frontBackButton.setIcon(FontAwesome.UNSORTED);
			frontBackButton.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1081062062078988510L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {
					LOG.info(MessagesEVT.getInstance().getProperty("menuClickFrontBack"));
					((Document) component).changeFrontBack();

				}
			});

			menuContainer.addComponent(frontBackButton);
		}

		if (!component.isDocument() && (!component.isTable() || (component.isTable() && !((RichTable) component).isForEachTable()))) {
			Button forEachButton = new Button();
			forEachButton.setIcon(FontAwesome.LIST_OL);
			forEachButton.addClickListener(new Button.ClickListener() {

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = 6690711972324133471L;

				/**
				 * {@inheritDoc}
				 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
				 */
				@Override
				public void buttonClick(ClickEvent event) {

					LOG.info(MessagesEVT.getInstance().getProperty("menuClickForEach"));

					ForEachWindow window = new ForEachWindow(component);

					UI.getCurrent().addWindow(window);
				}
			});

			menuContainer.addComponent(forEachButton);
		}

		Label lb = new Label();
		lb.setWidth(NumberConstants.FNUM40, Unit.PIXELS);
		lb.setStyleName("v-window-closebox");
		HorizontalLayout closeButon = new HorizontalLayout();
		closeButon.addComponent(lb);
		closeButon.setWidth(NumberConstants.FNUM40, Unit.PIXELS);
		closeButon.addLayoutClickListener(new LayoutClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 1283992573532426568L;

			/**
			 * {@inheritDoc}
			 * @see com.vaadin.event.LayoutEvents.LayoutClickListener#layoutClick(com.vaadin.event.LayoutEvents.LayoutClickEvent)
			 */
			@Override
			public void layoutClick(LayoutClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("menuClickClose"));
				container.close();
				((PositionAngle) component).setContainer(null);

				if (component.isTable()) {
					((RichTable) component).visibleSplits(false);
				}
			}
		});

		menuContainer.addComponent(closeButon);

		setContent(menuContainer);
		setSizeUndefined();

		// El tamaño aproximado del menú es el número de componentes por 45
		sizeAprox = menuContainer.getComponentCount() * NumberConstants.NUM45;

	}

	/**
	 * Method that sets angle value for rotation.
	 * @param angle new angle value for rotation.
	 */
	public final void setAngle(int angle) {
		if (rotary != null) {
			rotary.setValue(Double.parseDouble(angle + ""));
		}
	}

	/**
	 * Method that gets aprox size parameter.
	 * @return aprox size parameter value
	 */
	public final int getSizeAprox() {
		return sizeAprox;
	}

	/**
	 * Method that sets aprox size parameter.
	 * @param sizeAproxParam new aprox size parameter value
	 */
	public final void setSizeAprox(int sizeAproxParam) {
		this.sizeAprox = sizeAproxParam;
	}

}
