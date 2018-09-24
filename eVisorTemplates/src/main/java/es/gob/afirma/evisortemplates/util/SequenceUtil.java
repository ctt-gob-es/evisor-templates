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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.util.SequenceUtil.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>5/4/2016.</p>
 * @author Gobierno de España.
 * @version 1.0, 5/4/2016.
 */
package es.gob.afirma.evisortemplates.util;


/** 
 * <p>Class to obtain next sequence number for rotary styles id.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 5/4/2016.
 */
public final class SequenceUtil {

	/**
	 * Attribute that represents current sequence number. 
	 */
	private static int seq;
	
	static{
		seq = 1;
	}
	
	/**
	 * Constructor method for the class SequenceUtil.java. 
	 */
	private SequenceUtil(){
		
	}
	
	/**
	 * Method that obtains next sequence number.
	 * @return next sequence number.
	 */
	public static int getSequence() {
		seq++;
		return seq;
	}
}
