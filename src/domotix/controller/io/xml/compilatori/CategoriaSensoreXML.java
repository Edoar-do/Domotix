package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.InfoRilevabile;

public class CategoriaSensoreXML implements CompilatoreXML<CategoriaSensore> {

    /** Metodo per scrittore: CATEGORIA_SENSORE **/
    @Override
    public Element compileInstance(CategoriaSensore obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(CategoriaSensore obj, Document doc) {
		CategoriaSensore cat = (CategoriaSensore)obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_SENSORE);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME);
		nome.setValue(cat.getNome());
		root.setAttributeNode(nome);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO);
		elem.appendChild(doc.createTextNode(cat.getTestoLibero()));
		root.appendChild(elem);

		for (InfoRilevabile i : cat.getInformazioniRilevabili()) {
			elem = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
			elem.appendChild(doc.createTextNode(i.getNome()));
			root.appendChild(elem);
		}

		return root;
    }

	
    
}