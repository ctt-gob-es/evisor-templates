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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.VariablesZone.java.</p>
 * <b>Description:</b><p> Class that represents the globals variables zone.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>30/5/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/5/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.windows.GlobalVariableWindow;

/** 
 * <p>Class that represents the globals variables zone.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 30/5/2018.
 */
public class VariablesZone extends VerticalLayout {

	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = 6758479943152479263L;

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(DesignZone.class);

	/**
	 * Attribute that represents the list of global variables.
	 */
	private static List<TextField> listVariables = new ArrayList<TextField>();

	/**
	 * Attribute that represents the gridLayout which contains the globals variables.
	 */
	private VerticalLayout layoutListVariables = new VerticalLayout();

	/**
	 * Attribute that represents the formLayout which contains the global variable creation form.
	 */
	private VerticalLayout creationVariableLayout = new VerticalLayout();

	/**
	 * Attribute that represents the global layout where all the components will be included.
	 */
	private VerticalLayout globalPanel = new VerticalLayout();

	/**
	 * Attribute that represents the confirm button to create a new variable.
	 */
	private Button confirmCreationBtn = new Button();

	/**
	 * Attribute that represents the confirm button to edit a new variable.
	 */
	private Button confirmEditionBtn = new Button();

	/**
	 * Attribute that represents the name of the new variable.
	 */
	private TextField variableName;

	/**
	 * Attribute that represents the value of the new variable.
	 */
	private TextField variableValue;

	/**
	 * Attribute that represents a combo box with the global variables. 
	 */
	private ComboBox varsComboBox;

	/**
	 * Attribute that represents the creation global variable window.
	 */
	private GlobalVariableWindow window;

	/**
	 * Attribute that indicates if it is editing or creating global variables.
	 */
	private boolean isEditMode = false;

	/**
	 * Attribute that indicates if the application is loading global variables or not.
	 */
	private boolean loadMode = false;

	/**
	 * Attribute that represents the variable added into the layout.
	 */
	private Map<String, List<String>> varsByLayout = new HashMap<String, List<String>>();

	/**
	 * Attribute that represents the relation between a variable and their references to others variables.
	 */
	private static Map<String, List<String>> referencedVars = new HashMap<String, List<String>>();

	/**
	 * Constructor method for the class VariablesZone.java.
	 */
	@SuppressWarnings("serial")
	public VariablesZone() {
		super();

		LOG.info(MessagesEVT.getInstance().getProperty("variablesZoneCreation"));

		// Inicializamos la vista.
		this.addComponent(new Label(" "));
		this.setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);

		// Cargamos las variables globales ya almacenadas con anterioridad
		loadVariables();

