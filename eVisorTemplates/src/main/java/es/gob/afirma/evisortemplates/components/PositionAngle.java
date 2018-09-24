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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.PositionAngle.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components;

import com.vaadin.ui.Component;

import es.gob.afirma.evisortemplates.components.impl.CodeEditor;
import es.gob.afirma.evisortemplates.components.impl.ResizeDDContainer;

/** 
 * <p>Interface representing interface methods implemented by objects PositionAngle.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 12/03/2015.
 */
public interface PositionAngle extends Component {

	/**
	 * Method to check whether it is an image object.
	 * @return true if is image object. false otherwise.
	 */
	boolean isImage();

	/**
	 * Method to check whether it is an text object.
	 * @return true if is text object. false otherwise.
	 */
	boolean isText();

	/**
	 * Method to check whether it is an table object.
	 * @return true if is table object. false otherwise.
	 */
	boolean isTable();

	/**
	 * Method to check whether it is an BarCode object.
	 * @return true if is BarCode object. false otherwise.
	 */
	boolean isBarCode();

	/**
	 * Method to check whether it is an document object.
	 * @return true if is document object. false otherwise.
	 */
	boolean isDocument();

	/**
	 * Method to get the component's position x.
	 * @return component's position x in pixels
	 */
	float getPosX();

	/**
	 * Method to set the component's position x.
	 * @param posX that represents component's position x in pixels
	 */
	void setPosX(float posX);

	/**
	 * Method to get the component's position y.
	 * @return component's position y in pixels
	 */
	float getPosY();

	/**
	 * Method to set the component's position y.
	 * @param posY that represents component's position y in pixels
	 */
	void setPosY(float posY);

	/**
	 * Method to get the component's position x relative to XSLT template.
	 * @return component's position x relative to XSLT template in pixels
	 */
	float getPosXXSLT();

	/**
	 * Method to set the component's position x relative to XSLT template.
	 * @param posX that represents component's position x relative to XSLT template in pixels
	 */
	void setPosXXSLT(float posX);

	/**
	 * Method to get the component's position y relative to XSLT template.
	 * @return component's position y relative to XSLT template in pixels
	 */
	float getPosYXSLT();

	/**
	 * Method to set the component's position y relative to XSLT template.
	 * @param posY that represents component's position y relative to XSLT template in pixels
	 */
	void setPosYXSLT(float posY);

	/**
	 * Method to get the int value of component's width.
	 * @return int that represents the component's width
	 */
	int getIntWidth();

	/**
	 * Method to get the int value of component's height.
	 * @return int that represents the component's height
	 */
	int getIntHeight();

	/**
	 * Method to get component's rotation angle value.
	 * @return component's rotation angle value
	 */
	int getAngle();

	/**
	 *  Method to set component's rotation angle value.
	 * @param angle that represents new component's rotation angle value
	 */
	void setAngle(int angle);

	/**
	 * Method to get visibility condition's part 1.
	 * @return String that represents the visibility condition's part 1
	 */
	String getCondition1();

	/**
	 * Method to get visibility condition's part 2.
	 * @return String that represents the visibility condition's part 2
	 */
	String getCondition2();

	/**
	 * Method to get visibility condition's operator.
	 * @return String that represents the visibility condition's operator
	 */
	String getConditionOp();

	/**
	 * Method to set visibility condition's part 1.
	 * @param condition String value of new visibility condition's part 1
	 */
	void setCondition1(String condition);

	/**
	 * Method to set visibility condition's part 2.
	 * @param condition String value of new visibility condition's part 1
	 */
	void setCondition2(String condition);

	/**
	 * Method to set visibility condition's operator.
	 * @param condition String value of new visibility condition's operator
	 */
	void setConditionOp(String condition);

	/**
	 * Method to get component's container for edit mode.
	 * @return component's container for edit mode.
	 */
	ResizeDDContainer getContainer();

	/**
	 * ethod to set component's container for edit mode.
	 * @param container new component's container for edit mode
	 */
	void setContainer(ResizeDDContainer container);

	/**
	 * Method to get the component's position x of center point.
	 * @return component's position x of center point in pixels
	 */
	float getPosXCenter();

	/**
	 * Method to get the component's position y of center point.
	 * @return component's position y of center point in pixels
	 */
	float getPosYCenter();

	/**
	 * Method to calculate component's central point when needed. 
	 * The method set central position x and central position y  
	 */
	void calcCentralPoint();

	/**
	 * Method that obtains code XSLT representation for component.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component
	 */
	StringBuffer getCode(CodeEditor codeEditor);

	/**
	 * Method that obtains the visibility condition for component in XSLT format.
	 * @return visibility condition for component in XSLT format
	 */
	String mountCondition();

	/**
	 * Method that creates a new instance of component.
	 * @return new instance of component
	 */
	PositionAngle newInstance();

	/**
	 * Method to set if the component is a "for each" component.
	 * @param forEach new value indicating if component is a "for each" component
	 */
	void setIsForEach(boolean forEach);

	/**
	 * Method to check if the component is a "for each" component.
	 * @return true if the component is a "for each" component. false otherwise
	 */
	boolean isForEach();

	/**
	 * Method to set "for each" condition for component.
	 * @param condition new "for each" condition for component
	 */
	void setConditionForEach(String condition);

	/**
	 *  Method to get "for each" condition for component.
	 * @return String that represents "for each" condition for component
	 */
	String getConditionForEach();

	/**
	 * Method to set "for each" type condition for component.
	 * @param value new "for each" type condition for component
	 */
	void setForEachType(String value);

	/**
	 * Method to get "for each" type condition for component.
	 * @return String that represents "for each" type condition for component
	 */
	String getForEachType();
}
