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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.widgetset.client.richTextArea.VRichTextArea.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.widgetset.client.richTextArea;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Util;
import com.vaadin.client.ui.Field;
import com.vaadin.client.ui.ShortcutActionHandler;
import com.vaadin.client.ui.ShortcutActionHandler.ShortcutActionHandlerOwner;
import com.vaadin.client.ui.TouchScrollDelegate;

import es.gob.afirma.evisortemplates.util.NumberConstants;

/** 
 * <p>Class that implements a basic client side rich text editor component.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 18/03/2015.
 */
public class VRichTextArea extends Composite implements Field, KeyPressHandler, KeyDownHandler, Focusable {

	/**
	 * The input node CSS classname.
	 */
	public static final String CLASSNAME = "v-richtextarea";

	/** For internal use only. May be removed or replaced in the future. */
	private String id;

	/** For internal use only. May be removed or replaced in the future. */
	private ApplicationConnection client;

	/** For internal use only. May be removed or replaced in the future. */
	private boolean immediate = false;

	/** For internal use only. May be removed or replaced in the future. */
	private RichTextArea rta;

	/**
	 * Attribute that represents the tool bar and formatter from rich text area. 
	 */
	private VRichTextToolbar formatter;

	/**
	 * Attribute that represents if formatter is a internal formatter. 
	 */
	private boolean internalFormatter = false;

	/** For internal use only. May be removed or replaced in the future. */
	private HTML html = new HTML();

	/**
	 * Attribute that represents the flow panel. 
	 */
	private final FlowPanel fp = new FlowPanel();

	/**
	 * Attribute that represents if the rich text area is enabled.
	 **/
	private boolean enabled = true;

	/**
	 * Attribute that represents the extra horizontal pixels value. 
	 */
	private int extraHorizontalPixels = -1;
	
	/**
	 * Attribute that represents the extra vertical pixels value. 
	 */
	private int extraVerticalPixels = -1;

	/** For internal use only. May be removed or replaced in the future. */
	private int maxLength = -1;

	/**
	 * Attribute that represents tool bar natural width value. 
	 */
	private int toolbarNaturalWidth = NumberConstants.NUM500;

	/** For internal use only. May be removed or replaced in the future. */
	private HandlerRegistration keyPressHandler;

	/**
	 * Attribute that represents the shortcut action handler. 
	 */
	private ShortcutActionHandlerOwner hasShortcutActionHandler;

	/**
	 * Attribute that represents if rich text area is in read only mode. 
	 */
	private boolean readOnly = false;

	/**
	 * Attribute that represents the auto grow width attribute from component. 
	 */
	private boolean autoGrowWidth;
	
	/**
	 * Attribute that represents the auto grow height attribute from component. 
	 */
	private boolean autoGrowHeight;

	/**
	 * Attribute that represents if component are initialised. 
	 */
	private boolean initialised = false;

	/**
	 * Attribute that represents blur handlers map. 
	 */
	private final Map<BlurHandler, HandlerRegistration> blurHandlers = new HashMap<BlurHandler, HandlerRegistration>();

	/**
	 * Constructor method for the class VRichTextArea.java. 
	 */
	public VRichTextArea() {
		createRTAComponents();
		initWidget(fp);
		setStyleName(CLASSNAME);
		fp.add(rta);

		TouchScrollDelegate.enableTouchScrolling(html, html.getElement());
	}

