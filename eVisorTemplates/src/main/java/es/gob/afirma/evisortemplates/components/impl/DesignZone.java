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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.DesignZone.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vaadin.sebastian.dock.Dock;
import org.vaadin.sebastian.dock.DockItem;
import org.vaadin.sebastian.dock.LabelPosition;
import org.vaadin.sebastian.dock.events.DockClickEvent;
import org.vaadin.sebastian.dock.events.DockClickListener;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.PixelToCm;

/** 
 * <p>Class that represents design zone content.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class DesignZone extends VerticalLayout {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4585878896558861944L;

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(DesignZone.class);

	/**
	 * Constant that represents "OpInsertText" String. 
	 */
	public static final String OPINSERTTEXT = "OpInsertText";

	/**
	 * Constant that represents "OpInsertImage" String. 
	 */
	public static final String OPINSERTIMAGE = "OpInsertImage";

	/**
	 * Constant that represents "OpInsertTable" String. 
	 */
	public static final String OPINSERTTABLE = "OpInsertTable";

	/**
	 * Constant that represents "OpInsertCodeBar" String. 
	 */
	public static final String OPINSERTCODEBAR = "OpInsertCodeBar";

	/**
	 * Constant that represents "OpInsertDocument" String. 
	 */
	public static final String OPINSERTDOCUMENT = "OpInsertDocument";

	/**
	 * Constant that represents opción: String. 
	 */
	private static final String OPTION = "opción:";

	/**
	 * Constant that represents "menuOptionClicked" String. 
	 */
	private static final String MENUOPTIONCLIKED = "menuOptionClicked";

	/**
	 * Attribute that represents main template . 
	 */
	private Template template;

	/**
	 * Attribute that represents the front template button. 
	 */
	private Button frontButton;

	/**
	 * Attribute that represents the last template button. 
	 */
	private Button lastButton;

	/**
	 * Attribute that represents the front template remove button. 
	 */
	private Button removeFrontButton;

	/**
	 * Attribute that represents the last template remove button. 
	 */
	private Button removeLastButton;

	/**
	 * Attribute that contents the set of button to change the orientation of the top template.
	 */
	private HorizontalLayout orientationTop;

	/**
	 * Attribute that contents the set of button to change the orientation of the center template.
	 */
	private HorizontalLayout orientationCenter;

	/**
	 * Attribute that contents the set of button to change the orientation of the bottom template.
	 */
	private HorizontalLayout orientationBottom;

	/**
	 * Attribute that represents the selected menu option. 
	 */
	private static String selectedOption;

	/**
	 * Attribute that represents the selected menu option label. 
	 */
	private Label optionSelectedLabel;

	/**
	 * Attribute used to store the global variables into the file when the templates is downloaded.
	 */
	private Map<String, String> userGlobalVariables;

	/**
	 * Attribute that represents the list of global variables used in the design zone.
	 */
	private static List<String> globalVariablesUsed = new ArrayList<String>();

	/**
	 * Attribute that represents images String. 
	 */
	private static final String IMAGEDIR = "images";

	/**
	 * Constructor method for the class DesignZone.java. 
	 */
	public DesignZone() {
		super();

		LOG.info(MessagesEVT.getInstance().getProperty("designZoneCreation"));

		selectedOption = OPINSERTTEXT;

		LOG.info(MessagesEVT.getInstance().getProperty("newEmptyMainTemplate"));
		template = new Template();

		// Creamos los layouts de orientación
		orientationTop = new HorizontalLayout();
		Button verTopBtn = new Button(MessagesEVT.getInstance().getProperty("portraitOrientation"));
		verTopBtn.setIcon(FontAwesome.ARROWS_V);
		verTopBtn.addClickListener(new ClickListener() {

			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = -8047485431242762081L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getFrontTemplate().getHeight();
				float frontWidth = template.getFrontTemplate().getWidth();
				// Si esta con orientación horizontal, cambiamos a vertical,
				// sino, no hacemos nada
				if (frontWidth > frontHeight) {
					template.getFrontTemplate().setHeight(frontWidth, Unit.PIXELS);
					template.getFrontTemplate().setWidth(frontHeight, Unit.PIXELS);
					template.getFrontTemplate().setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.getFrontTemplate().setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}
			}
		});
		orientationTop.addComponent(verTopBtn);
		Button horTopBtn = new Button(MessagesEVT.getInstance().getProperty("landscapeOrientation"));
		horTopBtn.setIcon(FontAwesome.ARROWS_H);
		horTopBtn.addClickListener(new ClickListener() {

			/**
			 * serialVersionUID. 
			 */
			private static final long serialVersionUID = -5365004959119354055L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getFrontTemplate().getHeight();
				float frontWidth = template.getFrontTemplate().getWidth();
				// Si esta con orientación vertical, cambiamos a horizontal,
				// sino, no hacemos nada
				if (frontWidth < frontHeight) {
					template.getFrontTemplate().setHeight(frontWidth, Unit.PIXELS);
					template.getFrontTemplate().setWidth(frontHeight, Unit.PIXELS);
					template.getFrontTemplate().setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.getFrontTemplate().setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}
			}
		});
		orientationTop.addComponent(horTopBtn);
		orientationTop.setSpacing(true);

		orientationCenter = new HorizontalLayout();
		Button verCenBtn = new Button(MessagesEVT.getInstance().getProperty("portraitOrientation"));
		verCenBtn.setIcon(FontAwesome.ARROWS_V);
		verCenBtn.addClickListener(new ClickListener() {
			
			/**
			 * serialVersionUID. 
			 */
			private static final long serialVersionUID = -6869724254498082796L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getHeight();
				float frontWidth = template.getWidth();
				// Si esta con orientación horizontal, cambiamos a vertical,
				// sino, no hacemos nada
				if (frontWidth > frontHeight) {
					template.setHeight(frontWidth, Unit.PIXELS);
					template.setWidth(frontHeight, Unit.PIXELS);
					template.setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}
			}
		});
		orientationCenter.addComponent(verCenBtn);
		Button horCenBtn = new Button(MessagesEVT.getInstance().getProperty("landscapeOrientation"));
		horCenBtn.setIcon(FontAwesome.ARROWS_H);
		horCenBtn.addClickListener(new ClickListener() {
			
			/**
			 * serialVersionUID. 
			 */
			private static final long serialVersionUID = -7892349326857211865L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getHeight();
				float frontWidth = template.getWidth();
				// Si esta con orientación vertical, cambiamos a horizontal,
				// sino, no hacemos nada
				if (frontWidth < frontHeight) {
					template.setHeight(frontWidth, Unit.PIXELS);
					template.setWidth(frontHeight, Unit.PIXELS);
					template.setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}
			}
		});
		orientationCenter.addComponent(horCenBtn);
		orientationCenter.setSpacing(true);

		orientationBottom = new HorizontalLayout();
		Button verBotBtn = new Button(MessagesEVT.getInstance().getProperty("portraitOrientation"));
		verBotBtn.setIcon(FontAwesome.ARROWS_V);
		verBotBtn.addClickListener(new ClickListener() {
			
			/**
			 * serialVersionUID. 
			 */
			private static final long serialVersionUID = -7892349326857211864L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getLastTemplate().getHeight();
				float frontWidth = template.getLastTemplate().getWidth();
				// Si esta con orientación horizontal, cambiamos a vertical,
				// sino, no hacemos nada
				if (frontWidth > frontHeight) {
					template.getLastTemplate().setHeight(frontWidth, Unit.PIXELS);
					template.getLastTemplate().setWidth(frontHeight, Unit.PIXELS);
					template.getLastTemplate().setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.getLastTemplate().setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}
			}
		});
		orientationBottom.addComponent(verBotBtn);
		Button horBotBtn = new Button(MessagesEVT.getInstance().getProperty("landscapeOrientation"));
		horBotBtn.setIcon(FontAwesome.ARROWS_H);
		horBotBtn.addClickListener(new ClickListener() {
			
			/**
			 * serialVersionUID. 
			 */
			private static final long serialVersionUID = -8033415024930531682L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Recuperamos las dimensiones de la plantilla frontal
				float frontHeight = template.getLastTemplate().getHeight();
				float frontWidth = template.getLastTemplate().getWidth();
				// Si esta con orientación vertical, cambiamos a horizontal,
				// sino, no hacemos nada
				if (frontWidth < frontHeight) {
					template.getLastTemplate().setHeight(frontWidth, Unit.PIXELS);
					template.getLastTemplate().setWidth(frontHeight, Unit.PIXELS);
					template.getLastTemplate().setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));
					template.getLastTemplate().setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize"))) + 1));
				}				
			}
		});
		orientationBottom.addComponent(horBotBtn);
		orientationBottom.setSpacing(true);

		// Se crea para generar un espacio
		this.addComponent(new Label(" "));

		setSpacing(true);
		VerticalLayout front;
		front = new VerticalLayout();
		// se incrementa en 8 el width para compensar el borde de 4px
		setWidth(template.getWidth() + NumberConstants.FNUM8, Unit.PIXELS);

		frontButton = new Button(MessagesEVT.getInstance().getProperty("addFrontButton"));
		frontButton.setVisible(true);
		frontButton.setIcon(FontAwesome.FILE);

		frontButton.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4263501972778120718L;

			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("frontButtonClicked"));
				removeFrontButton.setVisible(true);
				frontButton.setVisible(false);
				orientationTop.setVisible(true);
				template.getFrontTemplate().setVisible(true);
			}
		});

		front.addComponent(frontButton);
		front.setComponentAlignment(frontButton, Alignment.MIDDLE_CENTER);

		removeFrontButton = new Button(MessagesEVT.getInstance().getProperty("removeFrontButton"));
		removeFrontButton.setIcon(FontAwesome.FILE);

		removeFrontButton.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -5924632131454611989L;

			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("frontRemoveButtonClicked"));
				removeFrontButton.setVisible(false);
				frontButton.setVisible(true);
				orientationTop.setVisible(false);
				template.getFrontTemplate().removeAllComponents();
				template.getFrontTemplate().setVisible(false);

			}
		});

		front.addComponent(removeFrontButton);
		front.setComponentAlignment(removeFrontButton, Alignment.MIDDLE_CENTER);
		front.addComponent(orientationTop);
		front.setComponentAlignment(orientationTop, Alignment.MIDDLE_CENTER);
		front.setSpacing(true);
		this.addComponent(front);

		removeFrontButton.setVisible(false);
		orientationTop.setVisible(false);

		LOG.info(MessagesEVT.getInstance().getProperty("newEmptyFrontTemplate"));
		template.setFrontTemplate(new Template());

		// Se carga la estructura de splits en el verticalLayout
		this.addComponent(template.getFrontTemplate());
		setComponentAlignment(template.getFrontTemplate(), Alignment.MIDDLE_CENTER);
		this.addComponent(orientationCenter);
		this.setComponentAlignment(orientationCenter, Alignment.MIDDLE_CENTER);
		template.getFrontTemplate().setFront(true);
		// Se añaade el listener para el click de ratón.
		template.getFrontTemplate().addLayoutClickListener(new LayoutTemplateClickListener(template.getFrontTemplate()));

		template.getFrontTemplate().setVisible(false);

		// Se carga la estructura de splits en el verticalLayout
		this.addComponent(template);
		setComponentAlignment(template, Alignment.MIDDLE_CENTER);

		// Se añaade el listener para el click de ratón.
		template.addLayoutClickListener(new LayoutTemplateClickListener(template));

		VerticalLayout last;
		last = new VerticalLayout();
		lastButton = new Button(MessagesEVT.getInstance().getProperty("addLastButton"));
		lastButton.setIcon(FontAwesome.FILE);
		last.addComponent(lastButton);
		last.setComponentAlignment(lastButton, Alignment.MIDDLE_CENTER);
		this.addComponent(last);

		lastButton.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -1532990670038341211L;

			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("lastButtonClicked"));
				removeLastButton.setVisible(true);
				orientationBottom.setVisible(true);
				lastButton.setVisible(false);
				template.getLastTemplate().setVisible(true);

			}
		});

		removeLastButton = new Button(MessagesEVT.getInstance().getProperty("removeLastButton"));
		removeLastButton.setIcon(FontAwesome.FILE);

		removeLastButton.addClickListener(new Button.ClickListener() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 5935166846712004808L;

			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info(MessagesEVT.getInstance().getProperty("lastRemoveButtonClicked"));
				removeLastButton.setVisible(false);
				lastButton.setVisible(true);
				orientationBottom.setVisible(false);
				template.getLastTemplate().removeAllComponents();
				template.getLastTemplate().setVisible(false);

			}
		});

		last.addComponent(removeLastButton);
		last.setComponentAlignment(removeLastButton, Alignment.MIDDLE_CENTER);
		last.addComponent(orientationBottom);
		last.setComponentAlignment(orientationBottom, Alignment.MIDDLE_CENTER);
		last.setSpacing(true);
		removeLastButton.setVisible(false);
		orientationBottom.setVisible(false);

		LOG.info(MessagesEVT.getInstance().getProperty("newEmptyLastTemplate"));
		template.setLastTemplate(new Template());

		// Se carga la estructura de splits en el verticalLayout
		this.addComponent(template.getLastTemplate());
		setComponentAlignment(template.getLastTemplate(), Alignment.MIDDLE_CENTER);
		template.getLastTemplate().setLast(true);
		// Se añaade el listener para el click de ratón.
		template.getLastTemplate().addLayoutClickListener(new LayoutTemplateClickListener(template.getLastTemplate()));
		template.getLastTemplate().setVisible(false);
		// Se crea para generar un espacio
		this.addComponent(new Label(" "));

		addComponent(createMenu());

	}

	/**
	 * Copy constructor method for the class DesignZone.java.
	 * @param old Old design zone to copy
	 */
	public DesignZone(DesignZone old) {
		this();

		LOG.info(MessagesEVT.getInstance().getProperty("designZoneCreation"));

		selectedOption = OPINSERTTEXT;

		// se incrementa en 8 el width para compensar el borde de 4px
		setWidth(old.getTemplate().getWidth() + NumberConstants.FNUM8, Unit.PIXELS);

		frontButton.setVisible(old.frontButton.isVisible());

		removeFrontButton.setVisible(old.removeFrontButton.isVisible());

		// copiar fronttemplate
		LOG.info(MessagesEVT.getInstance().getProperty("loadFrontTemplate"));

		template.getFrontTemplate().setVisible(old.getTemplate().getFrontTemplate().isVisible());
		template.getFrontTemplate().setWidth(old.getTemplate().getFrontTemplate().getWidth(), old.getTemplate().getFrontTemplate().getWidthUnits());
		template.getFrontTemplate().setHeight(old.getTemplate().getFrontTemplate().getHeight(), old.getTemplate().getFrontTemplate().getHeightUnits());
		template.getFrontTemplate().setRegionBeforeExtent(old.getTemplate().getFrontTemplate().getRegionBeforeExtent());
		template.getFrontTemplate().setRegionAfterExtent(old.getTemplate().getFrontTemplate().getRegionAfterExtent());
		template.getFrontTemplate().setRegionStartExtent(old.getTemplate().getFrontTemplate().getRegionStartExtent());
		template.getFrontTemplate().setRegionEndExtent(old.getTemplate().getFrontTemplate().getRegionEndExtent());
		for (Component component: old.getTemplate().getFrontTemplate().getAllComponents()) {

			Component comp = ((PositionAngle) component).newInstance();

			if (comp instanceof RichTable) {
				((RichTable) comp).visibleSplits(false);
			}

			template.getFrontTemplate().insertComponent(((PositionAngle) component).getPosX(), ((PositionAngle) component).getPosY(), comp);

			// Se resetea el ángulo para que vuelva a establecerse el estilo
			int angle = ((PositionAngle) component).getAngle();
			((PositionAngle) component).setAngle(0);
			((PositionAngle) component).setAngle(angle);
		}

		// copiar template
		LOG.info(MessagesEVT.getInstance().getProperty("loadMainTemplate"));

		template.setWidth(old.getTemplate().getWidth(), old.getTemplate().getWidthUnits());
		template.setHeight(old.getTemplate().getHeight(), old.getTemplate().getHeightUnits());
		template.setRegionBeforeExtent(old.getTemplate().getRegionBeforeExtent()); // 2cm
		template.setRegionAfterExtent(old.getTemplate().getRegionAfterExtent() + 1);
		template.setRegionStartExtent(old.getTemplate().getRegionStartExtent());
		template.setRegionEndExtent(old.getTemplate().getRegionEndExtent() + 1);
		for (Component component: old.getTemplate().getAllComponents()) {

			if (component instanceof Document) {
				Document doc = new Document(template);
				doc.setWidth(((PositionAngle) component).getWidth(), Unit.PIXELS);
				doc.setHeight(((PositionAngle) component).getHeight(), Unit.PIXELS);
				doc.setPosX(((Document) component).getPosX());
				doc.setPosY(((Document) component).getPosY());
				doc.setPosXCenter(((Document) component).getPosXCenter());
				doc.setPosYCenter(((Document) component).getPosYCenter());
				doc.setFront(((Document) component).isFront());
				template.insertComponent(((PositionAngle) component).getPosX(), ((PositionAngle) component).getPosY(), doc);
			} else {

				Component comp = ((PositionAngle) component).newInstance();

				if (comp instanceof RichTable) {
					((RichTable) comp).visibleSplits(false);
				}

				template.insertComponent(((PositionAngle) component).getPosX(), ((PositionAngle) component).getPosY(), comp);

				// Se resetea el ángulo para que vuelva a establecerse el estilo
				int angle = ((PositionAngle) component).getAngle();
				((PositionAngle) component).setAngle(0);
				((PositionAngle) component).setAngle(angle);
			}
		}

		lastButton.setVisible(old.lastButton.isVisible());

		removeLastButton.setVisible(old.removeLastButton.isVisible());

		// copiar lasttemplate
		LOG.info(MessagesEVT.getInstance().getProperty("loadLastTemplate"));

		template.getLastTemplate().setVisible(old.getTemplate().getLastTemplate().isVisible());
		template.getLastTemplate().setWidth(old.getTemplate().getLastTemplate().getWidth(), old.getTemplate().getLastTemplate().getWidthUnits());
		template.getLastTemplate().setHeight(old.getTemplate().getLastTemplate().getHeight(), old.getTemplate().getLastTemplate().getHeightUnits());
		template.getLastTemplate().setRegionBeforeExtent(old.getTemplate().getLastTemplate().getRegionBeforeExtent()); // 2cm
		template.getLastTemplate().setRegionAfterExtent(old.getTemplate().getLastTemplate().getRegionAfterExtent());
		template.getLastTemplate().setRegionStartExtent(old.getTemplate().getLastTemplate().getRegionStartExtent());
		template.getLastTemplate().setRegionEndExtent(old.getTemplate().getLastTemplate().getRegionEndExtent());
		for (Component component: old.getTemplate().getLastTemplate().getAllComponents()) {

			Component comp = ((PositionAngle) component).newInstance();

			if (comp instanceof RichTable) {
				((RichTable) comp).visibleSplits(false);
			}

			template.getLastTemplate().insertComponent(((PositionAngle) component).getPosX(), ((PositionAngle) component).getPosY(), comp);

			// Se resetea el ángulo para que vuelva a establecerse el estilo
			int angle = ((PositionAngle) component).getAngle();
			((PositionAngle) component).setAngle(0);
			((PositionAngle) component).setAngle(angle);
		}
		userGlobalVariables = old.userGlobalVariables;
	}

	/**
	 * Constructor method for the class DesignZone.java.
	 * @param width width for design zone
	 * @param height height for design zone
	 */
	public DesignZone(Float width, Float height) {
		super();
		LOG.debug(MessagesEVT.getInstance().getProperty("frontButtonClicked") + " width:" + width + " height:" + height);
		new DesignZone();
		template.setWidth(width, Unit.CM);
		template.setHeight(height, Unit.CM);

	}

	/**
	 * Method to create menú.
	 * @return layout that represents menu zone
	 */
	public final CssLayout createMenu() {
		LOG.info(MessagesEVT.getInstance().getProperty("creatingMenu"));

		CssLayout layout = new CssLayout();

		Dock dock;

		dock = new Dock();
		dock.addStyleName("dock_bottom");
		dock.setSize(NumberConstants.NUM50);
		dock.setSizeMax(NumberConstants.NUM150);
		dock.setAlignment(org.vaadin.sebastian.dock.Alignment.BOTTOM);
		dock.setLabelPosition(LabelPosition.TOP_CENTER);
		dock.addClickListener(new DockClickListenerDesignZone());

		DockItem itemImage;
		DockItem itemText;
		DockItem itemTable;
		DockItem itemBarcode;
		DockItem itemDocument;

		itemDocument = new DockItem(new ThemeResource(IMAGEDIR + File.separator + ConfigEVT.getInstance().getProperty("menuDocItem")));
		dock.addItem(itemDocument);

		itemImage = new DockItem(new ThemeResource(IMAGEDIR + File.separator + ConfigEVT.getInstance().getProperty("menuImageItem")));
		dock.addItem(itemImage);

		itemText = new DockItem(new ThemeResource(IMAGEDIR + File.separator + ConfigEVT.getInstance().getProperty("menuTextItem")));
		dock.addItem(itemText);

		itemTable = new DockItem(new ThemeResource(IMAGEDIR + File.separator + ConfigEVT.getInstance().getProperty("menuTableItem")));
		dock.addItem(itemTable);

		itemBarcode = new DockItem(new ThemeResource(IMAGEDIR + File.separator + ConfigEVT.getInstance().getProperty("menuCodeBarItem")));
		dock.addItem(itemBarcode);

		optionSelectedLabel = new Label();

		optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarTextoLabel"));
		optionSelectedLabel.addStyleName("dock_op");

		layout.addComponent(optionSelectedLabel);

		layout.addComponent(dock);

		return layout;
	}

	/** 
	 * <p>Inner Class that represents the menú options click listener.</p>
	 * <b>Project:</b><p>Template XSLT designer.</p>
	 * @version 1.0, 12/03/2015.
	 */
	class DockClickListenerDesignZone implements Serializable, DockClickListener {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 7101665666579820582L;

		/**
		 * {@inheritDoc}
		 * @see org.vaadin.sebastian.dock.events.DockClickListener#dockItemClicked(org.vaadin.sebastian.dock.events.DockClickEvent)
		 */
		@Override
		public void dockItemClicked(DockClickEvent event) {
			LOG.info(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED));
			if (event.getItemIndex() == 0) {
				LOG.debug(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED) + OPTION + MessagesEVT.getInstance().getProperty("opInsertarDocumentoLabel"));
				selectedOption = OPINSERTDOCUMENT;
				optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarDocumentoLabel"));
			} else if (event.getItemIndex() == 1) {
				LOG.debug(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED) + OPTION + MessagesEVT.getInstance().getProperty("opInsertarImagenLabel"));
				selectedOption = OPINSERTIMAGE;
				optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarImagenLabel"));
			} else if (event.getItemIndex() == 2) {
				LOG.debug(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED) + OPTION + MessagesEVT.getInstance().getProperty("opInsertarTextoLabel"));
				selectedOption = OPINSERTTEXT;
				optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarTextoLabel"));
			} else if (event.getItemIndex() == NumberConstants.NUM3) {
				LOG.debug(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED) + OPTION + MessagesEVT.getInstance().getProperty("opInsertarTablaLabel"));
				selectedOption = OPINSERTTABLE;
				optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarTablaLabel"));
			} else if (event.getItemIndex() == NumberConstants.NUM4) {
				LOG.debug(MessagesEVT.getInstance().getProperty(MENUOPTIONCLIKED) + OPTION + MessagesEVT.getInstance().getProperty("opInsertarBarcodeLabel"));
				selectedOption = OPINSERTCODEBAR;
				optionSelectedLabel.setCaption(MessagesEVT.getInstance().getProperty("opInsertarBarcodeLabel"));
			}
		}

	}

	/**
	 * Getter for selected menu option.
	 * @return selected menú option
	 */
	public static String getSelectedOption() {
		return selectedOption;
	}

	/**
	 * Getter for main template.
	 * @return main template
	 */
	public final Template getTemplate() {
		return template;
	}

	/**
	 * Method that closes all resize containers for component's edit mode.
	 */
	public final void closeContainers() {
		template.closeContainers();
	}

	/**
	 * Method that stores the userGlobalVariables attribute of the class FormVariable in order to save into the download template.
	 */
	public void storeGlobalVariables() {
		this.userGlobalVariables = FormVariable.getGlobalVariablesMap();
	}

	/**
	 * Method that load into the userGlobalVariables attribute of the class FormVariable the stored global variables. 
	 */
	public void loadGlobalVariables() {
		FormVariable.setGlobalVariablesMap(this.userGlobalVariables);
	}

	/**
	 * Method that gets the global variables used in the design zone.
	 * @return A list with the name of the global variables used.
	 */
	public static List<String> getGlobalVarUsed() {
		return globalVariablesUsed;
	}

	/**
	 * Method that updates the list of global variables used.
	 * @param globalVarUsed List of global variables used.
	 */
	public static void updateGlobalVarUsed(List<String> globalVarUsed) {
		globalVariablesUsed = globalVarUsed;
	}

}
