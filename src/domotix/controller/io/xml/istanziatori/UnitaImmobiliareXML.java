package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Stanza;

public class UnitaImmobiliareXML implements IstanziatoreXML<UnitaImmobiliare> {

	private Recuperatore recuperatore = null;
	private LetturaDatiSalvati lettore = null;

	public UnitaImmobiliareXML(Recuperatore recuperatore, LetturaDatiSalvati lettore) {
		this.recuperatore = recuperatore;
		this.lettore = lettore;
	}

    /** Metodo per scrittore: UNITA_IMMOB **/
    @Override
    public UnitaImmobiliare getInstance(Element el) throws Exception {
        return instanceElement(el, this.recuperatore, this.lettore);
	}

	/**
	 * Metodo per lettore: UNITA_IMMOB
	 * 
	 * @throws Exception
	 **/
	public static UnitaImmobiliare instanceElement(Element el, Recuperatore recuperatore, LetturaDatiSalvati lettore) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_UNITA_IMMOB)) {
	        String nome;
	        UnitaImmobiliare unit;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_UNITA_IMMOB_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_UNITA_IMMOB_NOME);
	        } else
	            throw new NoSuchElementException("UnitaImmobiliareXML.instanceElement(): attributo " + CostantiXML.NODO_XML_UNITA_IMMOB_NOME + " assente.");
	
	        unit = new UnitaImmobiliare(nome);
	
	        //Lettura e aggiunta di tutte le stanze
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_UNITA_IMMOB_STANZA);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String stanza = childs.item(i).getTextContent();
	                Stanza s = lettore.leggiStanza(stanza, nome);
	                if (stanza.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT))
	                    unit.setStanzaDefault(s);
	                else
	                    unit.addStanza(s);
	            }
	        }
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_REGOLA);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String regola = childs.item(i).getTextContent();
	                Regola r = lettore.leggiRegola(regola, nome);
	                unit.addRegola(r);
	            }
	        }
	
	        //ritorno istanza corretta
	        return unit;
	    }
	    else
	        throw new NoSuchElementException("UnitaImmobiliareXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_UNITA_IMMOB);
	}

    
}