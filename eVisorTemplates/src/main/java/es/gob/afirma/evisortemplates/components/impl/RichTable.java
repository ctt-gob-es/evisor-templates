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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichTable.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.peter.splitpaneldragextension.SplitPanelDragExtension;
import org.vaadin.peter.splitpaneldragextension.SplitPanelDragExtension.SplitterDragListener;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.util.PixelToCm;

/** 
 * <p>Class that represents template's tables.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * @version 1.0, 20/02/2015.
 */
public class RichTable extends PositionAngleAbsCustom {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3315384006447571692L;

	/**
	 * Attribute that represents number of columns in table. 
	 */
	private int ncols;
	
	/**
	 * Attribute that represents number of rows in table. 
	 */
	private int nrows;

	/**
	 * Attribute that represents if table show borders. 
	 */
	private boolean showBorder;

	/**
	 * Attribute that represents if table is a nested table. 
	 */
	private boolean nested;

	/**
	 * Attribute that represents layout that contains table structure. 
	 */
	private TableAbsoluteLayout layout;

	/**
	 * Attribute that represents the list of rows of table. 
	 */
	private List<RichTableRow> rows;

	/**
	 * Attribute that represents the list of vertical splits. 
	 */
	private List<VerticalSplitPanel> vspList;
	
	/**
	 * Attribute that represents the list of horizontal splits. 
	 */
	private List<HorizontalSplitPanel> hspList;

	/**
	 * Attribute that represents if table is a "for each" table. 
	 */
	private boolean isForEachTable;
	
	/**
	 * Attribute that represents if table have a head row. 
	 */
	private boolean headValue;

	/**
	 * Constructor method for the class RichTable.java.
	 * @param ncolsParam number of columns in table
	 * @param nrowsParam number of rows in table
	 * @param width table's width in pixels
	 * @param height table's height in pixels
	 * @param nestedParam if table is a nested table
	 * @param showBorderParam if table show borders
	 * @param isForEachTableParam if table is a "for each" table
	 * @param headValueParam if table have a head row
	 */
	public RichTable(int ncolsParam, int nrowsParam, Float width, Float height, boolean nestedParam, boolean showBorderParam, boolean isForEachTableParam, boolean headValueParam) {
		super();

		this.isForEachTable = isForEachTableParam;
		this.headValue = headValueParam;

		this.showBorder = showBorderParam;

		this.nested = nestedParam;

		layout = new TableAbsoluteLayout();

		this.ncols = ncolsParam;

		if (this.isForEachTable) {
			if (this.headValue) {
				this.nrows = 2;
			} else {
				this.nrows = 1;
			}
		} else {
			this.nrows = nrowsParam;
		}

		if (width == null) {
			this.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaulTabletWidthSize")), Unit.PIXELS);
			layout.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaulTabletWidthSize")), Unit.PIXELS);
		} else {
			this.setWidth(width, Unit.PIXELS);
			layout.setWidth(width, Unit.PIXELS);
		}

		if (height == null) {
			this.setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultRowHeightSize")) * this.nrows, Unit.PIXELS);
			layout.setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultRowHeightSize")) * this.nrows, Unit.PIXELS);
		} else {
			this.setHeight(height, Unit.PIXELS);
			layout.setHeight(height, Unit.PIXELS);
		}

		this.rows = new ArrayList<RichTableRow>();

		this.vspList = new ArrayList<VerticalSplitPanel>();
		this.hspList = new ArrayList<HorizontalSplitPanel>();

		for (int j = 0; j < this.nrows; j++) {
			RichTableRow row = new RichTableRow(this.ncols, layout.getHeight() / this.nrows, layout.getWidth() / this.ncols, this);

			// Si es la primera fila y es un forEachTable con head se marca como
			// head
			if (isHead(j)) {
				row.setIsHead(true);
			}

			if (j > 0) {
				TableVerticalSplitPanel vsp = new TableVerticalSplitPanel();
				vsp.setSplitPosition((layout.getHeight() / this.nrows) * j, Unit.PIXELS);
				vsp.setMinSplitPosition(((layout.getHeight() / this.nrows) * (j - 1)) + 1, Unit.PIXELS);
				vsp.setMaxSplitPosition(((layout.getHeight() / this.nrows) * (j + 1)) - 1, Unit.PIXELS);
				vsp.setImmediate(true);

				SplitPanelDragExtension extension = new SplitPanelDragExtension();
				extension.addSplitterDragListener(new RichTableSplitterDragListener(vsp, rows.get(j - 1), row));

				extension.extend(vsp);

				layout.addComponent(vsp);
				vspList.add(vsp);

			}

			this.rows.add(row);
			layout.addComponent(row);

			ComponentPosition pos = layout.getPosition(row);
			pos.setTopValue((layout.getHeight() / this.nrows) * j);

			layout.setPosition(row, pos);

		}

		for (int i = 1; i < this.ncols; i++) {
			TableHorizontalSplitPanel hsp = new TableHorizontalSplitPanel();
			hsp.setSplitPosition((layout.getWidth() / this.ncols) * i, Unit.PIXELS);

			hsp.setMinSplitPosition(((layout.getWidth() / this.ncols) * (i - 1) + 1), Unit.PIXELS);
			hsp.setMaxSplitPosition(((layout.getWidth() / this.ncols) * (i + 1) - 1), Unit.PIXELS);

			hsp.setImmediate(true);

			SplitPanelDragExtension extension = new SplitPanelDragExtension();
			extension.addSplitterDragListener(new RichTableSplitterDragListener(hsp, i - 1, i));

			extension.extend(hsp);

			layout.addComponent(hsp);
			hspList.add(hsp);

		}

		visibleSplits(false);

		applyBorderStyle();

		setCompositionRoot(layout);
	}

