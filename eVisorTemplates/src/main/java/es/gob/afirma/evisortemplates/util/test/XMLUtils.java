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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.test.XMLUtils.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/03/2015.
 */
package es.gob.afirma.evisortemplates.util.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import es.gob.afirma.evisortemplates.util.UtilsResources;

/**
 * <p>Class contains utilities for processing XML.</p>
 * <b>Project:</b><p>Horizontal platform to generation signature reports in legible format.</p>
 * @version 1.0, 10/02/2011.
 */
public final class XMLUtils {

	/**
	 * Attribute that represents Logger for class. 
	 */
	private static final Logger LOG = Logger.getLogger(XMLUtils.class);

	/**
	 * Attribute that defines a factory API that enables applications to obtain a
	 * parser that produces DOM object trees from XML documents .
	 */
	private static javax.xml.parsers.DocumentBuilderFactory dbf = null;

	static {
		try {
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setIgnoringComments(true);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * Constructor method for the class XMLUtils.java.
	 */
	private XMLUtils() {
		super();
	}

	/**
	* Method that returns the XML result of applying the XSL transformation
	to the data supplied.
	* @param xml XML input.
	* @param xslt XSL Transform.
	* @return the XML result of applying the XSL transformation
	* @throws ToolsException There was an error processing the XML.
	*/
	public static byte[ ] xslTransform(byte[ ] xml, byte[ ] xslt) throws ToolsException {
		ByteArrayInputStream xsltIn = new ByteArrayInputStream(xslt);
		ByteArrayInputStream xmlIn = new ByteArrayInputStream(xml);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer(new StreamSource(xsltIn));
			Source src = new StreamSource(xmlIn);
			Result res = new StreamResult(out);
			transformer.transform(src, res);
			return out.toByteArray();
		} catch (Exception e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.XSL_TRANSFORM_ERROR, "", e);
		} finally {
			UtilsResources.safeCloseInputStream(xsltIn);

			UtilsResources.safeCloseInputStream(xmlIn);

			UtilsResources.safeCloseOutputStream(out);

		}

	}

