package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Parametro;

public class ParametroXML implements IstanziatoreXML<Parametro> {
	
	/** Metodo per scrittore: PARAMETRO_MODALITA **/
	@Override
	public Parametro getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: PARAMETRO_MODALITA **/
	public static Object leggiParametroModalita(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_MODALITA_PARAMETRO)) {
	        String nome;
	        double valore;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_MODALITA_PARAMETRO_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_MODALITA_PARAMETRO_NOME);
	        } else
	            throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance(): attributo " + CostantiXML.NODO_XML_MODALITA_PARAMETRO_NOME + " assente.");
	
	        //Lettura e aggiunta dei parametri
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_MODALITA_PARAMETRO_VALORE);
	        if (childs.getLength() > 0) {
	            valore = Double.parseDouble(childs.item(0).getTextContent());
	        } else
	            throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance(): elemento " + CostantiXML.NODO_XML_MODALITA_PARAMETRO_VALORE + " assente.");
	
	        //ritorno istanza corretta
	        return new Parametro(nome, valore);
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance():  elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_MODALITA_PARAMETRO);
	}

	
    
}