package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.Sensore;

public class SensoreXML implements CompilatoreXML<Sensore> {
    
    /** Metodo per scrittore: SENSORE **/
    @Override
    public Element compileInstance(Sensore obj, Document doc) {
        return compileElement(obj, doc);
    }

    public static Element compileElement(Sensore obj, Document doc) {
        Sensore sensore = (Sensore) obj;

        // crea elemento base
        Element root = doc.createElement(CostantiXML.NODO_XML_SENSORE);

        // assegna attributo identificativo
        Attr nome = doc.createAttribute(CostantiXML.NODO_XML_SENSORE_NOME);
        nome.setValue(sensore.getNome());
        root.setAttributeNode(nome);

        // popola elemento base
        Element elem = doc.createElement(CostantiXML.NODO_XML_SENSORE_STATO);
        elem.appendChild(doc.createTextNode(sensore.getStato() ? "1" : "0"));
        root.appendChild(elem);

        elem = doc.createElement(CostantiXML.NODO_XML_SENSORE_CATEGORIA);
        elem.appendChild(doc.createTextNode(sensore.getCategoria().getNome()));
        root.appendChild(elem);

        sensore.getValori().forEach((nomeInfo, valore) -> {
            Element elemInfo = doc.createElement(CostantiXML.NODO_XML_SENSORE_VALORE);

            Attr attrNomeInfo = doc.createAttribute(CostantiXML.NODO_XML_SENSORE_NOME_VALORE);
            attrNomeInfo.setValue(nomeInfo);
            elemInfo.setAttributeNode(attrNomeInfo);

            elemInfo.appendChild(doc.createTextNode(valore + ""));
            root.appendChild(elemInfo);
        });

        return root;
    }
    
}