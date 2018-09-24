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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.TemplateData.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.util.Arrays;

/** 
 * <p>Class that contains the configuration information associated to a template stored into the system.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 24/02/2011.
 */
public class TemplateData {

	/**
	 * Attribute that represents the identifier of template. 
	 */
	private String identifier = null;
	/**
	 * Attribute that represents the type of report associated to template. 
	 */
	private int reportType = 0;

	/**
	 * Attribute that represents if the report should be generated although the signature is invalid. 
	 */
	private boolean forceGeneration = false;

	/**
	 * Attribute that represents mode of document include. 
	 */
	private int modeDocInclude = 0;

	/**
	 * Attribute that represents the rule that  document and report will be concatenated. 
	 * <br/>Examples:
	 * <br/>REP+DOC: report + document.
	 * <br/>REP(1) + DOC(2-4) + DOC(6) + REP(2-4). Concatenate the pages selected
	 */
	private String concatenationRule = null;

	/**
	 * Attribute that is used to indicate the range of the document page to draw. If this is null all  document pages  will be to extract.
	 * A example: 1-4,4,10, 56-60 
	 */
	private String pagesRange = null;

	/**
	 * Attribute that indicates if the signature is included into the report as attachment. 
	 */
	private boolean attachSignature = false;

	/**
	 * Attribute that indicates if the signed document is included into the report as attachment. 
	 */
	private boolean attachDocument = false;

	/**
	 * Attribute that indicates if the signer certificate is included into the report as attachment.
	 */
	private boolean attachCertificate = false;

	/**
	 *  Attribute that indicates if the XML response is included into the report as attachment. 
	 */
	private boolean attchResponse = false;

	/**
	 * Attribute that indicates if the document included into the signature will be included into the report as attachment.
	 */
	private boolean attchDocInSignature = false;

	/**
	 * Attribute that represents the template file to create report. 
	 */
	private byte[ ] template = null;

	/**
	 * Gets the value of the type of report associated to template.
	 * @return the value of the type of report associated to template.
	 */
	public final int getReportType() {
		return reportType;
	}

	/**
	 * Sets the value of the type of report associated to template.
	 * @param type The value for the type of report associated to template.
	 */
	public final void setReportType(int type) {
		this.reportType = type;
	}

	/**
	 * Gets the value of the identifier of template.
	 * @return the value of the identifier of template.
	 */
	public final String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the value of the identifier of template.
	 * @param reportId The value for the identifier of template.
	 */
	public final void setIdentifier(String reportId) {
		this.identifier = reportId;
	}

	/**
	 * Constructor method for the class TemplateData.java.
	 * @param templateId	Identifier of template.
	 * @param templateType 	Type of report.
	 */
	public TemplateData(String templateId, int templateType) {
		super();
		this.identifier = templateId;
		this.reportType = templateType;
	}

	/**
	 * Gets if the report should be generated although the signature is invalid. 
	 * @return true report should be generated.
	 */
	public final boolean isForceGeneration() {
		return forceGeneration;
	}

	/**
	 * Sets if the report should be generated although the signature is invalid.
	 * @param force 	True, if you force the creation of the report.
	 */
	public final void setForceGeneration(boolean force) {
		this.forceGeneration = force;
	}

	/**
	 * Gets the value of the template file to create report.
	 * @return The template file to create report.
	 */
	public final byte[ ] getTemplate() {
		return template;
	}

	/**
	 * Sets the value of the template file to create report.
	 * @param xslTemplate The template file to create report.
	 */
	public final void setTemplate(byte[ ] xslTemplate) {
		if (xslTemplate != null) {
			this.template = Arrays.copyOf(xslTemplate, xslTemplate.length);
		} else {
			this.template = null;
		}

	}

	/**
	 * Method that gets document include mode.
	 * @return document include mode
	 */
	public final int getModeDocInclude() {
		return modeDocInclude;
	}

	/**
	 * Method that sets document include mode.
	 * @param includeMode new document include mode
	 */
	public final void setModeDocInclude(int includeMode) {
		this.modeDocInclude = includeMode;
	}

