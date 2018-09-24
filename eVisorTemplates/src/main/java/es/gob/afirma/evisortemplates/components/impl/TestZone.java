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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.TestZone.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
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
import es.gob.afirma.evisortemplates.util.UtilsResources;
import es.gob.afirma.evisortemplates.util.test.PDFReportManager;
import es.gob.afirma.evisortemplates.util.test.ReportException;
import es.gob.afirma.evisortemplates.util.test.TemplateData;
import es.gob.afirma.evisortemplates.util.test.ToolsException;
import es.gob.afirma.evisortemplates.util.test.XMLUtils;

/** 
 * <p>Class that represents test zone content.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 17/03/2015.
 */
public class TestZone extends CustomComponent {

	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = 678476363193244657L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(TestZone.class);
	
	/**
	 * Attribute that represents fileSizeError String constant. 
	 */
	private static final String FILESIZEERROR = "fileSizeError";
	
	/**
	 * Attribute that represents fileNotFound String constant. 
	 */
	private static final String FILENOTFOUND = "fileNotFound";

	/**
	 * Attribute that represents WMSG166 String constant. 
	 */
	private static final String WMSG166 = "WMSG166";
	
	/**
	 * Attribute that represents PDF_REPORT = 0 int constant. 
	 */
	protected static final int PDF_REPORT = 0;

	/**
	 * Attribute that represents xml_in file in byte[]. 
	 */
	private byte[ ] xml;
	
	/**
	 * Attribute that represents signed document file in byte[]. 
	 */
	private byte[ ] pdf;

	/**
	 * Attribute that represents xml_in label. 
	 */
	private Label xmlLabel;
	
	/**
	 * Attribute that represents signed document label. 
	 */
	private Label pdfLabel;

	/**
	 * Attribute that represents code editor zone instance. 
	 */
	private CodeEditor codeEditor;

