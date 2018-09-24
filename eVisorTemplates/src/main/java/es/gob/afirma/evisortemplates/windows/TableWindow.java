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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.TableWindow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents the table input window.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class TableWindow extends WindowRelativePos {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4611758803176868903L;
	
	/**
	 * Attribute that represents accept button. 
	 */
	private Button acceptButton;
	
	/**
	 * Attribute that represents "number of columns" combo box. 
	 */
	private ComboBox cols;
	
	/**
	 * Attribute that represents "number of rows" combo box. 
	 */
	private ComboBox rows;

	/**
	 * Attribute that represents a flag that indicates if rows are repeat for each occurrence of individual signature. 
	 */
	private boolean forEachRows;
	
	/**
	 * Attribute that represents a check box for select if want or no a head row. 
	 */
	private CheckBox checkboxHead;

	/**
	 * Constructor method for the class TableWindow.java.
	 * @param nested if table is a nested table
	 */
	public TableWindow(boolean nested) {

		super();

		setCaption(MessagesEVT.getInstance().getProperty("tableWindowCaption"));

		setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("tableWindowWidth")), Unit.PIXELS);
		setModal(true);

		FormLayout fl = new FormLayout();

		cols = new ComboBox(MessagesEVT.getInstance().getProperty("tableWindowColumnsLabel"));
		cols.setNullSelectionAllowed(false);

		boolean firstInserted = false;
		int colsLimit = Integer.parseInt(ConfigEVT.getInstance().getProperty("tableColsNumLimit"));
		for (int i = 0; i < colsLimit; i++) {
			if (!firstInserted) {
				cols.addItem(i + 1);
				cols.setValue(i + 1);
				firstInserted = true;

			}
			cols.addItem(i + 1);
		}

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(cols);

		final Label lblForEach = new Label();

		hl.addComponent(lblForEach);

		hl.setComponentAlignment(lblForEach, Alignment.BOTTOM_CENTER);

		fl.addComponent(hl);

		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setSpacing(true);

		rows = new ComboBox(MessagesEVT.getInstance().getProperty("tableWindowRowsLabel"));

		firstInserted = false;
		int rowsLimit = Integer.parseInt(ConfigEVT.getInstance().getProperty("tableRowsLimit"));
		for (int i = 0; i < rowsLimit; i++) {
			if (!firstInserted) {
				rows.addItem(i + 1);
				rows.setValue(i + 1);
				firstInserted = true;

			}
			rows.addItem(i + 1);
		}

		hl2.addComponent(rows);

		if (!nested) {
			Button botonForEach = new Button();
			botonForEach.setIcon(FontAwesome.LIST_OL);
			// CHECKSTYLE:OFF To allow anonymous inner class size
			// greater than 20 lines.
			botonForEach.addClickListener(new Button.ClickListener() {

				// CHECKSTYLE:ON

				/**
				 * serialVersionUID
				 */
				private static final long serialVersionUID = -4582285643384705868L;

				@Override
				public void buttonClick(ClickEvent event) {

					forEachRows = !forEachRows;

					if (forEachRows) {
						rows.setValue(1);
						rows.setEnabled(false);
						checkboxHead.setEnabled(true);
						checkboxHead.setValue(false);
						lblForEach.setValue(MessagesEVT.getInstance().getProperty("tableWindowForEachAdv"));
					} else {
						rows.setEnabled(true);
						checkboxHead.setEnabled(false);
						checkboxHead.setValue(false);
						lblForEach.setValue("");
					}

				}
			});

			hl2.addComponent(botonForEach);
			hl2.setComponentAlignment(botonForEach, Alignment.BOTTOM_CENTER);

			checkboxHead = new CheckBox(MessagesEVT.getInstance().getProperty("tableWindowWithHead"), false);

			checkboxHead.setEnabled(false);

			hl2.addComponent(checkboxHead);
			hl2.setComponentAlignment(checkboxHead, Alignment.BOTTOM_CENTER);
		}

		fl.addComponent(hl2);

		acceptButton = new Button(MessagesEVT.getInstance().getProperty("acceptButton"));

		fl.addComponent(acceptButton);

		fl.setComponentAlignment(acceptButton, Alignment.BOTTOM_RIGHT);
		fl.setMargin(true);

		setContent(fl);

	}

	/**
	 * Method that adds an accept button click listener to window.
	 * @param listener accept button click listener
	 */
	public final void addClickAcceptButtonListener(ClickListener listener) {
		acceptButton.addClickListener(listener);
	}

	/**
	 * Method that gets the rows's combo box value selected.
	 * @return rows's combo box value selected
	 */
	public final Integer getRowsValue() {
		if (rows != null) {
			return (Integer) rows.getValue();
		}
		return null;
	}

	/**
	 * Method that gets the columns's combo box value selected.
	 * @return columns's combo box value selected
	 */
	public final Integer getColsValue() {
		if (cols != null) {
			return (Integer) cols.getValue();
		}
		return null;
	}

	/**
	 * Method that gets head´s check box value.
	 * @return head´s check box value
	 */
	public final boolean getHeadValue() {
		return checkboxHead.getValue();
	}

	/**
	 * Method that ckecks forEachRows value.
	 * @return forEachRows value
	 */
	public final boolean isForEachRows() {
		return forEachRows;
	}

}
