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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.MatrixPagesInclude.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** 
 * <p>Class that contains information about the pages of a signed document will 
be included in a signature report.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 10/03/2011.
 */
public class MatrixPagesInclude {

	/**
	 * Map that contains information about the pages to include in a signature report. The content is: <br>
	 * 
	 * Key: Page number of signature report. Value: Map with the pages of the original document to include in the page specified in the key. The content is:<br/>
	 * 
	 * Key: Page number of signed document. Value: Array of {@link PageIncludeFormat} that contains the location where the page is included.
	 */
	private Map<Integer, LinkedHashMap<Integer, PageIncludeFormat[ ]>> matrix = null;

	/**
	 * Constructor method for the class MatrixPagesInclude.java. 
	 */
	public MatrixPagesInclude() {
		matrix = new LinkedHashMap<Integer, LinkedHashMap<Integer, PageIncludeFormat[ ]>>();
	}

	/**
	 * Add page to the matrix.
	 * @param targetNumPage	Page number of signature report.
	 * @param originNumPage	Page number of signed document.
	 * @param format		{@link PageIncludeFormat} that contains the location where the page is included.
	 */
	public final void addPage(int targetNumPage, int originNumPage, PageIncludeFormat format) {
		Integer targetKey = Integer.valueOf(targetNumPage);
		if (!matrix.containsKey(targetKey)) {
			matrix.put(targetKey, new LinkedHashMap<Integer, PageIncludeFormat[ ]>());
		}
		Integer origenKey = Integer.valueOf(originNumPage);
		if (!matrix.get(targetKey).containsKey(origenKey)) {
			matrix.get(targetKey).put(origenKey, new PageIncludeFormat[ ] { format });
		} else {
			PageIncludeFormat[ ] formats = matrix.get(targetKey).remove(origenKey);
			PageIncludeFormat[ ] newFormats = new PageIncludeFormat[formats.length + 1];
			System.arraycopy(formats, 0, newFormats, 0, formats.length - 1);
			newFormats[formats.length] = format;
			matrix.get(targetKey).put(origenKey, newFormats);
		}
	}

	/**
	 * Gets the number of pages of the signed document will be included into the page of the report provided.
	 * @param targetNumPage	Page number of signature report.
	 * @return			Array of signed document pages.
	 */
	public final int[ ] getPageToInclude(int targetNumPage) {
		int[ ] pages = null;
		Integer targetKey = Integer.valueOf(targetNumPage);
		if (matrix.containsKey(targetKey)) {
			Set<Integer> keys = matrix.get(targetKey).keySet();
			pages = new int[keys.size()];
			int i = 0;
			Iterator<Integer> it = keys.iterator();
			while (it.hasNext()) {
				pages[i] = it.next().intValue();
				i++;
			}
		}
		return pages;
	}

	/**
	* Gets a array of {@link PageIncludeFormat} that contains the location
	where the page is included.
	* @param targetNumPage Page number of signature report.
	* @param originNumPage Page number of signed document.
	* @return Array of {@link PageIncludeFormat} that contains the location
	where the page is included.
	*/
	public final PageIncludeFormat[ ] getPagesFormat(int targetNumPage, int originNumPage) {
		Integer targetKey = Integer.valueOf(targetNumPage);
		Integer origenKey = Integer.valueOf(originNumPage);
		if (matrix.containsKey(targetKey) && matrix.get(targetKey).containsKey(origenKey)) {
			return matrix.get(targetKey).get(origenKey);
		}
		return null;
	}

	/**
	* Reports if the table is empty.
	* @return True if the table is empty, false otherwise.
	*/
	public final boolean isEmpty() {
		return matrix.isEmpty();
	}
}
