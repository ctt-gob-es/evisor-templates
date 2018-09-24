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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.ToolsException.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

/** 
 * <p>Class that contains information about an error that occurred in one of the utility classes.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 11/02/2011.
 */
public class ToolsException extends Exception {

	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = 1787741918765604568L;

	/**
	 * Attribute that represents the code associated to unknown error. 
	 */
	public static final int UNKNOWN_ERROR = -1;

	/**
	 * Attribute that represents the code associated to XML parser error. 
	 */
	public static final int XML_PARSER_ERROR = 1;

	/**
	 * Attribute that represents the code associated to an error while search by XPATH. 
	 */
	public static final int XPATH_ERROR = 2;

	/**
	 * Attribute that represents the code associated to a invalid signature. 
	 */
	public static final int INVALID_SIGNATURE = 3;

	/**
	 * Attribute that represents the code associated to an error while transform by XSLT. 
	 */
	public static final int XSL_TRANSFORM_ERROR = 4;

	/**
	 * Attribute that represents the error code associated to invalid number of page. 
	 */
	public static final int INVALID_PAGE_NUMBER = 5;

	/**
	 * Attribute that represents the file supplied is not valid PDF. 
	 */
	public static final int INVALID_PDF_FILE = 6;

	/**
	 * Attribute that represents the concatenation rule isn't valid. 
	 */
	public static final int INVALID_CONCATENATION_RULE = 7;

	/**
	 * Attribute that represents that an error occurs while the system access to a file. 
	 */
	public static final int ACCESS_FILE_ERROR = 8;

	/**
	 * Attribute that represents the document isn't a valid ODF. 
	 */
	public static final int INVALID_ODF_FILE = 9;

	/**
	 * Attribute that represents the operation is not allowed. 
	 */
	public static final int OPERATION_NOT_ALLOWED = 10;

	/**
	 * Attribute that represents the document is not a valid XSL-FO file. 
	 */
	public static final int INVALID_FO_FILE = 11;

	/**
	 * Attribute that represents the rotated angle is not allow. 
	 */
	public static final int INVALID_ROTATED_ANGLE = 12;

	/**
	 * Attribute that specifies the error type. 
	 */
	private int code = UNKNOWN_ERROR;

	/**
	 * Attribute that represents a description of error. 
	 */
	private String description = null;

	/**
	 * Gets the value of the attribute 'code'.
	 * @return the value of the attribute 'code'.
	 */
	public final int getCode() {
		return code;
	}

	/**
	 * Sets the value of the attribute 'code'.
	 * @param codeParam The value for the attribute 'code'.
	 */
	public final void setCode(int codeParam) {
		this.code = codeParam;
	}

	/**
	 * Gets the value of the attribute 'description'.
	 * @return the value of the attribute 'description'.
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the attribute 'description'.
	 * @param descriptionParam The value for the attribute 'description'.
	 */
	public final void setDescription(String descriptionParam) {
		this.description = descriptionParam;
	}

	/**
	 * Constructor method for the class TransformException.java.
	 * @param codeParam		Code of error.
	 * @param descriptionParam 	Description of error.
	 */
	public ToolsException(int codeParam, String descriptionParam) {
		super(descriptionParam);
		this.code = codeParam;
		this.description = descriptionParam;
	}

	/**
	 * Constructor method for the class TransformException.java.
	 * @param codeParam		Code of error.
	 * @param descriptionParam 	Description of error.
	 * @param causeParam			Error cause.
	 */
	public ToolsException(int codeParam, String descriptionParam, Throwable causeParam) {
		super(descriptionParam, causeParam);
		this.code = codeParam;
		this.description = descriptionParam;
	}
}
