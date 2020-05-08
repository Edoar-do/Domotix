package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.InfoRilevabile;

public class CategoriaSensoreXML implements IstanziatoreXML<CategoriaSensore> {

	private LetturaDatiSalvati lettore = null;

	public CategoriaSensoreXML(LetturaDatiSalvati lettore) {
		this.lettore = lettore;
	}

    /** Metodo per scrittore: CATEGORIA_SENSORE **/
    @Override
    public CategoriaSensore getInstance(Element el) throws Exception {
        return instanceElement(el, this.lettore);
	}

	/**
	 * Metodo per lettore: CATEGORIA_SENSORE
	 * 
	 * @throws Exception
	 **/
	public static CategoriaSensore instanceElement(Element el, LetturaDatiSalvati lettore) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CATEGORIA_SENSORE)) {
	        String nome;
	        String testoLibero;
	        CategoriaSensore c;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME);
	        } else
	            throw new NoSuchElementException("CategoriaSensoreXML.instanceElement(): attributo " + CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO);
	        if (childs.getLength() > 0) {
	            testoLibero = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("CategoriaSensoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
	        if (childs.getLength() > 0) {
	            //Per retro-compatibilita' con la versione 1, dove ogni categoria ha una sola informazione rilevabile e questa e' salvata direttamente
	            //nella descrizione della categoria senza un file apposito, si verifica qui il numero di informazioni rilevabili e se corrisponde a 1
	            //allora la si prova a leggere. In caso di fallimento di lettura allora si procede ad utilizzare il vecchio istanziamento.
	            //Altrimenti si procede ad utilizzare quello nuovo
	            if (childs.getLength() == 1) {
	                InfoRilevabile i = null;
	                String info = childs.item(0).getTextContent();
	                try {
	                    i = lettore.leggiInfoRilevabile(info, nome);
	                }
	                catch (Exception e) {
	                    i = null;
	                }
	                //se l'informazione letta fosse null allora si procede alla vecchia istanzazione
	                if (i == null)
	                    c = new CategoriaSensore(nome, testoLibero, info);
	                else //altrimenti e' della nuova versione ma con una sola informazione rilevabile
	                    c = new CategoriaSensore(nome, testoLibero, i);
	            }
	            else {
	                //Altrimenti procedo ad istanziare la categoria ed ad aggiungere le varie informazioni rilevabili
	                String info = childs.item(0).getTextContent();
	
	                c = new CategoriaSensore(nome, testoLibero, lettore.leggiInfoRilevabile(info, nome));
	
	                for (int i = 1; i < childs.getLength(); i++) {
	                    info = childs.item(i).getTextContent();
	                    c.addInformazioneRilevabile(lettore.leggiInfoRilevabile(info, c.getNome()));
	                }
	            }
	        } else
	            throw new NoSuchElementException("CategoriaSensoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE + " assente.");
	
	        //ritorno istanza corretta
	        return c;
	    }
	    else
	        throw new NoSuchElementException("CategoriaSensoreXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CATEGORIA_SENSORE);
	}

	
    
}