package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.datilocali.LettoriXML;
import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Conseguente;

public class ConseguenteXML implements IstanziatoreXML<Conseguente> {

    /** Metodo per scrittore: CONSEGUENTE **/
    @Override
    public Conseguente getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: CONSEGUENTE **/
	public static Object leggiConseguente(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CONSEGUENTE)) {
	        Conseguente cons = new Conseguente();
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element elAzione = (Element)childs.item(i);
	                cons.addAzione((Azione)LettoriXML.AZIONE.istanziatore.getInstance(elAzione));
	            }
	        } else
	            throw new NoSuchElementException("LettoriXML.CONSEGUENTE.getInstance(): elemento " + CostantiXML.NODO_XML_AZIONE_ATTUATORE + " assente.");
	
	        //ritorno istanza corretta
	        return cons;
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.CONSEGUENTE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CONSEGUENTE);
	}

	
    
}