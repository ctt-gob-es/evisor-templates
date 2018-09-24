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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.util.ImageTools.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class ImageTools contains general purpose methods for image manipulation.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public final class ImageTools {

	/**
	 * Attribute that represents buffered scaled images. 
	 */
	private static Map<String, File> scaledImages = new HashMap<String, File>();
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(ImageTools.class);

	/**
	 * Constructor method for the class ImageTools.java. 
	 */
	private ImageTools() {
		super();
	}

	/**
	 * Method that resizes a image from file.
	 * @param imageFile image file
	 * @param maxWidth new width for image
	 * @return file with image new size
	 * @throws ImageToolsException if unable to resize image
	 * @throws FileNotFoundException if file to be resized not found
	 */
	public static File resizeImage(File imageFile, int maxWidth) throws ImageToolsException, FileNotFoundException {

		if (!imageFile.exists()) {
			throw new FileNotFoundException(MessagesEVT.getInstance().getProperty("fileNotFound") + " " + imageFile.getAbsolutePath());
		}

		if (cacheContainsFile(imageFile.getAbsolutePath())) {
			return scaledImages.get(imageFile.getAbsolutePath());
		}

		try {
			int width = getImageWidth(imageFile);
			int height = getImageHeight(imageFile);

			height = height * maxWidth / width;
			width = maxWidth;

			// Read image from file
			BufferedImage sourceImage = ImageIO.read(imageFile);

			BufferedImage scaledImage = scaleBufferedImage(sourceImage, maxWidth, height);
			File out = writeScaledImageToDisk(scaledImage);

			scaledImages.put(imageFile.getAbsolutePath(), out);
			return out;
		} catch (IOException e) {
			throw new ImageToolsException("Unable to resize image " + e.getMessage(), e);
		}
	}

	/**
	 * Method that checks if a image is in cache.
	 * @param identifier id of the image
	 * @return true if the image is in cache, false otherwise
	 */
	private static boolean cacheContainsFile(String identifier) {
		if (scaledImages.containsKey(identifier)) {
			return scaledImages.get(identifier).exists();
		}

		return false;
	}

	/**
	 * Method that write image file to disk.
	 * @param scaledImage image to be stored in disk
	 * @return file stored in disk
	 * @throws IOException if any io problem
	 */
	private static File writeScaledImageToDisk(BufferedImage scaledImage) throws IOException {

		FileOutputStream output = null;

		try {
			// Se crea el directorio.
			String tmppathSession = UtilsResources.createSessionTempDir();
			File targetFile = new File(tmppathSession + Math.random());

			output = new FileOutputStream(targetFile);

			ImageIO.write(scaledImage, "jpg", output);
			return targetFile;
		} catch (IOException e) {
			LOG.error(e);
			throw e;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception ignored) {
					LOG.error(ignored);
				}
			}
		}
	}

	/**
	 * Method that scales a bufered image.
	 * @param sourceImage image to be scaled
	 * @param maxWidth new width
	 * @param maxHeight new height
	 * @return scaled buffered image
	 */
	private static BufferedImage scaleBufferedImage(BufferedImage sourceImage, int maxWidth, int maxHeight) {
		// Calculate scaled image's dimensions
		float calculatedWidth = 0;
		float calculatedHeight = 0;

		float aspectRatio = (float) sourceImage.getWidth() / (float) sourceImage.getHeight();

		if (aspectRatio > 1) {
			calculatedWidth = maxWidth;
			calculatedHeight = maxWidth / aspectRatio;

			if (calculatedHeight > maxHeight) {
				calculatedWidth = maxHeight * aspectRatio;
				calculatedHeight = calculatedWidth / aspectRatio;
			}
		} else {
			calculatedWidth = maxHeight * aspectRatio;
			calculatedHeight = maxHeight;

			if (calculatedWidth > maxWidth) {
				calculatedHeight = maxWidth / aspectRatio;
				calculatedWidth = calculatedHeight * aspectRatio;
			}
		}

		// Create empty image with new dimensions
		BufferedImage scaledImage = new BufferedImage((int) calculatedWidth, (int) calculatedHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = (Graphics2D) scaledImage.getGraphics();
		graphics.setComposite(AlphaComposite.Src);

		// Draw to scaled image
		graphics.drawImage(sourceImage, 0, 0, (int) calculatedWidth, (int) calculatedHeight, null);

		graphics.dispose();

		return scaledImage;
	}

	/**
	 * Method that returns image width value in pixels.
	 * @param imageFile file with image
	 * @return width of image in pixels from given file
	 * @throws ImageToolsException if any problem checking width value
	 */

	public static int getImageWidth(File imageFile) throws ImageToolsException {
		try {
			BufferedImage sourceImage = ImageIO.read(imageFile);
			return sourceImage.getWidth();
		} catch (Exception e) {
			LOG.error(e);
			throw new ImageToolsException("Error retrieving image's width", e);
		}
	}

	/**
	 * Method that returns image height value in pixels.
	 * @param imageFile file height image
	 * @return height of image in pixels from given file
	 * @throws ImageToolsException if any problem checking width value
	 */

	public static int getImageHeight(File imageFile) throws ImageToolsException {
		try {
			BufferedImage sourceImage = ImageIO.read(imageFile);
			return sourceImage.getHeight();
		} catch (Exception e) {
			LOG.error(e);
			throw new ImageToolsException("Error retrieving image's height", e);
		}
	}

	/**
	 * Method that clear image's cache.
	 */
	public static void clearCache() {
		scaledImages.clear();
	}

	/**
	 * Method that gets file from URL image.
	 * @param urlString URL
	 * @param fileName file name
	 * @return image file
	 */
	public static File obtenerFicheroDeUrl(String urlString, String fileName) {
		try {
			// primero especificaremos el origen de nuestro archivo a descargar
			// utilizando
			// la ruta completa
			URL url = new URL(urlString);

			// establecemos la conexión con el destino
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			// establecemos el método jet para nuestra conexión
			// el método setdooutput es necesario para este tipo de conexiones
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// por último establecemos nuestra conexión y cruzamos los dedos
			// <img
			// src="http://www.insdout.com/wp-includes/images/smilies/icon_razz.gif"
			// alt=":P" class="wp-smiley">
			urlConnection.connect();

			// Se crea el directorio.
			String tmppathSession = UtilsResources.createSessionTempDir();

			// vamos a crear un objeto del tipo de fichero
			// donde descargaremos nuestro fichero
			File temp = new File(tmppathSession + fileName);

			// utilizaremos un objeto del tipo fileoutputstream
			// para escribir el archivo que descargamos en el nuevo
			FileOutputStream fileOutput = new FileOutputStream(temp);

			// leemos los datos desde la url
			InputStream inputStream = urlConnection.getInputStream();

			// obtendremos el tamaño del archivo y lo asociaremos a una
			// variable de tipo entero
			int downloadedSize = 0;

			// creamos un buffer y una variable para ir almacenando el
			// tamaño temporal de este
			byte[ ] buffer = new byte[NumberConstants.NUM1024];
			int bufferLength = 0;

			// ahora iremos recorriendo el buffer para escribir el archivo de
			// destino
			// siempre teniendo constancia de la cantidad descargada y el total
			// del tamaño
			// con esto podremos crear una barra de progreso
			while ((bufferLength = inputStream.read(buffer)) > 0) {

				fileOutput.write(buffer, 0, bufferLength);
				downloadedSize += bufferLength;

			}
			// cerramos
			fileOutput.close();

			return temp;
			// y gestionamos errores
		} catch (MalformedURLException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}

		return null;

	}
}
