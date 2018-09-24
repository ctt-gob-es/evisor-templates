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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.CodeEditorInput.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import org.apache.log4j.Logger;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.PixelToCm;
import es.gob.afirma.evisortemplates.windows.ConfirmationWindow;

/** 
 * <p>Class that represents the text fields of the code editor zone.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 13/03/2015.
 */
public class CodeEditorInput extends TextField {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8322147969198702410L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(CodeEditorInput.class);

	/**
	 * Attribute that represents the text field value's type. 
	 */
	private String type;
	
	/**
	 * Attribute that represents the component associated with text field value. 
	 */
	private Component component;
	
	/**
	 * Attribute that represents the code editor associated whit the text field. 
	 */
	private CodeEditor codeEditor;

	/**
	 * Attribute that represents if the text field change her value. 
	 */
	private boolean valueChanged;

	/**
	 * Attribute that represents WIDTH type String value. 
	 */
	public static final String WIDTH = "WIDTH";
	
	/**
	 * Attribute that represents HEIGHT type String value. 
	 */
	public static final String HEIGHT = "HEIGHT";
	
	/**
	 * Attribute that represents SPLITVERTICAL1 type String value. 
	 */
	public static final String SPLITVERTICAL1 = "SPLITVERTICAL1";
	
	/**
	 * Attribute that represents SPLITVERTICAL2 type String value. 
	 */
	public static final String SPLITVERTICAL2 = "SPLITVERTICAL2";
	
	/**
	 * Attribute that represents SPLITHORIZONTAL1 type String value. 
	 */
	public static final String SPLITHORIZONTAL1 = "SPLITHORIZONTAL1";
	
	/**
	 * Attribute that represents SPLITHORIZONTAL2 type String value. 
	 */
	public static final String SPLITHORIZONTAL2 = "SPLITHORIZONTAL2";
	
	/**
	 * Attribute that represents MARGINTOP type String value. 
	 */
	public static final String MARGINTOP = "MARGINTOP";
	
	/**
	 * Attribute that represents MARGINBOTTOM type String value. 
	 */
	public static final String MARGINBOTTOM = "MARGINBOTTOM";
	
	/**
	 * Attribute that represents MARGINLEFT type String value. 
	 */
	public static final String MARGINLEFT = "MARGINLEFT";
	
	/**
	 * Attribute that represents MARGINRIGHT type String value. 
	 */
	public static final String MARGINRIGHT = "MARGINRIGHT";
	
	/**
	 * Attribute that represents MARGINTOPBODY type String value. 
	 */
	public static final String MARGINTOPBODY = "MARGINTOPBODY";
	
	/**
	 * Attribute that represents MARGINBOTTOMBODY type String value. 
	 */
	public static final String MARGINBOTTOMBODY = "MARGINBOTTOMBODY";
	
	/**
	 * Attribute that represents MARGINLEFTBODY type String value. 
	 */
	public static final String MARGINLEFTBODY = "MARGINLEFTBODY";
	
	/**
	 * Attribute that represents MARGINRIGHTBODY type String value. 
	 */
	public static final String MARGINRIGHTBODY = "MARGINRIGHTBODY";
	
	/**
	 * Attribute that represents POSX type String value. 
	 */
	public static final String POSX = "POSX";
	
	/**
	 * Attribute that represents POSY type String value. 
	 */
	public static final String POSY = "POSY";
	
	/**
	 * Attribute that represents ANGLE type String value. 
	 */
	public static final String ANGLE = "ANGLE";
	
	/**
	 * Attribute that represents VARIABLEBARCODE type String value. 
	 */
	public static final String VARIABLEBARCODE = "VARIABLEBARCODE";
	
	/**
	 * Attribute that represents MESSAGEBARCODE type String value. 
	 */
	public static final String MESSAGEBARCODE = "MESSAGEBARCODE";
	
