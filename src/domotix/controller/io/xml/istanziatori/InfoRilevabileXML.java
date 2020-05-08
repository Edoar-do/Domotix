package domotix.controller.io.xml.istanziatori;


import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.InfoRilevabile;

public class InfoRilevabileXML implements IstanziatoreXML<InfoRilevabile> {

    /** Metodo per scrittore: INFORMAZIONE_RILEVABILE **/
    @Override
    public InfoRilevabile getInstance(Element el) throws Exception {
        return instanceElement(el);
	}

	/** Metodo per lettore: INFORMAZIONE_RILEVABILE **/
	public static InfoRilevabile instanceElement(Element el) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_INFORILEVABILE)) {
	        String nome;
	        boolean numerica;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_INFORILEVABILE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_INFORILEVABILE_NOME);
	        } else
	            throw new NoSuchElementException("InfoRilevabileXML.instanceElement(): attributo " + CostantiXML.NODO_XML_INFORILEVABILE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFORILEVABILE_NUMERICA);
	        if (childs.getLength() > 0) {
	            numerica = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
	        } else
	            throw new NoSuchElementException("InfoRilevabileXML.instanceElement(): elemento " + CostantiXML.NODO_XML_INFORILEVABILE_NUMERICA + " assente.");
	
	        //ritorno istanza corretta
	        return new InfoRilevabile(nome, numerica);
	    }
	    else
	        throw new NoSuchElementException("InfoRilevabileXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_INFORILEVABILE);
	}

	
}