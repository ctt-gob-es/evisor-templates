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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichImage.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.ImageTools;
import es.gob.afirma.evisortemplates.util.ImageToolsException;
import es.gob.afirma.evisortemplates.util.PixelToCm;
import es.gob.afirma.evisortemplates.util.UtilsBase64;
import es.gob.afirma.evisortemplates.util.UtilsResources;

/** 
 * <p>Class that represents template's images.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class RichImage extends PositionAngleAbsImage {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2836060389231796196L;

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(RichImage.class);

	/**
	 * Attribute that represents the image's file. 
	 */
	private File file;

	/**
	 * Attribute that represents the image's url. 
	 */
	private String url;

	/**
	 * Attribute that represents image's variable url. 
	 */
	private String variable;

	/**
	 * Attribute that represents the image's original width. 
	 */
	private float originalWidth;

	/**
	 * Attribute that represents the image's original height. 
	 */
	private float originalHeight;

	/**
	 * Attribute that represents bytes array of save bean. 
	 */
	private byte[ ] bytes;

	/**
	 * Attribute that represents mime type set. 
	 */
	private static final Set<String> MIMETYPESET;

	static {
		MIMETYPESET = new HashSet<String>();
		MIMETYPESET.add("image/png");
		MIMETYPESET.add("application/png");
		MIMETYPESET.add("application/x-png");
		MIMETYPESET.add("image/bitmap");
		MIMETYPESET.add("image/bmp");
		MIMETYPESET.add("image/x-bmp");
		MIMETYPESET.add("image/x-bitmap");
		MIMETYPESET.add("image/x-xbitmap");
		MIMETYPESET.add("image/x-win-bitmap");
		MIMETYPESET.add("image/x-windows-bmp");
		MIMETYPESET.add("image/ms-bmp");
		MIMETYPESET.add("image/x-ms-bmp");
		MIMETYPESET.add("application/x-win-bitmap");
		MIMETYPESET.add("application/bmp");
		MIMETYPESET.add("application/x-bmp");
		MIMETYPESET.add("image/jpeg");
		MIMETYPESET.add("image/jpg");
		MIMETYPESET.add("image/jp_");
		MIMETYPESET.add("application/jpg");
		MIMETYPESET.add("application/x-jpg");
		MIMETYPESET.add("image/pjpeg");
		MIMETYPESET.add("image/pipeg");
		MIMETYPESET.add("image/vnd.swiftview-jpeg");
	}

	/**
	 * Constructor method for the class RichImage.java. 
	 */
	public RichImage() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#isImage()
	 */
	@Override
	public final boolean isImage() {
		return true;
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
		return false;
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
	 * Method that gets the image's file.
	 * @return image's file
	 */
	public final File getFile() {
		return file;
	}

	/**
	 * Method that sets the image's file.
	 * @param fileParam image's file
	 * @return true if mime type is allowed, false otherwise.
	 * @throws IOException with file problems
	 */
	public final boolean setFile(File fileParam) throws IOException {
		// Si el fichero no existe se crea en la carpeta temporal de sesion a
		// partir del byte[] que se encuentra almacenado.
		File aux;
		if (!fileParam.exists()) {
			aux = new File(UtilsResources.createSessionTempDir() + fileParam.getName());
			FileOutputStream fos = new FileOutputStream(aux);
			try {
				if (bytes != null) {
					fos.write(bytes);
				}
			} catch (IOException e) {

			} finally {
				fos.close();
			}
		} else {
			aux = fileParam;
			bytes = IOUtils.toByteArray(new FileInputStream(aux));
		}

		if (aux != null) {
			FileResource fr = new FileResource(aux);
			String mime = UtilsResources.getMimeType(bytes);

			if (MIMETYPESET.contains(mime)) {
				this.file = aux;

				setSource(fr);
				try {
					setOriginalHeight(ImageTools.getImageHeight(aux));
					setOriginalWidth(ImageTools.getImageWidth(aux));
				} catch (ImageToolsException e) {
					LOG.error(e);
				}
				return true;
			} else {
				Notification.show(MessagesEVT.getInstance().getProperty("imageFormatError"), Type.ERROR_MESSAGE);
			}

		}
		return false;
	}

	/**
	 * Method that sets the image's url value.
	 * @param value image's url value
	 */
	public final void setUrl(String value) {

		this.url = value;

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.PositionAngle#getCode(es.gob.afirma.evisortemplates.components.impl.CodeEditor)
	 */
	public final StringBuffer getCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();

		if (getParent() instanceof RichTableCel) {
			result.append(codeEditor.addLine("<fo:external-graphic scaling=\"non-uniform\" content-width=\"") + codeEditor.addText(PixelToCm.pxToCm(getWidth()) + "") + codeEditor.addText("cm\"\n") + codeEditor.addLine(" content-height=\"") + codeEditor.addText(PixelToCm.pxToCm(getHeight()) + "") + codeEditor.addText("cm\" "));
		} else {
			result.append(codeEditor.addLine("<fo:external-graphic scaling=\"non-uniform\" content-width=\"") + codeEditor.addEdition(PixelToCm.pxToCm(getWidth()) + "", this, CodeEditorInput.WIDTH, false) + codeEditor.addText("cm\"\n") + codeEditor.addLine(" content-height=\"") + codeEditor.addEdition(PixelToCm.pxToCm(getHeight()) + "", this, CodeEditorInput.HEIGHT, false) + codeEditor.addText("cm\" "));
		}
		if (url != null) {
			result.append(codeEditor.addLine("src=\"") + codeEditor.addText(this.url) + codeEditor.addText("\""));
		} else if (file != null) {
			try {
				result.append(codeEditor.addLine("src=\"data:application/octet-stream;base64," + UtilsBase64.encodeBytes(IOUtils.toByteArray(new FileInputStream(file))) + "\""));
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
		} else if (variable != null) {
			if (this.variable.contains("¬Parameter:")) {

				result.append(codeEditor.addLine("><xsl:attribute name=\"src\"> <xsl:value-of select=\""));
				result.append(codeEditor.addText(Format.BEFOREPARAMETER));

				result.append(codeEditor.addEdition(Format.parameter(this.variable), this, CodeEditorInput.VARIABLE, false));

				result.append(codeEditor.addText(Format.AFTERPARAMETER));
				result.append(codeEditor.addText("\"/></xsl:attribute"));
			} else {

				result.append(codeEditor.addLine("><xsl:attribute name=\"src\"> <xsl:value-of select=\"" + Format.formatVariable(this.variable, this) + "\"/></xsl:attribute"));
			}
		}

		result.append(codeEditor.addText(">\n"));

		result.append(codeEditor.addLine("</fo:external-graphic>"));

		return result;
	}

	/**
	 * Method that gets the image's variable value.
	 * @return the value of the image's variable.
	 */
	public final String getVariable() {
		return variable;
	}
	
	/**
	 * Method that sets image's variable url.
	 * @param value image's variable url
	 */
	public final void setVariable(String value) {
		this.variable = value;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.evisortemplates.components.impl.PositionAngleAbsImage#newInstance()
	 */
	public final RichImage newInstance() {
		RichImage aux = new RichImage();
		aux.setAngle(getAngle());
		aux.setConditionOp(getConditionOp());
		// Si no se tiene file o bytes significa que la imagen contenida no
		// la ha incluido el usuario, con lo que se clona solo el source.
		if (file != null || bytes != null) {
			aux.bytes = bytes;
			try {
				aux.setFile(file);
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
		} else {
			aux.setSource(getSource());
		}
		aux.setCondition1(getCondition1());
		aux.setCondition2(getCondition2());
		aux.setContainer(super.getContainer());
		aux.setHeight(getHeight(), getHeightUnits());
		aux.setPosX(getPosX());
		aux.setPosY(getPosY());
		aux.setPosXXSLT(getPosXXSLT());
		aux.setPosYXSLT(getPosYXSLT());
		aux.setPosXCenter(getPosXCenter());
		aux.setPosYCenter(getPosYCenter());
		aux.setUrl(url);
		aux.setVariable(variable);
		aux.setWidth(getWidth(), getWidthUnits());
		aux.setOriginalHeight(getOriginalHeight());
		aux.setOriginalWidth(getOriginalWidth());
		aux.setConditionForEach(getConditionForEach());
		aux.setIsForEach(isForEach());
		aux.setForEachType(getForEachType());
		return aux;
	}

	/**
	 * Method that gets image's original width.
	 * @return image's original width
	 */
	public final float getOriginalWidth() {
		return originalWidth;
	}

	/**
	 * Method that gets image's original height.
	 * @return image's original height
	 */
	public final float getOriginalHeight() {
		return originalHeight;
	}

	/**
	 * Method that sets image's original width. 
	 * @param originalHeightParam image's original width
	 */
	public final void setOriginalHeight(float originalHeightParam) {
		this.originalHeight = originalHeightParam;
	}

	/**
	 * Method that sets image's original height.
	 * @param originalWidthParam image's original height
	 */
	public final void setOriginalWidth(float originalWidthParam) {
		this.originalWidth = originalWidthParam;
	}

}
