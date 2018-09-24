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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.RichTextArea.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.Map;

import com.vaadin.data.Property;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.LegacyComponent;

/** 
 * <p>Class represents rich text areas.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public class RichTextArea extends AbstractField<String> implements Component, LegacyComponent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7970766041410642590L;

	/**
	 * Null representation.
	 */
	private String nullRepresentation = "null";

	/**
	 * Is setting to null from non-null value allowed by setting with null
	 * representation .
	 */
	private boolean nullSettingAllowed = false;

	/**
	 * Attribute that represents if auto grow width. 
	 */
	private boolean autoGrowWidth = false;
	
	/**
	 * Attribute that represents if auto grow height. 
	 */
	private boolean autoGrowHeight = false;

	/**
	 * Temporary flag that indicates all content will be selected after the next
	 * paint. Reset to false after painted.
	 */
	private boolean selectAll = false;

	/**
	 * Attribute that represents internal formatter. 
	 */
	private boolean internalFormatter = true;
	
	/**
	 * Attribute that represents font family. 
	 */
	private String fontFamily = null;
	
	/**
	 * Attribute that represents font size. 
	 */
	private String fontSize = null;
	
	/**
	 * Attribute that represents toolbar for rich text area. 
	 */
	private RichTextToolbar toolbar = null;

	/**
	 * Constructs an empty <code>RichTextArea</code> with no caption.
	 */
	public RichTextArea() {
		setValue("");
	}

	/**
	 * 
	 * Constructs an empty <code>RichTextArea</code> with the given caption.
	 * 
	 * @param caption
	 *            the caption for the editor.
	 */
	public RichTextArea(String caption) {
		this();
		setCaption(caption);
	}

	/**
	 * Constructs a new <code>RichTextArea</code> that's bound to the specified
	 * <code>Property</code> and has no caption.
	 * 
	 * @param dataSource
	 *            the data source for the editor value
	 */
	public RichTextArea(Property dataSource) {
		setPropertyDataSource(dataSource);
	}

	/**
	 * Constructs a new <code>RichTextArea</code> that's bound to the specified
	 * <code>Property</code> and has the given caption.
	 * 
	 * @param caption
	 *            the caption for the editor.
	 * @param dataSource
	 *            the data source for the editor value
	 */
	public RichTextArea(String caption, Property dataSource) {
		this(dataSource);
		setCaption(caption);
	}

	/**
	 * Constructs a new <code>RichTextArea</code> with the given caption and
	 * initial text contents.
	 * 
	 * @param caption
	 *            the caption for the editor.
	 * @param value
	 *            the initial text content of the editor.
	 */
	public RichTextArea(String caption, String value) {
		setValue(value);
		setCaption(caption);
	}

	/**
	 * Set the automatically adjust height mode. If enabled the height is
	 * automatically growing and shrinking with the value.
	 * @param inAutoGrowHeight true if automatically growing and shrinking with the value
	 */
	public final void setAutoGrowHeight(boolean inAutoGrowHeight) {
		if (inAutoGrowHeight == this.autoGrowHeight) {
			return;
		}
		this.autoGrowHeight = inAutoGrowHeight;
		markAsDirty();
	}

	/**
	 * Is the height automatically growing and shrinking with the value.
	 * 
	 * @return boolean Is automatic adjustment enabled
	 */
	public final boolean isAutoGrowHeight() {
		return autoGrowHeight;
	}

	/**
	 * Method that gets tool bar of rich text area.
	 * @return tool bar of rich text area
	 */
	public final RichTextToolbar getToolbar() {
		return toolbar;
	}

	/**
	 * Method that sets tool bar of rich text area.
	 * @param toolbarParam new tool bar for rich text area
	 */
	public final void setToolbar(RichTextToolbar toolbarParam) {
		if (toolbarParam == null) {
			this.toolbar = null;
			this.internalFormatter = true;
		} else if (this.toolbar == null || toolbarParam.equals(this.toolbar)) {
			this.internalFormatter = false;
			this.toolbar = toolbarParam;
		} else {
			return;
		}
		markAsDirty();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.LegacyComponent#paintContent(com.vaadin.server.PaintTarget)
	 */
	@Override
	public final void paintContent(PaintTarget target) throws PaintException {
		target.addAttribute("internalFormatter", internalFormatter);
		if (this.toolbar != null) {
			target.addAttribute("toolbar", this.toolbar);
		}
		if (fontFamily != null) {
			target.addAttribute("fontFamily", fontFamily);
		}
		if (fontSize != null) {
			target.addAttribute("fontSize", fontSize);
		}

		if (selectAll) {
			target.addAttribute("selectAll", true);
			selectAll = false;
		}

		if (autoGrowHeight) {
			target.addAttribute("autoGrowHeight", autoGrowHeight);
		}
		if (autoGrowWidth) {
			target.addAttribute("autoGrowWidth", autoGrowWidth);
		}

		// Adds the content as variable
		String value = getValue();
		if (value == null) {
			value = getNullRepresentation();
		}
		if (value == null) {
			throw new IllegalStateException("Null values are not allowed if the null-representation is null");
		}
		target.addVariable(this, "text", value);

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractField#setReadOnly(boolean)
	 */
	@Override
	public final void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		if (readOnly) {
			addStyleName("v-richtextarea-readonly");
		} else {
			removeStyleName("v-richtextarea-readonly");
		}
	}

	/**
	 * Selects all text in the rich text area. As a side effect, focuses the
	 * rich text area.
	 * 
	 */
	public final void selectAll() {
		selectAll = true;
		focus();
		markAsDirty();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.server.VariableOwner#changeVariables(java.lang.Object, java.util.Map)
	 */
	@Override
	public final void changeVariables(Object source, Map<String, Object> variables) {
		// Sets the text
		if (variables.containsKey("text") && !isReadOnly()) {

			// Only do the setting if the string representation of the value
			// has been updated
			String newValue = (String) variables.get("text");

			final String oldValue = getValue();
			if (newValue != null && (oldValue == null || isNullSettingAllowed()) && newValue.equals(getNullRepresentation())) {
				newValue = null;
			}
			if (newValue == null || !newValue.equals(oldValue)) {
				boolean wasModified = isModified();
				setValue(newValue, true);

				// If the modified status changes,
				// repaint is needed after all.
				if (wasModified != isModified()) {
					markAsDirty();
				}
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractField#getType()
	 */
	@Override
	public final Class<String> getType() {
		return String.class;
	}

	/**
	 * Gets the null-string representation.
	 * 
	 * <p>
	 * The null-valued strings are represented on the user interface by
	 * replacing the null value with this string. If the null representation is
	 * set null (not 'null' string), painting null value throws exception.
	 * </p>
	 * 
	 * <p>
	 * The default value is string 'null'.
	 * </p>
	 * 
	 * @return the String Textual representation for null strings.
	 * @see TextField#isNullSettingAllowed()
	 */
	public final String getNullRepresentation() {
		return nullRepresentation;
	}

	/**
	 * Is setting nulls with null-string representation allowed.
	 * 
	 * <p>
	 * If this property is true, writing null-representation string to text
	 * field always sets the field value to real null. If this property is
	 * false, null setting is not made, but the null values are maintained.
	 * Maintenance of null-values is made by only converting the textfield
	 * contents to real null, if the text field matches the null-string
	 * representation and the current value of the field is null.
	 * </p>
	 * 
	 * <p>
	 * By default this setting is false
	 * </p>
	 * 
	 * @return boolean Should the null-string represenation be always converted
	 *         to null-values.
	 * @see TextField#getNullRepresentation()
	 */
	public final boolean isNullSettingAllowed() {
		return nullSettingAllowed;
	}

	/**
	 * Sets the null-string representation.
	 * 
	 * <p>
	 * The null-valued strings are represented on the user interface by
	 * replacing the null value with this string. If the null representation is
	 * set null (not 'null' string), painting null value throws exception.
	 * </p>
	 * 
	 * <p>
	 * The default value is string 'null'
	 * </p>
	 * 
	 * @param nullRepresentationParam
	 *            Textual representation for null strings.
	 * @see TextField#setNullSettingAllowed(boolean)
	 */
	public final void setNullRepresentation(String nullRepresentationParam) {
		this.nullRepresentation = nullRepresentationParam;
	}

	/**
	 * Sets the null conversion mode.
	 * 
	 * <p>
	 * If this property is true, writing null-representation string to text
	 * field always sets the field value to real null. If this property is
	 * false, null setting is not made, but the null values are maintained.
	 * Maintenance of null-values is made by only converting the textfield
	 * contents to real null, if the text field matches the null-string
	 * representation and the current value of the field is null.
	 * </p>
	 * 
	 * <p>
	 * By default this setting is false.
	 * </p>
	 * 
	 * @param nullSettingAllowedParam
	 *            Should the null-string represenation be always converted to
	 *            null-values.
	 * @see TextField#getNullRepresentation()
	 */
	public final void setNullSettingAllowed(boolean nullSettingAllowedParam) {
		this.nullSettingAllowed = nullSettingAllowedParam;
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractField#isEmpty()
	 */
	@Override
	protected final boolean isEmpty() {
		return super.isEmpty() || getValue().length() == 0;
	}

}