	/**
	 * Attribute that represents VARIABLE type String value. 
	 */
	public static final String VARIABLE = "VARIABLE";
	
	/**
	 * Attribute that represents POSXDOC type String value. 
	 */
	public static final String POSXDOC = "POSXDOC";
	
	/**
	 * Attribute that represents POSYDOC type String value. 
	 */
	public static final String POSYDOC = "POSYDOC";
	
	/**
	 * Attribute that represents WIDTHDOC type String value. 
	 */
	public static final String WIDTHDOC = "WIDTHDOC";
	
	/**
	 * Attribute that represents HEIGHTDOC type String value. 
	 */
	public static final String HEIGHTDOC = "HEIGHTDOC";
	
	/**
	 * Attribute that represents FOREACHCONDITION type String value. 
	 */
	public static final String FOREACHCONDITION = "FOREACHCONDITION";

	/**
	 * Constructor method for the class CodeEditorInput.java.
	 * @param code code to add in the text field
	 * @param comp component associated with text field value
	 * @param typeParam type of the value of text field
	 * @param codeEditorParam Code editor reference
	 * @param big true if big size is needed
	 */
	public CodeEditorInput(String code, Component comp, String typeParam, CodeEditor codeEditorParam, boolean big) {
		super();
		this.codeEditor = codeEditorParam;
		this.setValue(code);
		this.component = comp;
		this.type = typeParam;
		if (big) {
			setStyleName("inputCodigoLong");
		} else {
			setStyleName("inputCodigo");
		}
		setImmediate(true);

		valueChanged = false;

		addTextChangeListener(new FieldEvents.TextChangeListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 8501828779396629786L;

			@Override
			public void textChange(TextChangeEvent event) {
				valueChanged = true;

			}
		});

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		addBlurListener(new FieldEvents.BlurListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -548284947467323486L;

			@Override
			public void blur(BlurEvent event) {
				if (valueChanged && (CodeEditorInput.this.type.equals(WIDTH) || CodeEditorInput.this.type.equals(HEIGHT)) && isTableInstance()) {
					final ConfirmationWindow window = new ConfirmationWindow(MessagesEVT.getInstance().getProperty("changeTableFromCode"));
					
					window.setClosable(false);

					// CHECKSTYLE:OFF To allow anonymous inner class size
					// greater than 20 lines.
					window.addClickYesButtonListener(new Button.ClickListener() {

						// CHECKSTYLE:ON

						/**
						 * serialVersionUID
						 */
						private static final long serialVersionUID = 2002163248069786913L;

						@Override
						public void buttonClick(ClickEvent event) {
							LOG.info(MessagesEVT.getInstance().getProperty("recalculatingTableValues"));
							if (component instanceof RichTable && CodeEditorInput.this.type.equals(WIDTH)) {
								setWidthRecalculatingTable();
							} else if (component instanceof RichTable && CodeEditorInput.this.type.equals(HEIGHT)) {
								setHeightRecalculatingTable();
							} else if (component instanceof RichTableRow && CodeEditorInput.this.type.equals(HEIGHT)) {
								setHeightRecalculating();
							} else if (component instanceof RichTableCel && CodeEditorInput.this.type.equals(WIDTH)) {
								setWidthRecalculating();
							}
							// se repinta la plantilla xml
							codeEditor.obtieneString();

							window.close();

						}
					});

					UI.getCurrent().addWindow(window);

					valueChanged = false;

				}

			}
		});

	}
	
	/**
	 * Method that indicates if component associated with text field is a tabla instance.
	 * @return true if component associated with text field is a tabla instance, false otherwise
	 */
	private boolean isTableInstance() {
		return component instanceof RichTable || component instanceof RichTableRow || component instanceof RichTableCel;
	}

	/**
	 * Method that sets new height value to table recalculating other values.
	 */
	private void setHeightRecalculatingTable() {
		((RichTable) component).setHeightRecalculating(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);

		setCommonHeight();
	}

	/**
	 * Method that sets new width value to table recalculating other values.
	 */
	private void setWidthRecalculatingTable() {
		((RichTable) component).setWidthRecalculating(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);
		setCommonWidth();

	}

	/**
	 * Method that sets new height value to table row recalculation other values.
	 */
	private void setHeightRecalculating() {
		((RichTableRow) component).setHeightRecalculating(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);

		setCommonHeight();
	}

	/**
	 * Method that sets new width value to table cell recalculation other values.
	 */
	private void setWidthRecalculating() {
		((RichTableCel) component).setWidthRecalculating(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);
		setCommonWidth();

	}

	/**
	 * Method that sets new height value to component.
	 */
	private void setHeight() {
		component.setHeight(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);

		setCommonHeight();

	}

	/**
	 * Method that sets new height value to common part of component.
	 */
	private void setCommonHeight() {
		if (component instanceof PositionAngle && ((PositionAngle) component).getContainer() != null) {
			((PositionAngle) component).getContainer().setHeight(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);
		}
	}

	/**
	 * Method that sets new width value to component.
	 */
	private void setWidth() {
		component.setWidth(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);
		setCommonWidth();

	}

	/**
	 * Method that sets new width value to common part of component.
	 */
	private void setCommonWidth() {
		if (component instanceof PositionAngle && ((PositionAngle) component).getContainer() != null) {
			((PositionAngle) component).getContainer().setWidth(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))), Unit.PIXELS);
		}
	}

	/**
	 * Method that solves template splits types.
	 * @return true if new value solved, false otherwise
	 */
	private boolean solveTemplateSplits() {

		if (type.equals(SPLITVERTICAL1)) {
			((VerticalSplitPanel) component).setSplitPosition(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
			return true;
		} else if (type.equals(SPLITVERTICAL2)) {
			float h = ((VerticalSplitPanel) component).getParent().getHeight();
			//Se resta 1 para compensar el tamaño del propio split
			((VerticalSplitPanel) component).setSplitPosition(Math.round(h - PixelToCm.cmToPx(Double.parseDouble(getValue()))) -1);
			return true;
		} else if (type.equals(SPLITHORIZONTAL1)) {
			((HorizontalSplitPanel) component).setSplitPosition(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
			return true;
		} else if (type.equals(SPLITHORIZONTAL2)) {
			float w = ((HorizontalSplitPanel) component).getParent().getWidth();
			//Se resta 1 para compensar el tamaño del propio split
			((HorizontalSplitPanel) component).setSplitPosition(Math.round(w - PixelToCm.cmToPx(Double.parseDouble(getValue()))) -1);
			return true;
		}
		return false;
	}

	/**
	 * Method that solves template types.
	 * @return true if new value solved, false otherwise
	 */
	private boolean solveTemplate() {
		if (!solveTemplateSplits()) {
			if (type.equals(MARGINTOP)) {
				((Template) component).setMarginTopTemplate(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINBOTTOM)) {
				((Template) component).setMarginBottomTemplate(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINLEFT)) {
				((Template) component).setMarginLeftTemplate(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINRIGHT)) {
				((Template) component).setMarginRightTemplate(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINTOPBODY)) {
				((Template) component).setMarginTopBody(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINBOTTOMBODY)) {
				((Template) component).setMarginBottomBody(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINLEFTBODY)) {
				((Template) component).setMarginLeftBody(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			} else if (type.equals(MARGINRIGHTBODY)) {
				((Template) component).setMarginRightBody(Math.round(PixelToCm.cmToPx(Double.parseDouble(getValue()))));
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Method that solves template position and size.
	 * @return true if new value solved, false otherwise
	 */
	private boolean solvePosAndSize() {
		if (type.equals(WIDTH)) {
			setWidth();
			return true;
		} else if (type.equals(HEIGHT)) {
			setHeight();
			return true;
		} else if (type.equals(POSX)) {
			float newX = (float) PixelToCm.mmToPx(Double.parseDouble(getValue()));
			float oldX = ((PositionAngle) component).getPosXXSLT();
			float varX = newX - oldX;
			float resultX = ((PositionAngle) component).getPosX() + varX;
			((PositionAngle) component).setPosX(resultX);
			if (component.getParent() instanceof Template) {
				((Template) component.getParent()).getPosition(component).setLeft(resultX, Unit.PIXELS);
			} else {
				((RichTableCel) component.getParent()).getPosition(component).setLeft(resultX, Unit.PIXELS);
			}
			return true;

		} else if (type.equals(POSY)) {
			float newY = (float) PixelToCm.mmToPx(Double.parseDouble(getValue()));
			float oldY = ((PositionAngle) component).getPosYXSLT();
			float varY = newY - oldY;
			float resultY = ((PositionAngle) component).getPosY() + varY;
			((PositionAngle) component).setPosY(resultY);
			if (component.getParent() instanceof Template) {
				((Template) component.getParent()).getPosition(component).setTop(resultY, Unit.PIXELS);
			} else {
				((RichTableCel) component.getParent()).getPosition(component).setTop(resultY, Unit.PIXELS);
			}
			return true;

		} else if (type.equals(ANGLE)) {
			// Al setear el angulo se modifican los estilos de los
			// componentes
			// para rectificarse.
			((PositionAngle) component).setAngle(Integer.parseInt(getValue()));
			return true;
		}
		return false;
	}

	/**
	 * Method that solves template's barcode and signed document types.
	 * @return true if new value solved, false otherwise
	 */
	private boolean solveBarcodesAndDoc() {
		if (type.equals(VARIABLEBARCODE)) {
			((BarCode) component).setMessage(Format.getParameterVarToTemplate(getValue()));
			return true;
		} else if (type.equals(MESSAGEBARCODE)) {
			((BarCode) component).setMessage(getValue());
			return true;
		} else if (type.equals(POSXDOC)) {
			float result = (float) PixelToCm.mmToPx(Double.parseDouble(getValue()));
			((Document) component).setPosX(result);
			((Template) component.getParent()).getPosition(component).setLeft(result, Unit.PIXELS);
			return true;
		} else if (type.equals(POSYDOC)) {

			float hp = ((Template) component.getParent()).getHeight();
			float hc = component.getHeight();
			float posy = hp - ((float) PixelToCm.mmToPx(Double.parseDouble(getValue())) + hc);
			((Document) component).setPosY(posy);
			((Template) component.getParent()).getPosition(component).setTop(posy, Unit.PIXELS);
			return true;
		} else if (type.equals(WIDTHDOC)) {
			float result = (float) PixelToCm.mmToPx(Double.parseDouble(getValue()));
			component.setWidth(result, Unit.PIXELS);
			return true;

		} else if (type.equals(HEIGHTDOC)) {
			float result = (float) PixelToCm.mmToPx(Double.parseDouble(getValue()));
			component.setHeight(result, Unit.PIXELS);
			return true;

		}
		return false;
	}

	/**
	 * Method that solves changes in code.
	 */
	public final void solve() {
		if (!solveTemplate() && !solvePosAndSize() && !solveBarcodesAndDoc()) {
			if (type.equals(VARIABLE) && component instanceof RichImage) {
				((RichImage) component).setVariable((Format.getParameterVarToTemplate(getValue())));

			} else if (type.equals(FOREACHCONDITION)) {
				((PositionAngle) component).setConditionForEach(getValue());
			}
		}

		resetPositions();

	}

	/**
	 * Method that resets inner table positions.
	 */
	private void resetPositions() {
		if (component instanceof RichTable) {
			((RichTable) component).resetPositions();
		}

		if (component instanceof RichTableRow) {
			((RichTableRow) component).resetPositions();
		}

		if (component instanceof RichTableCel) {
			((RichTableCel) component).resetPositions();
		}
	}
}