	/**
	 * Method to know if row is head row.
	 * @param index index of row to check
	 * @return true if index row is head row, false otherwise
	 */
	private boolean isHead(int index) {
		return index == 0 && this.isForEachTable && this.headValue;
	}

	/**
	 * Method that resets the splits set.
	 */
	private void resetSplits() {

		for (VerticalSplitPanel vsp: vspList) {
			layout.removeComponent(vsp);
		}

		for (HorizontalSplitPanel hsp: hspList) {
			layout.removeComponent(hsp);
		}

		this.vspList = new ArrayList<VerticalSplitPanel>();
		this.hspList = new ArrayList<HorizontalSplitPanel>();

		float heightSum = 0;
		for (int j = 0; j < nrows; j++) {
			RichTableRow row = rows.get(j);

			if (j > 0) {
				TableVerticalSplitPanel vsp = new TableVerticalSplitPanel();
				vsp.setSplitPosition(heightSum, Unit.PIXELS);

				vsp.setImmediate(true);

				SplitPanelDragExtension extension = new SplitPanelDragExtension();
				extension.addSplitterDragListener(new RichTableSplitterDragListener(vsp, rows.get(j - 1), row));

				extension.extend(vsp);
				layout.addComponent(vsp);
				vspList.add(vsp);

			}

			heightSum = heightSum + row.getHeight();

		}

		float widthSum = 0;
		RichTableRow row = rows.get(0);
		for (int i = 0; i < ncols; i++) {

			RichTableCel cel = row.getCells().get(i);

			if (i > 0) {
				TableHorizontalSplitPanel hsp = new TableHorizontalSplitPanel();
				hsp.setSplitPosition(widthSum, Unit.PIXELS);

				hsp.setImmediate(true);

				SplitPanelDragExtension extension = new SplitPanelDragExtension();
				extension.addSplitterDragListener(new RichTableSplitterDragListener(hsp, i - 1, i));

				extension.extend(hsp);

				layout.addComponent(hsp);
				hspList.add(hsp);
			}

			widthSum = widthSum + cel.getWidth();

		}

		visibleSplits(true);

		resetMaxMinSplitsPositions();

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isImage()
	 */
	@Override
	public final boolean isImage() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isText()
	 */
	@Override
	public final boolean isText() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isTable()
	 */
	@Override
	public final boolean isTable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isBarCode()
	 */
	@Override
	public final boolean isBarCode() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isDocument()
	 */
	@Override
	public final boolean isDocument() {
		return false;
	}

	/**
	 * Method that sets table's height without rows height recalculate.
	 * @param h new height value
	 * @param unit new height value's unit
	 */
	private void setHeightWithoutRecal(float h, Unit unit) {
		super.setHeight(h, unit);
		if (layout != null) {
			layout.setHeight(h, unit);
		}
	}

	/**
	 * Method that sets table's width without rows width recalculate.
	 * @param w  new width value
	 * @param unit new width value's unit
	 */
	private void setWidthWithoutRecal(float w, Unit unit) {
		super.setWidth(w, unit);
		if (layout != null) {
			layout.setWidth(w, unit);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#setWidth(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setWidth(float w, Unit unit) {
		super.setWidth(w, unit);
		if (layout != null) {
			layout.setWidth(w, unit);
		}
		if (rows != null) {
			for (RichTableRow row: rows) {
				float lastW = row.getWidth();
				float dif = (w - lastW) / row.getCells().size();
				row.setWidth(w, unit);
				if (row.getCells() != null) {
					float widthSum = 0;
					for (RichTableCel cel: row.getCells()) {
						if (w == 0) {
							cel.setWidth(w, unit);
						} else {
							cel.setWidth(cel.getWidth() + dif, unit);
						}

						ComponentPosition pos = row.getPosition(cel);
						if (pos != null) {
							pos.setLeftValue(widthSum);
							row.setPosition(cel, pos);
						}

						widthSum = widthSum + cel.getWidth();
					}

				}
			}
			resetSplits();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponent#setHeight(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setHeight(float h, Unit unit) {
		float lastHLay = layout.getHeight();
		super.setHeight(h, unit);
		if (layout != null) {
			layout.setHeight(h, unit);
		}
		if (rows != null) {
			float dif = (h - lastHLay) / rows.size();
			float heightSum = 0;
			for (RichTableRow row: rows) {
				if (h == 0) {
					row.setHeight(h, unit);
				} else {
					row.setHeight(row.getHeight() + dif, unit);
				}

				ComponentPosition pos = layout.getPosition(row);
				if (pos != null) {
					pos.setTopValue(heightSum);
					layout.setPosition(row, pos);
				}
				heightSum = heightSum + row.getHeight();
			}

			resetSplits();
		}
	}

	// Clase destinada a distinguir los clicks sobre RichTable
	/** 
	 * <p>Inner class to manage clicks on RichTable.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	public class TableAbsoluteLayout extends AbsoluteLayout {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -739092757871325528L;

	}

	/** 
	 * <p>Inner class to manage clicks on RichTable vertical splits.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	public class TableVerticalSplitPanel extends VerticalSplitPanel {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -6905971051006281056L;

	}

	/** 
	 * <p>Inner class to manage clicks on RichTable horizontal splits.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	public class TableHorizontalSplitPanel extends HorizontalSplitPanel {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -1681757363573988833L;

	}

	/** 
	 * <p>Class to manage splitters drag events.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	public class RichTableSplitterDragListener implements SplitterDragListener, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1502205250260964070L;

		/**
		 * Attribute that represents vertical split panel´s instance. 
		 */
		private VerticalSplitPanel vsp;
		
		/**
		 * Attribute that represents the vertical split panel's last position. 
		 */
		private float lastVspPos;
		
		/**
		 * Attribute that represents the horizontal split panel's last position. 
		 */
		private float lastHspPos;
		
		/**
		 *  Attribute that represents horizontal split panel´s instance. 
		 */
		private HorizontalSplitPanel hsp;
		
		/**
		 * Attribute that represents before horizontal split panel's cell. 
		 */
		private int cel1;
		
		/**
		 * Attribute that represents after horizontal split panel's cell. 
		 */
		private int cel2;
		
		/**
		 * Attribute that represents before vertical split panel's row. 
		 */
		private RichTableRow row1;
		
		/**
		 * Attribute that represents after vertical split panel's cell. 
		 */
		private RichTableRow row2;

		/**
		 * {@inheritDoc}
		 * @see org.vaadin.peter.splitpaneldragextension.SplitPanelDragExtension.SplitterDragListener#onSplitterDragged()
		 */
		@Override
		public final void onSplitterDragged() {
			if (vsp != null) {

				row1.setHeight(row1.getHeight() + (vsp.getSplitPosition() - lastVspPos), Unit.PIXELS);

				row2.setHeight(row2.getHeight() - (vsp.getSplitPosition() - lastVspPos), Unit.PIXELS);

				ComponentPosition pos = layout.getPosition(row2);
				pos.setTopValue(vsp.getSplitPosition());

				layout.setPosition(row2, pos);

				lastVspPos = vsp.getSplitPosition();
			} else {
				for (RichTableRow row: rows) {
					((RichTableCel) row.getCells().get(cel1)).setWidth(((RichTableCel) row.getCells().get(cel1)).getWidth() + (hsp.getSplitPosition() - lastHspPos), Unit.PIXELS);

					((RichTableCel) row.getCells().get(cel2)).setWidth(((RichTableCel) row.getCells().get(cel2)).getWidth() - (hsp.getSplitPosition() - lastHspPos), Unit.PIXELS);

					ComponentPosition pos = row.getPosition(row.getCells().get(cel2));
					pos.setLeftValue(hsp.getSplitPosition());

					row.setPosition(row.getCells().get(cel2), pos);
				}

				lastHspPos = hsp.getSplitPosition();
			}

			resetMaxMinSplitsPositions();
		}

		/**
		 * Constructor method for the class RichTable.java.
		 * @param vspParam vertical split panel´s instance
		 * @param row1Param before vertical split panel's row
		 * @param row2Param  after vertical split panel's row
		 */
		public RichTableSplitterDragListener(VerticalSplitPanel vspParam, RichTableRow row1Param, RichTableRow row2Param) {
			this.vsp = vspParam;
			this.lastVspPos = this.vsp.getSplitPosition();
			this.row1 = row1Param;
			this.row2 = row2Param;
		}

		/**
		 * Constructor method for the class RichTable.java.
		 * @param hspParam horizontal split panel´s instance
		 * @param cel1Param before horizontal split panel's row
		 * @param cel2Param  after horizontal split panel's row
		 */
		public RichTableSplitterDragListener(HorizontalSplitPanel hspParam, int cel1Param, int cel2Param) {
			this.hsp = hspParam;
			this.lastHspPos = this.hsp.getSplitPosition();
			this.cel1 = cel1Param;
			this.cel2 = cel2Param;
		}

	}

	/**
	 * Method to doing splits selectors visible or no.
	 * @param visible true if you want visible borders, false otherwise
	 */
	public final void visibleSplits(boolean visible) {
		for (HorizontalSplitPanel hsp: hspList) {
			hsp.setVisible(visible);
		}
		for (VerticalSplitPanel vsp: vspList) {
			vsp.setVisible(visible);
		}

		for (RichTableRow row: rows) {
			for (RichTableCel cel: row.getCells()) {
				Component component = null;

				if (cel.getComponentCount() == 1) {
					component = cel.getComponentIterator().next();
				}
				if (component instanceof RichTable) {
					((RichTable) component).visibleSplits(visible);
				}
			}
		}
	}

	/**
	 * Method to apply table's border style.
	 */
	private void applyBorderStyle() {
		// se niega showBorder ya que cabiarEstiloBorde lo vuelve a negar
		showBorder = !showBorder;
		changeBorderStyle();
	}

	/**
	 *  Method to change table's border style.
	 */
	public final void changeBorderStyle() {
		if (showBorder) {
			changeBorderStyleShowBorder();
			showBorder = false;
		} else {
			changeBorderSyleNoShowBorder();

			showBorder = true;
		}

		for (RichTableRow row: rows) {
			for (RichTableCel cel: row.getCells()) {
				Component component = null;

				if (cel.getComponentCount() == 1) {
					component = cel.getComponentIterator().next();
				}
				if (component instanceof RichTable) {
					((RichTable) component).changeBorderStyle();
				}
			}
		}
	}

	/**
	 *  Method to change table's border style to visible.
	 */
	private void changeBorderStyleShowBorder() {
		int i = 0;

		for (RichTableRow row: rows) {
			int j = 0;
			for (RichTableCel cel: row.getCells()) {
				if (!nested) {
					cel.setStyleName("table-area-cel");
					layout.setStyleName("table-area");
				} else {
					if (j < row.getCells().size() - 1 && i < rows.size() - 1) {
						cel.setStyleName("table-area-nested-cel");
					} else if (j == row.getCells().size() - 1 && i < rows.size() - 1) {
						cel.setStyleName("table-area-nested-celLast");
					} else if (j < row.getCells().size() - 1 && i == rows.size() - 1) {
						cel.setStyleName("table-area-nested-celBottom");
					}
				}
				j++;
			}
			i++;
		}
	}

	/**
	 *  Method to change table's border style to no visible.
	 */
	private void changeBorderSyleNoShowBorder() {
		int i = 0;

		for (RichTableRow row: rows) {
			int j = 0;
			for (RichTableCel cel: row.getCells()) {
				if (!nested) {
					cel.setStyleName("table-area-line-cel");
					layout.setStyleName("table-area-line");
				} else {
					if (j < row.getCells().size() - 1 && i < rows.size() - 1) {
						cel.setStyleName("table-area-line-nested-cel");
					} else if (j == row.getCells().size() - 1 && i < rows.size() - 1) {
						cel.setStyleName("table-area-line-nested-celLast");
					} else if (j < row.getCells().size() - 1 && i == rows.size() - 1) {
						cel.setStyleName("table-area-line-nested-celBottom");
					}
				}
				j++;
			}
			i++;
		}

	}

	/**
	 * Method that resets split's maximum and minimum position.
	 */
	private void resetMaxMinSplitsPositions() {
		int i = 0;
		for (HorizontalSplitPanel hsp: hspList) {

			if (i == 0) {
				hsp.setMinSplitPosition(1, Unit.PIXELS);
			} else {
				hsp.setMinSplitPosition(hspList.get(i - 1).getSplitPosition() + 1, Unit.PIXELS);
			}

			if (i < hspList.size() - 1) {
				hsp.setMaxSplitPosition(hspList.get(i + 1).getSplitPosition() - 1, Unit.PIXELS);
			} else {
				hsp.setMaxSplitPosition(layout.getWidth() - 1, Unit.PIXELS);
			}

			i++;
		}

		int j = 0;
		for (VerticalSplitPanel vsp: vspList) {

			if (j == 0) {
				vsp.setMinSplitPosition(1, Unit.PIXELS);
			} else {
				vsp.setMinSplitPosition(vspList.get(j - 1).getSplitPosition() + 1, Unit.PIXELS);
			}

			if (j < vspList.size() - 1) {
				vsp.setMaxSplitPosition(vspList.get(j + 1).getSplitPosition() - 1, Unit.PIXELS);
			} else {
				vsp.setMaxSplitPosition(layout.getHeight() - 1, Unit.PIXELS);
			}

			j++;
		}
	}

	/**
	 * Method to check if the component show borders.
	 * @return true if the component show borders, false otherwise
	 */
	public final boolean isShowBorder() {
		return showBorder;
	}

	/**
	 * Method that sets if the component show borders.
	 * @param showBorderParam true if the component show borders
	 */
	public final void setShowBorder(boolean showBorderParam) {
		this.showBorder = showBorderParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCode(es.gob.afirma.evisortemplates.components.impl.CodeEditor)
	 */
	public final StringBuffer getCode(CodeEditor editorCodigo) {
		StringBuffer result = new StringBuffer();

		if (getParent() instanceof Template) {
			result.append(editorCodigo.addLine("<fo:table left=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(getPosXXSLT()) + "", this, CodeEditorInput.POSX, false) + editorCodigo.addText("mm\" top=\"") + editorCodigo.addEdition(PixelToCm.pxToMm(getPosYXSLT()) + "", this, CodeEditorInput.POSY, false) + editorCodigo.addText("mm\"\n") + editorCodigo.addLine(" width=\"") + editorCodigo.addEdition(PixelToCm.pxToCm(getWidth()) + "", this, CodeEditorInput.WIDTH, false) + editorCodigo.addText("cm\" height=\"") + editorCodigo.addEdition(PixelToCm.pxToCm(getHeight()) + "", this, CodeEditorInput.HEIGHT, false) + editorCodigo.addText("cm\" \n"));
		} else {
			result.append(editorCodigo.addLine("<fo:table "));
		}
		if (showBorder && !nested) {

			result.append(editorCodigo.addLine(" border-bottom-width = \"0.75px\" border-right-width = \"0.75px\" border-bottom-style = \"solid\" border-right-style = \"solid\""));

		}
		result.append(editorCodigo.addText(">\n"));

		result.append(editorCodigo.addLine("<fo:table-body>\n"));
		if (rows != null && rows.size() > 0) {
			for (RichTableRow row: rows) {
				result.append(row.getCode(editorCodigo));
			}

		}
		result.append(editorCodigo.addLine("</fo:table-body>\n"));

		result.append(editorCodigo.addLine("</fo:table>\n"));

		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.impl.PositionAngleAbsCustom#newInstance()
	 */
	public final RichTable newInstance() {
		RichTable aux = new RichTable(this.ncols, this.nrows, getWidth(), getHeight(), this.nested, this.showBorder, this.isForEachTable, this.headValue);
		
		aux.rows = new ArrayList<RichTableRow>();

		final LinkedList<Component> list = new LinkedList<Component>();
		for (final Iterator<Component> i = aux.layout.getComponentIterator(); i.hasNext();) {
			list.add(i.next());
		}
		for (Component comp: list) {
			if (!(comp instanceof VerticalSplitPanel || comp instanceof HorizontalSplitPanel)) {
				aux.layout.removeComponent(comp);
			}
		}
		aux.layout.removeAllComponents();
		if (this.rows != null && this.rows.size() > 0) {
    		for (RichTableRow rtr: this.rows) {

    			RichTableRow row = (RichTableRow) rtr.newInstance(aux);
    			ComponentPosition pos = this.layout.getPosition(rtr);
    			aux.layout.addComponent(row);
    			ComponentPosition newPos = aux.layout.getPosition(row);
    			newPos.setTopValue(pos.getTopValue());

    			aux.layout.setPosition(row, newPos);
    			
    			aux.rows.add(row);
    		}
		}
		aux.setAngle(getAngle());
		aux.setConditionOp(getConditionOp());
		aux.setCondition1(getCondition1());
		aux.setCondition2(getCondition2());
		aux.setContainer(super.getContainer());
		aux.setHeight(getHeight(), getHeightUnits());
		aux.setPosX(getPosX());
		aux.setPosY(getPosY());
		aux.setPosXXSLT(getPosXXSLT());
		aux.setPosYXSLT(getPosYXSLT());
		aux.setPosXCenter(getPosXCenter());
		aux.setPosYCenter(getPosYCenter());
		aux.setWidth(getWidth(), getWidthUnits());
		aux.setConditionForEach(getConditionForEach());
		aux.setIsForEach(isForEach());
		aux.setForEachType(getForEachType());

		return aux;
	}

	/**
	 * Method that check if table is empty.
	 * @return true if table is empty, false otherwise
	 */
	public final boolean empty() {
		for (RichTableRow row: rows) {
			for (RichTableCel cel: row.getCells()) {
				Component component = null;

				if (cel.getComponentCount() == 1) {
					component = cel.getComponentIterator().next();
				}
				if (component != null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Method that recalculates size parameters of table if cell grow or decreases.
	 * @param cell cell that is modified
	 */
	public final void sizeRecalc(RichTableCel cell) {

		if (cell.getWidth() >= getWidth()) {
			sizeRecalcCellGrows(cell);
		} else {
			sizeRecalcCellDecreases(cell);
		}

		float wTotalSize = 0;
		for (RichTableCel cel: rows.get(0).getCells()) {
			wTotalSize = wTotalSize + cel.getWidth();
		}
		
		setWidthWithoutRecal(wTotalSize, Unit.PIXELS);
		// si es una subtabla hay que incrementar el tamaño de la celda
		// de la tabla padre
		if (this.getParent() instanceof RichTableCel) {
			((RichTableCel) this.getParent()).setWidthRecalculating(wTotalSize, cell.getWidthUnits());
		}		

		resetPositions();

	}

	/**
	 * Method that recalculates size parameters of table if cell grow.
	 * @param cell cell that is modified
	 */
	private void sizeRecalcCellGrows(RichTableCel cell) {

		int numCell = 0;
		boolean found = false;
		float widthCell = 0;

		for (RichTableRow row: rows) {
			row.setWidth(cell.getWidth(), Unit.PIXELS);

			if (!found) {
				int contador = 0;
				for (RichTableCel cel: row.getCells()) {
					if (cel.equals(cell)) {
						widthCell = cel.getWidth();
						numCell = contador;
						found = true;
						break;
					}
					contador++;
				}
			}

		}

		for (RichTableRow row: rows) {
			for (RichTableCel cel: row.getCells()) {
				cel.setWidth(0, Unit.PIXELS);
			}

			row.getCells().get(numCell).setWidth(widthCell, Unit.PIXELS);

		}
	}

	/**
	 * Method that recalculates size parameters of table if cell decreases.
	 * @param cell cell that is modified
	 */
	private void sizeRecalcCellDecreases(RichTableCel cell) {
		int numCell = 0;
		boolean found = false;
		float widthCell = 0;
		float wTotalSize = 0;
		float dif = 0;

		for (RichTableRow row: rows) {

			wTotalSize = 0;
			int contador = 0;

			for (RichTableCel cel: row.getCells()) {
				if (cel.equals(cell)) {
					numCell = contador;
					found = true;
					widthCell = cel.getWidth();
				}
				contador++;
				wTotalSize = wTotalSize + cel.getWidth();
			}

			if (found) {
				dif = (getWidth() - wTotalSize) / (row.getCells().size() - 1);

				break;
			}

		}
		if (found && dif != 0) {
			resizeCellsWhenDecreases(dif, wTotalSize, numCell, widthCell);
		}
	}

	
	/**
	 * Method that resizes cells if changes one.
	 * @param dif difference with old value
	 * @param wTotalSize total width of row
	 * @param numCell index of the changed cell 
	 * @param widthCell width of changed cell
	 */
	private void resizeCellsWhenDecreases(float dif, float wTotalSize, int numCell, float widthCell) {
		for (RichTableRow row: rows) {
			if (dif > 0) {
				row.setWidth(wTotalSize, Unit.PIXELS);
			}
			for (int i = 0; i < row.getCells().size(); i++) {
				if (i != numCell) {
					row.getCells().get(i).setWidth(row.getCells().get(i).getWidth() + dif, Unit.PIXELS);
				} else {
					row.getCells().get(i).setWidth(widthCell, Unit.PIXELS);
				}
			}
		}

	}

	/**
	 * Method that sets height rows to zero.
	 * @param paramRow row to be excluded of process
	 */
	private void setOtherRowsHeightToZero(RichTableRow paramRow) {
		for (RichTableRow row: rows) {
			if (!row.equals(paramRow)) {
				row.setHeight(0, Unit.PIXELS);
			}
		}
	}

	/**
	 * Method that gets total height of table.
	 * @return total height of table in pixels
	 */
	private float totalHeight() {
		float hTotalSize = 0;
		for (RichTableRow row: rows) {
			hTotalSize = hTotalSize + row.getHeight();
		}
		return hTotalSize;
	}

	/**
	 * Method that recalculates size parameters of table if row grow or decreases.
	 * @param paramRow row that is modified
	 */
	public final void sizeRecalc(RichTableRow paramRow) {

		if (paramRow.getHeight() >= getHeight()) {
			setOtherRowsHeightToZero(paramRow);
		} else {
			float hTotalSize = 0;
			boolean found = false;
			for (RichTableRow row: rows) {
				if (row.equals(paramRow)) {
					found = true;
				}
				hTotalSize = hTotalSize + row.getHeight();
			}
			if (found) {
				float dif = (getHeight() - hTotalSize) / (rows.size() - 1);

				if (dif != 0) {
					float partialSize = 0;
					for (RichTableRow row: rows) {
						if (!row.equals(paramRow)) {
							row.setHeight(row.getHeight() + dif, Unit.PIXELS);
						}

						partialSize = partialSize + row.getHeight();
					}

				}

			}

		}
		float hTotalSize = totalHeight();

		setHeightWithoutRecal(hTotalSize, Unit.PIXELS);
		// si es una subtabla hay que incrementar el tamaño de la celda
		// de la tabla padre
		if (this.getParent() instanceof RichTableCel) {
			((RichTableRow) this.getParent().getParent()).setHeightRecalculating(hTotalSize, paramRow.getHeightUnits());
		}

		resetPositions();

	}

	/**
	 * Method that resets position rows and columns.
	 */
	public final void resetPositions() {
		float partialSize = 0;
		for (RichTableRow row: rows) {
			ComponentPosition pos = layout.getPosition(row);
			if (pos != null) {
				pos.setTopValue(partialSize);
				layout.setPosition(row, pos);
			}

			if (row.getCells() != null && !row.getCells().isEmpty()) {
				float cellPartialSize = 0;
				for (RichTableCel cel: row.getCells()) {
					ComponentPosition pos2 = row.getPosition(cel);
					if (pos2 != null) {
						pos2.setLeftValue(cellPartialSize);
						row.setPosition(cel, pos2);
					}
					cellPartialSize = cellPartialSize + cel.getWidth();
				}
			}

			partialSize = partialSize + row.getHeight();
		}
		if (getContainer() == null) {
			visibleSplits(false);
		}

	}

	/**
	 * Method that sets table's heigth recalculating other table sizes.
	 * @param h new heigth value
	 * @param unit new heigth value unit
	 */
	public final void setHeightRecalculating(long h, Unit unit) {
		float lastHeight = getHeight();

		setHeightWithoutRecal(h, unit);
		float dif = (h - lastHeight) / rows.size();

		for (RichTableRow row: rows) {

			if (h != 0) {
				row.setHeight(row.getHeight() + dif, unit);
			} else {
				row.setHeight(h, unit);
			}

		}

		resetPositions();

	}

	/**
	 * Method that sets table's width recalculating other table sizes.
	 * @param w new width value
	 * @param unit new width value unit
	 */
	public final void setWidthRecalculating(long w, Unit unit) {
		float lastWidth = getWidth();

		setWidthWithoutRecal(w, unit);

		for (RichTableRow row: rows) {
			row.setWidth(w, unit);

			float dif = (w - lastWidth) / row.getCells().size();
			for (RichTableCel cel: row.getCells()) {
				if (w != 0) {
					cel.setWidth(cel.getWidth() + dif, unit);
				} else {
					cel.setWidth(w, unit);
				}
			}
		}

		resetPositions();

	}

	/**
	 * Method that checks if the table is a nested table.
	 * @return true if the table is a nested table
	 */
	public final boolean isNested() {
		return nested;
	}

	/**
	 * Method that sets if the table is a nested table.
	 * @param nestedParam true if the table is a nested table
	 */
	public final void setNested(boolean nestedParam) {
		this.nested = nestedParam;
	}

	/**
	 * Method that gets index value of a cell.
	 * @param celda cell to be checked for index
	 * @return index of cell
	 */
	public final int getCellIndex(RichTableCel celda) {

		for (RichTableRow row: rows) {
			int i = 0;
			for (RichTableCel cel: row.getCells()) {
				if (cel.equals(celda)) {
					return i;
				}
				i++;
			}
		}
		return 0;
	}

	/**
	 * Method that returns number of cells.
	 * @return number of cells of table
	 */
	public final int getCellsNum() {
		return ncols;
	}

	/**
	 * Method that returns number of rows.
	 * @return number of rows of table
	 */
	public final int getRowsNum() {
		return nrows;
	}

	/**
	 * Method that gets index value of a row containing cell.
	 * @param celda cell to be checked for index
	 * @return index of row that containing cell
	 */
	public final int getRowIndex(RichTableCel celda) {
		int i = 0;
		for (RichTableRow row: rows) {
			for (RichTableCel cel: row.getCells()) {
				if (cel.equals(celda)) {
					return i;
				}

			}
			i++;
		}
		return 0;
	}

	/**
	 * Method that checks if the table is a "for each" table.
	 * @return true if the table is a "for each" table
	 */
	public final boolean isForEachTable() {
		return isForEachTable;
	}


	/**
	 * Method that returns the list of rows of table.
	 * @return the list of rows of table
	 */
	public final List<RichTableRow> getRows() {
		return rows;
	}

}
