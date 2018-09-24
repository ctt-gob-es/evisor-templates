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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.PDFReportManager.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/** 
 * <p>Class for managing PDF reports.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 24/02/2011.
 */
public class PDFReportManager {
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(PDFReportManager.class);

	/**
	 * Attribute that represents the namespaces used in the XML message. 
	 */
	protected static final String NS = "urn:es:gob:signaturereport:generation:inputparameters";

	/**
	 * Attribute that represents the local name that identifies 'IncludePage' element. 
	 */
	protected static final String INCLUDEPAGE = "IncludePage";

	/**
	 * Attribute that represents the local name that identifies 'Ypos' attribute. 
	 */
	protected static final String YPOS = "Ypos";

	/**
	 * Attribute that represents the local name that identifies ' Xpos' attribute. 
	 */
	protected static final String XPOS = "Xpos";

	/**
	 * Attribute that represents the local name that identifies 'Width' attribute. 
	 */
	protected static final String WIDTH = "Width";

	/**
	 * Attribute that represents the local name that identifies 'Height' attribute. 
	 */
	protected static final String HEIGHT = "Height";

	/**
	 * Attribute that represents the local name that identifies 'Layout' attribute. 
	 */
	protected static final String LAYOUT = "Layout";

	/**
	 * Attribute that represents the local name that identifies 'DocumentPage' element. 
	 */
	protected static final String DOCUMENTPAGE = "DocumentPage";

	/**
	 * Attribute that represents the local name that identifies 'ReportPage' element. 
	 */
	protected static final String REPORTPAGE = "ReportPage";

	/**
	 * Attribute that represents xml input file. 
	 */
	private byte[ ] xmlInput;

	/**
	 * FO file may include various  "IncludePage" components in the following format:<br/>
	 // CHECKSTYLE:OFF -- The following describes a IncludePage element.
	 * "<IncludePage Ypos="String" Width="String" Height="String" Xpos="String" Layout="front/back" xmlns="urn:es:gob:signaturereport:generation:inputparameters">"<br/>
	 *  	"<DocumentPage>X</DocumentPage>"<br/>
	 *  	"<ReportPage>Y</ReportPage>"<br/>
	 * "</IncludePage>"<br/>
	 // CHECKSTYLE:ON
	 * In this component  is reported that the X page of signed document is included in the Y page of signature report.
	 * The position of the page is specified with the attributes Ypos, Xpos, Width and Height.
	 * @param doc Fo file.
	 * @return	A {@link MatrixPagesInclude} that contains information about the pages to include in a signature report
	 * @throws ReportException	If an error occurs.
	 */
	//CHECKSTYLE:OFF cyclomatic complex needed
	private MatrixPagesInclude getIncludePages(Document doc) throws ReportException {
		//CHECKSTYLE:ON
		MatrixPagesInclude matrix = new MatrixPagesInclude();
		NodeList list = doc.getElementsByTagNameNS(NS, INCLUDEPAGE);
		while (list != null && list.getLength() > 0) {
			Node ipNode = list.item(0);
			String docPag = null;
			String rptPag = null;
			NodeList childPage = ipNode.getChildNodes();
			for (int i = 0; i < childPage.getLength(); i++) {
				if (childPage.item(i).getNodeType() == Node.ELEMENT_NODE && childPage.item(i).getLocalName().equals(DOCUMENTPAGE) && childPage.item(i).getFirstChild() != null) {
					docPag = childPage.item(i).getFirstChild().getNodeValue();
				}
				if (childPage.item(i).getNodeType() == Node.ELEMENT_NODE && childPage.item(i).getLocalName().equals(REPORTPAGE) && childPage.item(i).getFirstChild() != null) {
					rptPag = childPage.item(i).getFirstChild().getNodeValue();
				}
			}
			try {
				PageIncludeFormat format = new PageIncludeFormat();

				if (ipNode.getAttributes().getNamedItem(XPOS) != null && ipNode.getAttributes().getNamedItem(XPOS).getNodeValue() != null) {
					format.setXpos(Double.parseDouble(ipNode.getAttributes().getNamedItem(XPOS).getNodeValue()));
				}

				if (ipNode.getAttributes().getNamedItem(YPOS) != null && ipNode.getAttributes().getNamedItem(YPOS).getNodeValue() != null) {
					format.setYpos(Double.parseDouble(ipNode.getAttributes().getNamedItem(YPOS).getNodeValue()));
				}

				if (ipNode.getAttributes().getNamedItem(WIDTH) != null && ipNode.getAttributes().getNamedItem(WIDTH).getNodeValue() != null) {
					format.setWidth(Double.parseDouble(ipNode.getAttributes().getNamedItem(WIDTH).getNodeValue()));
				}

				if (ipNode.getAttributes().getNamedItem(HEIGHT) != null && ipNode.getAttributes().getNamedItem(HEIGHT).getNodeValue() != null) {
					format.setHeight(Double.parseDouble(ipNode.getAttributes().getNamedItem(HEIGHT).getNodeValue()));
				}

				if (ipNode.getAttributes().getNamedItem(LAYOUT) != null && ipNode.getAttributes().getNamedItem(LAYOUT).getNodeValue() != null) {
					format.setLayout(ipNode.getAttributes().getNamedItem(LAYOUT).getNodeValue());
				}

				matrix.addPage(Integer.parseInt(rptPag), Integer.parseInt(docPag), format);
				Node parent = ipNode.getParentNode();
				parent.removeChild(ipNode);
			} catch (NumberFormatException nfe) {
				throw new ReportException(ReportException.INVALID_TEMPLATE, "", nfe);
			}

		}
		return matrix;
	}

