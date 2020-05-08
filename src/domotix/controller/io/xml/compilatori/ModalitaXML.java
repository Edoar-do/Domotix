package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

public class ModalitaXML implements CompilatoreXML<Modalita> {
    
    /** Metodo per scrittore: MODALITA **/
    @Override
    public Element compileInstance(Modalita obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Modalita obj, Document doc) {
		Modalita modalita = (Modalita)obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_MODALITA);

		//assegna attributo identificativo
		Attr nome = doc.createAttribute(CostantiXML.NODO_XML_MODALITA_NOME);
		nome.setValue(modalita.getNome());
		root.setAttributeNode(nome);

		Element elem;
		for (Parametro par : modalita.getParametri()) {
			elem = ParametroXML.compileElement(par, doc);
			root.appendChild(elem);
		}

		return root;
    }

	
    
}