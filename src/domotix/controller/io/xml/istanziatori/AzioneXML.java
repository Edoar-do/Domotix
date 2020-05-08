package domotix.controller.io.xml.istanziatori;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.datilocali.LettoriXML;
import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.ElencoAttuatori;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.Azione;

public class AzioneXML implements IstanziatoreXML<Azione> {

    /** Metodo per scrittore: AZIONE **/
    @Override
    public Azione getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: AZIONE **/
	public static Object leggiAzione(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_AZIONE)) {
	        Azione azione;
	        Attuatore att;
	        Modalita modalita;
	        ArrayList<Parametro> parametri = new ArrayList<>();
	        LocalTime start = null;
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_ATTUATORE);
	        if (childs.getLength() > 0) {
	            String nomeAttuatore = childs.item(0).getTextContent();
	
	            att = ElencoAttuatori.getInstance().getDispositivo(nomeAttuatore);
	            if (att == null)
	                throw  new NoSuchElementException("LettoriXML.AZIONE.getInstance(): attuatore " + nomeAttuatore + " non trovato.");
	
	        } else
	            throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_AZIONE_ATTUATORE + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_MODALITA);
	        if (childs.getLength() > 0) {
	            String nomeModalita = childs.item(0).getTextContent();
	
	            modalita = att.getCategoria().getModalita(nomeModalita);
	            if (att == null)
	                throw  new NoSuchElementException("LettoriXML.AZIONE.getInstance(): modalita' " + nomeModalita + " per categoria attuatore " + att.getCategoria().getNome() + " non trovato.");
	
	        } else
	            throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_AZIONE_MODALITA + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_PARAMETRO);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element elParametro = (Element)childs.item(i);
	                parametri.add((Parametro)LettoriXML.PARAMETRO_MODALITA.istanziatore.getInstance(elParametro));
	            }
	        }
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_START);
	        if (childs.getLength() > 0) {
	            String startStr = childs.item(0).getTextContent();
	            start = LocalTime.parse(startStr, SensoreOrologio.TIME_FORMATTER);
	        }
	
	        //ritorno istanza corretta
	        return new Azione(att, modalita, parametri, start);
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_AZIONE);
	}

	
    
}