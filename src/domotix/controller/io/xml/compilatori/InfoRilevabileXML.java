package domotix.controller.io.xml.compilatori;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.InfoRilevabile;

public class InfoRilevabileXML implements CompilatoreXML<InfoRilevabile> {

    /** Metodo per scrittore: INFORMAZIONE_RILEVABILE **/
    @Override
    public Element compileInstance(InfoRilevabile obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(InfoRilevabile obj, Document doc) {
		InfoRilevabile info = (InfoRilevabile)obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_INFORILEVABILE);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_INFORILEVABILE_NOME);
		nome.setValue(info.getNome());
		root.setAttributeNode(nome);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_INFORILEVABILE_NUMERICA);
		elem.appendChild(doc.createTextNode(info.isNumerica() ? "1" : "0"));
		root.appendChild(elem);

		return root;
    }

	
}