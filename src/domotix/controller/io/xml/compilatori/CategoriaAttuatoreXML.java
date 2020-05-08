package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.Modalita;

public class CategoriaAttuatoreXML implements CompilatoreXML<CategoriaAttuatore> {

    /** Metodo per scrittore: CATEGORIA_ATTUATORE **/
    @Override
    public Element compileInstance(CategoriaAttuatore obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(CategoriaAttuatore obj, Document doc) {
		CategoriaAttuatore cat = (CategoriaAttuatore)obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME);
		nome.setValue(cat.getNome());
		root.setAttributeNode(nome);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
		elem.appendChild(doc.createTextNode(cat.getTestoLibero()));
		root.appendChild(elem);

		for (Modalita modalita : cat.getElencoModalita()) {
			elem = doc.createElement(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
			elem.appendChild(doc.createTextNode(modalita.getNome()));
			root.appendChild(elem);
		}

		return root;
    }

	
    
}