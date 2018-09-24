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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.ImageUpload.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.Format;
import es.gob.afirma.evisortemplates.util.ImageTools;
import es.gob.afirma.evisortemplates.util.ImageToolsException;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.UtilsResources;

/** 
 * <p>Class that represents image upload zone.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class ImageUpload extends CustomComponent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4061268389372910440L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(ImageUpload.class);

	/**
	 * Attribute that represents the image component to show in view. 
	 */
	private RichImage image;
	
	/**
	 * Attribute that represents the upload component. 
	 */
	private Upload upload;
	
	/**
	 * Attribute that represents the text field component needed to insert a url to charge the image from url. 
	 */
	private TextField urlIn;
	
	/**
	 * Attribute that represents OpUpload String constant. 
	 */
	private static final String OPUPLOAD = "OpUpload";
	
	/**
	 * Attribute that represents OpUrlVar String constant. 
	 */
	private static final String OPURLVAR = "OpUrlVar";
	
	/**
	 * Attribute that represents defaultWidthSize String constant. 
	 */
	private static final String DEFAULTWIDTHSIZE = "defaultWidthSize";
	
	/**
	 * Attribute that represents fileSizeError String constant. 
	 */
	private static final String FILESIZEERROR = "fileSizeError";

	/**
	 * Attribute that represents the layout that contains the upload zone. 
	 */
	private VerticalLayout uploadZone;
	
	/**
	 * Attribute that represents the layout that contains the url/var zone. 
	 */
	private VerticalLayout urlVarZone;

	/**
	 * Attribute that represents the layout that contains the complete component. 
	 */
	private VerticalLayout layout;

	/**
	 * Constructor method for the class ImageUpload.java. 
	 */
	public ImageUpload() {

		layout = new VerticalLayout();

		// Se crean las dos opciones del menú
		final VerticalLayout menu = new VerticalLayout();
		final OptionGroup options = new OptionGroup();

		options.addItem(OPUPLOAD);
		options.setItemCaption(OPUPLOAD, MessagesEVT.getInstance().getProperty("fromFile"));

		options.addItem(OPURLVAR);
		options.setItemCaption(OPURLVAR, MessagesEVT.getInstance().getProperty("fromURLVAR"));

		options.select(OPURLVAR);
		options.setNullSelectionAllowed(false);
		options.setHtmlContentAllowed(true);
		options.setImmediate(true);

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		options.addValueChangeListener(new ValueChangeListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -6864443834336451051L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				String value = (String) event.getProperty().getValue();
				if (value.equals(OPUPLOAD)) {
					uploadZone.setVisible(true);
					urlVarZone.setVisible(false);
					urlIn.setValue("");
				} else if (value.equals(OPURLVAR)) {
					uploadZone.setVisible(false);
					urlVarZone.setVisible(true);
				}
				RichImage imageAux = new RichImage();
				imageAux.setVisible(false);
				layout.replaceComponent(image, imageAux);
				image = imageAux;
			}
		});

		options.addStyleName("smallText");
		menu.addComponent(options);

		// Se añade el menú en el layout principal
		layout.addComponent(menu);

		// Se crea la imagen y se setea a false la visibilidad hasta que tenga
		// contenido.
		image = new RichImage();
		image.setVisible(false);

		// Se añade la imagen en el layout principal
		layout.addComponent(image);

		// Se prepara la zona para el upload
		uploadZone = new VerticalLayout();

		ImageReceiver receiver = new ImageReceiver();
		// Crea un upload y despues setea el receiver
		upload = new Upload("", receiver);
		upload.setButtonCaption(MessagesEVT.getInstance().getProperty("imageUploadCaption"));
		upload.addSucceededListener(receiver);

		// Se marca el límite en tamaño del fichero
		final long UPLOAD_LIMIT = Long.parseLong(ConfigEVT.getInstance().getProperty("uploadSizeLimit"));

		upload.addStartedListener(new StartedListener() {
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5235777586202412241L;

			@Override
			public void uploadStarted(StartedEvent event) {
				
				LOG.debug(MessagesEVT.getInstance().getProperty("initUploadImage")+event.getContentLength()+"bytes");

				if (event.getContentLength() > UPLOAD_LIMIT) {

					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
					upload.interruptUpload();
				}

			}
		});

		// Check el tamaño durante el preceso de subida
		upload.addProgressListener(new ProgressListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 6583873703986944643L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
					upload.interruptUpload();
				}
			}
		});

		uploadZone.addComponent(upload);
		// Se setea la visibilidad a false ya que por defecto la zona a mostrar
		// es la de la url/var
		uploadZone.setVisible(false);
		uploadZone.setSpacing(true);

		// Se prepara la zona para la Url/Variable
		urlVarZone = new VerticalLayout();

		final FormVariable fv = new FormVariable(Format.TYPEIMAGE, false, false);
		fv.addClickAcceptListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4437553178746865476L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (fv.isFullVar()) {
					LOG.debug(MessagesEVT.getInstance().getProperty("insertingVariable")+fv.getVarFormed());
					urlIn.setValue(fv.getVarFormed());
				}

			}
		});
		urlVarZone.addComponent(fv);

		urlIn = new TextField(MessagesEVT.getInstance().getProperty("urlVarLabel"));
		urlIn.setWidth(NumberConstants.NUM400, Unit.PIXELS);
		final Button URLVaraccept = new Button(MessagesEVT.getInstance().getProperty("chargeFromURLVAR"));

		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		URLVaraccept.addClickListener(new Button.ClickListener() {

			// CHECKSTYLE:ON

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 5460324002068570726L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (urlIn.getValue() != null && !urlIn.getValue().isEmpty()) {
					LOG.debug(MessagesEVT.getInstance().getProperty("InsertingURL")+urlIn.getValue());
					if (!urlIn.getValue().contains("¬")) {

						try {
							image.setUrl(urlIn.getValue());
							image.setSource(new ExternalResource(urlIn.getValue()));
							File tmp = ImageTools.obtenerFicheroDeUrl(urlIn.getValue(), image.hashCode() + "");
							image.setOriginalHeight(ImageTools.getImageHeight(tmp));
							image.setOriginalWidth(ImageTools.getImageWidth(tmp));
							image.setVisible(true);
							image.setScaleWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty(DEFAULTWIDTHSIZE)));
						} catch (Exception e) {
							new Notification(MessagesEVT.getInstance().getProperty("chargeFromURLError"), "", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
							LOG.error(MessagesEVT.getInstance().getProperty("chargeFromURLError"),e);
							image.setSource(new ThemeResource("images" + File.separator + ConfigEVT.getInstance().getProperty("defaultImage")));
							image.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty(DEFAULTWIDTHSIZE)), Unit.PIXELS);
							image.setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultHeightSize")), Unit.PIXELS);
							image.setOriginalHeight(image.getHeight());
							image.setOriginalWidth(image.getWidth());
							image.setVariable(urlIn.getValue());
							image.setVisible(true);
						}

					} else {
						if (urlIn.getValue().contains(ConfigEVT.getInstance().getProperty("barCode"))) {
							image.setSource(new ThemeResource("images" + File.separator + ConfigEVT.getInstance().getProperty("defaultBarcodeImage")));
							image.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultBarcodeWidthSize")), Unit.PIXELS);
							image.setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultBarcodeHeightSize")), Unit.PIXELS);
							image.setOriginalHeight(image.getHeight());
							image.setOriginalWidth(image.getWidth());
						} else {
							image.setSource(new ThemeResource("images" + File.separator + ConfigEVT.getInstance().getProperty("defaultImage")));
							image.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty(DEFAULTWIDTHSIZE)), Unit.PIXELS);
							image.setHeight(Float.parseFloat(ConfigEVT.getInstance().getProperty("defaultHeightSize")), Unit.PIXELS);
							image.setOriginalHeight(image.getHeight());
							image.setOriginalWidth(image.getWidth());
						}
						image.setVariable(urlIn.getValue());
						image.setVisible(true);
					}
					urlVarZone.setVisible(false);
				}

			}
		});

		HorizontalLayout hl = new HorizontalLayout(urlIn, URLVaraccept);
		hl.setComponentAlignment(URLVaraccept, Alignment.BOTTOM_RIGHT);
		hl.setSpacing(true);
		urlVarZone.addComponent(hl);
		urlVarZone.setSpacing(true);

		// Se añaden las zonas al layout principal
		layout.addComponents(urlVarZone, uploadZone);

		setCompositionRoot(layout);
	}

	/**
	 * Method that gets the image component.
	 * @return the image component
	 */
	public final Component getImage() {

		return image;
	}

	/** 
	 * <p>Inner class to implements the receiver needed for the Upload component.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	class ImageReceiver implements Receiver, SucceededListener {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 8816328542238549710L;

		/**
		 * Attribute that represents the resulting file of upload proccess. 
		 */
		private File file;

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String, java.lang.String)
		 */
		public final OutputStream receiveUpload(String filename, String mimeType) {

			FileOutputStream fos = null;
			try {

				String tmppathSession = UtilsResources.createSessionTempDir();
				file = new File(tmppathSession + filename);
				fos = new FileOutputStream(file);

			} catch (final java.io.FileNotFoundException e) {
				LOG.error(MessagesEVT.getInstance().getProperty("fileNotFound"),e);
				new Notification(MessagesEVT.getInstance().getProperty("fileNotFound"), "", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				return null;
			}
			return fos;
		}

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.Upload.SucceededEvent)
		 */
		public final void uploadSucceeded(SucceededEvent event) {

			try {
				image.setWidth(ImageTools.getImageWidth(file), Unit.PIXELS);
				image.setHeight(ImageTools.getImageHeight(file), Unit.PIXELS);
				image.setOriginalHeight(image.getHeight());
				image.setOriginalWidth(image.getWidth());
				if (image.getWidth() > Float.parseFloat(ConfigEVT.getInstance().getProperty("maxWidthSize"))) {
					try {

						file = ImageTools.resizeImage(file, Integer.parseInt(ConfigEVT.getInstance().getProperty("maxWidthSize")));

					} catch (FileNotFoundException e) {
						LOG.error(MessagesEVT.getInstance().getProperty("scaleFileImageError"),e);
						new Notification(MessagesEVT.getInstance().getProperty("scaleFileImageError"), "", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					}
				}
				image.setScaleWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty(DEFAULTWIDTHSIZE)));
				if (image.setFile(file)) {

					// Show the uploaded file in the image viewer
					image.setVisible(true);
					uploadZone.setVisible(false);
				}

			} catch (ImageToolsException e) {
				LOG.error(e);
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}

		}
	}

}