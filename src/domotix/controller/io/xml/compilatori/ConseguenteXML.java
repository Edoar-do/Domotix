package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Conseguente;

public class ConseguenteXML implements CompilatoreXML<Conseguente> {

    /** Metodo per scrittore: CONSEGUENTE **/
    @Override
    public Element compileInstance(Conseguente obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Conseguente obj, Document doc) {
		Conseguente conseguente = (Conseguente) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_CONSEGUENTE);

		Element elem;
		for (Azione a : conseguente.getAzioni()) {
			elem = AzioneXML.compileElement(a, doc);
			root.appendChild(elem);
		}

		return root;
    }

	
    
}