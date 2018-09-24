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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.richTextArea.VRichTextToolbar.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.richTextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that implements a basic client side rich text tool bar component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
public class VRichTextToolbar extends Composite {

	/**
	 * We use an inner EventHandler class to avoid exposing event methods on the
	 * RichTextToolbar itself.
	 */
	private class EventHandler implements ClickHandler, ChangeHandler, KeyUpHandler {

		@Override
		public void onChange(ChangeEvent event) {
			Object sender = event.getSource();
			if (currentRTA == null) {
				return;
			}
			Formatter formatter = currentRTA.getRTAFormatter();
			if (sender.equals(backColors)) {
				formatter.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
				backColors.setSelectedIndex(0);
			} else if (sender.equals(foreColors)) {
				formatter.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
				foreColors.setSelectedIndex(0);
			} else if (sender.equals(fonts)) {
				formatter.setFontName(fonts.getValue(fonts.getSelectedIndex()));
				fonts.setSelectedIndex(0);
			} else if (sender.equals(fontSizes)) {
				formatter.setFontSize(FONTSIZESCONSTANTS[fontSizes.getSelectedIndex() - 1]);
				fontSizes.setSelectedIndex(0);
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Object sender = event.getSource();
			if (currentRTA == null) {
				return;
			}
			Formatter formatter = currentRTA.getRTAFormatter();
			if (sender.equals(bold)) {
				formatter.toggleBold();
			} else if (sender.equals(italic)) {
				formatter.toggleItalic();
			} else if (sender.equals(underline)) {
				formatter.toggleUnderline();
			} else if (sender.equals(currentRTA.getRTA())) {
				// We use the RichTextArea's onKeyUp event to update the
				// toolbar
				// status. This will catch any cases where the user moves the
				// cursur using the keyboard, or uses one of the browser's
				// built-in keyboard shortcuts.
				updateStatus();
			} else {
				for (VRichTextArea rta: attachedRTAs) {
					if (sender.equals(rta.getRTA())) {
						currentRTA = rta;
						updateStatus();
					}
				}
			}
		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (currentRTA == null) {
				return;
			}
			if (event.getSource() == currentRTA.getRTA()) {
				// We use the RichTextArea's onKeyUp event to update the toolbar
				// status. This will catch any cases where the user moves the
				// cursor using the keyboard, or uses one of the browser's
				// built-in keyboard shortcuts.
				updateStatus();
			}
		}
	}

	/**
	 * The input node CSS classname.
	 */
	public static final String CLASSNAME = "v-richtexttoolbar";


	/**
	 * Attribute that represents font sizes constants. 
	 */
	private static final RichTextArea.FontSize[] FONTSIZESCONSTANTS = new RichTextArea.FontSize[ ] { RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE, RichTextArea.FontSize.XX_LARGE };

	/**
	 * Attribute that represents event handler for component. 
	 */
	private final EventHandler handler = new EventHandler();

	/**
	 * Attribute that represents current rich text area. 
	 */
	private VRichTextArea currentRTA;
	
	/**
	 * Attribute that represents list of attached rich text area. 
	 */
	private final List<VRichTextArea> attachedRTAs = new ArrayList<VRichTextArea>();
	
	/**
	 * Attribute that represents a map with rich text area handlers. 
	 */
	private final Map<VRichTextArea, List<HandlerRegistration>> rtaHandlers = new HashMap<VRichTextArea, List<HandlerRegistration>>();

	/**
	 * Attribute that represents if component show form in a single line.. 
	 */
	private boolean singleLinePanel = false;

	/**
	 * Attribute that represents flow panel. 
	 */
	private final FlowPanel panel = new FlowPanel();
	
	/**
	 * Attribute that represents the bold button. 
	 */
	private ToggleButton bold;
	
	/**
	 * Attribute that represents the italic button. 
	 */
	private ToggleButton italic;
	
	/**
	 * Attribute that represents the underline button. 
	 */
	private ToggleButton underline;

	/**
	 * Attribute that represents the back colors combo box. 
	 */
	private ListBox backColors;
	
	/**
	 * Attribute that represents the fore colors combo box. 
	 */
	private ListBox foreColors;
	
	/**
	 * Attribute that represents the fonts combo box. 
	 */
	private ListBox fonts;
	
	/**
	 * Attribute that represents the font sizes combo box. 
	 */
	private ListBox fontSizes;

