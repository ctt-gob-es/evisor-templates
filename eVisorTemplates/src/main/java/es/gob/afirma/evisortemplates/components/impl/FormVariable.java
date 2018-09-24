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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.FormVariable.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that represents a form variable zone.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class FormVariable extends CustomComponent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 645129393079073952L;

	/**
	 * Attribute that represents the Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(FormVariable.class);

	/**
	 * Attribute that represents a combo box with variable's types. 
	 */
	private ComboBox types;

	/**
	 * Attribute that represents a combo box with variable's detail. 
	 */
	private ComboBox detail;

	/**
	 * Attribute that represents a combo box with bar code's detail. 
	 */
	private ComboBox codeDetail;

	/**
	 * Attribute that represents a combo box with bar code's number. 
	 */
	private ComboBox barcodeNum;

	/**
	 * Attribute that represents a combo box with signature's number. 
	 */
	private ComboBox signatureNum;

	/**
	 * Attribute that represents the variable's value. 
	 */
	private TextField text;

	/**
	 * Attribute that represents accept button for insert a variable. 
	 */
	private Button accept;

	/**
	 * Attribute that represents a flag to show or not the index combo box. 
	 */
	private boolean showIndex;

	/**
	 * Attribute that represents a flag that indicates if variabe's index are inserted. 
	 */
	private boolean hasVarIndex;

	/**
	 * Attribute that represents the set of global variables added by the user.
	 */
	private static Map<String, String> userGlobalVariables = new HashMap<String, String>();

	/**
	 * Attribute that represents opParameter String constant. 
	 */
	private static final String OPPARAMETER = "opParameter";

	/**
	 * Attribute that represents opBarCode String constant. 
	 */
	private static final String OPBARCODE = "opBarCode";

	/**
	 * Attribute that represents opCertificateInfo String constant. 
	 */
	private static final String OPCERTIFICATEINFO = "opCertificateInfo";

	/**
	 * Attribute that represents opGenerationTime String constant. 
	 */
	private static final String OPGENERATIONTIME = "opGenerationTime";

	/**
	 * Attribute that represents opGlobalResult String constant. 
	 */
	private static final String OPGLOBALRESULT = "opGlobalResult";

	/**
	 * Attribute that represents opResult String constant. 
	 */
	private static final String OPRESULT = "opResult";

	/**
	 * Attribute that represents opTimeStamp String constant. 
	 */
	private static final String OPTIMESTAMP = "opTimeStamp";

	/**
	 * Constructor method for the class FormVariable.java.
	 * @param type type of form variable
	 * @param showIndexParam true if want show index combo box
	 * @param hasVarIndexParam true if exists a variable with index inserted
	 */
	public FormVariable(final String type, boolean showIndexParam, boolean hasVarIndexParam) {
		this(MessagesEVT.getInstance().getProperty("formVariableDefaultCaption"), type, showIndexParam, hasVarIndexParam);
	}

	/**
	 * Constructor method for the class FormVariable.java.
	 * @param textButon label for insert button
	 * @param type type of form variable
	 * @param showIndexForEach true if want show index combo box
	 * @param hasVarIndexParam true if exists a variable with index inserted
	 */
	public FormVariable(final String textButon, final String type, boolean showIndexForEach, final boolean hasVarIndexParam) {

		this.hasVarIndex = hasVarIndexParam;

		types = new ComboBox();
		types.setCaption(MessagesEVT.getInstance().getProperty("formVariableTypesCaption"));

		types.setNullSelectionAllowed(true);
		addUserGlobalVariables();
		if (Format.TYPEIMAGE.equals(type)) {
			types.addItem(MessagesEVT.getInstance().getProperty(OPPARAMETER));
			types.addItem(MessagesEVT.getInstance().getProperty(OPBARCODE));
		} else if (Format.TYPEBARCODE.equals(type)) {
			types.addItem(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO));
			types.addItem(MessagesEVT.getInstance().getProperty(OPGENERATIONTIME));
			types.addItem(MessagesEVT.getInstance().getProperty(OPGLOBALRESULT));
			types.addItem(MessagesEVT.getInstance().getProperty(OPPARAMETER));
			types.addItem(MessagesEVT.getInstance().getProperty(OPRESULT));
			types.addItem(MessagesEVT.getInstance().getProperty(OPTIMESTAMP));
		} else if (Format.TYPEALL.equals(type)) {
			types.addItem(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO));
			types.addItem(MessagesEVT.getInstance().getProperty(OPGENERATIONTIME));
			types.addItem(MessagesEVT.getInstance().getProperty(OPGLOBALRESULT));
			types.addItem(MessagesEVT.getInstance().getProperty(OPPARAMETER));
			types.addItem(MessagesEVT.getInstance().getProperty(OPRESULT));
			types.addItem(MessagesEVT.getInstance().getProperty(OPTIMESTAMP));
			types.addItem(MessagesEVT.getInstance().getProperty(OPBARCODE));
		}
		types.setValue(null);

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		types.addValueChangeListener(new ValueChangeListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5611218531578009213L;

			private void evalSignatureNum() {
				if ((types.getValue().equals(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPRESULT)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPTIMESTAMP))) && showIndex) {
					if (hasVarIndex) {
						signatureNum.setNullSelectionAllowed(false);
						signatureNum.setValue(1);
					}
					signatureNum.setVisible(true);
				} else {
					signatureNum.setVisible(false);
					signatureNum.setValue(null);
				}
			}

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (types.getValue() != null) {
					if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPPARAMETER))) {
						evalSignatureNum();
						text.setVisible(true);
						barcodeNum.setVisible(false);
						detail.setVisible(false);
						detail.setValue(null);
						codeDetail.setVisible(false);
						codeDetail.setValue(null);
					} else if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPGLOBALRESULT)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPRESULT))) {
						evalSignatureNum();
						detail.setVisible(true);
						codeDetail.setVisible(false);
						codeDetail.setValue(null);
						barcodeNum.setVisible(false);
						text.setVisible(false);
						text.setValue("");
					} else if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPBARCODE))) {
						signatureNum.setVisible(false);
						signatureNum.setValue(null);
						codeDetail.setVisible(true);
						barcodeNum.setVisible(true);
						text.setVisible(false);
						text.setValue("");
						detail.setVisible(false);
						detail.setValue(null);
					} else if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPTIMESTAMP)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPGENERATIONTIME))) {
						evalSignatureNum();
						text.setVisible(false);
						text.setValue("");
						barcodeNum.setVisible(false);
						detail.setVisible(false);
						detail.setValue(null);
						codeDetail.setVisible(false);
						codeDetail.setValue(null);
					} else if (types.getValue() != null) {
						// Variables globales definidas por el usuario
						evalSignatureNum();
						text.setVisible(false);
						text.setValue(types.getValue().toString());
						barcodeNum.setVisible(false);
						detail.setVisible(false);
						detail.setValue(null);
						codeDetail.setVisible(false);
						codeDetail.setValue(null);
					}
				}

				accept.setVisible(true);

			}
		});

		HorizontalLayout formContainer;

		formContainer = new HorizontalLayout();

		formContainer.addComponent(types);

		signatureNum = new ComboBox();
		signatureNum.setNullSelectionAllowed(false);
		int signatureNumLimit = Integer.parseInt(ConfigEVT.getInstance().getProperty("signatureNumLimit"));
		for (int i = 1; i <= signatureNumLimit; i++) {
			signatureNum.addItem(i);
		}
		signatureNum.setWidth(NumberConstants.FNUM75, Unit.PIXELS);
		signatureNum.setVisible(false);
		this.showIndex = showIndexForEach;
		signatureNum.setNullSelectionAllowed(true);

		formContainer.addComponent(signatureNum);
		formContainer.setComponentAlignment(signatureNum, Alignment.BOTTOM_CENTER);

		text = new TextField();
		formContainer.addComponent(text);
		formContainer.setComponentAlignment(text, Alignment.BOTTOM_CENTER);
		text.setVisible(false);

		barcodeNum = new ComboBox();
		barcodeNum.setNullSelectionAllowed(false);
		boolean firstInserted = false;
		int barcodeNumLimit = Integer.parseInt(ConfigEVT.getInstance().getProperty("barcodeNumLimit"));
		for (int i = 1; i <= barcodeNumLimit; i++) {
			if (!firstInserted) {
				barcodeNum.addItem(i);
				barcodeNum.setValue(i);
				firstInserted = true;

			}
			barcodeNum.addItem(i);
		}
		barcodeNum.setWidth(NumberConstants.FNUM75, Unit.PIXELS);
		barcodeNum.setVisible(false);

		formContainer.addComponent(barcodeNum);
		formContainer.setComponentAlignment(barcodeNum, Alignment.BOTTOM_CENTER);

		detail = new ComboBox();

		detail.setNullSelectionAllowed(true);
		detail.addItem(MessagesEVT.getInstance().getProperty("major"));
		detail.addItem(MessagesEVT.getInstance().getProperty("minor"));
		detail.addItem(MessagesEVT.getInstance().getProperty("message"));

		detail.setVisible(false);

		formContainer.addComponent(detail);
		formContainer.setComponentAlignment(detail, Alignment.BOTTOM_CENTER);

		codeDetail = new ComboBox();

		codeDetail.setNullSelectionAllowed(true);
		if (Format.TYPEIMAGE.equals(type)) {
			codeDetail.addItem(MessagesEVT.getInstance().getProperty("url"));
		} else {
			codeDetail.addItem(MessagesEVT.getInstance().getProperty("type"));
			codeDetail.addItem(MessagesEVT.getInstance().getProperty("code"));
			codeDetail.addItem(MessagesEVT.getInstance().getProperty("url"));
		}

		codeDetail.setVisible(false);

		formContainer.addComponent(codeDetail);
		formContainer.setComponentAlignment(codeDetail, Alignment.BOTTOM_CENTER);

		accept = new Button(textButon);
		accept.setVisible(false);

		formContainer.addComponent(accept);
		formContainer.setComponentAlignment(accept, Alignment.BOTTOM_CENTER);

		formContainer.setExpandRatio(types, NumberConstants.FNUM1COMA25);
		formContainer.setExpandRatio(text, NumberConstants.FNUM1COMA25);
		formContainer.setExpandRatio(accept, NumberConstants.FNUM0COMA5);

		formContainer.setSpacing(true);

		setCompositionRoot(formContainer);

	}

	/**
	 * Method that adds a accept button click listener.
	 * @param listener listener to be inserted
	 */
	public final void addClickAcceptListener(ClickListener listener) {
		if (accept != null) {
			accept.addClickListener(listener);
		}
	}

	/**
	 * Method that gets the value of combo box types.
	 * @return the value of combo box types
	 */
	public final String getTypesValue() {
		String res = (String) types.getValue();
		if (res.contains("<") || res.contains(">")) {
			res = res.replaceAll("<", "&lt;");
			res = res.replaceAll(">", "&gt;");
		}
		return res;
	}

	/**
	 * Method that gets the value of text field (variable name).
	 * @return the value of text field (variable name)
	 */
	public final String getTextValue() {
		if (text.getValue() != null && !text.getValue().isEmpty()) {
			return text.getValue();
		} else if (detail.getValue() != null) {
			return (String) detail.getValue();
		} else if (codeDetail.getValue() != null) {
			return (String) codeDetail.getValue();
		}
		return " ";

	}

	/**
	 * Method that gets variable's value good formed.
	 * @return variable's value good formed.
	 */
	public final String getVarFormed() {
		if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPBARCODE))) {
			return "¬" + Format.equivalent(getTypesValue().toString()) + "[" + barcodeNum.getValue() + "]" + ":" + getTextValue() + "¬";
		} else {
			if (signatureNum.getValue() != null) {
				return "¬" + Format.equivalent(getTypesValue().toString()) + "[" + signatureNum.getValue() + "]" + ":" + getTextValue() + "¬";
			}
		}
		return "¬" + Format.equivalent(getTypesValue().toString()) + ":" + getTextValue() + "¬";
	}

	/**
	 * Method that checks if the variable is a full variable.
	 * @return true if the variable is a full variable, false otherwise
	 */
	public final boolean isFullVar() {
		boolean result = false;

		if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPTIMESTAMP)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPGENERATIONTIME))) {
			result = true;
		} else {
			if (text.getValue() != null && !text.getValue().isEmpty()) {
				result = true;
			} else if (detail.getValue() != null) {
				result = true;
			} else if (codeDetail.getValue() != null) {
				result = true;
			}
		}

		if (!result) {
			Notification.show(MessagesEVT.getInstance().getProperty("fullFormVar"), Type.ERROR_MESSAGE);
			LOG.error(MessagesEVT.getInstance().getProperty("fullFormVar"));
		}
		return result;
	}

	/**
	 * Method that checks if the variable is a "for each" variable.
	 * @return true if the variable is a "for each" variable, false otherwise
	 */
	public final boolean isForEach() {
		if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPRESULT)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPTIMESTAMP))) {
			return signatureNum.getValue() == null;
		} else {
			return false;
		}
	}

	/**
	 * Method that blocks "for each" combo box.
	 */
	public final void blockForEach() {
		if (types.getValue().equals(MessagesEVT.getInstance().getProperty(OPCERTIFICATEINFO)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPRESULT)) || types.getValue().equals(MessagesEVT.getInstance().getProperty(OPTIMESTAMP))) {
			showIndex = !isForEach();
			if (!showIndex) {
				signatureNum.setValue(null);
				signatureNum.setVisible(false);
				hasVarIndex = false;
			} else {
				signatureNum.setNullSelectionAllowed(false);
				hasVarIndex = true;
			}
		}
	}

	/**
	 * Method that adds new variables to the set of types.
	 * @param variableName Name of the variable to add.
	 * @param variableValue Value of the variable to add.
	 */
	public static void addType(String variableName, String variableValue) {
		if (variableName != null && !variableName.trim().isEmpty()) {
			// Añadimos al map la nueva variable. En caso de ya existir, se
			// sobrescribe.
			userGlobalVariables.put(variableName, variableValue);
			LOG.info(MessagesEVT.getInstance().getProperty("newVariableAdded") + variableName);
		}
	}

	/**
	 * Method that removes a variable from the set of types.
	 * @param variableName Name of the variable to remove.
	 */
	public static void removeType(String variableName) {
		if (variableName != null) {
			userGlobalVariables.remove(variableName);
		}
	}

	/**
	 * Method that gets the map with the global variables defined by the users.
	 * @return A Map with the name and value of the global variables defined.
	 */
	public static Map<String, String> getGlobalVariablesMap() {
		return userGlobalVariables;
	}

	/**
	 * Method that set the value of the userGlobalVariables attribute.
	 * @param globalVariables new value.
	 */
	public static void setGlobalVariablesMap(Map<String, String> globalVariables) {
		userGlobalVariables = globalVariables;
	}

	/**
	 * Method that add the set of variables into the variables comboBox.
	 */
	private void addUserGlobalVariables() {
		for (String var: userGlobalVariables.keySet()) {
			types.addItem(var);
		}
	}

	/**
	 * Method that adds a new global variable to the list of used variables.
	 * @param textValue name of the new global variable used.
	 */
	public static void addGlobalVarUsed(String textValue) {
		if (textValue != null) {
			List<String> textFormatted = processVar(textValue);
			List<String> globalVars = DesignZone.getGlobalVarUsed();
			globalVars.addAll(textFormatted);
			DesignZone.updateGlobalVarUsed(globalVars);
		}
	}

	/**
	 * Method that adds a new global variable used in a condition to the list of used variable.
	 * @param textValue name of the variable used in the condition.
	 */
	public static void addGlobalVarConditionUsed(String textValue) {
		if (textValue != null && !textValue.trim().isEmpty()) {
			if (textValue.startsWith("¬") || textValue.endsWith("¬")) {
				addGlobalVarUsed(textValue);
			} else {
				List<String> globalVars = DesignZone.getGlobalVarUsed();
				globalVars.add(textValue);
				DesignZone.updateGlobalVarUsed(globalVars);
			}
		}
	}

	/**
	 * Method that removes a global variable from the list of used variables.
	 * @param globalVarName Name of the global variable to remove. 
	 */
	private static void removeGlobalVarUsedAux(String globalVarName) {
		if (globalVarName != null) {
			List<String> vars = processVar(globalVarName);
			List<String> globalVars = DesignZone.getGlobalVarUsed();
			for (String var: vars) {
				if (globalVars.contains(var)) {
					globalVars.remove(var);
				}
			}
			DesignZone.updateGlobalVarUsed(globalVars);
		}
	}

	/**
	 * Method that removes a global variable used as condition in a component.
	 * @param condition Name of the global variable to remove.
	 */
	public static void removeGlobalVarUsed(String condition) {
		if (condition != null) {
			if (condition.startsWith("¬") || condition.endsWith("¬")) {
				removeGlobalVarUsedAux(condition);
			} else {
				List<String> globalVars = DesignZone.getGlobalVarUsed();
				if (globalVars.contains(condition)) {
					globalVars.remove(condition);
				}
				DesignZone.updateGlobalVarUsed(globalVars);
			}
		}
	}

	/**
	 * Method that process a string in order to obtain the list of variables.
	 * @param globalVar Input string which will be process.
	 * @return The list with the variables found in the input string.
	 */
	public static List<String> processVar(String globalVar) {
		List<String> res = new ArrayList<String>();
		String[ ] textArray = globalVar.split("¬");
		boolean par = false;
		boolean first = true;
		for (String var: textArray) {
			String varAux;
			varAux = var.replace("&nbsp;", " ");
			if (varAux.trim().length() != 0) {
				if (par) {
					if (first) {
						res.add(varAux.split(":")[1]);
						first = false;
					} else {
						res.add(varAux.split(":")[1]);
					}
				} else {
					if (first) {
						first = false;
					}

				}
			}
			par = !par;
		}
		return res;
	}

}
