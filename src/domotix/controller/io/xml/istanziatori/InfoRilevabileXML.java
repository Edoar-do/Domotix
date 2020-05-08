package domotix.controller.io.xml.istanziatori;


import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.InfoRilevabile;

public class InfoRilevabileXML implements IstanziatoreXML<InfoRilevabile> {

    /** Metodo per scrittore: INFORMAZIONE_RILEVABILE **/
    @Override
    public InfoRilevabile getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: INFORMAZIONE_RILEVABILE **/
	public static Object leggiInfoRilevabile(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_INFORILEVABILE)) {
	        String nome;
	        boolean numerica;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_INFORILEVABILE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_INFORILEVABILE_NOME);
	        } else
	            throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): attributo " + CostantiXML.NODO_XML_INFORILEVABILE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFORILEVABILE_NUMERICA);
	        if (childs.getLength() > 0) {
	            numerica = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
	        } else
	            throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): elemento " + CostantiXML.NODO_XML_INFORILEVABILE_NUMERICA + " assente.");
	
	        //ritorno istanza corretta
	        return new InfoRilevabile(nome, numerica);
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_INFORILEVABILE);
	}

	
}