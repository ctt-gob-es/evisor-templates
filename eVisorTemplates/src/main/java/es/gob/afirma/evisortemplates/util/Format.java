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
 * <b>File:</b><p>es.gob.afirma.evisortemplates.components.util.Format.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * <b>Date:</b><p>12/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.1, 14/06/2018.
 */
package es.gob.afirma.evisortemplates.util;

import es.gob.afirma.evisortemplates.components.PositionAngle;
import es.gob.afirma.evisortemplates.components.impl.RichLabel;
import es.gob.afirma.evisortemplates.components.impl.RichTable;
import es.gob.afirma.evisortemplates.components.impl.RichTableCel;
import es.gob.afirma.evisortemplates.components.impl.RichTableRow;
import es.gob.afirma.evisortemplates.components.impl.Template;
import es.gob.afirma.evisortemplates.properties.ConfigEVT;
import es.gob.afirma.evisortemplates.properties.MessagesEVT;

/** 
 * <p>Class that allows formatting tasks.</p>
 * <b>Project:</b><p>Template XSLT designer.</p>
 * @version 1.1, 14/06/2018.
 */
public final class Format {

	/**
	 * Attribute that represents TypeImage constant. 
	 */
	public static final String TYPEIMAGE = "TypeImage";
	
	/**
	 * Attribute that represents TypeBarCode constant. 
	 */
	public static final String TYPEBARCODE = "TypeBarCode";
	
	/**
	 * Attribute that represents TypeAll constant. 
	 */
	public static final String TYPEALL = "TypeAll";

	/**
	 * Attribute that represents ']/ri:FieldValue constant. 
	 */
	private static final String RIFIELDVALUE = "']/ri:FieldValue";
	
	/**
	 * Attribute that represents /fo:inline> constant. 
	 */
	private static final String FOINLINE = "</fo:inline>";
	
	/**
	 * Attribute that represents ri:ExternalParameters/ri:Parameter[ri:ParameterId=' constant. 
	 */
	public static final String BEFOREPARAMETER = "ri:ExternalParameters/ri:Parameter[ri:ParameterId='";
	
	/**
	 * Attribute that represents ']/ri:ParameterValue constant. 
	 */
	public static final String AFTERPARAMETER = "']/ri:ParameterValue";
	
	/**
	 * Attribute that represents String  font-size=\" constant. 
	 */
	public static final String FONTSIZE = " font-size=\"";

	/**
	 * Constructor method for the class Format.java. 
	 */
	private Format() {
		super();
	}

	/**
	 * Method that formats "for each result" variables.
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String formatVariableForEachResult(String index, String value) {
		if (index != null) {
			return "../../ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:Result/ri:" + value;
		}
		return "ri:Result/ri:" + value;
	}

	/**
	 * Method that formats "for each time stamp" variables.
	  * @param index index for variable
	 * @return formated variable
	 */
	private static String formatVariableForEachTimeStamp(String index) {
		if (index != null) {
			return "../../ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:TimeStamp";
		}
		return "ri:TimeStamp";
	}

	/**
	 * Method that formats "for each certificate info" variables.
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String formatVariableForEachCertificateInfo(String index, String value) {
		if (index != null) {
			return "../../ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:CertificateInfo/ri:Field[ri:FieldId='" + value + RIFIELDVALUE;
		}
		return "ri:CertificateInfo/ri:Field[ri:FieldId='" + value + RIFIELDVALUE;
	}

	/**
	 * Method that formats "for each" variables.
	 * @param type type of variable
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String fomratVariableForEach(String type, String index, String value) {
		if (type.equals(ConfigEVT.getInstance().getProperty("globalResult"))) {
			return "../../ri:ValidationResult/ri:Result/ri:" + value;
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("result"))) {
			return formatVariableForEachResult(index, value);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("timeStamp"))) {
			return formatVariableForEachTimeStamp(index);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("certificateInfo"))) {
			return formatVariableForEachCertificateInfo(index, value);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("generationTime"))) {
			return "../../ri:GenerationTime";
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("parameter"))) {
			return "../../ri:ExternalParameters/ri:Parameter[ri:ParameterId='" + value + "']/ri:ParameterValue";
		}
		if (type.contains(ConfigEVT.getInstance().getProperty("barCode"))) {
			return "../../ri:Barcodes/ri:" + type + "/ri:" + value;
		}
		return "";
	}

	/**
	 * Method that formats "not for each result" variables.
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String formatVariableNoForEachResult(String index, String value) {
		if (index != null) {
			return "ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:Result/ri:" + value;
		}
		return "ri:ValidationResult/ri:IndividualSignature/ri:Result/ri:" + value;
	}

	/**
	 * Method that formats "not for each time stamp" variables.
	  * @param index index for variable
	 * @return formated variable
	 */
	private static String formatVariableNoForEachTimeStamp(String index) {
		if (index != null) {
			return "ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:TimeStamp";
		}
		return "ri:ValidationResult/ri:IndividualSignature/ri:TimeStamp";
	}

