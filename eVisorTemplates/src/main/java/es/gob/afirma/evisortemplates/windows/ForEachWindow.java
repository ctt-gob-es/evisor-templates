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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.ForEachWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
//import com.vaadin.ui.TextField;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.components.impl.BarCode;
import es.gob.afirma.evisortemplates.components.impl.ComboForEach;
import es.gob.afirma.evisortemplates.components.impl.RichLabel;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that represents the "for each" window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class ForEachWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2288144668420255408L;

	/**
	 * Attribute that represents OpVertical String constant. 
	 */
	public static final String OPVERTICAL = "OpVertical";
	
	/**
	 * Attribute that represents OpHorizontal String constant. 
	 */
	public static final String OPORIZONTAL = "OpHorizontal";

	/**
	 * Constructor method for the class ForEachWindow.java.
	 * @param componente component
	 */
	public ForEachWindow(final PositionAngle componente) {

		super();
		if (componente.isForEach()) {
			setCaption(MessagesEVT.getInstance().getProperty("forEachWindowCaptionActive"));
		} else {
			setCaption(MessagesEVT.getInstance().getProperty("forEachWindowCaptionInactive"));
		}

		setModal(true);
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("forEachWindowWidth")), Unit.PIXELS);

		FormLayout layout = new FormLayout();

		final OptionGroup options = new OptionGroup();

		options.addItem(OPVERTICAL);
		options.setItemCaption(OPVERTICAL, MessagesEVT.getInstance().getProperty("forEachWindowOpVer"));

		options.addItem(OPORIZONTAL);
		options.setItemCaption(OPORIZONTAL, MessagesEVT.getInstance().getProperty("forEachWindowOpHor"));

		options.select(OPORIZONTAL);
		options.setNullSelectionAllowed(false);
		options.setHtmlContentAllowed(true);
		options.setImmediate(true);

		options.addValueChangeListener(new ValueChangeListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4721513772103463248L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				String value = (String) event.getProperty().getValue();
				options.select(value);
			}
		});

		options.addStyleName("smallText");
		layout.addComponent(options);

		final ComboForEach combo = new ComboForEach();
		combo.setWidth(NumberConstants.FNUM400, Unit.PIXELS);
		combo.setEnabled(false);

		layout.addComponent(combo);

//		final TextField condition = new TextField(MessagesEVT.getInstance().getProperty("forEachWindowTextFieldCaption"));
//
//		condition.setWidth(NumberConstants.FNUM400, Unit.PIXELS);
//
//		if (componente.getConditionForEach() != null && !componente.getConditionForEach().isEmpty()) {
//			condition.setValue(componente.getConditionForEach());
//		}
//		if (componente.getForEachType() != null && !componente.getForEachType().isEmpty()) {
//			options.setValue(componente.getForEachType());
//		}
//
//		layout.addComponent(condition);

		HorizontalLayout hl = new HorizontalLayout();

		Button acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		acceptButton.addClickListener(new Button.ClickListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4609601471800899199L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (combo.getValue() != null) {
					componente.setIsForEach(true);
					//componente.setConditionForEach(condition.getValue());
					componente.setForEachType((String) options.getValue());
					if (componente.isBarCode()) {
						((BarCode) componente).setMessage(Format.removeIndex(((BarCode) componente).getMessage()));
					} else if (componente.isText()) {
						((RichLabel) componente).setValue(Format.removeIndex(((RichLabel) componente).getValue()));
					}
					close();
				} else {
					Notification.show(MessagesEVT.getInstance().getProperty("noShowForEach"));
				}

			}
		});

		hl.addComponent(acceptButton);

		Button cancelarBucle = new Button(MessagesEVT.getInstance().getProperty("cancelForEachBlock"));

		cancelarBucle.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 1606380488980441684L;

			@Override
			public void buttonClick(ClickEvent event) {
				componente.setIsForEach(false);
				combo.setValue(null);
				componente.setConditionForEach(null);
				//condition.setValue("");
				componente.setForEachType(null);
				close();
			}
		});

		hl.addComponent(cancelarBucle);
		hl.setSpacing(true);

		layout.addComponent(hl);

		setContent(layout);

	}
}