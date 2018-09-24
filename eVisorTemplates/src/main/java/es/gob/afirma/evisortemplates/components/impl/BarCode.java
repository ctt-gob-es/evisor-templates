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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.BarCode.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.ImageTools;
import es.gob.afirma.evisortemplates.util.UtilsResources;

/** 
 * <p>Class that represents BarCode template's component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 13/03/2015.
 */
public class BarCode extends PositionAngleAbsImage {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8801312713763983189L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(BarCode.class);

	/**
	 * Attribute that represents message value for code bar generation. 
	 */
	private String message;
	
	/**
	 * Attribute that represents type value for code bar. 
	 */
	private String type;

	/**
	 * Attribute that represents String value for DPI constant. 
	 */
	private static final String DPI = "dpi";
	
	/**
	 * Attribute that represents String value for generatingBarCode constant. 
	 */
	private static final String GENERATIONBARCODE = "generatingBarCode";

	/**
	 * Constructor method for the class BarCode.java. 
	 */
	public BarCode() {

		super();

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isImage()
	 */
	@Override
	public final boolean isImage() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isText()
	 */
	@Override
	public final boolean isText() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isTable()
	 */
	@Override
	public final boolean isTable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isBarCode()
	 */
	@Override
	public final boolean isBarCode() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isDocument()
	 */
	@Override
	public final boolean isDocument() {
		return false;
	}

	/**
	 * Method that sets message value for barcode generation.
	 * @param textValue new message value for barcode generation
	 */
	public final void setMessage(String textValue) {
		this.message = textValue;

	}

	/**
	 * Method that generates a barcode.
	 * @param canvas canvas
	 * @param generator generator
	 */
	private void generateBarcode(BitmapCanvasProvider canvas, BarcodeGenerator generator) {
		if (this.message != null && !this.message.contains("¬")) {
			generator.generateBarcode(canvas, this.message);
		} else {
			if (generator instanceof EAN128Bean) {
				generator.generateBarcode(canvas, ConfigEVT.getInstance().getProperty("barcodeMsg2"));
			} else {
				generator.generateBarcode(canvas, ConfigEVT.getInstance().getProperty("barcodeMsg1"));
			}
		}

	}

	//CHECKSTYLE:OFF cyclomatic complex needed
	/**
	 * Method that sets a type for codebar regenerating the new barcode.
	 * @param typesValue new type for codebar
	 * @throws IncorrectMessageBarCodeException
	 */
	public final void setType(String typesValue) throws IncorrectMessageBarCodeException {
		//CHECKSTYLE:ON
		this.type = typesValue;

		String tmppathSession = UtilsResources.createSessionTempDir();

		File outputFile = new File(tmppathSession + hashCode() + ".png");
		OutputStream out = null;

		try {
			out = new FileOutputStream(outputFile);
			// Set up the canvas provider for monochrome PNG output
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI)), BufferedImage.TYPE_BYTE_BINARY, false, 0);

