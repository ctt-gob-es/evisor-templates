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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichTableCel.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.PixelToCm;
import es.gob.afirma.evisortemplates.windows.BarCodeWindow;
import es.gob.afirma.evisortemplates.windows.ConfirmationWindow;
import es.gob.afirma.evisortemplates.windows.ForEachWindow;
import es.gob.afirma.evisortemplates.windows.ImageWindow;
import es.gob.afirma.evisortemplates.windows.TableWindow;
import es.gob.afirma.evisortemplates.windows.TextWindow;

/** 
 * <p>Class that represents cells of a row.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class RichTableCel extends AbsoluteLayout {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2565422649163676886L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(RichTableCel.class);

	/**
	 * Attribute that represents parent table of cell. 
	 */
	private RichTable parentTable;

	/**
	 * Constructor method for the class RichTableCel.java.
	 * @param parent parent table of cell
	 */
	public RichTableCel(RichTable parent) {
		super();
		this.parentTable = parent;
		addLayoutClickListener(new CelLayoutClickListener(this));
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponentContainer#setHeight(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setHeight(float hNew, Unit unit) {

		Component component = null;

		if (getComponentCount() == 1) {
			component = getComponentIterator().next();
		}

		if (component != null) {

			if (((PositionAngle) component).isImage() || ((PositionAngle) component).isBarCode()) {

				float h = component.getHeight();
				float hcel = getHeight();

				if (hcel < hNew) {

					((PositionAngleAbsImage) component).setScaleHeight(h + (hNew - hcel));

				} else if (hcel > hNew && h > hNew) {

					((PositionAngleAbsImage) component).setScaleHeight(hNew);

				}

				if (component.getWidth() > getWidth()) {
					((PositionAngleAbsImage) component).setScaleWidth(getWidth());
				}
			} else {
				component.setHeight(hNew, Unit.PIXELS);
			}
		}

		super.setHeight(hNew, unit);

		if (component != null) {
			focusContent(component);
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponentContainer#setWidth(float, com.vaadin.server.Sizeable.Unit)
	 */
	public final void setWidth(float wNew, Unit unit) {
		Component component = null;

		if (getComponentCount() == 1) {
			component = getComponentIterator().next();
		}

		if (component != null) {

			if (((PositionAngle) component).isImage() || ((PositionAngle) component).isBarCode()) {

				float w = component.getWidth();
				float wcel = getWidth();

				if (wcel < wNew) {
					((PositionAngleAbsImage) component).setScaleWidth(w + (wNew - wcel));
				} else if (wcel > wNew && w > wNew) {

					((PositionAngleAbsImage) component).setScaleWidth(wNew);

				}

				if (component.getHeight() > getHeight()) {
					((PositionAngleAbsImage) component).setScaleHeight(getHeight());
				}
			} else {
				component.setWidth(wNew, Unit.PIXELS);
			}
		}

		super.setWidth(wNew, unit);

		if (component != null) {
			focusContent(component);
		}
	}

	/**
	 * Method that sets new value for width recalculating other table sizes.
	 * @param wNew new value for width
	 * @param unit new unit for width value
	 */
	public final void setWidthRecalculating(float wNew, Unit unit) {

		setWidth(wNew, unit);

		if (parentTable != null) {
			parentTable.sizeRecalc(this);
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbsoluteLayout#addComponent(com.vaadin.ui.Component)
	 */
	public final void addComponent(Component component) {

		if (component instanceof RichLabel) {
			component.setWidth(getWidth(), getWidthUnits());
			component.setHeight(getHeight(), getHeightUnits());
			((RichLabel) component).setComponentAlignment(((RichLabel) component).getContent(), Alignment.MIDDLE_LEFT);
		}
		super.addComponent(component);
		focusContent(component);

	}

	/**
	 * Method that focuses component in cell.
	 * @param component component to be focused
	 */
	public final void focusContent(Component component) {

		ComponentPosition pos = this.getPosition(component);

		if (component instanceof BarCode) {
			pos.setTopValue((getHeight() / 2) - (component.getHeight() / 2));
			pos.setLeftValue((getWidth() / 2) - (component.getWidth() / 2));
		} else {
			if (component.getWidth() == getWidth()) {
				pos.setTopValue((getHeight() / 2) - (component.getHeight() / 2));
				pos.setLeftValue(0f);
			} else {
				pos.setLeftValue((getWidth() / 2) - (component.getWidth() / 2));
				pos.setTopValue(0f);
			}
		}

		this.setPosition(component, pos);

	}

	/** 
	 * <p>Inner class that represents cell layout click listener.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 16/03/2015.
	 */
	public class CelLayoutClickListener implements LayoutClickListener {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -3152806539380023021L;

		/**
		 * Attribute that represents cell who is listened. 
		 */
		private RichTableCel cell;

		/**
		 * Constructor method for the class RichTableCel.java.
		 * @param cellParam cell who is listened
		 */
		public CelLayoutClickListener(RichTableCel cellParam) {
			this.cell = cellParam;
		}

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.event.LayoutEvents.LayoutClickListener#layoutClick(com.vaadin.event.LayoutEvents.LayoutClickEvent)
		 */
		@Override
		public final void layoutClick(LayoutClickEvent event) {
			if (event.getButton().equals(MouseButton.LEFT)) {
				Component component = null;

				if (cell.getComponentCount() == 1) {
					component = cell.getComponentIterator().next();
				}

				if (component == null) {
					emptyCell();
				} else {
					notEmptyCell(component);
				}

			}
		}

		/**
		 * Method that listen empty cells.
		 */
		private void emptyCell() {
			if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTIMAGE)) {
				final ImageWindow window = new ImageWindow();

				// CHECKSTYLE:OFF To allow anonymous inner class size
				// greater than 20 lines.
				window.addClickAcceptButtonListener(new Button.ClickListener() {

					// CHECKSTYLE:ON

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = 1041987046764863493L;

					@Override
					public void buttonClick(ClickEvent event) {

						// si se ha insertado ya la imagen
						if (window.isImageIn()) {
							RichImage image = window.getImage();
							float w = image.getWidth();
							float h = image.getHeight();
							float wcel = cell.getWidth();
							float hcel = cell.getHeight();

							// si la celda es menor que la imagen en
							// cualquiera
							// de sus dimensiones hay que escalarla

							if (((h * wcel) / w) > hcel) {
								image.setScaleHeight(hcel);
							} else if (((h * wcel) / w) < hcel) {
								image.setScaleWidth(wcel);
							} else {
								image.setWidth(wcel, Unit.PIXELS);
								image.setHeight(hcel, Unit.PIXELS);
							}

							new ClickPanel((PositionAngle) image, cell);
							cell.addComponent(image);
							window.close();
						}
					}
				});

				UI.getCurrent().addWindow(window);
			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTTEXT)) {

				final TextWindow window;

				if (((RichTableRow) cell.getParent()).isHead()) {
					window = new TextWindow("", MessagesEVT.getInstance().getProperty("textWindowCaption"), false, false, false, false);
				} else {
					if (isForEachTable()) {
						window = new TextWindow("", MessagesEVT.getInstance().getProperty("textWindowCaption"), false, true, true, false);
					} else {
						window = new TextWindow("", MessagesEVT.getInstance().getProperty("textWindowCaption"), false, true, !cell.getExternalTable().isForEach(), Format.hasAnyVarIndexInTable(cell.getExternalTable()));
					}
				}

				window.addClickBotonAceptarListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -5949498300851340835L;

					@Override
					public void buttonClick(ClickEvent event) {
						RichLabel label = new RichLabel();
						label.setOriginalValue(window.getOriginalTextValue());
						label.setValue(window.getTextValue());
						if (window.isForEach() && !cell.getExternalTable().isForEachTable()) {
							cell.getExternalTable().setIsForEach(window.isForEach());
							cell.getExternalTable().setForEachType(ForEachWindow.OPORIZONTAL);
						}
						cell.addComponent(label);
						window.close();
					}
				});

				UI.getCurrent().addWindow(window);

			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTTABLE)) {
				final TableWindow window = new TableWindow(true);

				window.addClickAcceptButtonListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -7080047553982765564L;

					@Override
					public void buttonClick(ClickEvent event) {
						RichTable table = new RichTable((Integer) window.getColsValue(), (Integer) window.getRowsValue(), cell.getWidth(), cell.getHeight(), true, parentTable.isShowBorder(), false, false);
						cell.addComponent(table);
						window.close();

					}
				});

				UI.getCurrent().addWindow(window);

			} else if (DesignZone.getSelectedOption().equals(DesignZone.OPINSERTCODEBAR)) {

				final BarCodeWindow window = new BarCodeWindow(!cell.getParentTable().isForEach());

				// CHECKSTYLE:OFF To allow anonymous inner class size
				// greater than 20 lines.
				window.addClickAcceptButtonListener(new Button.ClickListener() {

					// CHECKSTYLE:ON

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -2505941162210816208L;

					@Override
					public void buttonClick(ClickEvent event) {
						if (window.getTextValue() != null && !window.getTextValue().isEmpty()) {

							try {
								BarCode codigo = new BarCode();
								codigo.setMessage(window.getTextValue());
								codigo.setType(window.getCodeType());
								float w = codigo.getWidth();
								float h = codigo.getHeight();
								float wcel = cell.getWidth();
								float hcel = cell.getHeight();

								// si la celda es menor que la imagen en
								// cualquiera
								// de sus dimensiones hay que escalarla

								if (((h * wcel) / w) > hcel) {
									codigo.setScaleHeight(hcel);
								} else if (((h * wcel) / w) < hcel) {
									codigo.setScaleWidth(wcel);
								} else {
									codigo.setWidth(wcel, Unit.PIXELS);
									codigo.setHeight(hcel, Unit.PIXELS);
								}

								cell.addComponent(codigo);

								window.close();
							} catch (IncorrectMessageBarCodeException e) {
								LOG.error(MessagesEVT.getInstance().getProperty("barCodeError"),e);
								Notification notification = new Notification("Error", MessagesEVT.getInstance().getProperty("barCodeError"));
								notification.show(Page.getCurrent());
							}

						}

					}
				});

				UI.getCurrent().addWindow(window);
			}
		}

		
		/**
		 * Method that listen not empty cells.
		 * @param component content of cell
		 */
		private void notEmptyCell(Component component) {
			if (((PositionAngle) component).isImage() || ((PositionAngle) component).isBarCode()) {

				String text = null;

				if (component instanceof RichImage) {
					text = MessagesEVT.getInstance().getProperty("removeImage");

				} else if (component instanceof BarCode) {
					text = MessagesEVT.getInstance().getProperty("removeBarcode");
				}

				final ConfirmationWindow window = new ConfirmationWindow(text);

				window.addClickYesButtonListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -1869224406053032273L;

					@Override
					public void buttonClick(ClickEvent event) {
						cell.removeAllComponents();
						window.close();

					}
				});

				UI.getCurrent().addWindow(window);

			} else if (((PositionAngle) component).isText()) {

				final TextWindow window;

				if (((RichTableRow) cell.getParent()).isHead()) {
					window = new TextWindow(((RichLabel) component).getOriginalValue(), MessagesEVT.getInstance().getProperty("textWindowCaptionEdition"), true, false, false, false);
				} else {
					if (isForEachTable()) {
						window = new TextWindow(((RichLabel) component).getOriginalValue(), MessagesEVT.getInstance().getProperty("textWindowCaptionEdition"), true, true, true, false);
					} else {
						window = new TextWindow(((RichLabel) component).getOriginalValue(), MessagesEVT.getInstance().getProperty("textWindowCaptionEdition"), true, true, !cell.getParentTable().isForEach(), Format.hasVarIndex(((RichLabel) component).getOriginalValue()));
					}
				}

				window.addClickBotonEliminarListener(new Button.ClickListener() {

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -1869224406053032273L;

					@Override
					public void buttonClick(ClickEvent event) {
						cell.removeAllComponents();
						window.close();

					}
				});

				// CHECKSTYLE:OFF To allow anonymous inner class size
				// greater than 20 lines.
				window.addClickBotonAceptarListener(new Button.ClickListener() {

					// CHECKSTYLE:ON

					/**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -5596074821094937757L;

					@Override
					public void buttonClick(ClickEvent event) {
						if (cell.getComponentCount() == 1) {
							RichLabel label = ((RichLabel) cell.getComponentIterator().next());
							label.setOriginalValue(window.getOriginalTextValue());
							if (window.isForEach() && !cell.getParentTable().isForEachTable()) {
								cell.getParentTable().setIsForEach(window.isForEach());
								cell.getParentTable().setForEachType(ForEachWindow.OPORIZONTAL);
							}
							label.setValue(window.getTextValue());
							window.close();
						}
					}
				});
				UI.getCurrent().addWindow(window);
			}

		}
	}

	/**
	 * Method that obtains code XSLT representation for component.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	public final StringBuffer getCode(CodeEditor codeEditor) {

		StringBuffer result = new StringBuffer();
		result.append(codeEditor.addLine("<fo:table-cell display-align=\"center\" width=\" ") + codeEditor.addEdition(PixelToCm.pxToCm(getWidth()) + "", this, CodeEditorInput.WIDTH, false) + codeEditor.addText("cm\" height=\"") + codeEditor.addText(PixelToCm.pxToCm(getHeight()) + "") + codeEditor.addText("cm\" \n"));

		if (parentTable.isShowBorder()) {
			if (!parentTable.isNested()) {
				result.append(codeEditor.addLine(" border-top-width = \"0.75px\" border-left-width = \"0.75px\" border-top-style = \"solid\" border-left-style = \"solid\""));
			} else {
				if (parentTable.getCellIndex(this) < parentTable.getCellsNum() - 1 && parentTable.getRowIndex(this) < parentTable.getRowsNum() - 1) {
					result.append(codeEditor.addLine(" border-bottom-width = \"0.75px\" border-right-width = \"0.75px\" border-bottom-style = \"solid\" border-right-style = \"solid\""));
				} else if (parentTable.getCellIndex(this) == parentTable.getCellsNum() - 1 && parentTable.getRowIndex(this) < parentTable.getRowsNum() - 1) {
					result.append(codeEditor.addLine(" border-bottom-width = \"0.75px\" border-bottom-style = \"solid\" "));
				} else if (parentTable.getCellIndex(this) < parentTable.getCellsNum() - 1 && parentTable.getRowIndex(this) == parentTable.getRowsNum() - 1) {
					result.append(codeEditor.addLine(" border-right-width = \"0.75px\" border-right-style = \"solid\""));
				}
			}
		}
		result.append(codeEditor.addText(">\n"));

		result.append(getComponentCode(codeEditor));

		result.append(codeEditor.addLine("</fo:table-cell>\n"));

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for component.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	private StringBuffer getComponentCode(CodeEditor codeEditor) {

		StringBuffer result = new StringBuffer();

		if (getComponentCount() > 0) {

			Iterator<Component> it = getComponentIterator();
			while (it.hasNext()) {
				Component next = it.next();
				result.append(getBefore(next, codeEditor));
				result.append(((PositionAngle) next).getCode(codeEditor));
				result.append(getAfter(codeEditor));
			}
		} else {
			result.append(codeEditor.addLine("<fo:block/>\n"));
		}

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for component (before zone).
	 * @param comp component
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	private StringBuffer getBefore(Component comp, CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();

		result.append(codeEditor.addLine("<fo:block-container>\n"));

		if (((PositionAngle) comp).isImage() || ((PositionAngle) comp).isBarCode()) {
			result.append(codeEditor.addLine("<fo:block line-height=\"100%\" text-align=\"center\">\n"));
		} else {
			result.append(codeEditor.addLine("<fo:block line-height=\"100%\" >\n"));
		}

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for component (after zone).
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	private StringBuffer getAfter(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();

		result.append(codeEditor.addLine("</fo:block>\n"));
		result.append(codeEditor.addLine("</fo:block-container>\n"));

		return result;
	}

	/**
	 * Method that resets position rows and columns.
	 */
	public final void resetPositions() {
		parentTable.resetPositions();
	}

	/**
	 * Method to check if the parent table is a "for each" component.
	 * @return true if the parent table is a "for each" component, false otherwise
	 */
	public final boolean isForEach() {
		RichTable externalTable = parentTable;
		while (externalTable.getParent() instanceof RichTableCel) {
			externalTable = ((RichTableCel) externalTable.getParent()).parentTable;
		}
		return externalTable.isForEach();
	}

	/**
	 * Method that gets parent table of cell.
	 * @return parent table of cell
	 */
	public final RichTable getParentTable() {
		return parentTable;
	}
	
	/**
	 * Method that gets external table of nested table's cell.
	 * @return external table of nested table's cell
	 */
	private RichTable getExternalTable() {
		RichTable tablaExterna = parentTable;
		while (tablaExterna.getParent() instanceof RichTableCel) {
			tablaExterna = ((RichTableCel) tablaExterna.getParent()).parentTable;
		}
		return tablaExterna;
	}

	/**
	 * Method that sets parent table of the cell.
	 * @param parentTableParam parent table of the cell
	 */
	public final void setParentTable(RichTable parentTableParam) {
		this.parentTable = parentTableParam;
	}

	/**
	 * Method that checks if the cell belongs to "for each" table.
	 * @return true if the cell belongs to "for each" table, false otherwise
	 */
	public final boolean isForEachTable() {
		RichTable tablaExterna = parentTable;
		while (tablaExterna.getParent() instanceof RichTableCel) {
			tablaExterna = ((RichTableCel) tablaExterna.getParent()).parentTable;
		}
		return tablaExterna.isForEachTable();
	}

	/**
	 * Method that returns a new instance of RichTableCel.
	 * @param parent Parent table of RichTableCel.
	 * @return the new instance of RichTableCel.
	 */
	public final RichTableCel newInstance(RichTable parent) {
		RichTableCel result = new RichTableCel(parent);
		
		Component component = null;

		if (getComponentCount() == 1) {
			component = getComponentIterator().next();
			ComponentPosition pos = this.getPosition(component);
			PositionAngle comp = ((PositionAngle)component).newInstance();
			result.addComponent(comp);
			ComponentPosition newPos = result.getPosition(comp);
			newPos.setBottom(pos.getBottomValue(), pos.getBottomUnits());
			newPos.setLeft(pos.getLeftValue(), pos.getLeftUnits());
			newPos.setRight(pos.getRightValue(), pos.getRightUnits());
			newPos.setTop(pos.getTopValue(), pos.getTopUnits());
			result.setPosition(comp, newPos);
		
		}
		
		result.setHeight(getHeight(), Unit.PIXELS);
		result.setWidth(getWidth(), Unit.PIXELS);
		result.setStyleName(getStyleName());
		
		return result;
	}

}
