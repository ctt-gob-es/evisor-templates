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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.CodeEditor.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that represents contents of the tab code.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 13/03/2015.
 */
public class CodeEditor extends CustomComponent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6242330793475727027L;
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(CodeEditor.class);

	/**
	 * Attribute that represents code tab main layout. 
	 */
	private VerticalLayout layout;
	
	/**
	 * Attribute that represents a reference from content of the tab design instance.
	 */
	private DesignZone designZone;

	/**
	 * Constructor method for the class CodeEditor.java.
	 * @param designZoneParam reference from content of the tab design instance
	 */
	public CodeEditor(DesignZone designZoneParam) {

		this.designZone = designZoneParam;

		this.layout = new VerticalLayout();

		Button download = new Button(MessagesEVT.getInstance().getProperty("downloadButton"));
		StreamResource sr = getCodeStream();
		FileDownloader fileDownloader = new FileDownloader(sr);

		fileDownloader.extend(download);

		download.addStyleName("botonFijo");

		this.layout.setSizeUndefined();

		CssLayout csslay = new CssLayout();
		csslay.addComponent(this.layout);
		csslay.addComponent(download);
		csslay.setSizeUndefined();

		Panel panel = new Panel();
		panel.setContent(csslay);
		panel.setSizeFull();

		setCompositionRoot(panel);
	}

	/**
	 * Method to add a line to the code zone.
	 * @param code code to add in the line
	 * @return code passed as parameter
	 */
	public final String addLine(String code) {
		HorizontalLayout hl = new HorizontalLayout(new Label(code));
		layout.addComponent(hl);
		return code;
	}

	/**
	 * Method to add a text field to the last line of code zone.
	 * @param code code to add in the text field
	 * @param comp component asociated with text field value
	 * @param type of the value of text field
	 * @param isVar indicate if text field content is a variable
	 * @return code passed as parameter
	 */
	public final String addEdition(String code, Component comp, String type, boolean isVar) {
		HorizontalLayout lastLine = (HorizontalLayout) layout.getComponent(layout.getComponentCount() - 1);
		lastLine.addComponent(new CodeEditorInput(code, comp, type, this, isVar));
		return code;
	}

	/**
	 * Method to add a text to the last line of code zone.
	 * @param code code to add in the text field
	 * @return code passed as parameter
	 */
	public final String addText(String code) {
		HorizontalLayout lastLine = (HorizontalLayout) layout.getComponent(layout.getComponentCount() - 1);
		lastLine.addComponent(new Label(code));
		return code;
	}

	/**
	 * Method that returns a list of text fields in code editor.
	 * @return list of text fields in code editor
	 */
	public final List<CodeEditorInput> getInputs() {
		List<CodeEditorInput> result = new ArrayList<CodeEditorInput>();
		int size = layout.getComponentCount();
		for (int i = 0; i < size; i++) {
			if (layout.getComponent(i) instanceof HorizontalLayout) {
				HorizontalLayout compLine = (HorizontalLayout) layout.getComponent(i);
				int sizeLine = compLine.getComponentCount();
				for (int j = 0; j < sizeLine; j++) {
					Component comp = compLine.getComponent(j);
					if (comp instanceof CodeEditorInput) {
						result.add((CodeEditorInput) comp);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Method that returns a list of combo fields in code editor.
	 * @return list of combo fields in code editor
	 */
	public final List<ComboCBType> getCombosCB() {
		List<ComboCBType> result = new ArrayList<ComboCBType>();
		int size = layout.getComponentCount();
		for (int i = 0; i < size; i++) {
			if (layout.getComponent(i) instanceof HorizontalLayout) {
				HorizontalLayout compLine = (HorizontalLayout) layout.getComponent(i);
				int sizeLine = compLine.getComponentCount();
				for (int j = 0; j < sizeLine; j++) {
					Component comp = compLine.getComponent(j);
					if (comp instanceof ComboCBType) {
						result.add((ComboCBType) comp);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Method that resets the code editor.
	 */
	public final void reset() {
		LOG.info(MessagesEVT.getInstance().getProperty("resetCodeEditor"));
		// Se borran todos los componentes
		this.layout.removeAllComponents();

	}

	/**
	 * Method to add a text field to the last line of code zone.
	 * @param type type of the value of text field
	 * @param comp component associated with text field value
	 * @param combo combo to be inserted in line
	 * @return code passed as parameter
	 */
	public final String addComboCB(String type, Component comp, ComboCBType combo) {
		HorizontalLayout lastLine = (HorizontalLayout) layout.getComponent(layout.getComponentCount() - 1);
		lastLine.addComponent(combo);
		return type;
	}

	/**
	 * Method that gets XSLT code as StreamResource.
	 * @return XSLT code as StreamResource
	 */
	private StreamResource getCodeStream() {
		LOG.info(MessagesEVT.getInstance().getProperty("obtainingCodeStream"));
		StreamResource.StreamSource source = new StreamResource.StreamSource() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 6298313227400635137L;

			public InputStream getStream() {
				solve();
				try {
					return new ByteArrayInputStream(obtieneString().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					LOG.error(e);
				}
				return null;
			}
		};
		return new StreamResource(source, ConfigEVT.getInstance().getProperty("outputFileName"));

	}

	/**
	 * Method that solves changes in code editor zone and updates the template's components.
	 */
	public final void solve() {
		LOG.info(MessagesEVT.getInstance().getProperty("solvingCodeToTemplateComponents"));
		List<CodeEditorInput> inputsList = getInputs();

		for (int i = 0; i < inputsList.size(); i++) {
			inputsList.get(i).solve();
		}

		List<ComboCBType> comboList = getCombosCB();

		for (int j = 0; j < comboList.size(); j++) {
			comboList.get(j).solve();
		}
	}

	/**
	 * Method that gets a String representation of XSLT code.
	 * @return a String representation of XSLT code
	 */
	public final String obtieneString() {
		LOG.info(MessagesEVT.getInstance().getProperty("obtainingCode"));
		reset();
		StringBuffer xslt = this.designZone.getTemplate().getCode(this);
		return xslt.toString();
	}	

}
