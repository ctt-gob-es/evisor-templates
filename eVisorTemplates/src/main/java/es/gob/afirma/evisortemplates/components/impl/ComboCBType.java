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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.ComboCBType.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import org.apache.log4j.Logger;

import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents the combo box of the code editor zone..</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 13/03/2015.
 */
public class ComboCBType extends ComboBox {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 784083279839576457L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(ComboCBType.class);

	/**
	 * Attribute that represents barcode Code39 type constant. 
	 */
	public static final String OPCODE39 = "Code39";
	
	/**
	 * Attribute that represents barcode Code128 type constant. 
	 */
	public static final String OPCODE128 = "Code128";
	
	/**
	 * Attribute that represents barcode EAN128 type constant. 
	 */
	public static final String OPEAN128 = "EAN128";
	
	/**
	 * Attribute that represents barcode Codabar type constant. 
	 */
	public static final String OPCODABAR = "Codabar";
	
	/**
	 * Attribute that represents barcode pdf417 type constant. 
	 */
	public static final String OPPDF417 = "pdf417";
	
	/**
	 * Attribute that represents barcode DataMatrix type constant. 
	 */
	public static final String OPDATAMATRIX = "DataMatrix";

	/**
	 * Attribute that represents the component associated with combo box value. 
	 */
	private Component component;
	
	/**
	 * Attribute that represents the other combo box related. 
	 */
	private ComboCBType brother;

	/**
	 * Constructor method for the class ComboCBType.java. 
	 */
	public ComboCBType() {
		super();
		setCaption(MessagesEVT.getInstance().getProperty("comboCBTypeCaption"));
		setNullSelectionAllowed(false);
		addItem(OPCODABAR);
		addItem(OPCODE128);
		addItem(OPCODE39);
		addItem(OPDATAMATRIX);
		addItem(OPEAN128);
		addItem(OPPDF417);

		setValue(OPPDF417);
	}

	/**
	 * Constructor method for the class ComboCBType.java.
	 * @param type type of the value of combo box
	 * @param componentParam component associated with combo box value
	 * @param brotherCombo the other combo box related
	 */
	public ComboCBType(final String type, final Component componentParam, ComboCBType brotherCombo) {
		super();
		this.component = componentParam;
		this.brother = brotherCombo;
		setNullSelectionAllowed(false);
		addItem(OPCODABAR);
		addItem(OPCODE128);
		addItem(OPCODE39);
		addItem(OPDATAMATRIX);
		addItem(OPEAN128);
		addItem(OPPDF417);

		setValue(type);

		if (brotherCombo != null) {
			addValueChangeListener(new ValueChangeListener() {
				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = -8339426123234621329L;

				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					try {
						brother.setValue((String) event.getProperty().getValue());
						BarCode aux = new BarCode();
						aux.setMessage(((BarCode) componentParam).getMessage());
						aux.setType((String) event.getProperty().getValue());
					} catch (IncorrectMessageBarCodeException e) {
						LOG.error(MessagesEVT.getInstance().getProperty("barCodeError2"),e);
						Notification notification = new Notification("Error", MessagesEVT.getInstance().getProperty("barCodeError2"));
						notification.show(Page.getCurrent());
					}
				}
			});
		} else {
			setEnabled(false);
		}
		setStyleName("inputCodigo");
	}

	/**
	 *  Method that solves changes in code.
	 */
	public final void solve() {
		if (brother != null) {
			((BarCode) component).setTypeSinRecalcular((String) getValue());
			BarCode bc = ((BarCode) component).newInstance();
			new ClickPanel((PositionAngle) bc, (AbsoluteLayout) ((BarCode) component).getParent());
			((AbsoluteLayout) ((BarCode) component).getParent()).replaceComponent(component, bc);
			component = bc;
			if (((BarCode) component).getParent() instanceof RichTableCel) {
				((RichTableCel) ((BarCode) component).getParent()).focusContent(component);
			}
			brother.component = component;
		}

	}


}
