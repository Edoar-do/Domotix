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

public class ArtefattoXML implements IstanziatoreXML<Artefatto> {

	private Recuperatore recuperatore = null;
	private LetturaDatiSalvati lettore = null;
	
	public ArtefattoXML(Recuperatore recuperatore, LetturaDatiSalvati lettore) {
		this.recuperatore = recuperatore;
		this.lettore = lettore;
	}

    /** Metodo per scrittore: ARTEFATTO **/
    @Override
    public Artefatto getInstance(Element el) throws Exception {
        return instanceElement(el, recuperatore, lettore);
	}

	/** Metodo per lettore: ARTEFATTO **/
	public static Artefatto instanceElement(Element el, Recuperatore recuperatore, LetturaDatiSalvati lettore) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_ARTEFATTO)) {
	        String nome;
	        String unitImmob;
	        Artefatto artefatto;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_ARTEFATTO_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_ARTEFATTO_NOME);
	        } else
	            throw new NoSuchElementException("ArtefattoXML.instanceElement(): attributo " + CostantiXML.NODO_XML_ARTEFATTO_NOME + " assente.");
	
	        if (el.hasAttribute(CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB)) {
	            unitImmob = el.getAttribute(CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB);
	        } else
	            throw new NoSuchElementException("ArtefattoXML.instanceElement(): attributo " + CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB + " assente.");
	
	        artefatto = new Artefatto(nome);
	        artefatto.setUnitaOwner(unitImmob);
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_ARTEFATTO_SENSORE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String sensore = childs.item(i).getTextContent();
					
					//verifico che non sia gia' stato letto (collegato a piu' stanze)
					Sensore s = recuperatore.getSensore(sensore);
					if (s == null) {
	                	//se sensore ancora sconosciuto lo leggo
	                    s = lettore.leggiSensore(sensore);
	                }
	
	                artefatto.addSensore(s);
	            }
	        }
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ARTEFATTO_ATTUATORE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String attuatore = childs.item(i).getTextContent();
					
	                //verifico che non sia gia' stato letto (collegato a piu' stanze)
					Attuatore a = recuperatore.getAttuatore(attuatore);
					if (a == null) {
	                //se attuatore ancora sconosciuto lo leggo
	                    a = lettore.leggiAttuatore(attuatore);
	                }
	
	                artefatto.addAttuatore(a);
	            }
	        }
	
	        //ritorno istanza corretta
	        return artefatto;
	    }
	    else
	        throw new NoSuchElementException("ArtefattoXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_ARTEFATTO);
	}


	
    
}