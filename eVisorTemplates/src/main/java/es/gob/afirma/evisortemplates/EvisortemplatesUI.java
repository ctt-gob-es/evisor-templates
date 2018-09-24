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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.EvisortemplatesUI.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.components.impl.CodeEditor;
import es.gob.afirma.evisortemplates.components.impl.DesignZone;
import es.gob.afirma.evisortemplates.components.impl.TestZone;
import es.gob.afirma.evisortemplates.components.impl.VariablesZone;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.save.SaveBean;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.UtilsResources;
import es.gob.afirma.evisortemplates.windows.LoadTemplateWindow;

/** 
 * <p>Class that represents the user interface eVisorTemplates.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
@Theme("evisortemplates")
@PreserveOnRefresh
public class EvisortemplatesUI extends UI {
	
	/**
	 * Attribute that represents serialVersionUID. 
	 */
	private static final long serialVersionUID = -9007141333326697393L;

	/**
	 * Attribute that represents the Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(EvisortemplatesUI.class);
	
	/**
	 * Attribute that represents fileNotFound String constant. 
	 */
	private static final String FILENOTFOUND = "fileNotFound";
	
	/**
	 * Attribute that represents the tab design caption. 
	 */
	private static final String TABDESIGN = "tabDesign";
	
	/**
	 * Attribute that represents the tab code caption. 
	 */
	private static final String TABCODE = "tabCode";

	/**
	 * Attribute that represents the design zone content. 
	 */
	private DesignZone designZone;
	
	/**
	 * Attribute that represents the tabs structure. 
	 */
	private TabSheet tabs;
	
	/**
	 * Attribute that represents the code zone content. 
	 */
	private CodeEditor codeEditor;
	
	/**
	 * Attribute that represents the variables zone content.
	 */
	private VariablesZone variablesZone;
	
	/**
	 * Attribute that represents fileSizeError String constant. 
	 */
	private static final String FILESIZEERROR = "fileSizeError";
	
	/**
	 * Attribute that represents the load saved template window. 
	 */
	private LoadTemplateWindow loadWindow;



	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected final void init(VaadinRequest requestParam) {
		
		LOG.info(MessagesEVT.getInstance().getProperty("instanceUI"));

		create();
		
		LOG.info(MessagesEVT.getInstance().getProperty("instanceUIFinal"));
	}
	
	/**
	 * Method that creates the ui content.
	 */
	public final void create() {
		
		VerticalLayout base = new VerticalLayout();
		
		// Se crea la estructura de pestaña
		tabs = new TabSheet();
		tabs.setHeight(NumberConstants.FNUM100, Unit.PERCENTAGE);

		// Se instancia la zona de diseño del documento
		if (designZone == null) {
			designZone = new DesignZone();
		} 
		
		designZone.setSizeFull();
		designZone.setHeightUndefined();
	
		// Se crea la zona de código
		codeEditor = new CodeEditor(designZone);

		codeEditor.setSizeUndefined();

		TestZone testZone = new TestZone(codeEditor);
		
		LoadReceiver loadReceiver = new LoadReceiver();
		
		// Se crea la zona de variables globales
		variablesZone = new VariablesZone();

		final Upload upload = new Upload("", loadReceiver);
		upload.setButtonCaption(MessagesEVT.getInstance().getProperty("SaveUploadCaption"));
		upload.addSucceededListener(loadReceiver);

		// Prevent too big downloads
		final long UPLOAD_LIMIT = Long.parseLong(ConfigEVT.getInstance().getProperty("uploadSizeLimitSave"));
		upload.addStartedListener(new StartedListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 7486235419966488878L;

			@Override
			public void uploadStarted(StartedEvent event) {
				LOG.debug(MessagesEVT.getInstance().getProperty("initUploadSaved")+event.getContentLength());
				
				if (event.getContentLength() > UPLOAD_LIMIT) {

					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					upload.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}

			}
		});

		// Check el tamaño durante el preceso de subida
		upload.addProgressListener(new ProgressListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 3538374424365597728L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					Notification.show(MessagesEVT.getInstance().getProperty(FILESIZEERROR), Notification.Type.ERROR_MESSAGE);
					upload.interruptUpload();
					LOG.error(MessagesEVT.getInstance().getProperty(FILESIZEERROR));
				}
			}
		});
		
		// Se añaden las pestañas
		tabs.addTab(designZone, MessagesEVT.getInstance().getProperty(TABDESIGN), null, 0);
		tabs.addTab(codeEditor, MessagesEVT.getInstance().getProperty(TABCODE), null, 1);
		tabs.addTab(testZone, MessagesEVT.getInstance().getProperty("tabTest"), null, 2);
		tabs.addTab(variablesZone, MessagesEVT.getInstance().getProperty("addGlobalVariable"), null, 3);
		
		tabs.setSizeFull();

		// Se añade el listener para controlar el cambio de pestaña
		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		tabs.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {

			/**
			 * Attribute that represents . 
			 */
			private static final long serialVersionUID = 3846165218398847241L;
			
			// CHECKSTYLE:ON
			private String lastTab = MessagesEVT.getInstance().getProperty(TABDESIGN);

			public void selectedTabChange(SelectedTabChangeEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("tabChange"));

				TabSheet tabsheet = event.getTabSheet();
				String caption = tabsheet.getTab(tabsheet.getSelectedTab()).getCaption();
				
				LOG.debug(MessagesEVT.getInstance().getProperty("tabChange")+" a: "+ caption);

				if (caption.equals(MessagesEVT.getInstance().getProperty(TABDESIGN))) {
					try {
						codeEditor.solve();
					} catch (Exception e) {
						tabs.setSelectedTab(0);
						Notification.show(MessagesEVT.getInstance().getProperty("parseError"), Type.ERROR_MESSAGE);
						LOG.error(MessagesEVT.getInstance().getProperty("parseError"),e);
					}
					lastTab = MessagesEVT.getInstance().getProperty(TABDESIGN);
				}
				if (caption.equals(MessagesEVT.getInstance().getProperty(TABCODE))) {
					codeEditor.obtieneString();
					designZone.closeContainers();
					lastTab = MessagesEVT.getInstance().getProperty(TABCODE);
				}
				if (caption.equals(MessagesEVT.getInstance().getProperty("tabTest"))) {
					if (lastTab != null && lastTab.equals(MessagesEVT.getInstance().getProperty(TABCODE))) {
						codeEditor.solve();
					}
					lastTab = MessagesEVT.getInstance().getProperty("tabTest");
				}
			}
		});

		// Configure the error handler for the UI
		getCurrent().setErrorHandler(new DefaultErrorHandler() {

			/**
			 * Attribute that represents serialVersionUID. 
			 */
			private static final long serialVersionUID = -7552475592655975537L;

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				LOG.error(MessagesEVT.getInstance().getProperty("unsespectedError"),event.getThrowable());
			}
		});
		
		Button save = new Button(FontAwesome.SAVE);
		save.setStyleName("botonSaveFijo");
		save.addClickListener(new Button.ClickListener() {
			
			/**
			 * Attribute that represents . 
			 */
			private static final long serialVersionUID = 2750123077280211882L;

			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("saveTemplate"));
				
			}
		});
		StreamResource sr = getStream();
		FileDownloader fileDownloader = new FileDownloader(sr);
		fileDownloader.extend(save);
		
		
		Button load = new Button(FontAwesome.UPLOAD);
		load.setStyleName("botonLoadFijo");
		load.addClickListener(new Button.ClickListener() {
			
			/**
			 * Attribute that represents . 
			 */
			private static final long serialVersionUID = 2750123077280211882L;

			@Override
			public void buttonClick(ClickEvent event) {
				loadWindow = new LoadTemplateWindow(upload);
				UI.getCurrent().addWindow(loadWindow);
			}
		});
		
		base.addComponent(tabs);
		base.addComponent(save);
		base.addComponent(load);
		setContent(base);
	}
	
	/** 
	 * <p>Inner class receiver that saves upload in a file and
	 * listener for successful upload.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 17/03/2015.
	 */
	class LoadReceiver implements Receiver, SucceededListener {

		/**
		 * Attribute that represents serialVersionUID. 
		 */
		private static final long serialVersionUID = 5055414806860630636L;
		
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
				byte[] fileUploaded = Base64.decode(IOUtils.toByteArray(new FileInputStream(file)));
				ByteArrayInputStream bis = new ByteArrayInputStream(fileUploaded);
				ObjectInput in = null;
				in = new ObjectInputStream(bis);
				 
				SaveBean saveBean = (SaveBean) in.readObject();
				DesignZone newDesign = saveBean.getDesignZone();
				
				designZone = new DesignZone(newDesign);
				designZone.loadGlobalVariables();
				
				LOG.info(MessagesEVT.getInstance().getProperty("reInstanceUI"));

				create();
				
				getUI().getPage().setLocation(Page.getCurrent().getLocation());
				
				LOG.info(MessagesEVT.getInstance().getProperty("reInstanceUIFinal"));
				
				loadWindow.close();
			} catch (Exception e) {
				LOG.error(MessagesEVT.getInstance().getProperty("fileNotFoundOrError"),e);
				Notification.show(MessagesEVT.getInstance().getProperty("fileNotFoundOrError"), Notification.Type.ERROR_MESSAGE);
			} 

		}
	}
		  
	/**
	* Method that gets a SaveBean containing the design zone as StreamResource.
	* @return SaveBean containing the design zone as StreamResource
	*/
	private StreamResource getStream() {
		LOG.info(MessagesEVT.getInstance().getProperty("obtainingSaveStream"));
		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		StreamResource.StreamSource source = new StreamResource.StreamSource() {
		// CHECKSTYLE:ON
			
		/**
		* Attribute that represents serialVersionUID. 
		*/
		private static final long serialVersionUID = 6298313227400635137L;

		public InputStream getStream() {				
			return new ByteArrayInputStream(obtieneBytes());
		}

		private byte[] obtieneBytes() {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			try {
				out = new ObjectOutputStream(bos);   
				designZone.storeGlobalVariables();
				out.writeObject(new SaveBean(new DesignZone(designZone)));
				byte[] yourBytes = bos.toByteArray();
				return Base64.encode(yourBytes);
			} catch (IOException e) {
				LOG.error(e);
			} finally {
				try {
				    if (out != null) {
				    out.close();
				    }
				} catch (IOException ex) {LOG.error(ex);}
				try {
					bos.close();
				} catch (IOException ex) {LOG.error(ex);}
			}
			return null;
		}};
		return new StreamResource(source, ConfigEVT.getInstance().getProperty("outputSaveFileName"));
	}
}