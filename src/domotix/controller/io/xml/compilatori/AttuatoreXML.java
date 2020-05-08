package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Attuatore;

public class AttuatoreXML implements CompilatoreXML<Attuatore> {

    /** Metodo per scrittore: ATTUATORE **/
    @Override
    public Element compileInstance(Attuatore obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Attuatore obj, Document doc) {
		Attuatore attuatore = (Attuatore) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_ATTUATORE);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_ATTUATORE_NOME);
		nome.setValue(attuatore.getNome());
		root.setAttributeNode(nome);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_ATTUATORE_STATO);
		elem.appendChild(doc.createTextNode(attuatore.getStato() ? "1" : "0"));
		root.appendChild(elem);

		elem = doc.createElement(CostantiXML.NODO_XML_ATTUATORE_CATEGORIA);
		elem.appendChild(doc.createTextNode(attuatore.getCategoria().getNome()));
		root.appendChild(elem);

		elem = doc.createElement(CostantiXML.NODO_XML_ATTUATORE_MODALITA);
		elem.appendChild(doc.createTextNode(attuatore.getModoOp().getNome()));
		root.appendChild(elem);

		return root;
    }

	
    
}