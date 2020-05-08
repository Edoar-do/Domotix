package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Stanza;

public class UnitaImmobiliareXML implements CompilatoreXML<UnitaImmobiliare> {

    /** Metodo per scrittore: UNITA_IMMOB **/
    @Override
    public Element compileInstance(UnitaImmobiliare obj, Document doc) {
        return compileElement(obj, doc);
    }

    public static Element compileElement(UnitaImmobiliare obj, Document doc) {
        UnitaImmobiliare unitaImmobiliare = (UnitaImmobiliare) obj;

        // crea elemento base
        Element root = doc.createElement(CostantiXML.NODO_XML_UNITA_IMMOB);

        // assegna attributo identificativo
        Attr nome = doc.createAttribute(CostantiXML.NODO_XML_UNITA_IMMOB_NOME);
        nome.setValue(unitaImmobiliare.getNome());
        root.setAttributeNode(nome);

        // popola elemento base
        Element elem = null;

        for (Stanza s : unitaImmobiliare.getStanze()) {
            elem = doc.createElement(CostantiXML.NODO_XML_UNITA_IMMOB_STANZA);
            elem.appendChild(doc.createTextNode(s.getNome()));
            root.appendChild(elem);
        }

        /*
         * Sensori salvati a livello di stanza/artefatto
         * 
         * //salva i sensori e attuatori presenti nell'unita' immobiliare (essendo qui
         * presenti tutti i riferimenti) for (Sensore s : unitaImmobiliare.getSensori())
         * { DatiLocali.getInstance().salva(s); } for (Attuatore a :
         * unitaImmobiliare.getAttuatori()) { DatiLocali.getInstance().salva(a); }
         */

        for (Regola r : unitaImmobiliare.getRegole()) {
            elem = doc.createElement(CostantiXML.NODO_XML_REGOLA);
            elem.appendChild(doc.createTextNode(r.getId()));
            root.appendChild(elem);
        }

        return root;
    }
    
}