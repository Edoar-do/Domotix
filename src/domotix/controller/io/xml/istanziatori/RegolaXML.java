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
import domotix.model.bean.regole.Antecedente;
import domotix.model.bean.regole.Conseguente;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.regole.StatoRegola;

public class RegolaXML implements IstanziatoreXML<Regola> {

    /** Metodo per lettore: REGOLA **/
    @Override
    public Regola getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: REGOLA **/
	public static Object leggiRegola(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_REGOLA)) {
	        String id;
	        //boolean stato;
	        StatoRegola stato = null;
	        Antecedente ant;
	        Conseguente cons;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_REGOLA_ID)) {
	            id = el.getAttribute(CostantiXML.NODO_XML_REGOLA_ID);
	        } else
	            throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): attributo " + CostantiXML.NODO_XML_REGOLA_ID + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_REGOLA_STATO);
	        if (childs.getLength() > 0) {
	            //stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
	            String nomeStato = childs.item(0).getTextContent();
	            if (nomeStato.equals("1"))
	                stato = StatoRegola.ATTIVA;
	            else if (nomeStato.equals("0"))
	                stato = StatoRegola.DISATTIVA;
	            else {
	                for (StatoRegola value : StatoRegola.values()) {
	                    if (value.name().equals(nomeStato)) {
	                        stato = value;
	                        break;
	                    }
	                }
	            }
	
	            if (stato == null)
	                throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): valore " + nomeStato + " per stato regola non riconosciuto.");
	        } else
	            throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + CostantiXML.NODO_XML_REGOLA_STATO + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ANTECEDENTE);
	        if (childs.getLength() > 0) {
	            Element elAntecedente = (Element) childs.item(0);
	            ant = (Antecedente) LettoriXML.ANTECEDENTE.istanziatore.getInstance(elAntecedente);
	        } else
	            //throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + Costanti.NODO_XML_REGOLA_STATO + " assente.");
	            ant = null; //nessun antecedente nel file XML --> imposto null per indicare un ANTECEDENTE = TRUE
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CONSEGUENTE);
	        if (childs.getLength() > 0) {
	            Element elConseguente = (Element) childs.item(0);
	            cons = (Conseguente) LettoriXML.CONSEGUENTE.istanziatore.getInstance(elConseguente);
	        } else
	            throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + CostantiXML.NODO_XML_REGOLA_STATO + " assente.");
	
	        //ritorno istanza corretta
	        return new Regola(id, stato, ant, cons);
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_REGOLA);
	}
    
    
}