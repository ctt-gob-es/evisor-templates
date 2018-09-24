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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.impl.Template.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.components.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;
import es.gob.afirma.evisortemplates.util.NumberConstants;
import es.gob.afirma.evisortemplates.util.PixelToCm;
import es.gob.afirma.evisortemplates.windows.ForEachWindow;

/** 
 * <p>Class that represents a concrete template.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public class Template extends AbsoluteLayout {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1066933996336614616L;

	/**
	 * Attribute that represents the Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(Template.class);

	/**
	 * Attribute that represents first horizontal split. 
	 */
	private HorizontalSplitPanel splitHorizontal;

	/**
	 * Attribute that represents second horizontal split. 
	 */
	private HorizontalSplitPanel splitHorizontal2;

	/**
	 * Attribute that represents first vertical split. 
	 */
	private VerticalSplitPanel splitVertical;

	/**
	 * Attribute that represents second vertical split. 
	 */
	private VerticalSplitPanel splitVertical2;

	/**
	 * Attribute that represents template's margin top. 
	 */
	private float marginTopTemplate = 0;

	/**
	 * Attribute that represents template's margin bottom. 
	 */
	private float marginBottomTemplate = 0;

	/**
	 * Attribute that represents template's margin left. 
	 */
	private float marginLeftTemplate = 0;

	/**
	 * Attribute that represents template's margin right. 
	 */
	private float marginRightTemplate = 0;

	/**
	 * Attribute that represents body's margin top. 
	 */
	private float marginTopBody = 0;

	/**
	 * Attribute that represents body's margin bottom. 
	 */
	private float marginBottomBody = 0;

	/**
	 * Attribute that represents body's margin left. 
	 */
	private float marginLeftBody = 0;

	/**
	 * Attribute that represents bod's margin right. 
	 */
	private float marginRightBody = 0;

	/**
	 * Attribute that represents content's margin top. 
	 */
	private List<Component> marginTopContent;

	/**
	 * Attribute that represents content's margin bottom. 
	 */
	private List<Component> marginBottomContent;

	/**
	 * Attribute that represents content's margin right. 
	 */
	private List<Component> marginRightContent;

	/**
	 * Attribute that represents content's margin left. 
	 */
	private List<Component> marginLeftContent;

	/**
	 * Attribute that represents signed document component. 
	 */
	private Document document;

	/**
	 * Attribute that represents the template's central zone component's list. 
	 */
	private List<Component> center;

	/**
	 * Attribute that represents if the template is or not a front template. 
	 */
	private boolean isFront;

	/**
	 * Attribute that represents if the template is or not a last template. 
	 */
	private boolean isLast;

	/**
	 * Attribute that represents the main template's front template. 
	 */
	private Template frontTemplate;

	/**
	 * Attribute that represents the main template's last template. 
	 */
	private Template lastTemplate;

	/**
	 * Attribute that represents "cm\" String constant. 
	 */
	private static final String CM = "cm\" ";

	/**
	 * Attribute that represents "cm\">\n" String constant. 
	 */
	private static final String CMNEWLINE = "cm\">\n";

	/**
	 * Attribute that represents "cm\"/>\n" String constant. 
	 */
	private static final String CMENDNEWLINE = "cm\"/>\n";

	/**
	 * Attribute that represents "/fo:static-content>\n" String constant. 
	 */
	private static final String FINALSTATICCONTENT = "</fo:static-content>\n";

	/**
	 * Attribute that represents "<fo:block/>\n" String constant. 
	 */
	private static final String BLOCKNEWLINE = "<fo:block/>\n";

	/**
	 * Constructor method for the class Template.java.
	 */
	public Template() {
		super();

		LOG.info(MessagesEVT.getInstance().getProperty("newEmptyTemplate"));

		setStyleName("hideOverflow");

		// Se crea la estructura de splits
		splitHorizontal2 = new HorizontalSplitPanel();
		splitHorizontal2.setSizeFull();

		splitHorizontal = new HorizontalSplitPanel();
		splitHorizontal.setSizeFull();

		splitVertical2 = new VerticalSplitPanel();
		splitVertical2.setSizeFull();

		splitVertical = new VerticalSplitPanel();
		splitVertical.setSizeFull();

		setWidth(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defalutTemplateWidth")))), Unit.PIXELS); // 21cm
		setHeight(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defalutTemplateHeight")))), Unit.PIXELS); // 29.7cm

		addStyleName("drop-area");
		addComponent(splitHorizontal);
		addComponent(splitHorizontal2);
		addComponent(splitVertical);
		addComponent(splitVertical2);

		setRegionBeforeExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionBeforeSize"))))); // 2cm
		// Se suma 1 para compensar el tamaño del propio split
		setRegionAfterExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionAfterSize")))) + 1);
		setRegionStartExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionStartSize")))));
		// Se suma 1 para compensar el tamaño del propio split
		setRegionEndExtent(Math.round(PixelToCm.cmToPx(Double.parseDouble(ConfigEVT.getInstance().getProperty("defaultRegionEndSize"))) + 1));

		marginTopContent = new ArrayList<Component>();
		marginBottomContent = new ArrayList<Component>();
		marginRightContent = new ArrayList<Component>();
		marginLeftContent = new ArrayList<Component>();
		center = new ArrayList<Component>();
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbstractComponentContainer#removeAllComponents()
	 */
	public final void removeAllComponents() {
		int i;

		i = marginTopContent.size() - 1;
		while (i >= 0) {
			this.removeComponent(marginTopContent.get(i));
			i--;
		}
		marginTopContent = new ArrayList<Component>();

		i = marginBottomContent.size() - 1;
		while (i >= 0) {
			this.removeComponent(marginBottomContent.get(i));
			i--;
		}
		marginBottomContent = new ArrayList<Component>();

		i = marginRightContent.size() - 1;
		while (i >= 0) {
			this.removeComponent(marginRightContent.get(i));
			i--;
		}
		marginRightContent = new ArrayList<Component>();

		i = marginLeftContent.size() - 1;
		while (i >= 0) {
			this.removeComponent(marginLeftContent.get(i));
			i--;
		}
		marginLeftContent = new ArrayList<Component>();

		i = center.size() - 1;
		while (i >= 0) {
			this.removeComponent(center.get(i));
			i--;
		}
		center = new ArrayList<Component>();
	}

	/**
	 * Method that sets a start extent region's value in pixels.
	 * @param var new start extent region's value in pixels
	 */
	public final void setRegionStartExtent(float var) {
		splitHorizontal.setSplitPosition(var, Unit.PIXELS);
	}

	/**
	 * Method that sets a end extent region's value in pixels.
	 * @param var new end extent region's value in pixels
	 */
	public final void setRegionEndExtent(float var) {
		splitHorizontal2.setSplitPosition(getWidth() - var, Unit.PIXELS);
	}

	/**
	 * Method that sets a before extent region's value in pixels.
	 * @param var new before extent region's value in pixels
	 */
	public final void setRegionBeforeExtent(float var) {
		splitVertical.setSplitPosition(var, Unit.PIXELS);
	}

	/**
	 * Method that sets a after extent region's value in pixels.
	 * @param var new after extent region's value in pixels
	 */
	public final void setRegionAfterExtent(float var) {
		splitVertical2.setSplitPosition(getHeight() - var, Unit.PIXELS);
	}

	/**
	 * Method that gets a before extent region's value in centimeters.
	 * @return before extent region's value in centimeters
	 */
	public final String getRegionBeforeExtentCm() {
		return PixelToCm.pxToCm(getRegionBeforeExtent()) + "";
	}

	/**
	 * Method that gets a end extent region's value in centimeters.
	 * @return end extent region's value in centimeters
	 */
	public final String getRegionEndExtentCm() {
		return PixelToCm.pxToCm(getRegionEndExtent()) + "";
	}

	/**
	 * Method that gets a start extent region's value in centimeters.
	 * @return start extent region's value in centimeters
	 */
	public final String getRegionStartExtentCm() {

		return PixelToCm.pxToCm(getRegionStartExtent()) + "";
	}

	/**
	 * Method that gets a after extent region's value in centimeters.
	 * @return after extent region's value in centimeters
	 */
	public final String getRegionAfterExtentCm() {
		return PixelToCm.pxToCm(getRegionAfterExtent()) + "";

	}

	/**
	 * Method that gets a before extent region's value in pixels.
	 * @return before extent region's value in pixels
	 */
	public final float getRegionBeforeExtent() {
		return splitVertical.getSplitPosition();
	}

	/**
	 * Method that gets a end extent region's value in pixels.
	 * @return end extent region's value in pixels
	 */
	public final float getRegionEndExtent() {
		// Se suma un pixel para compensar el tamaño del split
		return getWidth() - (splitHorizontal2.getSplitPosition() + 1);
	}

	/**
	 * Method that gets a start extent region's value in pixels.
	 * @return start extent region's value in pixels
	 */
	public final float getRegionStartExtent() {

		return splitHorizontal.getSplitPosition();

	}

	/**
	 * Method that gets a after extent region's value in pixels.
	 * @return after extent region's value in pixels
	 */
	public final float getRegionAfterExtent() {
		// Se suma un pixel para compensar el tamaño del split
		return getHeight() - (splitVertical2.getSplitPosition() + 1);
	}

	/**
	 * Method that gets body's left margin value as String.
	 * @return body's left margin value as String
	 */
	public final String getMarginLeftBody() {
		return marginLeftBody + "";
	}

	/**
	 * Method that gets body's bottom margin value as String.
	 * @return body's bottom margin value as String
	 */
	public final String getMarginBottomBody() {
		return marginBottomBody + "";
	}

	/**
	 * Method that gets body's top margin value as String.
	 * @return body's top margin value as String
	 */
	public final String getMarginTopBody() {
		return marginTopBody + "";
	}

	/**
	 * Method that gets body's right margin value as String.
	 * @return body's right margin value as String
	 */
	public final String getMarginRightBody() {
		return marginRightBody + "";
	}

	/**
	 * Method that sets body's top margin value in pixels.
	 * @param marginTopBodyParam new body's top margin value in pixels
	 */
	public final void setMarginTopBody(float marginTopBodyParam) {
		this.marginTopBody = marginTopBodyParam;
	}

	/**
	 * Method that sets body's bottom margin value in pixels.
	 * @param marginBottomBodyParam new body's bottom margin value in pixels
	 */
	public final void setMarginBottomBody(float marginBottomBodyParam) {
		this.marginBottomBody = marginBottomBodyParam;
	}

	/**
	 * Method that sets body's left margin value in pixels.
	 * @param marginLeftBodyParam new body's left margin value in pixels
	 */
	public final void setMarginLeftBody(float marginLeftBodyParam) {
		this.marginLeftBody = marginLeftBodyParam;
	}

	/**
	 * Method that sets body's right margin value in pixels.
	 * @param marginRightBodyParam new body's right margin value in pixels
	 */
	public final void setMarginRightBody(float marginRightBodyParam) {
		this.marginRightBody = marginRightBodyParam;
	}

	/**
	 * Method that gets template's width value in centimeters.
	 * @return template's width value in centimeters as String
	 */
	public final String getWidthTemplateCm() {
		return PixelToCm.pxToCm(getWidth()) + "";
	}

	/**
	 * Method that gets template's height value in centimeters.
	 * @return template's height value in centimeters as String
	 */
	public final String getHeightTemplateCm() {
		return PixelToCm.pxToCm(getHeight()) + "";
	}

	/**
	 * Method that gets template's top margin value in centimeters as String.
	 * @return template's top margin value in centimeters as String
	 */
	public final String getMarginTopTemplateCm() {
		return marginTopTemplate + "";
	}

	/**
	 * Method that sets template's top margin value in pixels.
	 * @param marginTopTemplateParam template's top margin value in pixels
	 */
	public final void setMarginTopTemplate(float marginTopTemplateParam) {
		this.marginTopTemplate = marginTopTemplateParam;
	}

	/**
	 * Method that gets template's bottom margin value in centimeters as String.
	 * @return template's bottom margin value in centimeters as String
	 */
	public final String getMarginBottomTemplateCm() {
		return marginBottomTemplate + "";
	}

	/**
	 * Method that sets template's bottom margin value in pixels.
	 * @param marginBottomTemplateParam template's bottom margin value in pixels
	 */
	public final void setMarginBottomTemplate(float marginBottomTemplateParam) {
		this.marginBottomTemplate = marginBottomTemplateParam;
	}

	/**
	 * Method that gets template's left margin value in centimeters as String.
	 * @return template's left margin value in centimeters as String
	 */
	public final String getMarginLeftTemplateCm() {
		return marginLeftTemplate + "";
	}

	/**
	 * Method that sets template's left margin value in pixels.
	 * @param marginLeftTemplateParam template's left margin value in pixels
	 */
	public final void setMarginLeftTemplate(float marginLeftTemplateParam) {
		this.marginLeftTemplate = marginLeftTemplateParam;
	}

	/**
	 * Method that gets template's right margin value in centimeters as String.
	 * @return template's right margin value in centimeters as String
	 */
	public final String getMarginRightTemplateCm() {
		return marginRightTemplate + "";
	}

	/**
	 * Method that sets template's right margin value in pixels.
	 * @param marginRigthTemplate template's right margin value in pixels
	 */
	public final void setMarginRightTemplate(float marginRigthTemplate) {
		this.marginRightTemplate = marginRigthTemplate;
	}

	/**
	 * Method that inserts a component in a template particular position.
	 * @param posX component's position x value in pixels
	 * @param posY component's position y value in pixels
	 * @param component component's instance
	 */
	public final void insertComponent(float posX, float posY, Component component) {
		// Se inicializa el widget
		new ClickPanel((PositionAngle) component, this);
		ComponentPosition pos = new ComponentPosition();
		pos.setLeftValue(posX);
		pos.setTopValue(posY);
		addComponent(component);
		setPosition(component, pos);

	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbsoluteLayout#replaceComponent(com.vaadin.ui.Component, com.vaadin.ui.Component)
	 */
	@Override
	public final void replaceComponent(Component oldComponent, Component newComponent) {
		ComponentPosition position = getPosition(oldComponent);
		removeComponent(oldComponent);
		addComponent(newComponent);
		setPosition(newComponent, position);
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbsoluteLayout#setPosition(com.vaadin.ui.Component, com.vaadin.ui.AbsoluteLayout.ComponentPosition)
	 */
	public final void setPosition(Component component, ComponentPosition pos) {

		super.setPosition(component, pos);

		if (component instanceof PositionAngle) {

			((PositionAngle) component).calcCentralPoint();

			if (component instanceof Document) {
				this.document = (Document) component;
			} else {

				if (!(component instanceof VerticalSplitPanel) && !(component instanceof HorizontalSplitPanel)) {

					removeComponentFromLists(component);

					if (((PositionAngle) component).getPosYCenter() < splitVertical.getSplitPosition()) {
						marginTopContent.add(component);

					} else if (((PositionAngle) component).getPosYCenter() > splitVertical2.getSplitPosition()) {
						marginBottomContent.add(component);

					} else {
						if (((PositionAngle) component).getPosXCenter() < splitHorizontal.getSplitPosition()) {
							marginLeftContent.add(component);

						} else if (((PositionAngle) component).getPosXCenter() > splitHorizontal2.getSplitPosition()) {
							marginRightContent.add(component);

						} else {

							center.add(component);

						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.vaadin.ui.AbsoluteLayout#removeComponent(com.vaadin.ui.Component)
	 */
	public final void removeComponent(Component componente) {
		removeComponentFromLists(componente);

		super.removeComponent(componente);

	}

	/**
	 * Method that removes component of inner component's lists.
	 * @param component component to remove
	 */
	private void removeComponentFromLists(Component component) {
		if (marginRightContent.contains(component)) {
			marginRightContent.remove(component);
		} else if (marginBottomContent.contains(component)) {
			marginBottomContent.remove(component);
		} else if (marginLeftContent.contains(component)) {
			marginLeftContent.remove(component);
		} else if (marginTopContent.contains(component)) {
			marginTopContent.remove(component);
		} else if (center.contains(component)) {
			center.remove(component);
		}
		if (component instanceof Document) {
			document = null;
		}
	}

	/**
	 * Method that obtains code XSLT representation for template.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template
	 */
	public final StringBuffer getCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (!isFront && !isLast) {
			LOG.info(MessagesEVT.getInstance().getProperty("generatingMainTemplateCode"));
			result.append(codeEditor.addLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"));

			result.append(codeEditor.addLine("<xsl:stylesheet version=\"1.0\" xmlns:ri=\"urn:es:gob:signaturereport:generation:inputparameters\"\n ") + codeEditor.addLine(" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n") + codeEditor.addLine("xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" xmlns:fox=\"http://xmlgraphics.apache.org/fop/extensions\">\n"));
			result.append(codeEditor.addLine("<xsl:output method=\"xml\" version=\"1.0\" omit-xml-declaration=\"no\" indent=\"yes\" encoding=\"UTF-8\"/>\n"));
			result.append(codeEditor.addLine("<xsl:template match=\"ri:GenerationReport\">\n"));
			result.append(codeEditor.addLine("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">\n"));
			result.append(codeEditor.addLine("<fo:layout-master-set>\n"));

			result.append(getTemplatePropertiesCode(codeEditor));

			result.append(codeEditor.addLine("</fo:simple-page-master>\n</fo:layout-master-set>\n"));
			// VARIABLES
			result.append(codeEditor.addLine("<xsl:variable name = \"includeDocInfo\" select = \"count(ri:DocumentInfo)\"/> \n") + codeEditor.addLine("<xsl:variable name = \"numTotalPages\" select = \"ri:DocumentInfo/ri:NumPages\"/> \n") + codeEditor.addLine("<xsl:variable name = \"initPage\" select = \"0\"/> ") + codeEditor.addText("<xsl:variable name = \"signersNumber\" select = \"count(ri:CertificateInfo)\"/>\n"));
			// FIN VARIABLES

			// LLAMADA A PLANTILLAS
			if (!frontTemplate.isEmpty()) {
				result.append(codeEditor.addLine("<xsl:call-template name=\"preTemplate\"/>\n"));
			}
			result.append(codeEditor.addLine("<xsl:if test=\"$includeDocInfo!=0\">\n"));
			result.append(templateCall(false, codeEditor));
			result.append(codeEditor.addLine("</xsl:if>\n"));
			if (!lastTemplate.isEmpty()) {
				result.append(codeEditor.addLine("<xsl:call-template name=\"postTemplate\"/>\n"));
			}
			// FIN LLAMADA A PLANTILLAS
			result.append(codeEditor.addLine("</fo:root>\n"));

			result.append(codeEditor.addLine("</xsl:template>\n"));

			result.append(codeEditor.addLine("<xsl:template name=\"pageSequenceWithDocument\">\n"));
			result.append(codeEditor.addLine("<xsl:param name=\"currentPage\"/>\n"));
			result.append(codeEditor.addLine("<xsl:param name=\"numPages\"/>\n"));
			result.append(codeEditor.addLine("<xsl:param name=\"signersNumber\"/>\n"));
			result.append(codeEditor.addLine("<xsl:if test=\"$currentPage &lt; $numPages\">\n"));
			result.append(globalVariables(codeEditor));

			if (document != null) {
				result.append(document.getCode(codeEditor));
			}

			result.append(codeEditor.addLine("<fo:page-sequence master-reference=\"simple\">\n"));

			result.append(leftRegionaGetCode(codeEditor));
			result.append(topRegionGetCode(codeEditor));
			result.append(bottomRegionGetCode(codeEditor));
			result.append(rightRegionGetCode(codeEditor));
			result.append(centerRegionGetCode(codeEditor));

			result.append(codeEditor.addLine("</fo:page-sequence>\n"));

			result.append(templateCall(true, codeEditor));

			result.append(codeEditor.addLine("</xsl:if>\n"));
			result.append(codeEditor.addLine("</xsl:template>\n"));

			if (!frontTemplate.isEmpty()) {
				result.append(frontTemplate.getCode(codeEditor));
			}
			if (!lastTemplate.isEmpty()) {
				result.append(lastTemplate.getCode(codeEditor));
			}

			result.append(codeEditor.addLine("</xsl:stylesheet>"));
		} else {

			if (isFront) {
				LOG.info(MessagesEVT.getInstance().getProperty("generatingFrontTemplateCode"));
				result.append(codeEditor.addLine("<xsl:template name=\"preTemplate\">\n"));
			}
			if (isLast) {
				LOG.info(MessagesEVT.getInstance().getProperty("generatingLastTemplateCode"));
				result.append(codeEditor.addLine("<xsl:template name=\"postTemplate\">\n"));
			}

			result.append(codeEditor.addLine("<fo:page-sequence master-reference=\"simple\">\n"));

			result.append(leftRegionaGetCode(codeEditor));
			result.append(rightRegionGetCode(codeEditor));
			result.append(topRegionGetCode(codeEditor));
			result.append(bottomRegionGetCode(codeEditor));
			result.append(centerRegionGetCode(codeEditor));

			result.append(codeEditor.addLine("</fo:page-sequence>\n"));

			result.append(codeEditor.addLine("</xsl:template>\n"));
		}

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's properties zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's properties zone
	 */
	private StringBuffer getTemplatePropertiesCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		result.append(codeEditor.addLine("<fo:simple-page-master master-name=\"simple\" page-height=\""));
		result.append(codeEditor.addEdition(getHeightTemplateCm(), this, CodeEditorInput.HEIGHT, false));
		result.append(codeEditor.addText("cm\" page-width=\""));
		result.append(codeEditor.addEdition(getWidthTemplateCm(), this, CodeEditorInput.WIDTH, false) + codeEditor.addText(CM));
		if (marginTopTemplate > 0) {
			result.append(codeEditor.addText("margin-top=\"") + codeEditor.addEdition(getMarginTopTemplateCm(), this, CodeEditorInput.MARGINTOP, false) + codeEditor.addText(CM));
		}
		if (marginBottomTemplate > 0) {
			result.append(codeEditor.addText("margin-bottom=\"") + codeEditor.addEdition(getMarginBottomTemplateCm(), this, CodeEditorInput.MARGINBOTTOM, false) + codeEditor.addText(CM));
		}
		if (marginLeftTemplate > 0) {
			result.append(codeEditor.addText("margin-left=\"") + codeEditor.addEdition(getMarginLeftTemplateCm(), this, CodeEditorInput.MARGINLEFT, false) + codeEditor.addText(CM));
		}
		if (marginRightTemplate > 0) {
			result.append(codeEditor.addText("margin-rigth=\"") + codeEditor.addEdition(getMarginRightTemplateCm(), this, CodeEditorInput.MARGINRIGHT, false) + codeEditor.addText(CM));
		}
		result.append(codeEditor.addText(">\n"));

		result.append(codeEditor.addLine("<fo:region-body "));
		if (marginTopBody > 0) {
			result.append(codeEditor.addText("margin-top=\"") + codeEditor.addEdition(getMarginTopBody(), this, CodeEditorInput.MARGINTOPBODY, false) + codeEditor.addText(CM));
		}
		if (marginBottomBody > 0) {
			result.append(codeEditor.addText("margin-bottom=\"") + codeEditor.addEdition(getMarginBottomBody(), this, CodeEditorInput.MARGINBOTTOMBODY, false) + codeEditor.addText("cm\""));
		}
		if (marginLeftBody > 0) {
			result.append(codeEditor.addText(" margin-left=\"") + codeEditor.addEdition(getMarginLeftBody(), this, CodeEditorInput.MARGINLEFTBODY, false) + codeEditor.addText("cm\""));
		}
		if (marginRightBody > 0) {
			result.append(codeEditor.addText(" margin-right=\"") + codeEditor.addEdition(getMarginRightBody(), this, CodeEditorInput.MARGINRIGHTBODY, false) + codeEditor.addText("cm\""));
		}
		result.append(codeEditor.addText("/>\n"));

		result.append(getRegionsExtendZones(codeEditor));

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's extends zones.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's extends zones
	 */
	private StringBuffer getRegionsExtendZones(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (getRegionBeforeExtent() > 0) {
			result.append(codeEditor.addLine("<fo:region-before extent=\"") + codeEditor.addEdition(getRegionBeforeExtentCm(), splitVertical, CodeEditorInput.SPLITVERTICAL1, false) + codeEditor.addText(CMENDNEWLINE));
		}
		if (getRegionAfterExtent() > 0) {
			result.append(codeEditor.addLine("<fo:region-after extent=\"") + codeEditor.addEdition(getRegionAfterExtentCm(), splitVertical2, CodeEditorInput.SPLITVERTICAL2, false) + codeEditor.addText(CMENDNEWLINE));
		}
		if (getRegionStartExtent() > 0) {
			result.append(codeEditor.addLine("<fo:region-start extent=\"") + codeEditor.addEdition(getRegionStartExtentCm(), splitHorizontal, CodeEditorInput.SPLITHORIZONTAL1, false) + codeEditor.addText(CMENDNEWLINE));
		}
		if (getRegionEndExtent() > 0) {
			result.append(codeEditor.addLine("<fo:region-end extent=\"") + codeEditor.addEdition(getRegionEndExtentCm(), splitHorizontal2, CodeEditorInput.SPLITHORIZONTAL2, false) + codeEditor.addText(CMENDNEWLINE));
		}
		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's call zones.
	 * @param incremento indicates if template's call is incremental
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's call zones
	 */
	private StringBuffer templateCall(boolean incremento, CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		result.append(codeEditor.addLine("<xsl:call-template name=\"pageSequenceWithDocument\">\n") + codeEditor.addLine("<xsl:with-param name=\"currentPage\">\n"));
		if (incremento) {
			result.append(codeEditor.addLine("<xsl:value-of select=\"$currentPage +1\"/>\n"));
		} else {
			result.append(codeEditor.addLine("<xsl:value-of select=\"$initPage\"/>\n"));
		}
		result.append(codeEditor.addLine("</xsl:with-param>\n") +

		codeEditor.addLine("<xsl:with-param name=\"numPages\">\n"));
		if (incremento) {
			result.append(codeEditor.addLine("<xsl:value-of select=\"$numPages\"/>\n"));
		} else {
			result.append(codeEditor.addLine("<xsl:value-of select=\"$numTotalPages\"/>\n"));
		}
		result.append(codeEditor.addLine("</xsl:with-param>\n") +

		codeEditor.addLine("<xsl:with-param name=\"signersNumber\">\n") + codeEditor.addLine("<xsl:value-of select=\"$signersNumber\"/>\n") + codeEditor.addLine("</xsl:with-param>\n")

		+ codeEditor.addLine("</xsl:call-template>\n"));

		return result;
	}

	/**
	 * Indicates if a component have the condition field selected.
	 * @param comp component
	 * @return true if the component have the condition field selected. false otherwise
	 */
	private boolean conditionFieldSelected(Component comp) {
		boolean conditionFieldSelected = (((PositionAngle) comp).getCondition1() != null && !((PositionAngle) comp).getCondition1().isEmpty() && ((PositionAngle) comp).getCondition2() != null && !((PositionAngle) comp).getCondition2().isEmpty());
		return conditionFieldSelected && ((PositionAngle) comp).getConditionOp() != null && !((PositionAngle) comp).getConditionOp().isEmpty();
	}

	/**
	 * Method that obtains code XSLT representation for component's before zone.
	 * @param comp component
	 * @param angle rotation angle
	 * @param wOverflow width overflow
	 * @param hOverflow height overflow
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component's before zone
	 */
	private StringBuffer getBeforeComponentCode(Component comp, int angle, float wOverflow, float hOverflow, CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();

		boolean conditionFieldsSeted = conditionFieldSelected(comp);

		if (conditionFieldsSeted && !((PositionAngle) comp).isForEach()) {
			result.append(codeEditor.addLine("<fo:block/>"));
			result.append(codeEditor.addLine("<xsl:if test=\"" + ((PositionAngle) comp).mountCondition() + "\">\n"));
		}
		result.append(codeEditor.addLine("<fo:block-container position=\"absolute\" "));
		if (((PositionAngle) comp).isText() && !((PositionAngle) comp).getWidthUnits().equals(Unit.PERCENTAGE)) {
			result.append(codeEditor.addText("width=\"") + codeEditor.addEdition(PixelToCm.pxToCm(comp.getWidth()) + "", comp, CodeEditorInput.WIDTH, false) + codeEditor.addText(CM));
		}
		if (angle <= 0) {
			result.append(codeEditor.addText(" left=\"") + codeEditor.addEdition(PixelToCm.pxToMm(((PositionAngle) comp).getPosX() - wOverflow) + "", comp, CodeEditorInput.POSX, false) + codeEditor.addText("mm\" top=\"") + codeEditor.addEdition(PixelToCm.pxToMm(((PositionAngle) comp).getPosY() - hOverflow) + "", comp, CodeEditorInput.POSY, false) + codeEditor.addText("mm\" "));
			((PositionAngle) comp).setPosXXSLT((float) ((PositionAngle) comp).getPosX() - wOverflow);
			((PositionAngle) comp).setPosYXSLT((float) ((PositionAngle) comp).getPosY() - hOverflow);
		}
		if (angle > 0) {

			double r = Math.sqrt(Math.pow(((PositionAngle) comp).getPosX() - ((PositionAngle) comp).getPosXCenter(), 2) + Math.pow(((PositionAngle) comp).getPosY() * -1 - ((PositionAngle) comp).getPosYCenter() * -1, 2));

			double pendiente = (((PositionAngle) comp).getPosY() - ((PositionAngle) comp).getPosYCenter()) / (((PositionAngle) comp).getPosX() - ((PositionAngle) comp).getPosXCenter());

			double anguloAux = Math.atan(pendiente);

			double x = ((PositionAngle) comp).getPosXCenter() + (r * Math.cos(Math.toRadians(NumberConstants.NUM180 - angle) - anguloAux));
			double y = (((PositionAngle) comp).getPosYCenter() * -1) + (r * Math.sin(Math.toRadians(NumberConstants.NUM180 - angle) - anguloAux));

			result.append(codeEditor.addText(" left=\"") + codeEditor.addEdition(PixelToCm.pxToMm(x - wOverflow) + "", comp, CodeEditorInput.POSX, false) + codeEditor.addText("mm\" top=\"") + codeEditor.addEdition(PixelToCm.pxToMm((y * -1) - hOverflow) + "", comp, CodeEditorInput.POSY, false) + codeEditor.addText("mm\" \n"));

			result.append(codeEditor.addLine(" fox:transform=\"rotate("));
			result.append(codeEditor.addEdition(angle + "", comp, CodeEditorInput.ANGLE, false));
			result.append(codeEditor.addText(")\""));

			((PositionAngle) comp).setPosXXSLT((float) (x - wOverflow));
			((PositionAngle) comp).setPosYXSLT((float) (y * -1) - hOverflow);
		}

		result.append(codeEditor.addText(">\n"));

		result.append(codeEditor.addLine("<fo:block line-height=\"100%\">\n"));

		if (((PositionAngle) comp).isForEach()) {
			result.append(getBeforeComponentCodeForEachZone(comp, angle, codeEditor));
		}

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for component's code for each zone.
	 * @param comp component
	 * @param angle rotation anble
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component's code for each zone
	 */
	private StringBuffer getBeforeComponentCodeForEachZone(Component comp, int angle, CodeEditor codeEditor) {

		StringBuffer result = new StringBuffer();

		result.append(getBeforeComponentCodeForEachZoneEvalAngle(comp, angle, codeEditor));

		result.append(codeEditor.addLine("<xsl:for-each select=\"//ri:IndividualSignature"));
		boolean conditionFieldsSeted = conditionFieldSelected(comp);
		if (conditionFieldsSeted) {
			result.append(codeEditor.addText("["));
			result.append(codeEditor.addText(((PositionAngle) comp).mountCondition()));
			result.append(codeEditor.addText("]"));
		}
		result.append(codeEditor.addText("\">\n"));

		if (((PositionAngle) comp).getForEachType() != null && ((PositionAngle) comp).getForEachType().equals(ForEachWindow.OPVERTICAL)) {
			result.append(codeEditor.addLine("<fo:table-row height=\"" + PixelToCm.pxToCm(comp.getHeight()) + CMNEWLINE));
		}

		result.append(codeEditor.addLine("<fo:table-cell>\n"));

		if (((PositionAngle) comp).isText() && !((PositionAngle) comp).getWidthUnits().equals(Unit.PERCENTAGE)) {
			result.append(codeEditor.addLine("<fo:block-container  width=\""));

			result.append(codeEditor.addEdition(PixelToCm.pxToCm(comp.getWidth()) + "", comp, CodeEditorInput.WIDTH, false) + codeEditor.addText(CMNEWLINE));
		}

		result.append(codeEditor.addLine("<fo:block>\n"));

		return result;

	}

	/**
	 * Method that obtains code XSLT representation for component's code for each zone evaluating angle value.
	 * @param comp component
	 * @param angle rotation anble
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component's code for each zone evaluating angle value
	 */
	private StringBuffer getBeforeComponentCodeForEachZoneEvalAngle(Component comp, int angle, CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (angle == NumberConstants.NUM90 || angle == NumberConstants.NUM270) {
			if (((PositionAngle) comp).getForEachType() != null && ((PositionAngle) comp).getForEachType().equals(ForEachWindow.OPORIZONTAL)) {
				float ht = (float) (PixelToCm.pxToCm(getHeight()) - (PixelToCm.pxToCm(((PositionAngle) comp).getPosYXSLT())));

				result.append(codeEditor.addLine("<fo:table width=\"" + ht + CMNEWLINE));
				result.append(codeEditor.addLine("<fo:table-body width=\"" + ht + CMNEWLINE));

				result.append(codeEditor.addLine("<fo:table-row width=\"" + ht + CMNEWLINE));
			} else {
				result.append(codeEditor.addLine("<fo:table>\n"));
				result.append(codeEditor.addLine("<fo:table-body>\n"));
			}

		} else {
			if (((PositionAngle) comp).getForEachType() != null && ((PositionAngle) comp).getForEachType().equals(ForEachWindow.OPORIZONTAL)) {

				float wt = (float) (PixelToCm.pxToCm(getWidth()) - (PixelToCm.pxToCm(((PositionAngle) comp).getPosXXSLT())));

				result.append(codeEditor.addLine("<fo:table width=\"" + wt + CMNEWLINE));
				result.append(codeEditor.addLine("<fo:table-body width=\"" + wt + CMNEWLINE));

				result.append(codeEditor.addLine("<fo:table-row width=\"" + wt + CMNEWLINE));
			} else {
				result.append(codeEditor.addLine("<fo:table>\n"));
				result.append(codeEditor.addLine("<fo:table-body>\n"));
			}
		}
		return result;
	}

	/**
	 * Method that obtains code XSLT representation for component's after zone.
	 * @param comp component
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for component's after zone
	 */
	private StringBuffer getAfterComponentCode(Component comp, CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		boolean conditionFieldsSeted = conditionFieldSelected(comp);

		if (((PositionAngle) comp).isForEach()) {
			result.append(codeEditor.addLine("</fo:block>\n"));
			if (((PositionAngle) comp).isText() && !((PositionAngle) comp).getWidthUnits().equals(Unit.PERCENTAGE)) {
				result.append(codeEditor.addLine("</fo:block-container>\n"));
			}
			result.append(codeEditor.addLine("</fo:table-cell>\n"));
			if (((PositionAngle) comp).getForEachType() != null && ((PositionAngle) comp).getForEachType().equals(ForEachWindow.OPVERTICAL)) {
				result.append(codeEditor.addLine("</fo:table-row>\n"));
			}
			result.append(codeEditor.addLine("</xsl:for-each>\n"));
			if (((PositionAngle) comp).getForEachType() != null && ((PositionAngle) comp).getForEachType().equals(ForEachWindow.OPORIZONTAL)) {
				result.append(codeEditor.addLine("</fo:table-row>\n"));
			}
			result.append(codeEditor.addLine("</fo:table-body>\n"));
			result.append(codeEditor.addLine("</fo:table>\n"));
		}

		result.append(codeEditor.addLine("</fo:block>\n"));
		result.append(codeEditor.addLine("</fo:block-container>\n"));

		if (!((PositionAngle) comp).isForEach() && conditionFieldsSeted) {
			result.append(codeEditor.addLine("</xsl:if>\n"));
		}

		return result;
	}

	/**
	 * Method that obtains code XSLT representation of the users global variables defined.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for global variables.
	 */
	private StringBuffer globalVariables(CodeEditor codeEditor) {
		Map<String, String> globalVars = getGlobalVars();
		if (globalVars != null && !globalVars.keySet().isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (String varName: orderGlobalVars(globalVars.keySet())) {
				sb.append(codeEditor.addLine("<xsl:variable name=\"" + varName + "\" select=\"" + globalVars.get(varName) + "\"/>"));
			}
			return sb;
		}
		return null;
	}

	/**
	 * Method that gives a order considering the dependency between variables.
	 * @param varsSet Set of variable to order.
	 * @param varListOrder current list of variables ordered.
	 * @return a list with the set of variables ordered.
	 */
	private List<String> orderGlobalVars(Set<String> varsSet) {
		List<String> res = new ArrayList<String>();
		List<String> refs = new ArrayList<String>();
		List<String> analyzed = new ArrayList<String>();
		// Recuperamos el map con la correspondencia de variables y sus
		// referencias
		Map<String, List<String>> varsRefs = VariablesZone.getReferencedVars();
		Set<String> keys = varsRefs.keySet();
		while (varsSet.size() != analyzed.size()) {
			for (String var: keys) {
				// Añadimos a la lista aquellas variables que no tengan ninguna
				// referencia
				if (varsRefs.get(var).isEmpty() && !analyzed.contains(var)) {
					res.add(var);
					// Añadimos la variable a la lista de analizados. 
					analyzed.add(var);
				} else {
					// Si tiene alguna referencia...
					// Recuperamos la lista de referencias
					refs = varsRefs.get(var);
					if (res.containsAll(refs) && !analyzed.contains(var)) {
						// Si todas las referencias estan ya incluidas, añadimos
						// la variable a la lista
						res.add(var);
						// Añadimos la variable a la lista de analizados.
						analyzed.add(var);
					}
				}
			}
		}
		return res;
	}

	/**
	 * Method that obtains the defined global variables.
	 * @return A map object with the name and the value of the global variables.
	 */
	private Map<String, String> getGlobalVars() {
		return FormVariable.getGlobalVariablesMap();
	}

	/**
	 * Method that obtains code XSLT representation for template's central zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's central zone
	 */
	private StringBuffer centerRegionGetCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		result.append(codeEditor.addLine("<fo:flow flow-name=\"xsl-region-body\">\n"));

		if (center == null || center.isEmpty()) {
			result.append(codeEditor.addLine(BLOCKNEWLINE));
		} else {
			for (Component comp: center) {
				result.append(getBeforeComponentCode(comp, ((PositionAngle) comp).getAngle(), 0, 0, codeEditor));
				result.append(((PositionAngle) comp).getCode(codeEditor));
				result.append(getAfterComponentCode(comp, codeEditor));
			}
		}

		result.append(codeEditor.addLine("</fo:flow>\n"));

		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's left zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's left zone
	 */
	private StringBuffer leftRegionaGetCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (getRegionStartExtent() > 0) {
			result.append(codeEditor.addLine("<fo:static-content flow-name=\"xsl-region-start\">\n"));

			if (marginLeftContent == null || marginLeftContent.isEmpty()) {
				result.append(codeEditor.addLine(BLOCKNEWLINE));
			} else {
				for (Component comp: marginLeftContent) {
					result.append(getBeforeComponentCode(comp, ((PositionAngle) comp).getAngle(), 0, 0, codeEditor));

					result.append(((PositionAngle) comp).getCode(codeEditor));
					result.append(getAfterComponentCode(comp, codeEditor));

				}
			}

			result.append(codeEditor.addLine(FINALSTATICCONTENT));
		}
		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's right zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's right zone
	 */
	private StringBuffer rightRegionGetCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (getRegionEndExtent() > 0) {
			result.append(codeEditor.addLine("<fo:static-content flow-name=\"xsl-region-end\">\n"));

			if (marginRightContent == null || marginRightContent.isEmpty()) {
				result.append(codeEditor.addLine(BLOCKNEWLINE));
			} else {
				for (Component comp: marginRightContent) {
					result.append(getBeforeComponentCode(comp, ((PositionAngle) comp).getAngle(), getWidth() - getRegionEndExtent(), 0, codeEditor));

					result.append(((PositionAngle) comp).getCode(codeEditor));
					result.append(getAfterComponentCode(comp, codeEditor));
				}
			}

			result.append(codeEditor.addLine(FINALSTATICCONTENT));
		}
		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's bottom zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's bottom zone
	 */
	private StringBuffer bottomRegionGetCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (getRegionAfterExtent() > 0) {
			result.append(codeEditor.addLine("<fo:static-content flow-name=\"xsl-region-after\">\n"));

			if (marginBottomContent == null || marginBottomContent.isEmpty()) {
				result.append(codeEditor.addLine(BLOCKNEWLINE));
			} else {

				for (Component comp: marginBottomContent) {
					result.append(getBeforeComponentCode(comp, ((PositionAngle) comp).getAngle(), getRegionStartExtent(), getHeight() - getRegionAfterExtent(), codeEditor));

					result.append(((PositionAngle) comp).getCode(codeEditor));
					result.append(getAfterComponentCode(comp, codeEditor));
				}
			}

			result.append(codeEditor.addLine(FINALSTATICCONTENT));
		}
		return result;
	}

	/**
	 * Method that obtains code XSLT representation for template's top zone.
	 * @param codeEditor container from code view.
	 * @return code XSLT representation for template's top zone
	 */
	private StringBuffer topRegionGetCode(CodeEditor codeEditor) {
		StringBuffer result = new StringBuffer();
		if (getRegionBeforeExtent() > 0) {
			result.append(codeEditor.addLine("<fo:static-content flow-name=\"xsl-region-before\">\n"));

			if (marginTopContent == null || marginTopContent.isEmpty()) {
				result.append(codeEditor.addLine(BLOCKNEWLINE));
			} else {
				for (Component comp: marginTopContent) {
					result.append(getBeforeComponentCode(comp, ((PositionAngle) comp).getAngle(), getRegionStartExtent(), 0, codeEditor));

					result.append(((PositionAngle) comp).getCode(codeEditor));
					result.append(getAfterComponentCode(comp, codeEditor));
				}
			}

			result.append(codeEditor.addLine(FINALSTATICCONTENT));
		}
		return result;
	}

	/**
	 * Method that indicates if is a front template.
	 * @return true if is a front template. false otherwise
	 */
	public final boolean isFront() {
		return isFront;
	}

	/**
	 * Method that sets if template is a front template.
	 * @param isFrontParam new value indicating if template is a front template
	 */
	public final void setFront(boolean isFrontParam) {
		this.isFront = isFrontParam;
	}

	/**
	 * Method that indicates if is a last template.
	 * @return true if is a last template. false otherwise
	 */
	public final boolean isLast() {
		return isLast;
	}

	/**
	 * Method that sets if template is a last template.
	 * @param isLastParam new value indicating if template is a last template
	 */
	public final void setLast(boolean isLastParam) {
		this.isLast = isLastParam;
	}

	/**
	 * Method that indicates if the template is empty.
	 * @return true if the template is empty, false otherwise
	 */
	public final boolean isEmpty() {
		boolean marginsEmpty = marginRightContent.isEmpty() && marginBottomContent.isEmpty() && marginLeftContent.isEmpty() && marginTopContent.isEmpty();
		return marginsEmpty && center.isEmpty();
	}

	/**
	 * Method that gets the front template instance from main template.
	 * @return front template instance from main template
	 */
	public final Template getFrontTemplate() {
		return frontTemplate;
	}

	/**
	 * Method that sets the front template instance from main template.
	 * @param frontTemplateParam new front template instance from main template
	 */
	public final void setFrontTemplate(Template frontTemplateParam) {
		this.frontTemplate = frontTemplateParam;
	}

	/**
	 * Method that gets the last template instance from main template.
	 * @return last template instance from main template
	 */
	public final Template getLastTemplate() {
		return lastTemplate;
	}

	/**
	 * Method that sets the last template instance from main template.
	 * @param lastTemplateParam last template instance from main template
	 */
	public final void setLastTemplate(Template lastTemplateParam) {
		this.lastTemplate = lastTemplateParam;
	}

	/**
	 * Method that closes the edit mode container from a component.
	 * @param comp component
	 */
	private void closeContainer(Component comp) {
		if (((PositionAngle) comp).getContainer() != null) {
			((PositionAngle) comp).getContainer().close();
		}
	}

	/**
	 * Method that closes all edit mode containers from all components of template.
	 */
	public final void closeContainers() {

		for (Component comp: center) {
			closeContainer(comp);
		}
		for (Component comp: marginRightContent) {
			closeContainer(comp);
		}
		for (Component comp: marginLeftContent) {
			closeContainer(comp);
		}
		for (Component comp: marginTopContent) {
			closeContainer(comp);
		}
		for (Component comp: marginBottomContent) {
			closeContainer(comp);
		}

		if (frontTemplate != null) {
			frontTemplate.closeContainers();
		}

		if (lastTemplate != null) {
			lastTemplate.closeContainers();
		}

	}

	/**
	 * Method that indicates if the template have a signed document instanced.
	 * @return true if he template have a signed document instanced, false otherwise
	 */
	public final boolean isHasDocument() {
		return document != null;
	}

	/**
	 * Method that gets a list of components that contents all components of template.
	 * @return list of components that contents all components of template
	 */
	public final List<Component> getAllComponents() {
		List<Component> result = new ArrayList<Component>();
		result.addAll(marginRightContent);
		result.addAll(marginBottomContent);
		result.addAll(marginLeftContent);
		result.addAll(marginTopContent);
		result.addAll(center);
		if (isHasDocument()) {
			result.add(document);
		}
		return result;
	}

}