			// Generate the barcode
			if (typesValue.equals(ComboCBType.OPCODE39)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPCODE39);
				Code39Bean beanCode39 = new Code39Bean();
				generateBarcode(canvas, beanCode39);
				beanCode39.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			} else if (typesValue.equals(ComboCBType.OPCODE128)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPCODE128);
				Code128Bean beanCode128 = new Code128Bean();
				generateBarcode(canvas, beanCode128);
				beanCode128.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			} else if (typesValue.equals(ComboCBType.OPEAN128)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPEAN128);
				EAN128Bean beanEAN128 = new EAN128Bean();
				generateBarcode(canvas, beanEAN128);
				beanEAN128.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			} else if (typesValue.equals(ComboCBType.OPPDF417)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPPDF417);
				PDF417Bean beanPDF417 = new PDF417Bean();
				generateBarcode(canvas, beanPDF417);
				beanPDF417.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			} else if (typesValue.equals(ComboCBType.OPCODABAR)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPCODABAR);
				CodabarBean beanCodabar = new CodabarBean();
				generateBarcode(canvas, beanCodabar);
				beanCodabar.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			} else if (typesValue.equals(ComboCBType.OPDATAMATRIX)) {
				LOG.info(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE));
				LOG.debug(MessagesEVT.getInstance().getProperty(GENERATIONBARCODE)+ComboCBType.OPDATAMATRIX);
				DataMatrixBean beanDataMatrix = new DataMatrixBean();
				generateBarcode(canvas, beanDataMatrix);
				beanDataMatrix.setModuleWidth(UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))));
			}
			// Signal end of generation
			canvas.finish();

			out.close();

			setWidth(ImageTools.getImageWidth(outputFile), Unit.PIXELS);
			setHeight(ImageTools.getImageHeight(outputFile), Unit.PIXELS);
			setSource(new FileResource(outputFile));

		} catch (IllegalArgumentException e) {
			try {
				out.close();
			} catch (IOException e1) {
				LOG.error(e1);
			}
			LOG.error(e);
			throw new IncorrectMessageBarCodeException(MessagesEVT.getInstance().getProperty("barCodeError"), e);
		} catch (Exception e) {
			try {
				out.close();
			} catch (IOException e1) {
				LOG.error(e1);
			}
			LOG.error(e);
		} 

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCode(es.gob.afirma.evisortemplates.components.impl.CodeEditor)
	 */
	public final StringBuffer getCode(CodeEditor editorCodigo) {
		LOG.info(MessagesEVT.getInstance().getProperty("generatingBarCodeCode"));
		StringBuffer resultado = new StringBuffer();
		resultado.append(editorCodigo.addLine("<fo:instream-foreign-object>\n"));
		resultado.append(editorCodigo.addLine("<barcode:barcode xmlns:barcode=\"http://barcode4j.krysalis.org/ns\">\n"));

		if (this.message.contains("¬")) {

			resultado.append(editorCodigo.addLine("<xsl:attribute name=\"message\">\n"));
			resultado.append(editorCodigo.addLine("<xsl:value-of select=\""));
			if (this.message.contains("¬Parameter:")) {
				resultado.append(editorCodigo.addText(Format.BEFOREPARAMETER));

				resultado.append(editorCodigo.addEdition(Format.parameter(this.message), this, CodeEditorInput.VARIABLEBARCODE, false));

				resultado.append(editorCodigo.addText(Format.AFTERPARAMETER));
			} else {
				resultado.append(editorCodigo.addText(Format.formatVariable(this.message, this)));
			}
			resultado.append(editorCodigo.addText("\"/>\n") + editorCodigo.addLine("</xsl:attribute>\n"));
		} else {
			resultado.append(editorCodigo.addLine("<xsl:attribute name=\"message\">") + editorCodigo.addEdition(this.message, this, CodeEditorInput.MESSAGEBARCODE, false) + editorCodigo.addText("\n") + editorCodigo.addLine("</xsl:attribute>\n"));
		}

		ComboCBType combo2 = new ComboCBType(this.type, this, null);
		ComboCBType combo1 = new ComboCBType(this.type, this, combo2);
		resultado.append(editorCodigo.addLine("<barcode:") + editorCodigo.addComboCB(this.type, this, combo1) + editorCodigo.addText(">\n") + editorCodigo.addLine("</barcode:") + editorCodigo.addComboCB(this.type, this, combo2) + editorCodigo.addText(">\n"));
		resultado.append(editorCodigo.addLine("<module-width>" + UnitConv.in2mm(1d / Integer.parseInt(ConfigEVT.getInstance().getProperty(DPI))) + "</module-width>\n"));

		resultado.append(editorCodigo.addLine("</barcode:barcode>\n"));
		resultado.append(editorCodigo.addLine("</fo:instream-foreign-object>\n"));
		return resultado;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.impl.PositionAngleAbsImage#newInstance()
	 */
	public final BarCode newInstance() {
		BarCode aux = new BarCode();
		aux.setAngle(getAngle());
		aux.setConditionOp(getConditionOp());
		aux.setCondition1(getCondition1());
		aux.setCondition2(getCondition2());
		aux.setContainer(super.getContainer());
		aux.setHeight(getHeight(), getHeightUnits());
		aux.setPosX(getPosX());
		aux.setPosY(getPosY());
		aux.setPosXCenter(getPosXCenter());
		aux.setPosYCenter(getPosYCenter());
		aux.setSource(getSource());
		aux.setMessage(this.message);
		aux.setWidth(getWidth(), getWidthUnits());
		aux.setConditionForEach(getConditionForEach());
		aux.setIsForEach(isForEach());
		aux.setForEachType(getForEachType());
		try {
			aux.setType(type);
		} catch (IncorrectMessageBarCodeException e) {
			LOG.error(MessagesEVT.getInstance().getProperty("barCodeError2"),e);
			Notification notification = new Notification("Error", MessagesEVT.getInstance().getProperty("barCodeError2"));
			notification.show(Page.getCurrent());
		}
		return aux;
	}

	/**
	 * Method that gets message for barcode generation.
	 * @return message for barcode generation
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * Method that sets a type for codebar.
	 * @param value new  type for codebar
	 */
	public final void setTypeSinRecalcular(String value) {
		type = value;

	}

}