	/**
	 * Attribute that represents spacer. 
	 */
	private SimplePanel spacer;

	
	/**
	 * Constructor method for the class VRichTextToolbar.java. 
	 */
	public VRichTextToolbar() {

		initWidget(panel);
		setStyleName(CLASSNAME);

		bold = createToggleButton("rtt-b");
		panel.add(bold);
		italic = createToggleButton("rtt-i");
		panel.add(italic);
		underline = createToggleButton("rtt-u");
		panel.add(underline);
		spacer = new SimplePanel();
		spacer.getElement().addClassName("rtt-spacer");
		panel.add(spacer);

		SimplePanel div = new SimplePanel(fonts);
		backColors = createColorList(Character.toString((char) NumberConstants.NUM_0XF14B));
		backColors.getElement().addClassName("rtt-bgselect");
		div = new SimplePanel(backColors);
		div.getElement().addClassName("rtt-bgcolor");
		panel.add(div);

		foreColors = createColorList(Character.toString((char) NumberConstants.NUM_0XF040));
		foreColors.getElement().addClassName("rtt-fselect");
		div = new SimplePanel(foreColors);
		div.getElement().addClassName("rtt-fcolor");
		panel.add(div);

		fonts = createFontList();
		fonts.getElement().addClassName("rtt-familyselect");
		div = new SimplePanel(fonts);
		div.getElement().addClassName("rtt-family");
		panel.add(div);

		fontSizes = createFontSizes();
		fontSizes.getElement().addClassName("rtt-sizeselect");
		div = new SimplePanel(fontSizes);
		div.getElement().addClassName("rtt-size");
		panel.add(div);
	}

	/**
	 * Method that creates the color combo box.
	 * @param text first icon
	 * @return the color combo box
	 */
	private ListBox createColorList(String text) {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);
		lb.addItem(text, "");
		lb.addItem("", "white");
		lb.addItem("", "black");
		lb.addItem("", "red");
		lb.addItem("", "green");
		lb.addItem("", "yellow");
		lb.addItem("", "blue");
		NodeList<Node> children = lb.getElement().getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			OptionElement o = children.getItem(i).cast();
			o.getStyle().setBackgroundColor(o.getValue());
		}
		lb.setTabIndex(-1);
		return lb;
	}

	/**
	 * Method that creates the font combo box.
	 * @return the font combo box
	 */
	private ListBox createFontList() {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);

		lb.addItem(Character.toString((char) NumberConstants.NUM_0XF031), "");
		lb.addItem("Times-Roman", "Times-Roman");
		lb.addItem("Helvetica", "Helvetica");
		lb.addItem("Courier", "Courier");
		lb.addItem("serif", "serif");
		lb.addItem("sans-serif", "sans-serif");
		lb.addItem("monospace", "monospace");
		lb.setTabIndex(-1);
		return lb;
	}

	/**
	 * Method that creates the font sizes combo box.
	 * @return the font sizes combo box
	 */
	private ListBox createFontSizes() {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);

		lb.addItem(Character.toString((char) NumberConstants.NUM_0XF034));
		lb.addItem("XXS");
		lb.addItem("XS");
		lb.addItem("S");
		lb.addItem("M");
		lb.addItem("L");
		lb.addItem("XL");
		lb.addItem("XXL");
		lb.setTabIndex(-1);
		return lb;
	}

	/**
	 * Method that creates the toggle button.
	 * @param className name of class
	 * @return the toggle button
	 */
	private ToggleButton createToggleButton(String className) {
		final ToggleButton tb = new ToggleButton();
		tb.getElement().addClassName(className);
		tb.addClickHandler(handler);
		tb.setTabIndex(-1);
		return tb;
	}

	/**
	 * Updates the status of all the stateful buttons.
	 */
	private void updateStatus() {
		if (currentRTA == null) {
			return;
		}
		Formatter formatter = currentRTA.getRTAFormatter();
		bold.setDown(formatter.isBold());
		italic.setDown(formatter.isItalic());
		underline.setDown(formatter.isUnderlined());
	}

	/**
	 * Sets if component show form in a single line.
	 * @param singleLinePanelParam true if component show form in a single line
	 */
	public final void setSingleLinePanel(boolean singleLinePanelParam) {
		if (this.singleLinePanel == singleLinePanelParam) {
			return;
		} else if (singleLinePanelParam) {
			spacer.setVisible(false);
		} else {
			spacer.setVisible(true);
		}
		this.singleLinePanel = singleLinePanelParam;
	}

	/**
	 * Method that adds a rich text area to list of attached rich text area.
	 * @param vrta rich text area to be added
	 */
	public final void attachVRTA(VRichTextArea vrta) {
		if (!attachedRTAs.contains(vrta)) {
			attachedRTAs.add(vrta);
			List<HandlerRegistration> handlerregs = new ArrayList<HandlerRegistration>(2);
			handlerregs.add((vrta).getRTA().addKeyUpHandler(handler));
			handlerregs.add((vrta).getRTA().addClickHandler(handler));
			rtaHandlers.put(vrta, handlerregs);
			if (currentRTA == null) {
				currentRTA = attachedRTAs.get(0);
			}
		}
	}

	/**
	 * Method that removes a rich text area to list of attached rich text area.
	 * @param vrta rich text area to be removed
	 */
	public final void detachVRTA(VRichTextArea vrta) {
		if (vrta == null || !attachedRTAs.contains(vrta)) {
			return;
		}
		for (HandlerRegistration hr: rtaHandlers.get(vrta)) {
			if (hr != null) {
				hr.removeHandler();
			}
		}
		attachedRTAs.remove(vrta);
		if (currentRTA != null && currentRTA.equals(vrta)) {
			if (attachedRTAs.size() == 0) {
				currentRTA = null;
			} else {
				currentRTA = attachedRTAs.get(0);
			}
		}
	}

}
