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
import domotix.model.bean.regole.Condizione;
import domotix.model.bean.regole.InfoSensoriale;

public class CondizioneXML implements IstanziatoreXML<Condizione> {

    /** Metodo per scrittore: CONDIZIONE **/
    @Override
    public Condizione getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: CONDIZIONE **/
	public static Object leggiCondizione(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CONDIZIONE)) {
	        Condizione cond;
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
	                        sinistra = (InfoSensoriale)LettoriXML.INFO_SENSORIALE.istanziatore.getInstance(elInfoSens);
	                    else
	                        destra = (InfoSensoriale)LettoriXML.INFO_SENSORIALE.istanziatore.getInstance(elInfoSens);
	                }
	                else
	                    throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_POSIZIONE + " assente.");
	            }
	
	            if (sinistra == null)
	                throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_SINISTRA + " assente.");
	            if (destra == null)
	                throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_DESTRA + " assente.");
	
	        } else
	            throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_INFO_SENSORIALE + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CONDIZIONE_OPERATORE);
	        if (childs.getLength() > 0) {
	            op = childs.item(0).getTextContent();
	
	            if (!op.equals(Condizione.MAGGIORE_UGUALE) &&
	                !op.equals(Condizione.MAGGIORE) &&
	                !op.equals(Condizione.UGUALE) &&
	                !op.equals(Condizione.MINORE) &&
	                !op.equals(Condizione.MINORE_UGUALE))
	                throw new IllegalArgumentException("LettoriXML.CONDIZIONE.getInstance(): operatore " + op + " non gestito.");
	        } else
	            throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + CostantiXML.NODO_XML_CONDIZIONE_OPERATORE + " assente.");
	
	        //ritorno istanza corretta
	        return new Condizione(sinistra, op, destra);
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CONDIZIONE);
	}



	
    
}