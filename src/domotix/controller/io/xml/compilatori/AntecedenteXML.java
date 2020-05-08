package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.regole.Antecedente;

public class AntecedenteXML implements CompilatoreXML<Antecedente> {

    /** Metodo per scrittore: ANTECEDENTE **/
    @Override
    public Element compileInstance(Antecedente obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(Antecedente obj, Document doc) {
		Antecedente antecedente = (Antecedente) obj;

		//crea elemento base
		Element root = doc.createElement(CostantiXML.NODO_XML_ANTECEDENTE);

		//popola elemento base
		Element elem = CondizioneXML.compileElement(antecedente.getCondizione(), doc);
		root.appendChild(elem);

		if (!antecedente.isLast()) {
			elem = doc.createElement(CostantiXML.NODO_XML_ANTECEDENTE_OPLOGICO);
			elem.appendChild(doc.createTextNode(antecedente.getOperatoreLogico()));
			root.appendChild(elem);

			elem = AntecedenteXML.compileElement(antecedente.getProssimoAntecedente(), doc);
			root.appendChild(elem);
		}

		return root;
    }

	
    
}