	/**
	 * Method that create the rich text area componentes.
	 */
	private void createRTAComponents() {
		if (formatter != null) {
			formatter.detachVRTA(this);
		}
		rta = new RichTextArea();
		// CHECKSTYLE:OFF To allow anonymous inner class size
		// greater than 20 lines.
		rta.addInitializeHandler(new InitializeHandler() {

			// CHECKSTYLE:ON

			@Override
			public void onInitialize(InitializeEvent ie) {
				initialised = true;
				IFrameElement fe = IFrameElement.as(rta.getElement());
				Document cd = fe.getContentDocument();
				if (cd == null) {
					return;
				}
				if (autoGrowHeight) {
					setAutoHeight();
				}
				if (autoGrowWidth) {
					setAutoWidth();
				}

				NodeList<com.google.gwt.dom.client.Element> heads = cd.getElementsByTagName("head");
				HeadElement head = null;
				if (heads != null && heads.getLength() > 0) {
					head = heads.getItem(0).cast();
				}

				if (head == null) {
					head = cd.getDocumentElement().getFirstChildElement().cast();

					if (head == null) {
						head = cd.createElement("head").cast();
						cd.insertFirst(head);
					}
				}
				LinkElement linkElement = cd.createLinkElement();
				linkElement.setRel("stylesheet");
				linkElement.setHref("./VAADIN/addons/richtexttoolbar/styles.css");
				linkElement.setType("text/css");
				head.appendChild(linkElement);
			}
		});
		rta.setWidth("100%");
		rta.addKeyDownHandler(this);
		if (formatter != null) {
			formatter.attachVRTA(this);
		}
		// Add blur handlers
		for (Entry<BlurHandler, HandlerRegistration> handler: blurHandlers.entrySet()) {

			// Remove old registration
			handler.getValue().removeHandler();

			// Add blur handlers
			addBlurHandler(handler.getKey());
		}
	}

	/**
	 * Method that enable/disable the rich text area.
	 * @param enabledrParam true if enable
	 */
	public final void setEnabled(boolean enabledrParam) {
		if (this.enabled != enabledrParam) {
			// rta.setEnabled(enabled);
			swapEditableArea();
			this.enabled = enabledrParam;
		}
	}

	/**
	 * Swaps html to rta and visa versa.
	 */
	private void swapEditableArea() {
		String value = getValue();
		if (html.isAttached()) {
			fp.remove(html);
			if (BrowserInfo.get().isWebkit()) {
				if (internalFormatter) {
					fp.remove(formatter);
				}
				createRTAComponents(); // recreate new RTA to bypass #5379
				if (internalFormatter) {
					fp.add(formatter);
				}
			}
			fp.add(rta);
		} else {
			fp.remove(rta);
			fp.add(html);
		}
		setValue(value);
	}

	/** For internal use only. May be removed or replaced in the future. */
	public final void selectAll() {
		/*
		 * There is a timing issue if trying to select all immediately on first
		 * render. Simple deferred command is not enough. Using Timer with
		 * moderated timeout. If this appears to fail on many (most likely slow)
		 * environments, consider increasing the timeout.
		 * 
		 * FF seems to require the most time to stabilize its RTA. On Vaadin
		 * tiergarden test machines, 200ms was not enough always (about 50%
		 * success rate) - 300 ms was 100% successful. This however was not
		 * enough on a sluggish old non-virtualized XP test machine. A bullet
		 * proof solution would be nice, GWT 2.1 might however solve these. At
		 * least setFocus has a workaround for this kind of issue.
		 */
		new Timer() {

			@Override
			public void run() {
				rta.getFormatter().selectAll();
			}
		}.schedule(NumberConstants.NUM320);
	}

	/**
	 * Method that sets if the rich text area are in read only mode.
	 * @param b true if the rich text area are in read only mode
	 */
	public final void setReadOnly(boolean b) {
		if (isReadOnly() != b) {
			swapEditableArea();
			readOnly = b;
		}
		// reset visibility in case enabled state changed and the formatter was
		// recreated
		if (internalFormatter) {
			formatter.setVisible(!readOnly);
		}
	}

	/**
	 * Method that gets if the rich text area are in read only mode.
	 * @return true if the rich text area are in read only mode, false otherwise
	 */
	private boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @return space used by components paddings and borders
	 */
	private int getExtraHorizontalPixels() {
		if (extraHorizontalPixels < 0) {
			detectExtraSizes();
		}
		return extraHorizontalPixels;
	}

