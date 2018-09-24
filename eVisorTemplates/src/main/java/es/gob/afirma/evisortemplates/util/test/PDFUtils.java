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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.PDFUtils.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.tools.UnitConv;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.gob.afirma.evisortemplates.util.UtilsResources;

/**
 * <p>Class that contains tools to manage of PDF files.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 04/03/2011.
 */
public final class PDFUtils {

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(PDFUtils.class);
	
	/**
	 * Attribute that represents the default resolution (dpi) used to create PDF file.
	 */
	private static final int RESOLUTION_DEFAULT = 72;

	/**
	 * Attribute that represents the 90 number. 
	 */
	private static final float XC = 90;

	/**
	 * Attribute that represents the 270 number. 
	 */
	private static final float CCLXX = 270;

	/**
	 * Attribute that represents the 180 number. 
	 */
	private static final float CLXXX = 180;

	/**
	 * Constructor method for the class PDFUtils.java.
	 */
	private PDFUtils() {
	}

	/**
	 * Method that creates a PDF file by including a document in another with the specified rules.
	 * @param targetPdf	Target document.
	 * @param originPdf	Original document.
	 * @param pagesIncl	Rules.
	 * @return	A PDF document.
	 * @throws ToolsException	If an error occurs.
	 */
	//CHECKSTYLE:OFF cyclomatic complex needed
	public static byte[ ] includePages(byte[ ] targetPdf, byte[ ] originPdf, MatrixPagesInclude pagesIncl) throws ToolsException {
		//CHECKSTYLE:ON
		byte[ ] pdf = null;
		ByteArrayOutputStream out = null;
		try {
			PdfReader readerTarget = new PdfReader(targetPdf);
			PdfReader readerOrigin = new PdfReader(originPdf);
			com.lowagie.text.Rectangle psize = readerTarget.getPageSize(1);
			Document document = new Document(new com.lowagie.text.Rectangle(psize.getWidth(), psize.getHeight()));
			out = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			PdfContentByte directContent = writer.getDirectContent();
			PdfContentByte under = writer.getDirectContentUnder();
			for (int i = 1; i <= readerTarget.getNumberOfPages(); i++) {
				document.newPage();
				PdfImportedPage pageTarget = writer.getImportedPage(readerTarget, i);

				directContent.addTemplate(pageTarget, 0, 0);
				int[ ] originPages = pagesIncl.getPageToInclude(i);
				if (originPages != null) {
					for (int j = 0; j < originPages.length; j++) {
						PageIncludeFormat[ ] format = pagesIncl.getPagesFormat(i, originPages[j]);
						PdfImportedPage pageOrigen = writer.getImportedPage(readerOrigin, (originPages[j]));
						float rotate = 0;
						if (readerOrigin.getPageN((originPages[j])).getAsNumber(PdfName.ROTATE) != null) {
							rotate = readerOrigin.getPageN((originPages[j])).getAsNumber(PdfName.ROTATE).floatValue();
						}
						for (int k = 0; k < format.length; k++) {
							float a = 0;
							float b = 0;
							float c = 0;
							float d = 0;
							float e = 0;
							float f = 0;
							float width = 0;
							if (format[k].getWidth() > 0) {
								width = UnitConv.mm2px(format[k].getWidth(), RESOLUTION_DEFAULT);
							}
							float height = 0;
							if (format[k].getHeight() > 0) {
								height = UnitConv.mm2px(format[k].getHeight(), RESOLUTION_DEFAULT);
							}
							float xpos = UnitConv.mm2px(format[k].getXpos(), RESOLUTION_DEFAULT);
							float ypos = UnitConv.mm2px(format[k].getYpos(), RESOLUTION_DEFAULT);
							float xFactor = 1;
							if (width > 0) {
								xFactor = width / pageOrigen.getWidth();
							}

							float yFactor = 1;
							if (height > 0) {
								yFactor = height / pageOrigen.getHeight();
							}
							if (rotate == 0) {
								a = xFactor;
								b = 0;
								c = 0;
								d = yFactor;
								e = xpos;
								f = ypos;
							} else if (rotate == XC) {
								xFactor = width / pageOrigen.getHeight();
								yFactor = height / pageOrigen.getWidth();
								a = 0;
								b = -yFactor;
								c = xFactor;
								d = 0;
								e = xpos;
								f = ypos + height;
							} else if (rotate == CCLXX) {
								xFactor = width / pageOrigen.getHeight();
								yFactor = height / pageOrigen.getWidth();
								a = 0;
								b = yFactor;
								c = -xFactor;
								d = 0;
								e = xpos + width;
								f = ypos;
							} else {
								float angle = (float) (-rotate * (Math.PI / CLXXX));
								float rotWidth = (float) ((pageOrigen.getHeight() * Math.sin(angle)) + (pageOrigen.getWidth() * Math.cos(angle)));
								xFactor = (float) (width / rotWidth);
								float rotHeight = (float) ((pageOrigen.getWidth() * Math.sin(angle)) + (pageOrigen.getHeight() * Math.cos(angle)));
								yFactor = height / rotHeight;
								a = (float) (xFactor * Math.cos(angle));
								b = (float) (yFactor * Math.sin(angle));
								c = (float) (xFactor * -Math.sin(angle));
								d = (float) (yFactor * Math.cos(angle));
								e = (float) (xpos + (width * Math.sin(angle)));
								f = (float) (ypos + (height * Math.cos(angle)));
							}
							if (format[k].getLayout().equals(PageIncludeFormat.BACK_LAYOUT)) {
								under.addTemplate(pageOrigen, a, b, c, d, e, f);
							} else {
								directContent.addTemplate(pageOrigen, a, b, c, d, e, f);
							}

						}
					}
				}
			}
			document.close();
			readerOrigin.close();
			readerTarget.close();
			pdf = out.toByteArray();
		} catch (Exception e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.UNKNOWN_ERROR, "", e);
		} finally {
			UtilsResources.safeCloseOutputStream(out);
		}
		return pdf;

	}

}
