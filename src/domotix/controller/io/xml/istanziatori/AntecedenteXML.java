package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.regole.Antecedente;
import domotix.model.bean.regole.Condizione;

public class AntecedenteXML implements IstanziatoreXML<Antecedente> {

	private Recuperatore recuperatore = null;

	public AntecedenteXML(Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per scrittore: ANTECEDENTE **/
    @Override
    public Antecedente getInstance(Element el) throws Exception {
		return instanceElement(el, this.recuperatore);
	}

	public static Antecedente instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_ANTECEDENTE)) {
	        Antecedente antecedente, next = null;
	        Condizione cond;
	        String op = null;
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_CONDIZIONE);
	        if (childs.getLength() > 0) {
	            Element elCondizione = (Element)childs.item(0);
	            cond = CondizioneXML.instanceElement(elCondizione, recuperatore);
	        } else
	            throw new NoSuchElementException("AntecedenteXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CONDIZIONE + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ANTECEDENTE_OPLOGICO);
	        if (childs.getLength() > 0) {
	            op = childs.item(0).getTextContent();
	
	            if (!op.equals(Antecedente.OPERATORE_AND) &&
	                    !op.equals(Antecedente.OPERATORE_OR))
	                throw new IllegalArgumentException("AntecedenteXML.instanceElement(): operatore " + op + " non gestito.");
	
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_ANTECEDENTE);
	            if (childs.getLength() > 0) {
	                Element elAntecedente = (Element)childs.item(0);
	                next = AntecedenteXML.instanceElement(elAntecedente, recuperatore);
	            } else
	                throw new NoSuchElementException("AntecedenteXML.instanceElement(): elemento " + CostantiXML.NODO_XML_ANTECEDENTE + " assente.");
	        }
	
	        antecedente = new Antecedente(cond);
	
	        if (op != null && next != null) {
	            antecedente.addAntecedente(op, next);
	        }
	
	        //ritorno istanza corretta
	        return antecedente;
	    }
	    else
	        throw new NoSuchElementException("AntecedenteXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_ANTECEDENTE);
	}

}