	/**
	 * Method that returns report.
	 * @param templateConf template configuration object
	 * @param document signed document.
	 * @return byte[] report
	 * @throws ReportException if any problem generating report
	 */
	public final byte[ ] createReport(TemplateData templateConf, byte[ ] document) throws ReportException {
		// 1.Aplicamos la transformación XSLT.
		byte[ ] foFile = null;
		try {
			foFile = XMLUtils.xslTransform(xmlInput, templateConf.getTemplate());
		} catch (ToolsException e) {
			LOG.error(e);
			throw new ReportException(ReportException.INVALID_TEMPLATE, "", e);
		}

		if (foFile == null) {
			throw new ReportException(ReportException.UNKNOWN_ERROR, "");
		}
		// 2.Analizamos si se incrusta el documento original
		MatrixPagesInclude pagesIncl = null;

		try {
			Document doc = XMLUtils.getDocument(foFile);
			pagesIncl = getIncludePages(doc);
			foFile = XMLUtils.getXMLBytes(doc);
		} catch (ToolsException e) {
			LOG.error(e);
			throw new ReportException(ReportException.UNKNOWN_ERROR, "", e);
		}

		// 3. Realizamos el procesado XSL-FO
		byte[ ] pdf = null;
		try {
			pdf = FOUtils.fo2pdf(foFile);
		} catch (ToolsException e) {
			LOG.error(e);
			if (e.getCode() == ToolsException.INVALID_FO_FILE) {
				throw new ReportException(ReportException.INVALID_TEMPLATE, e.getDescription(), e);
			} else {
				throw new ReportException(ReportException.UNKNOWN_ERROR, "", e);
			}

		}
		// 4. Se realiza la incrustación del documento original en el informe de
		// firma
		if (pagesIncl != null && !pagesIncl.isEmpty() && document != null) {
			try {
				pdf = PDFUtils.includePages(pdf, document, pagesIncl);
			} catch (ToolsException e) {
				LOG.error(e);
				throw new ReportException(ReportException.UNKNOWN_ERROR, "", e);
			}
		}

		return pdf;
	}
	
	
	/**
	 * Sets xml input file in byte[] format.
	 * @param xmlInputParam new xml input file in byte[] format
	 */
	public final void setXmlInput(byte[ ] xmlInputParam) {
		this.xmlInput = Arrays.copyOf(xmlInputParam, xmlInputParam.length); 
	}
	
	

}
