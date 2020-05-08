package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.regole.Conseguente;

public class ConseguenteXML implements IstanziatoreXML<Conseguente> {

	private Recuperatore recuperatore = null;

	public ConseguenteXML(Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per scrittore: CONSEGUENTE **/
    @Override
    public Conseguente getInstance(Element el) throws Exception {
        return instanceElement(el, recuperatore);
	}

	/** Metodo per lettore: CONSEGUENTE **/
	public static Conseguente instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CONSEGUENTE)) {
	        Conseguente cons = new Conseguente();
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element elAzione = (Element)childs.item(i);
	                cons.addAzione(AzioneXML.instanceElement(elAzione, recuperatore));
	            }
	        } else
	            throw new NoSuchElementException("ConseguenteXML.instanceElement(): elemento " + CostantiXML.NODO_XML_AZIONE_ATTUATORE + " assente.");
	
	        //ritorno istanza corretta
	        return cons;
	    }
	    else
	        throw new NoSuchElementException("ConseguenteXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CONSEGUENTE);
	}

	
    
}