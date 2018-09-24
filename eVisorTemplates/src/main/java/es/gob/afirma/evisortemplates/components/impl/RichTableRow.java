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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichTableRow.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;

import es.gob.afirma.evisortemplates.util.PixelToCm;

/** 
 * <p>Class that represents a row of a table.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class RichTableRow extends AbsoluteLayout {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4299988107387326102L;

	/**
	 * Attribute that represents the list of cells of the row. 
	 */
	private List<RichTableCel> cells;

	/**
	 * Attribute that represents the parent table of a row. 
	 */
	private RichTable parentTable;

	/**
	 * Attribute that represents if a row is a head row. 
	 */
	private boolean isHead;

	/**
	 * Constructor method for the class RichTableRow.java. 
	 */
	public RichTableRow() {
		super();
		cells = new ArrayList<RichTableCel>();
	}

	/**
	 * Constructor method for the class RichTableRow.java.
	 * @param cels number of cells of the row
	 * @param height height value for the row
	 * @param tamanoCelda width value for each cell of row
	 * @param parent parent table of the row
	 */
	public RichTableRow(int cels, float height, float tamanoCelda, RichTable parent) {
		super();

		this.parentTable = parent;

		setHeight(height, Unit.PIXELS);
		setWidth(tamanoCelda * cels, Unit.PIXELS);

		cells = new ArrayList<RichTableCel>();

		for (int i = 0; i < cels; i++) {
			RichTableCel celda = new RichTableCel(parent);
			celda.setWidth(tamanoCelda, Unit.PIXELS);
			celda.setHeight(height, Unit.PIXELS);
			cells.add(celda);
			this.addComponent(celda);

			ComponentPosition pos = this.getPosition(celda);
			pos.setLeftValue(tamanoCelda * i);

			this.setPosition(celda, pos);

		}
	}

	/**
	 * Method that gets a list of cells of the row.
	 * @return list of cells of the row
	 */
	public final List<RichTableCel> getCells() {
		return cells;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponentContainer#setHeight(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setHeight(float h, Unit unit) {
		super.setHeight(h, unit);
		if (cells != null) {
			for (RichTableCel cel: cells) {
				cel.setHeight(h, unit);
			}
		}
	}

	/**
	 * Method that sets new value for height recalculating other table sizes.
	 * @param h new value for height
	 * @param unit new unit for height value
	 */
	public final void setHeightRecalculating(float h, Unit unit) {
		super.setHeight(h, unit);
		setHeight(h, unit);
		if (parentTable != null) {
			parentTable.sizeRecalc(this);
		}
	}

	/**
	 * Method that obtains code XSLT representation for component.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	public final StringBuffer getCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (parentTable.isForEachTable() && !isHead()) {
			result.append(codeEditor.addLine("<xsl:for-each select=\"//ri:IndividualSignature\">"));
		}
		result.append(codeEditor.addLine("<fo:table-row width=\"") + codeEditor.addText(PixelToCm.pxToCm(getWidth()) + "") + codeEditor.addText("cm\" height=\"") + codeEditor.addEdition(PixelToCm.pxToCm(getHeight()) + "", this, CodeEditorInput.HEIGHT, false) + codeEditor.addText("cm\">\n"));

		if (getCells() != null && getCells().size() > 0) {
			for (RichTableCel cel: getCells()) {
				result.append(cel.getCode(codeEditor));
			}
		}

		result.append(codeEditor.addLine("</fo:table-row>\n"));

		if (parentTable.isForEachTable() && !isHead()) {
			result.append(codeEditor.addLine("</xsl:for-each>"));
		}

		return result;
	}

	/**
	 * Method that resets position rows and columns.
	 */
	public final void resetPositions() {
		parentTable.resetPositions();
	}

	/**
	 * Method that sets parent table of the cell.
	 * @param parentTableParam parent table of the cell
	 */
	public final void setParentTable(RichTable parentTableParam) {
		this.parentTable = parentTableParam;
	}

	/**
	 * Method that checks if the row is a head row.
	 * @return true if the row is a head row, false otherwise
	 */
	public final boolean isHead() {
		return this.isHead;
	}

	/**
	 * Method that sets if the row is a head row.
	 * @param isHeadParam true if the row is a head row
	 */
	public final void setIsHead(boolean isHeadParam) {
		this.isHead = isHeadParam;
	}

	/**
	 * Method that returns a new instance of RichTableRow.
	 * @param parent Parent table of RichTableRow.
	 * @return the new instance of RichTableRow.
	 */
	public final Component newInstance(RichTable parent) {
		RichTableRow result = new RichTableRow();
		
		result.parentTable = parent;

		result.setHeight(getHeight(), Unit.PIXELS);
		result.setWidth(getWidth(), Unit.PIXELS);

		result.isHead = isHead;
		
		for (int i = 0; i < cells.size(); i++) {
			ComponentPosition pos = this.getPosition(cells.get(i));
			
			RichTableCel celda = cells.get(i).newInstance(parent);
			result.cells.add(celda);
			result.addComponent(celda);
			
			ComponentPosition newPos = result.getPosition(celda);
			newPos.setLeftValue(pos.getLeftValue());
			result.setPosition(celda, newPos);
		}
		result.setStyleName(getStyleName());
		return result;
	}

}
