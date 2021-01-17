package packCodigo;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserRanking extends DefaultHandler {
	private static XMLParserRanking mXMLParserRanking = new XMLParserRanking();
	private String texto = null;
	private String nombre = null;
	private int puntos = 0;
	private Jugador jug = null;
	
	/**
	 * Funcionalidad Premios:
	 * Declaraciones de las variables 'ganadas' y 'seguidas'.
	 */
	private String ganadas="";
	private String seguidas="";

//	private Puntuacion puntuacionActual = null;

	// Atributo que contiene la factoría de tags
	private TagOperatorFactory operatorFactory = new TagOperatorFactory();

	private XMLParserRanking() {
		// Constructora de la clase XMLParser. Implementación por defecto
	}

	public static XMLParserRanking getPDF2XMLParser() {
		// Método de acceso a la única instancia de XMLParser
		return mXMLParserRanking;
	}

	public void parseXmlFile(String pFile) throws XmlParsingException {
		// Método que procesa el fichero XML
		// Utiliza el patrón Factory para obtener la instancia de parser
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		// saxParserFactori es una instancia de la factoría SAXParserFactory
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			// Crea una nueva instancia de la clase SAXParser utilizando los
			// parámetros de la factoría
			// previamente construida

			/*
			 * Procesa el fichero XML. Le pasamos la instancia que procesa las
			 * etiquetas, documento, etc. Tiene que proporcionar los métodos
			 * que se implementan debajo
			 */

			saxParser.parse(new FileInputStream(pFile), this); // this en vez de defaul
		} catch (Exception e) {


		}
	}

	public void characters(char[] pCh, int pStart, int pLength)
			throws SAXException {
		// Este método de la clase SAXParser se invoca cuando SAX encuentra
		// texto dentro de un elemento.
		// Es frecuente que el texto esté formado por varios caracteres, por lo
		// que NO se lee todo de una pasada
		// y cada caracter leido debe ir añadiédose para construir el texto
		// final.

		// Para construir el String correspondiente al texto contenido entre un
		// tag de inicio y su correspondiente
		// tag de finalización:
		texto = new String(pCh, pStart, pLength).trim();
	}

	public void endDocument() throws SAXException {
		// Notifica el final de un documento XML. Aquí podeis incluir lo que
		// quereis hacer al finalizar la revisión del
		// documento XML. En el caso que nos ocupa podeis imprimir en la salida
		// estándar un mensaje informativo,
		// diciendo que finaliza el proceso de parseo
	}

	public void endElement(String pUri, String pLocalName, String pName)
			throws SAXException {
		// Notifica el final de un elemento. Se ejecuta cuando SAX lee un tag de
		// finalización: </nombreTag>
		// El parámetro pName contiene el nombre del tag
		// En nuestro caso, el fichero XML contiene varios tags distintos, por
		// lo que el proceso a ejecutar cuando se
		// detecte un tag de finalización será diferente para cada uno de
		// ellos.
		// Para simplificar y generalizar el código de este método
		// utilizaremos el patrón Symple Factory:
		// a) Definiremos una clase privada interna que implemente la factoría
		// de tags
		// b) Definiremos una clase privada interna para cada tag que aparece en
		// el fichero XML.
		// c) Estas clases privadas correspondientes a los diferentes tag
		// descienden de una interfaz común en la que
		// se especifica el método invoqueEnd(). Este método se implementará
		// en todas las clases, y contendrá las
		// instrucciones a ejecutar cada vez que se detecte dicho tag de
		// finalización.
		
		TagOperator op = operatorFactory.getTagOperator(pName); // Recoge la
																// instancia de
																// tag que ha
																// finalizado
		if (op == null) {
			// TODO: Add Log information
			return;
		}
		op.invokeEnd(); // Invoca al metodo de finalización adecuado a la clase
						// de tag que ha finalizado
	}

	public void startDocument() throws SAXException {
		// Notifica el comienzo de un documento XML. Aquí podeis incluir lo que
		// quereis hacer al comenzar a tratar
		// el fichero XML. En el caso que nos ocupa podeis imprimir en la salida
		// estándar un mensaje informativo,
		// diciendo que comienza el proceso de parseo
	}

	public void startElement(String pUri, String pLocalName, String pName,
			Attributes pAttributes) throws SAXException {
		// Notifica el comienzo de un elemento (cuando detecta un tag de
		// comienzo).
		// Aquí se incluirá lo que se debe hacer al encontrar un tag de inicio
		// de elemento. En el caso que nos ocupa,
		// no es necesario hacer nada al encontrar un tag de comienzo, por lo
		// que podeis dejar vacía la implementación
		// de este método
	}

	private class TagOperatorFactory { // Es un TAD. Se ha incluido en XMLParser
										// un atributo que contenga su
										// instanciación
		private Map<String, TagOperator> operators = null;

		public TagOperatorFactory() {
			operators = new HashMap<String, TagOperator>();
			operators.put("root", new rootTagOperator());
			operators.put("Puntuacion", new PuntuacionTagOperator());
			operators.put("Nombre", new NombreTagOperator());
			
			/**
			 * Funcionalidad Premios:
			 * A�adimos los operators para Ganadas y Seguidas.
			 */
			operators.put("Ganadas", new GanadasTagOperator());
			operators.put("Seguidas", new SeguidasTagOperator());
		}

		public TagOperator getTagOperator(String pName) {
			return operators.get(pName);
		}
	}

	// Tag operators

	private class rootTagOperator implements TagOperator {
		public void invokeEnd(){
			
		}
	}
	
	private class PuntuacionTagOperator implements TagOperator {
		// Hereda la constructora de Object
		
		// Implementación del tratamiento del tag </Definicion>
		public void invokeEnd() {
			puntos = Integer.parseInt(texto);
			jug.establecerPuntuacion(puntos);
			Ranking.getRanking().anadirLista(jug);
		}

	}

	private class NombreTagOperator implements TagOperator {

		public void invokeEnd() {
			nombre = texto;
			jug = new Jugador(nombre);
		}

	}

	// TENEIS QUE IMPLEMENTAR UNA CLASE PRIVADA SIMILAR A ESTA PARA CADA TAG QUE
	// DEFINAIS EN VUESTRO FICHERO XML
	
	/**
	 * Funcionalidad Premios:
	 * Clase privada para nuestro tag 'Ganadas'.
	 */
	private class GanadasTagOperator implements TagOperator {
		// Implementacion del tratamiento del tag </Ganadas>
		public void invokeEnd() {
			ganadas = texto;
			jug.extraerGanadas(texto);
		}

	}
	/**
	 * Funcionalidad Premios:
	 * Clase privada para nuestro tag 'Seguidas'.
	 */
	private class SeguidasTagOperator implements TagOperator {
		// Implementacion del tratamiento del tag </Seguidas>
		public void invokeEnd() {
			seguidas = texto;
			jug.extraerSeguidas(texto);
		}

	}

}

