package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;

public class ArtefattoXML implements CompilatoreXML<Artefatto> {

    /** Metodo per scrittore: ARTEFATTO **/
    @Override
    public Element compileInstance(Artefatto obj, Document doc) {
        return compileElement(obj, doc);
	}

	public static Element compileElement(Artefatto obj, Document doc) {
		Artefatto artefatto = (Artefatto) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_ARTEFATTO);

		//assegna attributo identificativo
		Attr attr = doc.createAttribute(CostantiXML.NODO_XML_ARTEFATTO_NOME);
		attr.setValue(artefatto.getNome());
		root.setAttributeNode(attr);

		attr = doc.createAttribute(CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB);
		attr.setValue(artefatto.getUnitaOwner());
		root.setAttributeNode(attr);

		//popola elemento base
		Element elem = null;

		for (Sensore s : artefatto.getSensori()) {
			elem = doc.createElement(CostantiXML.NODO_XML_ARTEFATTO_SENSORE);
			elem.appendChild(doc.createTextNode(s.getNome()));
			root.appendChild(elem);
		}

		for (Attuatore a : artefatto.getAttuatori()) {
			elem = doc.createElement(CostantiXML.NODO_XML_ARTEFATTO_ATTUATORE);
			elem.appendChild(doc.createTextNode(a.getNome()));
			root.appendChild(elem);
		}

		return root;
    }

	
    
}