		// Inicializamos el layout para representar el formulario de creación de
		// variables globales.
		creationVariableLayout.setSizeFull();
		creationVariableLayout.setMargin(true);
		// Creamos e inicializamos los componentes necesarios para la creación
		// de nuevas variables
		variableName = new TextField();
		variableName.setInputPrompt(MessagesEVT.getInstance().getProperty("formVariableTypesCaption"));
		variableValue = new TextField();
		variableValue.setInputPrompt(MessagesEVT.getInstance().getProperty("globalVariableValue"));
		variableValue.setWidth(95, Unit.PERCENTAGE);
		Button addVariableBtn = new Button(FontAwesome.PLUS_CIRCLE);
		addVariableBtn.setStyleName("button-margin-style2");
		addVariableBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				resetFields();
				variableName.focus();
				window = new GlobalVariableWindow(creationVariableLayout);
				UI.getCurrent().addWindow(window);
			}
		});
		// Instanciamos el botón de confirmación para la creación de variables
		// globales.
		confirmCreationBtn = new Button(FontAwesome.CHECK_CIRCLE);
		confirmCreationBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean success = addGlobalVariable();
					if (success) {
						window.close();
						window = null;
					}
				} catch (IllegalArgumentException e) {
					LOG.warn(MessagesEVT.getInstance().getProperty("globalVariableInputTextValidationFail"));
				}
			}

		});

		// Instanciamos el botón de confirmación para la edición de variables
		// globales.
		confirmEditionBtn = new Button(FontAwesome.CHECK_CIRCLE);
		confirmEditionBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// Comprobamos que el nuevo valor introducido no contiene
				// caracteres inválidos
				if (variableName.getValue().contains(":")) {
					// Mostramos mensaje de error y nos salimos del método
					Notification.show(MessagesEVT.getInstance().getProperty("inputValuesHasInvalidCharacters"), Type.ERROR_MESSAGE);
				} else  if(variableName == null || variableName.getValue().trim().isEmpty()){
					// Si el nombre al que se va a cambiar es nulo o esta vacío, mostramos un mensaje de error
					variableName.setComponentError(new UserError(MessagesEVT.getInstance().getProperty("variableNameErrorTooltip")));
			}else{
					// Cambiamos a modo edición
					isEditMode = true;
					// Recuperamos el id de la variable a eliminar
					String globalVarId = ((GlobalVariableWindow) event.getComponent().getParent().getParent()).globalVarId;
					// Eliminamos la variable
					removeGlobalVarById(globalVarId);
					// Creamos una nueva variable con los nuevos valores
					boolean success = addGlobalVariable();
					// Volvemos a cambiar a modo creación
					isEditMode = false;
					if (success) {
						// Cerramos la ventana
						window.close();
					} else {
						LOG.debug(MessagesEVT.getInstance().getProperty("globalVarEditionFails") + variableName.getValue());
					}
				}
			}
		});

		// Añadimos el comboBox con las variables disponibles actualmente.
		varsComboBox = new ComboBox();
		varsComboBox.setCaption(MessagesEVT.getInstance().getProperty("formVariableTypesCaption"));
		Set<String> globalVars = FormVariable.getGlobalVariablesMap().keySet();
		for (String var: globalVars) {
			varsComboBox.addItem(var);
		}
		varsComboBox.setNullSelectionAllowed(true);
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opCertificateInfo"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opGenerationTime"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opGlobalResult"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opParameter"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opResult"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opTimeStamp"));
		varsComboBox.addItem(MessagesEVT.getInstance().getProperty("opBarCode"));
		varsComboBox.setValue(null);

		// Creamos un botón para insertar variables en el valor de las nuevas
		// variables globales.
		Button addVarToValue = new Button();
		addVarToValue.setCaption(MessagesEVT.getInstance().getProperty("formVariableDefaultCaption"));
		addVarToValue.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (varsComboBox.getValue() != null) {
					StringBuffer value = new StringBuffer(variableValue.getValue());
					int index = variableValue.getCursorPosition();
					String varToAdd = varsComboBox.getValue().toString();
					if (variableName.getValue().equals(varToAdd)) {
						Notification.show(MessagesEVT.getInstance().getProperty("inputValuesHasSelfReferences"), Type.ERROR_MESSAGE);
					} else {
						varToAdd = "$" + varToAdd;
						if (value.length() == 0) {
							value.append(varToAdd);
						} else {
							value.insert(index, varToAdd);
						}
						variableValue.setValue(value.toString());
					}
				}
			}
		});

		// Añadimos ambos elementos a un layout vertical
		HorizontalLayout extraVarsLayout = new HorizontalLayout();
		extraVarsLayout.addComponent(varsComboBox);
		extraVarsLayout.addComponent(addVarToValue);
		extraVarsLayout.setSpacing(true);
		extraVarsLayout.setComponentAlignment(varsComboBox, Alignment.BOTTOM_CENTER);
		extraVarsLayout.setComponentAlignment(addVarToValue, Alignment.BOTTOM_CENTER);

		// Añadimos los componentes al layout
		creationVariableLayout.addComponent(variableName);
		creationVariableLayout.setComponentAlignment(variableName, Alignment.MIDDLE_LEFT);
		creationVariableLayout.addComponent(variableValue);
		creationVariableLayout.setComponentAlignment(variableValue, Alignment.MIDDLE_LEFT);
		creationVariableLayout.addComponent(extraVarsLayout);
		creationVariableLayout.setComponentAlignment(extraVarsLayout, Alignment.BOTTOM_LEFT);
		creationVariableLayout.addComponent(confirmCreationBtn);
		creationVariableLayout.setComponentAlignment(confirmCreationBtn, Alignment.BOTTOM_CENTER);

		globalPanel.addComponent(layoutListVariables);
		globalPanel.setComponentAlignment(layoutListVariables, Alignment.TOP_LEFT);
		globalPanel.addComponent(addVariableBtn);
		globalPanel.setComponentAlignment(addVariableBtn, Alignment.BOTTOM_RIGHT);
		Panel panel = new Panel(MessagesEVT.getInstance().getProperty("definedVariables"));
		panel.setContent(globalPanel);
		panel.setWidth(670, Unit.PIXELS);

		// Añadimos los layout a la vista principal
		this.addComponent(panel);
		this.setSpacing(true);
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	}

	public VariablesZone(VariablesZone variablesZone) {
	}

	/**
	 * Method that adds a new global variable to the list of globals variables. 
	 */
	@SuppressWarnings("serial")
	private boolean addGlobalVariable() {
		if (variableName != null && !variableName.getValue().trim().isEmpty() && variableValue != null) {
			// Comprobamos que no se han insertado caracteres inválidos
			if (variableName.getValue().contains(":")) {
				// Mostramos mensaje de error y nos salimos del método
				Notification.show(MessagesEVT.getInstance().getProperty("inputValuesHasInvalidCharacters"), Type.ERROR_MESSAGE);
				return false;
				// Comprobamos que el valor de la variable no contenga una
				// referencia a si misma
			} else if (variableValue.getValue().contains("$" + variableName.getValue())) {
				Notification.show(MessagesEVT.getInstance().getProperty("inputValuesHasSelfReferences"), Type.ERROR_MESSAGE);
				return false;
				// Comprobamos que no exista ya una variable con el mismo
				// nombre.
			} else if (FormVariable.getGlobalVariablesMap().keySet().contains(variableName.getValue()) && !loadMode) {
				Notification.show(MessagesEVT.getInstance().getProperty("varAlreadyExists"), Type.ERROR_MESSAGE);
				return false;
			} else {
				// Generamos la representación de la variable con el formato:
				// NOMBRE: VALOR
				String representation = getVarRepresentation();
				// Creamos el componente que representará los datos de la
				// variable.
				TextField textField = new TextField();
				textField.setValue(representation);
				textField.setReadOnly(true);
				textField.setWidth(500, Unit.PIXELS);
				// Añadimos el elemento a la lista de variables
				listVariables.add(textField);
				// Añadimos las referencias a otras variables al map.
				List<String> references = getVariablesReferences(variableValue.getValue());
				referencedVars.put(variableName.getValue(), references);
				// Creamos el botón para eliminar la variable
				Button removeBtn = new Button(FontAwesome.TRASH_O);
				removeBtn.setStyleName("button-margin-style");
				removeBtn.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// Recuperamos el textField que queremos eliminar
						TextField fieldToRemove = (TextField) ((HorizontalLayout) event.getComponent().getParent()).getComponent(0);
						try {
							// Comprobamos que la variable puede eliminarse
							checkVarToRemove(((HorizontalLayout) event.getComponent().getParent()).getId());
							// Eliminamos los componentes de la vista
							layoutListVariables.removeComponent((HorizontalLayout) event.getComponent().getParent());
							// Eliminamos el objeto de la lista de variables
							// globales
							listVariables.remove(fieldToRemove);
							LOG.info(MessagesEVT.getInstance().getProperty("globalVariableRemoved") + fieldToRemove.getValue());
							// Eliminamos la variable de los comboBox.
							FormVariable.removeType(fieldToRemove.getValue().split(":")[0]);
							varsComboBox.removeItem(fieldToRemove.getValue().split(":")[0]);
							// Eliminamos la variable del map de variables
							// creadas
							varsByLayout.remove(((HorizontalLayout) event.getComponent().getParent()).getId());
							varsComboBox.removeItem(fieldToRemove.getValue().split(":")[0]);
						} catch (IllegalArgumentException e) {
							// mostramos mensaje de error
							if (isEditMode) {
								Notification.show(MessagesEVT.getInstance().getProperty("editModeGlobalVariableIsUsed"), Type.ERROR_MESSAGE);
								throw e;
							} else {
								Notification.show(MessagesEVT.getInstance().getProperty("globalVariableIsUsed"), Type.ERROR_MESSAGE);
							}
						}
					}

					/**
					 * Method that checks if the selected global variable can be removed.
					 * @param id identifier of the object that represents the global variable to remove.
					 */
					private void checkVarToRemove(String id) {
						List<String> globalVarUsed = DesignZone.getGlobalVarUsed();
						// Si la variable esta siendo usada en la zona de
						// diseño, no
						// se permite su eliminación.
						if (globalVarUsed.contains(varsByLayout.get(id).get(0)) || usedInOthersVars(varsByLayout.get(id).get(0))) {
							throw new IllegalArgumentException();
						}
					}

					/**
					 * Method that checks if the variable is used to define another variable.
					 * @param varName Name of the variable
					 * @return True if the variable is used in another variable or False if not.
					 */
					private boolean usedInOthersVars(String varName) {
						// Recuperamos el conjunto de variables definidas
						Map<String, String> globalVars = FormVariable.getGlobalVariablesMap();
						String value;
						for (String var: globalVars.keySet()) {
							value = globalVars.get(var);
							if (value.contains("$" + varName)) {
								return true;
							}
						}
						return false;
					}
				});
				Button editBtn = new Button(FontAwesome.PENCIL);
				editBtn.setStyleName("button-margin-style");
				editBtn.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// Guardamos los valores de los atributos de la variable
						// global a cambiar
						List<String> var = varsByLayout.get(event.getComponent().getParent().getId());
						// Cambiamos el botón de creación del layout por el de
						// modificación
						creationVariableLayout = exchangeWindowGlobalVarButton(true);
						// Creamos la ventana y la mostramos
						window = new GlobalVariableWindow(creationVariableLayout, var.get(0), var.get(1), event.getComponent().getParent().getId());

						// Mostramos la ventana
						UI.getCurrent().addWindow(window);
					}

				});

				// Cremos el layout horizontal y añadimos los componentes.
				HorizontalLayout hl = new HorizontalLayout();
				hl.addComponent(textField);
				hl.addComponent(removeBtn);
				hl.addComponent(editBtn);
				hl.setId(generateId().toString());
				hl.setStyleName("variables-margin_style");
				List<String> varAttributes = new ArrayList<String>();
				varAttributes.add(variableName.getValue());
				varAttributes.add(variableValue.getValue());
				varsByLayout.put(hl.getId(), varAttributes);
				// Añadimos el layout horizontal en el layout principal
				layoutListVariables.addComponent(hl);
				// Le damos estilo al layout
				layoutListVariables.addStyleName("layout-margin-style");
				layoutListVariables.setComponentAlignment(hl, Alignment.MIDDLE_LEFT);
				// Actualizamos las variables disponibles
				FormVariable.addType(variableName.getValue(), variableValue.getValue());
				if (varsComboBox == null) {
					varsComboBox = new ComboBox();
				}
				varsComboBox.addItem(variableName.getValue());
				// Reseteamos los campos
				resetFields();
				return true;
			}

		} else {
			// Si alguno de los campos es nulo o estan vacíos, lanzamos un
			// mensaje de error
			if (variableName == null || variableName.getValue().trim().isEmpty()) {
				variableName.setComponentError(new UserError(MessagesEVT.getInstance().getProperty("variableNameErrorTooltip")));
			} else if (variableValue == null) {
				variableValue.setComponentError(new UserError(MessagesEVT.getInstance().getProperty("variableValueErrorTooltip")));
			} else {
				LOG.error(MessagesEVT.getInstance().getProperty("globalVariableInputTextError"));
			}
			return false;
		}
	}

	/**
	 * Method that gets the representation of the global variable.
	 * @return A String with the representation of the global variable.
	 */
	private String getVarRepresentation() {
		String varName = variableName.getValue();
		String varValue = variableValue.getValue();
		return varName + ": " + varValue;

	}

	/**
	 * Method that gets the map with the references variables defined.
	 * @return a map object which key is the name of the variable and the value is a list with every reference to another variable.
	 */
	public static Map<String, List<String>> getReferencedVars() {
		return referencedVars;
	}

	/**
	 * Method that loads the global variables into the layout.
	 */
	private void loadVariables() {
		Map<String, String> globalVariablesStored = FormVariable.getGlobalVariablesMap();
		loadMode = true;
		for (String variableName: globalVariablesStored.keySet()) {
			this.variableName = new TextField();
			this.variableName.setValue(variableName);
			this.variableValue = new TextField();
			this.variableValue.setValue(globalVariablesStored.get(variableName));
			addGlobalVariable();
		}
		loadMode = false;
		resetFields();
	}

	/**
	 * Method that resets the variable name and value attributes.
	 */
	private void resetFields() {
		if (variableName != null) {
			variableName.setValue("");
			variableName.setComponentError(null);
		}
		if (variableValue != null) {
			variableValue.setValue("");
			variableValue.setComponentError(null);
		}
		if (creationVariableLayout.getComponentCount() == 2) {
			creationVariableLayout = exchangeWindowGlobalVarButton(false);
		}
		if (varsComboBox != null) {
			varsComboBox.setValue(null);
		}
	}

	/**
	 * Method that generates an identifier.
	 * @return a new number used as identifier.
	 */
	private Long generateId() {
		Random randomGen = new Random();
		return randomGen.nextLong();
	}

	/**
	 * Method that removes a global variable by its identifier. 
	 * @param id Identifier of the global variable to remove.
	 */
	private void removeGlobalVarById(String id) {
		// Recorremos la lista de variables
		for (int i = 0; i < layoutListVariables.getComponentCount(); i++) {
			HorizontalLayout hl = ((HorizontalLayout) layoutListVariables.getComponent(i));
			if (hl.getId().equals(id)) {
				// Si los IDs coinciden, simulamos el click en el boton de
				// eliminar
				try {
					((Button) hl.getComponent(1)).click();
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			}
		}
	}

	/**
	 * Method that change the creation button for the edit button and viceversa.
	 * @param fromCreateToEdit boolean that indicates if the change must be done from creation to edition button (True) or from edition button to creation button (False).
	 * @return a new verticalLyaout with the new button inserted.
	 */
	private VerticalLayout exchangeWindowGlobalVarButton(boolean fromCreateToEdit) {
		VerticalLayout vl = creationVariableLayout;
		// Eliminamos el boton.
		Button btn = (Button) vl.getComponent(3);
		vl.removeComponent(btn);
		if (fromCreateToEdit) {
			// Si vamos a cambiar el botón de creación por el de
			// edición...
			vl.addComponent(confirmEditionBtn);
			vl.setComponentAlignment(confirmEditionBtn, Alignment.MIDDLE_CENTER);
		} else {
			// Si vamos a cambiar el botón de edición por el de
			// creación...
			vl.addComponent(confirmCreationBtn);
			vl.setComponentAlignment(confirmCreationBtn, Alignment.MIDDLE_CENTER);
		}
		return vl;
	}

	/**
	 * Method that gets the list of variables references defined into the value of the variable given as parameter.
	 * @param originalVarValue Value of the variable to analyze.
	 * @return a list with the names of the variables defined in the variable.
	 */
	public static List<String> getVariablesReferences(String originalVarValue) {
		// Definimos la lista que devolveremos como resultado
		List<String> res = new ArrayList<String>();
		// Obtenemos la lista de variables definidas
		Set<String> variables = FormVariable.getGlobalVariablesMap().keySet();
		// Definimos e inicializamos la variable que cotendrá las referencias
		// contenidas en el valor de la variable.
		List<String> matchVariables = new ArrayList<String>();
		// Comprobamos que exista al menos una variable definida
		if (originalVarValue.contains("$")) {
			// Dividimos el valor de la variable original con el caracter '$'
			String[ ] potentialReferences = originalVarValue.split("\\$");
			// Recorremos la lista de referencias a variables potenciales,
			// obviando la primera posición, ya que nunca podrá ser una variable
			for (int i = 1; i < potentialReferences.length; i++) {
				// realizamos una copia de la referencia
				String tempRef = potentialReferences[i];
				// Recorremos las variables definidas
				for (String var: variables) {
					if (tempRef.contains(var)) {
						// Si la variable global esta contenida en la
						// referencia, la añadimso a la lista de coincidencias
						matchVariables.add(var);
					}
				}
				// Vamos comprobando si la referencia coincide con asi variables
				// encontradas previamente. Si son exactamente iguales, se añade
				// dicha variable a la lista y continuamos con la siguiente
				// referencia. En caso de no coincidir, eliminamos el último
				// caracter de la referencia y volvemos a comparar.
				boolean isDone = false;
				while (tempRef.length() > 0 && !isDone) {
					for (String matchVar: matchVariables) {
						if (tempRef.equals(matchVar)) {
							res.add(matchVar);
							isDone = true;
							break;
						}
					}
					if (!isDone) {
						// Si no han concidido ambas cadenas, eliminamos el
						// último caracter y continuamos con las iteraciones
						tempRef = tempRef.substring(0, tempRef.length() - 1);
					}
				}
				// Limpiamos la lista para la siguiente iteración
				matchVariables.clear();
			}
		}
		return res;
	}
}
