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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.listeners.SessionExpireListener.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.listeners;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.vaadin.server.VaadinSession;

import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class to manage the expired session events.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.0, 13/03/2015.
 */
public class SessionExpireListener implements HttpSessionListener {
	
	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(SessionExpireListener.class);

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public final void sessionCreated(HttpSessionEvent hse) {
		LOG.info(MessagesEVT.getInstance().getProperty("sessionCreated"));
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public final void sessionDestroyed(HttpSessionEvent hse) {
		LOG.info(MessagesEVT.getInstance().getProperty("sessionExpired"));
		Enumeration<String> e = hse.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			Object o = hse.getSession().getAttribute((String) e.nextElement());
			if (o instanceof VaadinSession) {
				VaadinSession vs = (VaadinSession) o;
				File folder = new File(System.getProperty("java.io.tmpdir"), vs.getSession().getId());
				if (folder.exists()) {
					for (File img: folder.listFiles()) {
						img.delete();
					}
					folder.delete();
					LOG.debug(MessagesEVT.getInstance().getProperty("removeDirPart1")+" "+folder.getAbsolutePath()+" "+MessagesEVT.getInstance().getProperty("removeDirPart2"));
				}
				vs.getSession().getId();
			}
		}
	}
}
