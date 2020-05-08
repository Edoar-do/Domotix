package domotix.controller.io.xml.istanziatori;


import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.util.SommarioDispositivi;

public class StanzaXML implements IstanziatoreXML<Stanza> {
	
	private Recuperatore recuperatore = null;
	private LetturaDatiSalvati lettore = null;

	public StanzaXML(Recuperatore recuperatore, LetturaDatiSalvati lettore) {
		this.recuperatore = recuperatore;
		this.lettore = lettore;
	}

    /** Metodo per scrittore: Stanza **/
    @Override
    public Stanza getInstance(Element el) throws Exception {
        return instanceElement(el, this.recuperatore, this.lettore);
	}

	/** Metodo per lettore: STANZA **/
	public static Stanza instanceElement(Element el, Recuperatore recuperatore, LetturaDatiSalvati lettore) throws Exception {
	//controllo tag elemento
	        if (el.getTagName().equals(CostantiXML.NODO_XML_STANZA)) {
				SommarioDispositivi sommarioSensori = recuperatore.getSommarioSensori();
				SommarioDispositivi sommarioAttuatori = recuperatore.getSommarioAttuatori();
	            String nome;
	            String unitImmob;
	            Stanza stanza;
	
	            //estrazione attrubuti
	            if (el.hasAttribute(CostantiXML.NODO_XML_STANZA_NOME)) {
	                nome = el.getAttribute(CostantiXML.NODO_XML_STANZA_NOME);
	            } else
	                throw new NoSuchElementException("StanzaXML.instanceElement(): attributo " + CostantiXML.NODO_XML_STANZA_NOME + " assente.");
	
	            if (el.hasAttribute(CostantiXML.NODO_XML_STANZA_UNITA_IMMOB)) {
	                unitImmob = el.getAttribute(CostantiXML.NODO_XML_STANZA_UNITA_IMMOB);
	            } else
	                throw new NoSuchElementException("StanzaXML.instanceElement(): attributo " + CostantiXML.NODO_XML_STANZA_UNITA_IMMOB + " assente.");
	
	            stanza = new Stanza(nome);
	            //Aggiungo gli elenchi globali come osservatori dei dispositivi
	            stanza.addOsservatoreListaSensori(sommarioSensori);
	            stanza.addOsservatoreListaAttuatori(sommarioAttuatori);
	            stanza.setUnitaOwner(unitImmob);
	
	
	            //estrazione elementi
	            NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_STANZA_SENSORE);
	            if (childs.getLength() > 0) {
	                for (int i = 0; i < childs.getLength(); i++) {
	                    String sensore = childs.item(i).getTextContent();
	
						//verifico che non sia gia' stato letto (collegato a piu' stanze)
						Sensore s = recuperatore.getSensore(sensore);
						if (s == null) {
							//se sensore ancora sconosciuto lo leggo
							s = lettore.leggiSensore(sensore);
						}
	
	                    stanza.addSensore(s);
	                }
	            }
	
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_STANZA_ATTUATORE);
	            if (childs.getLength() > 0) {
	                for (int i = 0; i < childs.getLength(); i++) {
	                    String attuatore = childs.item(i).getTextContent();
	                    
						//verifico che non sia gia' stato letto (collegato a piu' stanze)
						Attuatore a = recuperatore.getAttuatore(attuatore);
						if (a == null) {
						//se attuatore ancora sconosciuto lo leggo
							a = lettore.leggiAttuatore(attuatore);
						}
	
	                    stanza.addAttuatore(a);
	                }
	            }
	
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_STANZA_ARTEFATTO);
	            if (childs.getLength() > 0) {
	                for (int i = 0; i < childs.getLength(); i++) {
	                    String artefatto = childs.item(i).getTextContent();
	                    Artefatto a = lettore.leggiArtefatto(artefatto, unitImmob);
	                    stanza.addArtefatto(a);
	                }
	            }
	
	            //ritorno istanza corretta
	            return stanza;
	        }
	        else
	            throw new NoSuchElementException("StanzaXML.instanceElement():  elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_STANZA);
	    }
    
}