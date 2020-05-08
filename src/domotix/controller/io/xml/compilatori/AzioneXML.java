package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Parametro;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.Azione;

public class AzioneXML implements CompilatoreXML<Azione> {

    /** Metodo per scrittore: AZIONE **/
    @Override
    public Element compileInstance(Azione obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Azione obj, Document doc) {
		Azione azione = (Azione) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_AZIONE);

		//popola elemento base
		Element elem = doc.createElement(CostantiXML.NODO_XML_AZIONE_ATTUATORE);
		elem.appendChild(doc.createTextNode(azione.getAttuatore().getNome()));
		root.appendChild(elem);

		elem = doc.createElement(CostantiXML.NODO_XML_AZIONE_MODALITA);
		elem.appendChild(doc.createTextNode(azione.getModalita().getNome()));
		root.appendChild(elem);

		for (Parametro p : azione.getParametri()) {
			elem = ParametroXML.compileElement(p, doc);
			root.appendChild(elem);
		}

		if (azione.getStart() != null) {
			elem = doc.createElement(CostantiXML.NODO_XML_AZIONE_START);
			elem.appendChild(doc.createTextNode(azione.getStart().format(SensoreOrologio.TIME_FORMATTER)));
			root.appendChild(elem);
		}

		return root;
    }

	
    
}