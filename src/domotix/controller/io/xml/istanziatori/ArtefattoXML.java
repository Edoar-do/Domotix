package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.datilocali.LetturaDatiLocali;
import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.ElencoAttuatori;
import domotix.model.ElencoSensori;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;

public class ArtefattoXML implements IstanziatoreXML<Artefatto> {

    /** Metodo per scrittore: ARTEFATTO **/
    @Override
    public Artefatto getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: ARTEFATTO **/
	public static Object leggiArtefatto(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_ARTEFATTO)) {
	        String nome;
	        String unitImmob;
	        Artefatto artefatto;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_ARTEFATTO_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_ARTEFATTO_NOME);
	        } else
	            throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): attributo " + CostantiXML.NODO_XML_ARTEFATTO_NOME + " assente.");
	
	        if (el.hasAttribute(CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB)) {
	            unitImmob = el.getAttribute(CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB);
	        } else
	            throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): attributo " + CostantiXML.NODO_XML_ARTEFATTO_UNITA_IMMOB + " assente.");
	
	        artefatto = new Artefatto(nome);
	        //Aggiungo gli elenchi globali come osservatori dei dispositivi
	        artefatto.addOsservatoreListaSensori(ElencoSensori.getInstance());
	        artefatto.addOsservatoreListaAttuatori(ElencoAttuatori.getInstance());
	        artefatto.setUnitaOwner(unitImmob);
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_ARTEFATTO_SENSORE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String sensore = childs.item(i).getTextContent();
	                Sensore s = null;
	
	                //verifico che non sia gia' stato letto (collegato a piu' stanze)
	                if (ElencoSensori.getInstance().contains(sensore)) {
	                    s = ElencoSensori.getInstance().getDispositivo(sensore);
	                }
	                //se sensore ancora sconosciuto lo leggo
	                else {
	                    s = LetturaDatiLocali.getInstance().leggiSensore(sensore);
	                }
	
	                artefatto.addSensore(s);
	            }
	        }
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ARTEFATTO_ATTUATORE);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String attuatore = childs.item(i).getTextContent();
	                Attuatore a = null;
	
	                //verifico che non sia gia' stato letto (collegato a piu' stanze)
	                if (ElencoSensori.getInstance().contains(attuatore)) {
	                    a = ElencoAttuatori.getInstance().getDispositivo(attuatore);
	                }
	                //se sensore ancora sconosciuto lo leggo
	                else {
	                    a = LetturaDatiLocali.getInstance().leggiAttuatore(attuatore);
	                }
	
	                artefatto.addAttuatore(a);
	            }
	        }
	
	        //ritorno istanza corretta
	        return artefatto;
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_ARTEFATTO);
	}

	
    
}