	/**
	 * Constructor method for the class TestZone.java.
	 * @param codeEditorParam code editor zone instance
	 */
	public TestZone(CodeEditor codeEditorParam) {
		
		VerticalLayout layout;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try {
			LOG.info(MessagesEVT.getInstance().getProperty("sampleFilesCharging"));
			xml = IOUtils.toByteArray(classLoader.getResourceAsStream(ConfigEVT.getInstance().getProperty("xmlIn")));
			pdf = IOUtils.toByteArray(classLoader.getResourceAsStream((ConfigEVT.getInstance().getProperty("signedDoc"))));
			LOG.info(MessagesEVT.getInstance().getProperty("sampleFilesCharged"));
		} catch (IOException e) {
			LOG.error(e);
		}

		codeEditor = codeEditorParam;

		layout = new VerticalLayout();

		VerticalLayout layoutContent = new VerticalLayout();
		layoutContent.setSpacing(true);
		layoutContent.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("fileUploadPanelWidth")), Unit.PIXELS);

		layout.setSpacing(true);

		// Label para generar un espacion antes que los paneles.
		layoutContent.addComponent(new Label(""));

		FileXmlReceiver xmlReceiver = new FileXmlReceiver();

		final Upload uploadXml = new Upload("", xmlReceiver);
		uploadXml.setButtonCaption(MessagesEVT.getInstance().getProperty("testUploadCaption"));
		uploadXml.addSucceededListener(xmlReceiver);

		// Prevent too big downloads
		final long UPLOAD_LIMIT = Long.parseLong(ConfigEVT.getInstance().getProperty("uploadSizeLimit"));
		uploadXml.addStartedListener(new StartedListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5235777586202412241L;

			@Override
			public void uploadStarted(StartedEvent event) {
				LOG.debug(MessagesEVT.getInstance().getProperty("initUploadXML")+event.getContentLength());
				
				if (event.getContentLength() > UPLOAD_LIMIT) {

					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					uploadXml.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}

			}
		});

		// Check el tamaño durante el preceso de subida
		uploadXml.addProgressListener(new ProgressListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 6583873703986944643L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					uploadXml.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}
			}
		});

		xmlLabel = new Label(MessagesEVT.getInstance().getProperty("defaultUploadXmlLabel"));

		// Put the components in a panel
		Panel panelXml = new Panel(MessagesEVT.getInstance().getProperty("xmlUploadLabel"));
		panelXml.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("fileUploadPanelWidth")), Unit.PIXELS);
		VerticalLayout panelContentXml = new VerticalLayout();
		panelContentXml.addComponents(uploadXml, xmlLabel);
		panelContentXml.setSpacing(true);
		panelXml.setContent(panelContentXml);

		layoutContent.addComponent(panelXml);
		layoutContent.setComponentAlignment(panelXml, Alignment.MIDDLE_CENTER);

		Button download = new Button(MessagesEVT.getInstance().getProperty("downloadButtonInputXML"));
		StreamResource srXMLDown = getXmlTemplate();
		FileDownloader fileDownloaderXMLDown = new FileDownloader(srXMLDown);

		fileDownloaderXMLDown.extend(download);

		layoutContent.addComponent(download);

		layoutContent.setComponentAlignment(download, Alignment.MIDDLE_RIGHT);

		FilePdfReceiver pdfReceiver = new FilePdfReceiver();

		final Upload uploadPdf = new Upload("", pdfReceiver);
		uploadPdf.setButtonCaption(MessagesEVT.getInstance().getProperty("testUploadCaption"));
		uploadPdf.addSucceededListener(pdfReceiver);

		uploadPdf.addStartedListener(new StartedListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5235777586202412241L;

			@Override
			public void uploadStarted(StartedEvent event) {
				LOG.debug(MessagesEVT.getInstance().getProperty("initUploadSignedDoc")+event.getContentLength());
				if (event.getContentLength() > UPLOAD_LIMIT) {

					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					uploadPdf.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}

			}
		});

		// Check el tamaño durante el preceso de subida
		uploadPdf.addProgressListener(new ProgressListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 6583873703986944643L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					uploadPdf.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}
			}
		});

		pdfLabel = new Label(MessagesEVT.getInstance().getProperty("defaultUploadPdfLabel"));

		// Put the components in a panel
		Panel panelPdf = new Panel(MessagesEVT.getInstance().getProperty("pdfUploadLabel"));
		panelPdf.setWidth(Float.parseFloat(ConfigEVT.getInstance().getProperty("fileUploadPanelWidth")), Unit.PIXELS);
		VerticalLayout panelContentPdf = new VerticalLayout();
		panelContentPdf.addComponents(uploadPdf, pdfLabel);
		panelPdf.setContent(panelContentPdf);
		panelContentPdf.setSpacing(true);

		layoutContent.addComponent(panelPdf);
		layoutContent.setComponentAlignment(panelPdf, Alignment.MIDDLE_CENTER);

		Button testButton = new Button(MessagesEVT.getInstance().getProperty("testButtonCaption"));

		StreamResource sr = getReportStream();
		FileDownloader fileDownloader = new FileDownloader(sr);

		fileDownloader.extend(testButton);

		layoutContent.addComponent(testButton);

		layoutContent.setComponentAlignment(testButton, Alignment.MIDDLE_RIGHT);

		layout.addComponent(layoutContent);

		layout.setComponentAlignment(layoutContent, Alignment.MIDDLE_CENTER);

		setCompositionRoot(layout);
	}

	/**
	 * Method that gets template's code in StreamResource representation.
	 * @return template's code in StreamResource representation
	 */
	private StreamResource getXmlTemplate() {
		
		StreamResource.StreamSource source = new StreamResource.StreamSource() {

			/**
			 * Attribute that represents serialVersionUID. 
			 */
			private static final long serialVersionUID = -3782896194204824206L;

			public InputStream getStream() {
				LOG.info(MessagesEVT.getInstance().getProperty("downloadingXMLIn"));
				return new ByteArrayInputStream(xml);

			}
		};
		return new StreamResource(source, ConfigEVT.getInstance().getProperty("outputFileXMLInName"));

	}

	/**
	 * Method that gets sample report in StramResource representation.
	 * @return sample report in StramResource representation
	 */
	private StreamResource getReportStream() {
		
		// CHECKSTYLE:OFF needed inner class
		StreamResource.StreamSource source = new StreamResource.StreamSource() {

			// CHECKSTYLE:ON

			/**
			 * Attribute that represents serialVersionUID. 
			 */
			private static final long serialVersionUID = -1185163908547235470L;

			public InputStream getStream() {
				if (pdf != null && xml != null) {
					LOG.info(MessagesEVT.getInstance().getProperty("downloadingSampleReport"));
					byte[ ] report = null;
					TemplateData templateEdit = new TemplateData("", PDF_REPORT);

					Document doc = null;
					try {

						templateEdit.setTemplate(codeEditor.obtieneString().getBytes("UTF-8"));

						if (xml != null && xml.length > 0) {
							try {
								doc = XMLUtils.getDocument(xml);
							} catch (ToolsException e1) {
								new Notification(MessagesEVT.getInstance().getProperty(WMSG166), MessagesEVT.getInstance().getProperty("WMSG168"), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
								LOG.error(MessagesEVT.getInstance().getProperty(WMSG166),e1);
								return null;
							}
						}
						if (doc != null) {
							try {
								PDFReportManager reportManager = new PDFReportManager();

								reportManager.setXmlInput(xml);
								report = reportManager.createReport(templateEdit, pdf);

							} catch (ReportException e) {
								new Notification(MessagesEVT.getInstance().getProperty(WMSG166), e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
								LOG.error(MessagesEVT.getInstance().getProperty(WMSG166),e);
								return null;
							}
						} else {
							LOG.error(MessagesEVT.getInstance().getProperty(WMSG166));
							new Notification(MessagesEVT.getInstance().getProperty(WMSG166), MessagesEVT.getInstance().getProperty("WMSG167"), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
						}
					} catch (IOException e2) {
						LOG.error(e2);
					}

					return new ByteArrayInputStream(report);

				}
				return null;
			}

		};

		return new StreamResource(source, ConfigEVT.getInstance().getProperty("outputTestFileName"));
	}

	/** 
	 * <p>Inner class receiver that saves upload in a file and
	 * listener for successful upload.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	class FileXmlReceiver implements Receiver, SucceededListener {

		/**
		 * Attribute that represents serialVersionUID. 
		 */
		private static final long serialVersionUID = 7690134720633914861L;

		/**
		 * Attribute that represents file uploaded. 
		 */
		private File file;

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String, java.lang.String)
		 */
		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			// Create upload stream
			FileOutputStream fos = null;
			try {

				String tmppathSession = UtilsResources.createSessionTempDir();
				file = new File(tmppathSession + filename);
				fos = new FileOutputStream(file);

			} catch (final FileNotFoundException e) {
				new Notification(MessagesEVT.getInstance().getProperty(FILENOTFOUND), "", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				LOG.error(MessagesEVT.getInstance().getProperty(FILENOTFOUND),e);
				return null;
			}
			return fos;
		}

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.Upload.SucceededEvent)
		 */
		@Override
		public void uploadSucceeded(SucceededEvent event) {
			try {
				xml = IOUtils.toByteArray(new FileInputStream(file));
				xmlLabel.setValue(MessagesEVT.getInstance().getProperty("preFileNameXML") + " " + file.getName());
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}

		}
	}

	/** 
	 * <p>Inner class receiver that saves upload in a file and
	 * listener for successful upload.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	class FilePdfReceiver implements Receiver, SucceededListener {

		/**
		 * Attribute that represents serialVersionUID. 
		 */
		private static final long serialVersionUID = -4282709503318069326L;
		
		/**
		 * Attribute that represents file uploaded. 
		 */
		private File file;

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String, java.lang.String)
		 */
		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			// Create upload stream
			FileOutputStream fos = null;
			try {

				String tmppathSession = UtilsResources.createSessionTempDir();
				file = new File(tmppathSession + filename);
				fos = new FileOutputStream(file);

			} catch (final java.io.FileNotFoundException e) {
				new Notification(MessagesEVT.getInstance().getProperty(FILENOTFOUND), "", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				LOG.error(MessagesEVT.getInstance().getProperty(FILENOTFOUND),e);
				return null;
			}
			return fos;
		}

		/**
		 * {@inheritDoc}
		 * @see com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.Upload.SucceededEvent)
		 */
		@Override
		public void uploadSucceeded(SucceededEvent event) {
			try {
				pdf = IOUtils.toByteArray(new FileInputStream(file));
				pdfLabel.setValue(MessagesEVT.getInstance().getProperty("preFileNamePDF") + " " + file.getName());
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}

		}
	}

	
}