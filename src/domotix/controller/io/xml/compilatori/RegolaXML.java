package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.regole.Regola;

public class RegolaXML implements CompilatoreXML<Regola> {

    /** Metodo per scrittore: REGOLA **/
    @Override
    public Element compileInstance(Regola obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Regola obj, Document doc) {
		Regola regola = (Regola) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_REGOLA);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_REGOLA_ID);
		nome.setValue(regola.getId());
		root.setAttributeNode(nome);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_REGOLA_STATO);
		//elem.appendChild(doc.createTextNode(regola.getStato() ? "1" : "0"));
		elem.appendChild(doc.createTextNode(regola.getStato().name()));
		root.appendChild(elem);

		//antecedente e conseguente
		if (regola.getAntecedente() != null) {
			elem = AntecedenteXML.compileElement(regola.getAntecedente(), doc);
			root.appendChild(elem);
		} //se antecedente = null allora e' TRUE e non lo scrivo

		elem = ConseguenteXML.compileElement(regola.getConseguente(), doc);
		root.appendChild(elem);

		return root;
	}

    
    
}