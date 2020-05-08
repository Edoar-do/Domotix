package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.regole.Condizione;
import domotix.model.bean.regole.InfoSensoriale;

public class CondizioneXML implements IstanziatoreXML<Condizione> {

	Recuperatore recuperatore = null;

	public CondizioneXML (Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per scrittore: CONDIZIONE **/
    @Override
    public Condizione getInstance(Element el) throws Exception {
        return instanceElement(el, recuperatore);
	}

	/** Metodo per lettore: CONDIZIONE **/
	public static Condizione instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CONDIZIONE)) {
	        InfoSensoriale sinistra = null, destra = null;
	        String op;
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFO_SENSORIALE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element elInfoSens = (Element)childs.item(i);
	                if (elInfoSens.hasAttribute(CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE)) {
	                    String pos = elInfoSens.getAttribute(CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE);
	                    if (pos.equals(CostantiXML.NODO_XML_CONDIZIONE_SINISTRA))
	                        sinistra = InfoSensorialeXML.instanceElement(elInfoSens, recuperatore);
	                    else
	                        destra = InfoSensorialeXML.instanceElement(elInfoSens, recuperatore);
	                }
	                else
	                    throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE + " assente.");
	            }
	
	            if (sinistra == null)
	                throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_SINISTRA + " assente.");
	            if (destra == null)
	                throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_DESTRA + " assente.");
	
	        } else
	            throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_INFO_SENSORIALE + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CONDIZIONE_OPERATORE);
	        if (childs.getLength() > 0) {
	            op = childs.item(0).getTextContent();
	
	            if (!op.equals(Condizione.MAGGIORE_UGUALE) &&
	                !op.equals(Condizione.MAGGIORE) &&
	                !op.equals(Condizione.UGUALE) &&
	                !op.equals(Condizione.MINORE) &&
	                !op.equals(Condizione.MINORE_UGUALE))
	                throw new IllegalArgumentException("CondizioneXML.instanceElement(): operatore " + op + " non gestito.");
	        } else
	            throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_OPERATORE + " assente.");
	
	        //ritorno istanza corretta
	        return new Condizione(sinistra, op, destra);
	    }
	    else
	        throw new NoSuchElementException("CondizioneXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CONDIZIONE);
	}



	
    
}