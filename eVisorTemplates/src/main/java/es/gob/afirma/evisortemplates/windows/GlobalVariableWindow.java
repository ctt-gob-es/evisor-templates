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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.windows.GlobalVariableWindow.java.</p>
 * <b>Description:</b><p> Class that represents the window to create and edit a global variable.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * <b>Date:</b><p>07/06/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 07/06/2018.
 */
package es.gob.afirma.evisortemplates.windows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;


/** 
 * <p>Class that represents the window to create and edit a global variable.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * @version 1.0, 07/06/2018.
 */
public class GlobalVariableWindow extends WindowRelativePos {
	
	/**
	 * Attribute that represents the identifier of the global variable to edit.
	 */
	public String globalVarId;
	
	/**
	 * Attribute that represents the new name of the variable to edit.
	 */
	public String newVarName;

	/**
	 * Attribute that represents the new value of the variable to edit.
	 */
	public String newVarValue;

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 4990141311291357736L;

	/**
	 * Constructor method for the class GlobalVariableWindow.java.
	 * @param layout
	 */
	public GlobalVariableWindow(Layout layout){
		super();
		
        this.setWidth(450.0f, Unit.PIXELS);
        this.setHeight(300.0f, Unit.PIXELS);
        this.setCaption(MessagesEVT.getInstance().getProperty("createGlobalVariable"));
        this.setModal(true);
        this.setPositionX(575);
        this.setPositionY(200);        
        this.setContent(layout);  
	}

	/**
	 * Constructor method for the class GlobalVariableWindow.java.
	 * @param layout
	 * @param variableName
	 * @param variableValue
	 * @param globalVarId
	 */
	public GlobalVariableWindow(Layout layout, String variableName, String variableValue, String globalVarId) {
		super();
		
		this.globalVarId = globalVarId;
		
        this.setWidth(450.0f, Unit.PIXELS);
        this.setHeight(300.0f, Unit.PIXELS);
        this.setCaption(MessagesEVT.getInstance().getProperty("createGlobalVariable"));
        this.setModal(true);
        this.setPositionX(570);
        this.setPositionY(200);
        
        TextField textFieldName = (TextField) ((VerticalLayout) layout).getComponent(0);
        TextField textFieldValue = (TextField) ((VerticalLayout) layout).getComponent(1);
        HorizontalLayout extraVarsLayout = (HorizontalLayout) ((VerticalLayout) layout).getComponent(2);
        Button confirmBtn = (Button) ((VerticalLayout) layout).getComponent(3);

        textFieldName.setValue(variableName);
        textFieldValue.setValue(variableValue);
        ((VerticalLayout) layout).removeComponent((TextField) ((VerticalLayout) layout).getComponent(0));
        ((VerticalLayout) layout).removeComponent((TextField) ((VerticalLayout) layout).getComponent(0));
        ((VerticalLayout) layout).removeComponent((HorizontalLayout) ((VerticalLayout) layout).getComponent(0));
        ((VerticalLayout) layout).removeComponent((Button) ((VerticalLayout) layout).getComponent(0));
        ((VerticalLayout) layout).addComponent(textFieldName);
        ((VerticalLayout) layout).setComponentAlignment(textFieldName, Alignment.MIDDLE_LEFT);
        ((VerticalLayout) layout).addComponent(textFieldValue);
        ((VerticalLayout) layout).setComponentAlignment(textFieldValue, Alignment.MIDDLE_LEFT);
        ((VerticalLayout) layout).addComponent(extraVarsLayout);
        ((VerticalLayout) layout).setComponentAlignment(extraVarsLayout, Alignment.BOTTOM_LEFT);        
        ((VerticalLayout) layout).addComponent(confirmBtn);
        ((VerticalLayout) layout).setComponentAlignment(confirmBtn, Alignment.BOTTOM_CENTER);
        this.setContent(layout);
	}
	
	/**
	 * Get method for the globalVarId attribute.
	 * @return the value of the globalVarId attribute.
	 */
	public String getGlobalVarId(){
		return globalVarId;
	}
}
