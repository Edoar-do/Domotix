package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.datilocali.LettoriXML;
import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

public class ModalitaXML implements IstanziatoreXML<Modalita> {
    
    /** Metodo per scrittore: MODALITA **/
    @Override
    public Modalita getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: MODALITA **/
	public static Object leggiModalita(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_MODALITA)) {
	        String nome;
	        Modalita m;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_MODALITA_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_MODALITA_NOME);
	        } else
	            throw new NoSuchElementException("LettoriXML.MODALITA.getInstance(): attributo " + CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");
	
	        m = new Modalita(nome);
	
	        //Lettura e aggiunta dei parametri
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_MODALITA_PARAMETRO);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element e = (Element) childs.item(i);
	                m.addParametro((Parametro) LettoriXML.PARAMETRO_MODALITA.getInstance(e));
	            }
	        }
	
	        //ritorno istanza corretta
	        return m;
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.MODALITA.getInstance():  elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_MODALITA);
	}

	
    
}