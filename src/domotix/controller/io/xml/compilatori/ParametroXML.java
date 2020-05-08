package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Parametro;

public class ParametroXML implements CompilatoreXML<Parametro> {
	
	/** Metodo per scrittore: PARAMETRO_MODALITA **/
	@Override
	public Element compileInstance(Parametro obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Parametro obj, Document doc) {
		Parametro parametro = (Parametro) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_MODALITA_PARAMETRO);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_MODALITA_PARAMETRO_NOME);
		nome.setValue(parametro.getNome());
		root.setAttributeNode(nome);

		Element elem;
		elem = doc.createElement(CostantiXML.NODO_XML_MODALITA_PARAMETRO_VALORE);
		elem.appendChild(doc.createTextNode(Double.toString(parametro.getValore())));
		root.appendChild(elem);

		return root;
	}

	
    
}