	/**
	 * Method that formats "not for each certificate info" variables.
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String formatVariableForNoEachCertificateInfo(String index, String value) {
		if (index != null) {
			return "ri:ValidationResult/ri:IndividualSignature[" + index + "]/ri:CertificateInfo/ri:Field[ri:FieldId='" + value + RIFIELDVALUE;
		}
		return "ri:ValidationResult/ri:IndividualSignature/ri:CertificateInfo/ri:Field[ri:FieldId='" + value + RIFIELDVALUE;
	}

	/**
	 * Method that formats "not for each" variables.
	 * @param type type of variable
	 * @param index index for variable
	 * @param value value of the variable
	 * @return formated variable
	 */
	private static String fomratVariableNoForEach(String type, String index, String value) {
		if (type.equals(ConfigEVT.getInstance().getProperty("globalResult"))) {
			return "ri:ValidationResult/ri:Result/ri:" + value;
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("result"))) {
			return formatVariableNoForEachResult(index, value);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("timeStamp"))) {
			return formatVariableNoForEachTimeStamp(index);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("certificateInfo"))) {
			return formatVariableForNoEachCertificateInfo(index, value);
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("generationTime"))) {
			return "ri:GenerationTime";
		}
		if (type.equals(ConfigEVT.getInstance().getProperty("parameter"))) {
			return "ri:ExternalParameters/ri:Parameter[ri:ParameterId='" + value + "']/ri:ParameterValue";
		}
		if (type.contains(ConfigEVT.getInstance().getProperty("barCode"))) {
			return "ri:Barcodes/ri:" + type + "/ri:" + value;
		}
		if (type.contains(ConfigEVT.getInstance().getProperty("globalVar"))){
			return "$"+value;
		}
		return "";
	}

	/**
	 * Method that formats a variable for XSLT representation.
	 * @param variable variable to be formated
	 * @param comp component who contains the variable
	 * @return formated variable
	 */
	public static String formatVariable(String variable, PositionAngle comp) {

		String var = variable.split("¬")[1];
		String type = var.split(":")[0];
		String index = null;
		//
		if (type.contains("[")) {
			int ind = type.indexOf('[');
			int ind2 = type.indexOf(']');
			index = type.substring(ind + 1, ind2);
			type = type.substring(0, ind);

		}
		String value;
		try {
			value = var.split(":")[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			// Si salta la exception significa que no han nada tras los : porque
			// el usuario habrá eliminado el espacio. En
			// ese caso se introduce un espacio vacío.
			value = " ";
		}

		boolean forEach = false;
		if (comp.getParent() instanceof Template) {
			forEach = comp.isForEach();

		} else if (comp.getParent() instanceof RichTableCel) {

			forEach = ((RichTableCel) comp.getParent()).isForEach();

			if (!forEach) {
				forEach = ((RichTableCel) comp.getParent()).isForEachTable();
			}

		}

		if (forEach) {
			return fomratVariableForEach(type, index, value);

		} else {

			return fomratVariableNoForEach(type, index, value);

		}
	}

	/**
	 * Method that formats text block.
	 * @param text text to format
	 * @return formated text
	 */
	public static String formatText(String text) {
		String result;

		result = text;

		result = result.replace("<b>", "<fo:inline font-weight=\"bold\">");
		result = result.replace("<b", "<fo:inline font-weight=\"bold\" ");
		result = result.replace("</b>", FOINLINE);

		result = result.replace("<i>", "<fo:inline  font-style=\"italic\">");
		result = result.replace("<i", "<fo:inline  font-style=\"italic\" ");
		result = result.replace("</i>", FOINLINE);

		result = result.replace("<u>", "<fo:inline  text-decoration=\"underline\">");
		result = result.replace("<u", "<fo:inline  text-decoration=\"underline\" ");
		result = result.replace("</u>", FOINLINE);

		// text = text.replace("<font color", "<fo:inline color");

		result = result.replace("<font", "<fo:inline ");

		result = result.replace(" size=\"1\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeXXS")+"\"");

		result = result.replace(" size=\"2\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeXS")+"\"");

		result = result.replace(" size=\"3\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeS")+"\"");

		result = result.replace(" size=\"4\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeM")+"\"");

		result = result.replace(" size=\"5\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeL")+"\"");
		
		result = result.replace(" size=\"6\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeXL")+"\"");

		result = result.replace(" size=\"7\"", FONTSIZE+ConfigEVT.getInstance().getProperty("fontSizeXXL")+"\"");

		result = result.replace(" face=\"", " font-family=\"");

		result = result.replace("</font>", FOINLINE);

		result = result.replace("<span style=\"background-color:", "<fo:inline  background-color=\"");

		result = result.replace(" style=\"background-color:", " background-color=\"");

		result = result.replace(";", "");
		result = result.replace("</span>", FOINLINE);

		return result;
	}

	/**
	 * Method that returns a parameter from a variable.
	 * @param variable variable to be checked
	 * @return parameter from variable
	 */
	public static String parameter(String variable) {
		String var = variable.split("¬")[1];
		return var.split(":")[1];
	}

	/**
	 * Method that transforms parameter variable to template format.
	 * @param var variable to be transform
	 * @return transformed variable
	 */
	public static String getParameterVarToTemplate(String var) {
		return "¬Parameter:" + var + "¬";
	}

	/**
	 * Method that removes index zone to variable ([index]).
	 * @param value variable to be changed
	 * @return result variable.
	 */
	public static String removeIndex(String value) {

		return value.replaceAll("\\[[0-9]+\\]", "");

	}

	/**
	 * Method that checks if a variable have index zone.
	 * @param value variable to be checked
	 * @return true if the variable have index zone, false otherwise
	 */
	public static boolean hasVarIndex(String value) {
		if (value != null && !value.isEmpty() && value.contains("¬")) {
			String var = value.split("¬")[1];
			if (var.contains("¬")) {
				var = value.split("¬")[0];
			}
			String type = var.split(":")[0];
			//
			if (type.contains("[")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that checks if a table have a variable that have index zone.
	 * @param tableParam table to be checked
	 * @return true if the table have a variable that have index zone, false otherwise
	 */
	public static boolean hasAnyVarIndexInTable(RichTable tableParam) {
		for (RichTableRow row: tableParam.getRows()) {
			for (RichTableCel cel: row.getCells()) {
				if (cel.getComponentCount() > 0) {
					PositionAngle comp = (PositionAngle) cel.getComponentIterator().next();
					if (comp != null && comp.isText() && hasVarIndex(((RichLabel) comp).getOriginalValue())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Method that returns equivalent representation for variable type.
	 * @param value type to be transform
	 * @return equivalent type
	 */
	public static String equivalent(String value) {
		if (MessagesEVT.getInstance().getProperty("opCertificateInfo").equals(value)) {
			return ConfigEVT.getInstance().getProperty("certificateInfo");
		} else if (MessagesEVT.getInstance().getProperty("opGenerationTime").equals(value)) {
			return ConfigEVT.getInstance().getProperty("generationTime");
		} else if (MessagesEVT.getInstance().getProperty("opGlobalResult").equals(value)) {
			return ConfigEVT.getInstance().getProperty("globalResult");
		} else if (MessagesEVT.getInstance().getProperty("opParameter").equals(value)) {
			return ConfigEVT.getInstance().getProperty("parameter");
		} else if (MessagesEVT.getInstance().getProperty("opResult").equals(value)) {
			return ConfigEVT.getInstance().getProperty("result");
		} else if (MessagesEVT.getInstance().getProperty("opTimeStamp").equals(value)) {
			return ConfigEVT.getInstance().getProperty("timeStamp");
		} else if (MessagesEVT.getInstance().getProperty("opBarCode").equals(value)) {
			return ConfigEVT.getInstance().getProperty("barCode");
		} else if (!value.equals("")){
			return ConfigEVT.getInstance().getProperty("globalVar");
		}
		return "";
	}
}