	/**
	 * Gets the rule that  document and report will be concatenated. 
	 * @return String that represents the rule that  document and report will be concatenated. 
	 * <br/>Examples:
	 * <br/>REP+DOC: report + document.
	 * <br/>REP(1) + DOC(2-4) + DOC(6) + REP(2-4). Concatenate the pages selected
	 */
	public final String getConcatenationRule() {
		return concatenationRule;
	}

	/**
	 * Sets the rule that  document and report will be concatenated. 
	 * @param concatRule Rule that  document and report will be concatenated. 
	 * <br/>Examples:
	 * <br/>REP+DOC: report + document.
	 * <br/>REP(1) + DOC(2-4) + DOC(6) + REP(2-4). Concatenate the pages selected
	 */
	public final void setConcatenationRule(String concatRule) {
		this.concatenationRule = concatRule;
	}

	/**
	 * Returns the range of the document page to draw.
	 * @return	String that represents the range of the document page to draw. If this is null all  document pages  will be to extract.
	 * A example: 1-4,4,10, 56-60 
	 */
	public final String getPagesRange() {
		return pagesRange;
	}

	/**
	 * Sets the range of the document page to draw.
	 * @param range	String that represents the range of the document page to draw. If this is null all  document pages  will be to extract.
	 * A example: 1-4,4,10, 56-60
	 */
	public final void setPagesRange(String range) {
		this.pagesRange = range;
	}

	/**
	 * Indicates if the signature is included into the report as attachment.
	 * @return	True if the signature is included into the report as attachment. Otherwise false.
	 */
	public final boolean isAttachSignature() {
		return attachSignature;
	}

	/**
	 * Sets if the signature is included into the report as attachment.
	 * @param isAttachSignature	True if the signature is included into the report as attachment. Otherwise false.
	 */
	public final void setAttachSignature(boolean isAttachSignature) {
		this.attachSignature = isAttachSignature;
	}

	/**
	 * Gets if the signed document is included into the report as attachment.
	 * @return	True if the signed document is included into the report as attachment. Otherwise false.
	 */
	public final boolean isAttachDocument() {
		return attachDocument;
	}

	/**
	 * Gets if the signed document is included into the report as attachment.
	 * @param isAttachDocument	True if the signed document is included into the report as attachment. Otherwise false.
	 */
	public final void setAttachDocument(boolean isAttachDocument) {
		this.attachDocument = isAttachDocument;
	}

	/**
	 * Indicates if the signer certificate is included into the report as attachment.
	 * @return True if the signer certificate is included into the report as attachment. Otherwise false.
	 */
	public final boolean isAttachCertificate() {
		return attachCertificate;
	}

	/**
	 * Sets if the signer certificate is included into the report as attachment.
	 * @param isAttachCertificate	True if the signer certificate is included into the report as attachment. Otherwise false.
	 */
	public final void setAttachCertificate(boolean isAttachCertificate) {
		this.attachCertificate = isAttachCertificate;
	}

	/**
	 * Indicates if the XML response is included into the report as attachment.
	 * @return	True if the XML response is included into the report as attachment. Otherwise false.
	 */
	public final boolean isAttchResponse() {
		return attchResponse;
	}

	/**
	 * Set if the XML response is included into the report as attachment.
	 * @param isAttchResponse	True if the XML response is included into the report as attachment. Otherwise false.
	 */
	public final void setAttchResponse(boolean isAttchResponse) {
		this.attchResponse = isAttchResponse;
	}

	/**
	 * Gets if the document included into the signature will be included into the report as attachment.
	 * @return True if the document included into the signature will be included into the report as attachment, otherwise false.
	 */
	public final boolean isAttchDocInSignature() {
		return attchDocInSignature;
	}

	/**
	 * Sets if the document included into the signature will be included into the report as attachment.
	 * @param attchDocInSignatureParam True if the document included into the signature will be included into the report as attachment, otherwise false.
	 */
	public final void setAttchDocInSignature(boolean attchDocInSignatureParam) {
		this.attchDocInSignature = attchDocInSignatureParam;
	}

}
