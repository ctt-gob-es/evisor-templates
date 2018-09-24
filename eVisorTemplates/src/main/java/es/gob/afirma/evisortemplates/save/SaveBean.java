/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2013-,2014 Gobierno de España
* Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
* condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este 
* fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
*/

/** 
 * <b>File:</b><p>es.gob.afirma.evisortemplates.save.saveBean.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>28/3/2016.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/3/2016.
 */
package es.gob.afirma.evisortemplates.save;

import java.io.Serializable;

import es.gob.afirma.evisortemplates.components.impl.DesignZone;


/** 
 * <p>Class for contain object needed for save option.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 28/3/2016.
 */
public class SaveBean implements Serializable{

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = -8034155335691578538L;

	/**
	 * Attribute that represents the design zone content. 
	 */
	private DesignZone designZone;

	/**
	 * Constructor method for the class SaveBean.java.
	 * @param designZoneParam DesignZone to copy
	 */
	public SaveBean(DesignZone designZoneParam) {
		super();
		this.designZone = designZoneParam;
	}

	/**
	 * Method that gets the design zone to save.
	 * @return DesignZone design zone to save
	 */
	public final DesignZone getDesignZone() {
		return designZone;
	}


	/**
	 * Method that sets the design zone to save.
	 * @param designZoneParam design zone to save
	 */
	public final void setDesignZone(DesignZone designZoneParam) {
		this.designZone = designZoneParam;
	}
	
}
