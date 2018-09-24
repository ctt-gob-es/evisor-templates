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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.FOUtils.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

import es.gob.afirma.evisortemplates.util.UtilsResources;

/** 
 * <p>Utility class for processing XSL-FO files.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 09/03/2011.
 */
public final class FOUtils {

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(FOUtils.class);

	/**
	 * Constructor method for the class FOUtils.java. 
	 */
	private FOUtils() {
	}

	/**
	 * Creates a PDF file from a XSL-FO document.
	 * @param foFile	XSL-FO document.
	 * @return	PDF File.
	 * @throws ToolsException If an error occurs.
	 */
	public static byte[ ] fo2pdf(byte[ ] foFile) throws ToolsException {
		FopFactory fopFactory = FopFactory.newInstance();
		FOUserAgent fopUserAgent = fopFactory.newFOUserAgent();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		byte[ ] pdf = null;
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopUserAgent, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			in = new ByteArrayInputStream(foFile);
			Source src = new StreamSource(in);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);
			pdf = out.toByteArray();
			if (pdf != null) {
				FormattingResults foResults = fop.getResults();
				LOG.debug(new Object[ ] { String.valueOf(foResults.getPageCount()) });
			}
			return pdf;
		} catch (TransformerException e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.INVALID_FO_FILE, "", e);
		} catch (Exception e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.UNKNOWN_ERROR, "", e);
		} finally {
			UtilsResources.safeCloseInputStream(in);
			UtilsResources.safeCloseOutputStream(out);
		}
	}
}
