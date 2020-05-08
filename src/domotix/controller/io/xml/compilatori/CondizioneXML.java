package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.regole.Condizione;

public class CondizioneXML implements CompilatoreXML<Condizione> {

    /** Metodo per scrittore: CONDIZIONE **/
    @Override
    public Element compileInstance(Condizione obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Condizione obj, Document doc) {
		Condizione condizione = (Condizione) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_CONDIZIONE);

		//popola elemento base
		Element elem = InfoSensorialeXML.compileElement(condizione.getSinistra(), doc);
		Attr posizione = doc.createAttribute(CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE);
		posizione.setValue(CostantiXML.NODO_XML_CONDIZIONE_SINISTRA);
		elem.setAttributeNode(posizione);
		root.appendChild(elem);

		elem = InfoSensorialeXML.compileElement(condizione.getDestra(), doc);
		posizione = doc.createAttribute(CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE);
		posizione.setValue(CostantiXML.NODO_XML_CONDIZIONE_DESTRA);
		elem.setAttributeNode(posizione);
		root.appendChild(elem);

		elem = doc.createElement(CostantiXML.NODO_XML_CONDIZIONE_OPERATORE);
		elem.appendChild(doc.createTextNode(condizione.getOperatore()));
		root.appendChild(elem);

		return root;
    }

	
    
}