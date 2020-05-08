package domotix.controller.io.xml.compilatori;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

public class StanzaXML implements CompilatoreXML<Stanza> {
    
    /** Metodo per scrittore: Stanza **/
    @Override
    public Element compileInstance(Stanza obj, Document doc) {
        return compileElement(obj, doc);
    }

    public static Element compileElement(Stanza obj, Document doc) {
        Stanza stanza = (Stanza) obj;

        //crea elemento base
        Element root = doc.createElement(CostantiXML.NODO_XML_STANZA);

        //assegna attributo identificativo
        Attr attr = doc.createAttribute(CostantiXML.NODO_XML_STANZA_NOME);
        attr.setValue(stanza.getNome());
        root.setAttributeNode(attr);

        attr = doc.createAttribute(CostantiXML.NODO_XML_STANZA_UNITA_IMMOB);
        attr.setValue(stanza.getUnitaOwner());
        root.setAttributeNode(attr);

        //popola elemento base
        Element elem = null;

        for (Sensore s : stanza.getSensori()) {
            elem = doc.createElement(CostantiXML.NODO_XML_STANZA_SENSORE);
            elem.appendChild(doc.createTextNode(s.getNome()));
            root.appendChild(elem);
        }

        for (Attuatore a : stanza.getAttuatori()) {
            elem = doc.createElement(CostantiXML.NODO_XML_STANZA_ATTUATORE);
            elem.appendChild(doc.createTextNode(a.getNome()));
            root.appendChild(elem);
        }

        for (Artefatto a : stanza.getArtefatti()) {
            elem = doc.createElement(CostantiXML.NODO_XML_STANZA_ARTEFATTO);
            elem.appendChild(doc.createTextNode(a.getNome()));
            root.appendChild(elem);
        }

        return root;
    }
    
}