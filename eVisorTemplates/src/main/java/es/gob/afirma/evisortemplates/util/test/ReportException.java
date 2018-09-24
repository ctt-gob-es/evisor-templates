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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.ReportException.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

/** 
 * <p>Class that contains information about an error that occurred into  generation report module.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 21/02/2011.
 */
public class ReportException extends Exception {

	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = 890044467933152751L;

	/**
	 * Attribute that represents the code associated to unknown error. 
	 */
	public static final int UNKNOWN_ERROR = -1;

	/**
	 * Attribute that represents the code that indicates that input parameters are invalid. 
	 */
	public static final int INVALID_INPUT_PARAMETERS = 1;

	/**
	 * Attribute that represents the code that indicates that the signature is invalid. 
	 */
	public static final int INVALID_SIGNATURE = 2;

	/**
	 * Attribute that represents that has exceeded the maximum number of pages allowed. 
	 */
	public static final int OVER_MAX_PAGE_NUMBER = 3;

	/**
	 * Attribute that represents the page number is invalid. 
	 */
	public static final int INVALID_PAGE_NUMBER = 5;

	/**
	 * Attribute that represents the document isn't a valid PDF. 
	 */
	public static final int INVALID_PDF_FILE = 6;

	/**
	 * Attribute that represents the template is invalid. 
	 */
	public static final int INVALID_TEMPLATE = 7;

	/**
	 * Attribute that represents the document isn't a valid ODF. 
	 */
	public static final int INVALID_ODF_FILE = 8;

	/**
	 * Attribute that identifies the error type occurred. 
	 */
	private int code = UNKNOWN_ERROR;

	/**
	 * Attribute that represents a error description. 
	 */
	private String description = null;

	/**
	 * Constructor method for the class ReportException.java.
	 * @param codeId	Identifier of error.
	 * @param message 	Error description.
	 */
	public ReportException(int codeId, String message) {
		super(message);
		this.code = codeId;
		this.description = message;
	}

	/**
	 * Constructor method for the class ReportException.java.
	 * @param codeId	Identifier of error.
	 * @param message 	Error description.
	 * @param cause		Error cause.
	 */
	public ReportException(int codeId, String message, Throwable cause) {
		super(message, cause);
		this.code = codeId;
		this.description = message;

	}

	/**
	 * Gets the value of the attribute that identifies the error type occurred.
	 * @return the value of the attribute that identifies the error type occurred.
	 */
	public final int getCode() {
		return code;
	}

	/**
	 * Sets the value of the attribute that identifies the error type occurred.
	 * @param codeId The value for the attribute that identifies the error type occurred.
	 */
	public final void setCode(int codeId) {
		this.code = codeId;
	}

	/**
	 * Gets the value of the error description.
	 * @return the value of the error description.
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the error description.
	 * @param message The value for the error description.
	 */
	public final void setDescription(String message) {
		this.description = message;
	}

}
