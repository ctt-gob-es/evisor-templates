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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.util.StyleUtil.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util;

import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Component;

import es.gob.afirma.evisortemplates.components.PositionAngle;

/** 
 * <p>Class to apply style to rotated components.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 16/03/2015.
 */
public final class StyleUtil {

	/**
	 * Constructor method for the class StyleUtil.java. 
	 */
	private StyleUtil() {
		super();
	}
	
	/**
	 * Method to apply style to rotated components.
	 * @param component component
	 */
	public static void rotaryStyle(Component component) {

			int seq = SequenceUtil.getSequence();
		
    		Styles styles = Page.getCurrent().getStyles();
    		if (((PositionAngle) component).getAngle() != 0) {
    			styles.add(".rotacion" + seq + " {-ms-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg); -webkit-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);}");
    			component.setStyleName("rotacion" + seq);
    		} else {
    			component.setStyleName("eliminarRotacion" + seq);
    		}
    		if (((PositionAngle) component).getContainer() != null) {
    			if (((PositionAngle) component).isText()) {
    				styles.add(".ventanaTransparente" + seq + "{margin-left: -2px !important; margin-top: -2px !important; " + "opacity:0.5; filter:alpha(opacity=50); border:2px dashed #a4a4a2; ms-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg); -webkit-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);}");
    			} else {
    				styles.add(".ventanaTransparente" + seq + "{opacity:0.5; filter:alpha(opacity=50); border:2px dashed #a4a4a2; ms-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg); -webkit-transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);transform: rotate(" + ((PositionAngle) component).getAngle() + "deg);}");
    			}
    			((PositionAngle) component).getContainer().setStyleName("ventanaTransparente" + seq);
    		}
		
	}
}