	//
	/**
	* Gets a XML as bytes array.
	* @param xml XML document.
	* @return XML as bytes array.
	* @throws ToolsException There was an error processing the XML.
	*/
	public static byte[ ] getXMLBytes(Node xml) throws ToolsException {
		ByteArrayOutputStream baos = null;

		try {

			TransformerFactory tf = TransformerFactory.newInstance();

			Transformer trans = tf.newTransformer();
			baos = new ByteArrayOutputStream();
			trans.transform(new DOMSource(xml), new StreamResult(baos));

			byte[ ] eSignatureBytes = baos.toByteArray();

			return eSignatureBytes;
		} catch (Exception e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.XML_PARSER_ERROR, "", e);
		} finally {
			UtilsResources.safeCloseOutputStream(baos);
		}
	}

	/**
	 * Gets Document interface that represents the entire HTML or XML document.
	 * @param in	InputStream containing the content to be parsed.
	 * @return Document Result of parsing the InputStream.
	 * @throws ToolsException 	There was an error processing the XML.
	 */
	public static Document getDocument(InputStream in) throws ToolsException {
		try {
			return getDocumentImpl(in);
		} catch (Exception e) {
			LOG.error(e);
			throw new ToolsException(ToolsException.XML_PARSER_ERROR, "", e);
		}
	}

	/**
	 * Gets Document interface that represents the entire HTML or XML document.
	 * @param xml	Array of bytes that contain the content to be parsed.
	 * @return	Document result of parsing the bytes.
	 * @throws 	ToolsException There was an error processing the XML.
	 */
	public static Document getDocument(byte[ ] xml) throws ToolsException {
		Document xmlDoc = null;
		if (xml != null) {
			ByteArrayInputStream in = new ByteArrayInputStream(xml);
			xmlDoc = getDocument(in);
			UtilsResources.safeCloseInputStream(in);
		}
		return xmlDoc;
	}

	// /**
	// * Method that includes a DOM object into the XML supplied and in the
	// indicated path.
	// * @param xml Document for including of element.
	// * @param xpath Xpath that locates the node where will be included the
	// element.
	// * @param value Object that represents the element to be included. Allowed
	// Object:<br/>
	// * {@link String} - If the object to include is a text node or a cdata
	// section.<br/>
	// * {@link Node} - If the object to include is a element node.<br/>
	// * @param type Parameter that indicates the type object to be included.
	// Allowed values:<br/>
	// * {@link Node#TEXT_NODE} - If the object to include is a text node.<br/>
	// * {@link Node#CDATA_SECTION_NODE} - If the object to include is a cdata
	// section.<br/>
	// * {@link Node#ELEMENT_NODE} - If the object to include is a element
	// node.<br/>
	// * @throws ToolsException There was an error processing the XML.
	// */
	// public static void includeElementValue(Document xml, String xpath, Object
	// value, int type) throws ToolsException {
	//
	// NodeList nl = null;
	// try {
	// nl = XPathAPI.selectNodeList(xml.getDocumentElement(), xpath);
	// } catch (TransformerException e) {
	// String msg = Language.getFormatMessage(LanguageKeys.UTIL_004, new Object[
	// ] { xpath });
	// LOGGER.error(msg, e);
	// throw new ToolsException(ToolsException.XPATH_ERROR, msg, e);
	// }
	//
	// switch (type) {
	//
	// case Node.TEXT_NODE:
	// for (int i = 0; i < nl.getLength(); i++) {
	// Text text = xml.createTextNode((String) value);
	// nl.item(i).appendChild(text);
	// }
	// break;
	// case Node.CDATA_SECTION_NODE:
	// for (int i = 0; i < nl.getLength(); i++) {
	// CDATASection text = xml.createCDATASection((String) value);
	// nl.item(i).appendChild(text);
	// }
	// break;
	// case Node.ELEMENT_NODE:
	// for (int i = 0; i < nl.getLength(); i++) {
	// Node impNode = xml.importNode(((Node) value).cloneNode(true), true);
	// nl.item(i).appendChild(impNode);
	// }
	// default:
	// break;
	// }
	//
	// }
	//
	// /**
	// * This method removes of the XML supplied the nodes indicated.
	// * @param xml Document for removing the nodes supplied.
	// * @param nodesToRemove List of XPath that locate the nodes to remove.
	// * @throws ToolsException There was an error processing the XML.
	// */
	// public static void removeNodes(Document xml, ArrayList<String>
	// nodesToRemove) throws ToolsException {
	// Iterator<String> it = nodesToRemove.iterator();
	// while (it.hasNext()) {
	// String xpath = (String) it.next();
	// NodeList nl;
	// try {
	// nl = XPathAPI.selectNodeList(xml.getDocumentElement(), xpath);
	// } catch (TransformerException e) {
	// String msg = Language.getFormatMessage(LanguageKeys.UTIL_004, new Object[
	// ] { xpath });
	// LOGGER.error(msg, e);
	// throw new ToolsException(ToolsException.XPATH_ERROR, msg, e);
	// }
	// for (int i = 0; i < nl.getLength(); i++) {
	// removeNode(nl.item(i));
	// }
	// }
	// }
	//
	// /**
	// * Remove a XML node.
	// * @param node Node to remove.
	// */
	// private static void removeNode(Node node) {
	// Node parent = node.getParentNode();
	// parent.removeChild(node);
	// // Eliminamos los espacios en blanco
	// while (parent.getParentNode().getNodeType() != Node.DOCUMENT_NODE &&
	// getNumChildElement(parent) == 0) {
	// Node child = parent;
	// parent = parent.getParentNode();
	// parent.removeChild(child);
	// }
	// }
	//
	// /**
	// * Methods that returns the number of element or cdata is contained into
	// node supplied.
	// * @param node Node whose children want to know.
	// * @return Number of element or cdata is contained into node supplied.
	// */
	// private static int getNumChildElement(Node node) {
	// int n = 0;
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeType() == Node.ELEMENT_NODE ||
	// l.item(i).getNodeType() == Node.CDATA_SECTION_NODE) {
	// n++;
	// }
	// }
	// return n;
	// }
	/**
	 * Gets Document interface that represents the entire HTML or XML document.
	 * @param in	InputStream containing the content to be parsed.
	 * @return Document Result of parsing the InputStream.
	 * @throws ParserConfigurationException If an error occurs while parsing the document.
	 * @throws IOException If an error occurs while reading the document.
	 * @throws SAXException If an error occurs while processing the document.
	 */
	protected static Document getDocumentImpl(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(in);
	}
}