	/**
	 * @return space used by components paddings and borders
	 */
	private int getExtraVerticalPixels() {
		if (extraVerticalPixels < 0) {
			detectExtraSizes();
		}
		return extraVerticalPixels;
	}

	/**
	 * Detects space used by components paddings and borders.
	 */
	private void detectExtraSizes() {
		Element clone = Util.cloneNode(getElement(), false);
		DOM.setElementAttribute(clone, "id", "");
		DOM.setStyleAttribute(clone, "visibility", "hidden");
		DOM.setStyleAttribute(clone, "position", "absolute");
		// due FF3 bug set size to 10px and later subtract it from extra pixels
		DOM.setStyleAttribute(clone, "width", "10px");
		DOM.setStyleAttribute(clone, "height", "10px");
		DOM.appendChild(DOM.getParent(getElement()), clone);
		extraHorizontalPixels = DOM.getElementPropertyInt(clone, "offsetWidth") - NumberConstants.NUM10;
		extraVerticalPixels = DOM.getElementPropertyInt(clone, "offsetHeight") - NumberConstants.NUM10;

		DOM.removeChild(DOM.getParent(getElement()), clone);
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
	 */
	@Override
	public final void setHeight(String height) {
		if (height.endsWith("px")) {
			float h = Float.parseFloat(height.substring(0, height.length() - 2));
			h -= getExtraVerticalPixels();
			if (h < 0) {
				h = 0;
			}

			super.setHeight(h + 2 + "px");
		} else {
			super.setHeight(height);
		}

		if (height == null || height.equals("")) {
			rta.setHeight("");
		} else {
			/*
			 * The formatter height will be initially calculated wrong so we
			 * delay the height setting so the DOM has had time to stabilize.
			 */
			Scheduler.get().scheduleDeferred(new Command() {

				@Override
				public void execute() {
					int editorHeight = getOffsetHeight() - getExtraVerticalPixels() - (internalFormatter ? formatter.getOffsetHeight() : 0);
					if (editorHeight < 0) {
						editorHeight = 0;
					}
					rta.setHeight(editorHeight + "px");
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public final void setWidth(String width) {
		if (width.endsWith("px")) {
			float w = Float.parseFloat(width.substring(0, width.length() - 2));
			w -= getExtraHorizontalPixels();
			if (w < 0) {
				w = 0;
			}

			super.setWidth(w + "px");
		} else if (width.equals("")) {
			/*
			 * IE cannot calculate the width of the 100% iframe correctly if
			 * there is no width specified for the parent. In this case we would
			 * use the toolbar but IE cannot calculate the width of that one
			 * correctly either in all cases. So we end up using a default width
			 * for a RichTextArea with no width definition in all browsers (for
			 * compatibility).
			 */

			super.setWidth(toolbarNaturalWidth + "px");
		} else {
			super.setWidth(width);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.KeyPressHandler#onKeyPress(com.google.gwt.event.dom.client.KeyPressEvent)
	 */
	@Override
	public final void onKeyPress(KeyPressEvent event) {
		if (maxLength >= 0) {
			Scheduler.get().scheduleDeferred(new Command() {

				@Override
				public void execute() {
					if (rta.getHTML().length() > maxLength) {
						rta.setHTML(rta.getHTML().substring(0, maxLength));
					}
				}
			});
		}
		if (autoGrowWidth) {
			Scheduler.get().scheduleDeferred(new Command() {

				@Override
				public void execute() {
					setAutoWidth();
				}
			});
		}
		if (autoGrowHeight) {
			Scheduler.get().scheduleDeferred(new Command() {

				@Override
				public void execute() {
					setAutoHeight();
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.event.dom.client.KeyDownHandler#onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent)
	 */
	@Override
	public final void onKeyDown(KeyDownEvent event) {
		// delegate to closest shortcut action handler
		// throw event from the iframe forward to the shortcuthandler
		ShortcutActionHandler shortcutHandler = getShortcutHandlerOwner().getShortcutActionHandler();
		if (shortcutHandler != null) {
			shortcutHandler.handleKeyboardEvent(com.google.gwt.user.client.Event.as(event.getNativeEvent()), ConnectorMap.get(client).getConnector(this));
		}
	}

	/**
	 * Method that gets the shortcut handler.
	 * @return the shortcut handler
	 */
	private ShortcutActionHandlerOwner getShortcutHandlerOwner() {
		if (hasShortcutActionHandler == null) {
			Widget parent = getParent();
			while (parent != null) {
				if (parent instanceof ShortcutActionHandlerOwner) {
					break;
				}
				parent = parent.getParent();
			}
			hasShortcutActionHandler = (ShortcutActionHandlerOwner) parent;
		}
		return hasShortcutActionHandler;
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Focusable#getTabIndex()
	 */
	@Override
	public final int getTabIndex() {
		return rta.getTabIndex();
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Focusable#setAccessKey(char)
	 */
	@Override
	public final void setAccessKey(char key) {
		rta.setAccessKey(key);
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Focusable#setFocus(boolean)
	 */
	@Override
	public final void setFocus(final boolean focused) {
		/*
		 * Similar issue as with selectAll. Focusing must happen before possible
		 * selectall, so keep the timeout here lower.
		 */
		new Timer() {

			@Override
			public void run() {
				BrowserInfo bi = BrowserInfo.get();
				if (focused && (bi.isChrome() || bi.isSafari())) {
					rta.getElement().focus();
				}
				rta.setFocus(focused);
			}
		}.schedule(NumberConstants.NUM300);
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Focusable#setTabIndex(int)
	 */
	@Override
	public final void setTabIndex(int index) {
		rta.setTabIndex(index);
	}

	/**
	 * Set the value of the text area.
	 * 
	 * @param value
	 *            The text value. Can be html.
	 */
	public final void setValue(String value) {
		if (rta.isAttached()) {
			rta.setHTML(value);
			if (initialised) {
				if (autoGrowWidth) {
					Scheduler.get().scheduleDeferred(new Command() {

						@Override
						public void execute() {
							setAutoWidth();
						}
					});
				}
				if (autoGrowHeight) {
					Scheduler.get().scheduleDeferred(new Command() {

						@Override
						public void execute() {
							setAutoHeight();
						}
					});
				}
			}
		} else {
			html.setHTML(value);
		}
	}

	/**
	 * Get the value the text area.
	 * @return value the text area
	 */
	public final String getValue() {
		if (isAttached() && rta.isAttached()) {
			return rta.getHTML();
		} else {
			return html.getHTML();
		}
	}

	/**
	 * Browsers differ in what they return as the content of a visually empty
	 * rich text area. This method is used to normalize these to an empty
	 * string. 
	 * 
	 * @return cleaned html string
	 */
	public final String getSanitizedValue() {
		String br = "<br>";
		BrowserInfo browser = BrowserInfo.get();
		String result = getValue();
		if (browser.isFirefox()) {
			result = getFirefoxResult(br, result);
		} else if (browser.isWebkit()) {
			if (br.equals(result) || "<div><br></div>".equals(result)) {
				result = "";
			}
		} else if (browser.isIE()) {
			if ("<P>&nbsp;</P>".equals(result)) {
				result = "";
			}
		} else if (browser.isOpera() && (br.equals(result) || "<p><br></p>".equals(result))) {

			result = "";
		}
		return result;
	}

	/**
	 * Method that gets result for firefox.
	 * @param br br code value
	 * @param result code
	 * @return result for firefox
	 */
	private String getFirefoxResult(String br, String result) {
		String finalResult = result;
		if (br.equals(result)) {
			finalResult = "";
		}
		if (!result.isEmpty() && result.endsWith(br) && !result.endsWith("<br><br>")) {
			finalResult = result.substring(0, result.length() - NumberConstants.NUM4);
		}
		return finalResult;
	}

	/**
	 * Adds a blur handler to the component.
	 * 
	 * @param blurHandler
	 *            the blur handler to add
	 */
	public final void addBlurHandler(BlurHandler blurHandler) {
		blurHandlers.put(blurHandler, rta.addBlurHandler(blurHandler));
	}

	/**
	 * Removes a blur handler.
	 * 
	 * @param blurHandler
	 *            the handler to remove
	 */
	public final void removeBlurHandler(BlurHandler blurHandler) {
		HandlerRegistration registration = blurHandlers.remove(blurHandler);
		if (registration != null) {
			registration.removeHandler();
		}
	}

	/**
	 * Gets the rich text area formatter.
	 * @return the rich text area formatter
	 */
	public final Formatter getRTAFormatter() {
		return rta.getFormatter();
	}

	/**
	 * Gets the rich text area component.
	 * @return the rich text area component
	 */
	public final RichTextArea getRTA() {
		return rta;
	}

	/**
	 * Sets the formatter for rich text area.
	 * @param formatterParam the formatter for rich text area
	 */
	public final void setFormatter(VRichTextToolbar formatterParam) {
		if (formatterParam == null) {
			return;
		}
		if (internalFormatter) {
			setInternalFormatter(false);
		} else {
			formatterParam.detachVRTA(this);
		}

		this.formatter = formatterParam;
		this.formatter.attachVRTA(this);
	}

	/**
	 * Change to internal formatter.
	 * @param internalFormatterParam true if internal formatter
	 */
	public final void setInternalFormatter(boolean internalFormatterParam) {
		if (this.internalFormatter == internalFormatterParam) {
			return;
		}

		if (this.internalFormatter && formatter != null) {
			fp.remove(formatter);
		}
		if (formatter != null) {
			formatter.detachVRTA(this);
		}

		this.internalFormatter = internalFormatterParam;

		if (!this.internalFormatter) {
			rta.setHeight("");
		} else {
			formatter = new VRichTextToolbar();
			fp.remove(rta);
			fp.add(formatter);
			fp.add(rta);
			formatter.attachVRTA(this);
		}
	}

	
	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected final void onUnload() {
		if (!readOnly) {
			if (internalFormatter) {
				fp.remove(formatter);
			}
			try {
				fp.remove(rta);
			} catch (Exception e) {}
		}
		if (formatter != null) {
			formatter.detachVRTA(this);
		}
		super.onUnload();
	}

	/**
	 * {@inheritDoc}
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected final void onLoad() {
		super.onLoad();
		if (formatter != null) {
			formatter.attachVRTA(this);
		}
		if (!readOnly) {
			// rta.setHTML(getValue());
			if (internalFormatter) {
				fp.add(formatter);
			}
			fp.add(rta);
		}
		if (client != null) {
			client.sendPendingVariableChanges();
		}
	}

	/**
	 * Method that changes to auto width mode.
	 */
	private void setAutoWidth() {
		IFrameElement fe = IFrameElement.as(rta.getElement());
		Document contentDocument = fe.getContentDocument();
		int w = contentDocument.getBody().getOffsetWidth();

		if (internalFormatter && w < formatter.getOffsetWidth()) {
			w = formatter.getOffsetWidth();
		}
		setWidth(w + "px");
	}

	/**
	 * Method that changes to auto height mode.
	 */
	private void setAutoHeight() {
		IFrameElement fe = IFrameElement.as(rta.getElement());
		Document contentDocument = fe.getContentDocument();
		BodyElement body = contentDocument.getBody();
		int minH = countLineBraks() * NumberConstants.NUM14 + NumberConstants.NUM4;
		rta.setHeight(minH + "px");
		// add 4 pixels to be on a safer side with different browsers
		int h = body.getScrollHeight() + NumberConstants.NUM4;
		rta.setHeight(h + "px");
	}

	/**
	 * Returns number of line breaks.
	 * @return number of line breaks
	 */
	private int countLineBraks() {
		String value = getValue();
		if (value == null) {
			return 1;
		}
		BrowserInfo browser = BrowserInfo.get();
		value = value.toLowerCase();
		if (browser.isFirefox()) {
			value = value.replaceAll("<br>", "\n");
		} else if (browser.isWebkit()) {
			value = value.replaceAll("</div>", "</div>\n");
		}
		if (browser.isIE() || browser.isOpera()) {
			value = value.replaceAll("</p>", "</p>\n");
		}
		return value.split("\n").length;

	}

	/**
	 * Gets the id of component.
	 * @return the id of component
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Sets the id of component.
	 * @param idParam new id of component
	 */
	public final void setId(String idParam) {
		this.id = idParam;
	}

	/**
	 * Gets the client AplicationConnection.
	 * @return the client AplicationConnection
	 */
	public final ApplicationConnection getClient() {
		return client;
	}

	/**
	 * Sets a client AplicationConnection.
	 * @param clientParam new client AplicationConnection
	 */
	public final void setClient(ApplicationConnection clientParam) {
		this.client = clientParam;
	}

	/**
	 * Method that checks if the component are in immediate mode.
	 * @return true if the component are in immediate mode, false otherwise
	 */
	public final boolean isImmediate() {
		return immediate;
	}

	/**
	 * Method that sets if the component are in immediate mode.
	 * @param immediateParam true if the component are in immediate mode
	 */
	public final void setImmediate(boolean immediateParam) {
		this.immediate = immediateParam;
	}

	/**
	 * Gets the rich text area component.
	 * @return the rich text area component
	 */
	public final RichTextArea getRta() {
		return rta;
	}

	/**
	 * Sets the rich text area component.
	 * @param rtaParam the rich text area component
	 */
	public final void setRta(RichTextArea rtaParam) {
		this.rta = rtaParam;
	}

	/**
	 * Gets the max length value.
	 * @return the max length value
	 */
	public final int getMaxLength() {
		return maxLength;
	}

	/**
	 * Sets the max length value.
	 * @param maxLengthParam new max length value
	 */
	public final void setMaxLength(int maxLengthParam) {
		this.maxLength = maxLengthParam;
	}

	/**
	 * Gets the key press handler.
	 * @return the key press handler
	 */
	public final HandlerRegistration getKeyPressHandler() {
		return keyPressHandler;
	}

	/**
	 * Sets the key press handler.
	 * @param keyPressHandlerParam new key press handler
	 */
	public final void setKeyPressHandler(HandlerRegistration keyPressHandlerParam) {
		this.keyPressHandler = keyPressHandlerParam;
	}

	/**
	 * Method that checks if is in auto grow width.
	 * @return true if is in auto grow width, false otherwise
	 */
	public final boolean isAutoGrowWidth() {
		return autoGrowWidth;
	}

	/**
	 * Method that sets if is in auto grow width.
	 * @param autoGrowWidthParam true if is in auto grow width
	 */
	public final void setAutoGrowWidth(boolean autoGrowWidthParam) {
		this.autoGrowWidth = autoGrowWidthParam;
	}

	/**
	 * Method that checks if is in auto grow height.
	 * @return true if is in auto grow height, false otherwise
	 */
	public final boolean isAutoGrowHeight() {
		return autoGrowHeight;
	}

	/**
	 * Method that sets if is in auto grow height.
	 * @param autoGrowHeightParam true if is in auto grow height
	 */
	public final void setAutoGrowHeight(boolean autoGrowHeightParam) {
		this.autoGrowHeight = autoGrowHeightParam;
	}

}
