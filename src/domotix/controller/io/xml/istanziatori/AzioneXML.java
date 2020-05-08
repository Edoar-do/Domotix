package domotix.controller.io.xml.istanziatori;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.Azione;

public class AzioneXML implements IstanziatoreXML<Azione> {

	Recuperatore recuperatore = null;

	public AzioneXML(Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per scrittore: AZIONE **/
    @Override
    public Azione getInstance(Element el) throws Exception {
        return instanceElement(el, recuperatore);
	}

	/** Metodo per lettore: AZIONE **/
	public static Azione instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_AZIONE)) {
	        Attuatore att;
	        Modalita modalita;
	        ArrayList<Parametro> parametri = new ArrayList<>();
	        LocalTime start = null;
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_ATTUATORE);
	        if (childs.getLength() > 0) {
	            String nomeAttuatore = childs.item(0).getTextContent();
	
	            att = recuperatore.getAttuatore(nomeAttuatore);
	            if (att == null)
	                throw  new NoSuchElementException("AzioneXML.instanceElement(): attuatore " + nomeAttuatore + " non trovato.");
	
	        } else
	            throw new NoSuchElementException("AzioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_AZIONE_ATTUATORE + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_MODALITA);
	        if (childs.getLength() > 0) {
	            String nomeModalita = childs.item(0).getTextContent();
	
	            modalita = att.getCategoria().getModalita(nomeModalita);
	            if (modalita == null)
	                throw  new NoSuchElementException("AzioneXML.instanceElement(): modalita' " + nomeModalita + " per categoria attuatore " + att.getCategoria().getNome() + " non trovato.");
	
	        } else
	            throw new NoSuchElementException("AzioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_AZIONE_MODALITA + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_AZIONE_PARAMETRO);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element elParametro = (Element)childs.item(i);
	                parametri.add(ParametroXML.instanceElement(elParametro));
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
	        throw new NoSuchElementException("AzioneXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_AZIONE);
	}
    
}