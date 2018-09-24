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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.ConditionWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.components.impl.FormVariable;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;

/** 
 * <p>Class that represents the window for include visibility condition.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class ConditionWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5454855484832187688L;

	/**
	 * Attribute that represents variable's insert zone. 
	 */
	private FormVariable fv;
	
	/**
	 * Attribute that represents variable's insert zone. 
	 */
	private FormVariable fv2;
	
	/**
	 * Attribute that represents = String constant. 
	 */
	private static final String EQUALS = "=";
	
	/**
	 * Attribute that represents != String constant. 
	 */
	private static final String NOTEQUALS = "!=";
	
	/**
	 * Attribute that represents > String constant. 
	 */
	private static final String MAYOR = ">";
	
	/**
	 * Attribute that represents < String constant. 
	 */
	private static final String MINOR = "<";
	
	/**
	 * Attribute that represents <= String constant. 
	 */
	private static final String MINOREQUALS = "<=";
	
	/**
	 * Attribute that represents >= String constant. 
	 */
	private static final String MAYOREQUALS = ">=";

	/**
	 * Constructor method for the class ConditionWindow.java.
	 * @param componente component to be conditioned
	 */
	public ConditionWindow(final PositionAngle componente) {

		super();
		Button acceptButton;

		setCaption(MessagesEVT.getInstance().getProperty("conditionWindowCaption"));

		setModal(true);
		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("conditionWindowWidth")), Unit.PIXELS);

		acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));

		FormLayout layout = new FormLayout();

		HorizontalLayout hl = new HorizontalLayout();
		layout.setSizeUndefined();

		final TextField part1 = new TextField(MessagesEVT.getInstance().getProperty("conditionWindowPart1"));
		if (componente.getCondition1() != null) {
			part1.setValue(componente.getCondition1());
		}
		part1.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("conditionWindowPartsWidth")), Unit.PIXELS);
		hl.addComponent(part1);

		final ComboBox op = new ComboBox();
		op.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("conditionWindowOperatorWidth")), Unit.PIXELS);
		op.setNullSelectionAllowed(false);
		op.addItem(EQUALS);
		op.addItem(NOTEQUALS);
		op.addItem(MAYOR);
		op.addItem(MINOR);
		op.addItem(MINOREQUALS);
		op.addItem(MAYOREQUALS);
		op.setValue(EQUALS);

		if (componente.getConditionOp() != null) {
			op.setValue(revTrans(componente.getConditionOp()));
		}

		hl.addComponent(op);

		hl.setComponentAlignment(op, Alignment.BOTTOM_CENTER);

		final TextField part2 = new TextField(MessagesEVT.getInstance().getProperty("conditionWindowPart2"));
		if (componente.getCondition2() != null) {
			part2.setValue(componente.getCondition2());
		}
		part2.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("conditionWindowPartsWidth")), Unit.PIXELS);
		hl.addComponent(part2);

		layout.addComponent(hl);

		fv = new FormVariable(MessagesEVT.getInstance().getProperty("conditionWindowVar1Label"), Format.TYPEALL, true, false);

		fv.addClickAcceptListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -1060452939076215592L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (fv.isFullVar()) {
					part1.setValue(fv.getVarFormed());
				}

			}
		});

		layout.addComponent(fv);

		fv2 = new FormVariable(MessagesEVT.getInstance().getProperty("conditionWindowVar2Label"), Format.TYPEALL, true, false);

		fv2.addClickAcceptListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -556432865419893880L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (fv2.getTextValue() != null && !fv2.getTextValue().isEmpty()) {
					part2.setValue(fv2.getVarFormed());
				}

			}
		});

		layout.addComponent(fv2);

		acceptButton.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 5069198370239385333L;

			@Override
			public void buttonClick(ClickEvent event) {
				FormVariable.removeGlobalVarUsed(componente.getCondition1());
				FormVariable.removeGlobalVarUsed(componente.getCondition2());
				componente.setCondition1(part1.getValue());
				componente.setCondition2(part2.getValue());
				componente.setConditionOp(trans((String) op.getValue()));
				FormVariable.addGlobalVarConditionUsed(fv.getTextValue());
				FormVariable.addGlobalVarConditionUsed(fv2.getTextValue());
				close();
			}
		});

		layout.addComponent(acceptButton);

		setContent(layout);

	}

	/**
	 *  Method that reverse translate operators.
	 * @param value operator to translate
	 * @return new operator
	 */
	private Object revTrans(String value) {
		if (value.equals("&lt;")) {
			return "<";
		} else if (value.equals("&gt;")) {
			return ">";
		} else if (value.equals("&lt;=")) {
			return "<=";
		} else if (value.equals("&gt;=")) {
			return ">=";
		}
		return value;
	}

	/**
	 * Method that translate operators.
	 * @param value operator to translate
	 * @return new operator
	 */
	private String trans(String value) {
		if (value.equals("<")) {
			return "&lt;";
		} else if (value.equals(">")) {
			return "&gt;";
		} else if (value.equals("<=")) {
			return "&lt;=";
		} else if (value.equals(">=")) {
			return "&gt;=";
		}
		return value;
	}

}
