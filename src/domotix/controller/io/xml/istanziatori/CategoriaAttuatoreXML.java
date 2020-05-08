package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.CategoriaAttuatore;

public class CategoriaAttuatoreXML implements IstanziatoreXML<CategoriaAttuatore> {

	LetturaDatiSalvati lettore = null;

	public CategoriaAttuatoreXML(LetturaDatiSalvati lettore) {
		this.lettore = lettore;
	}

    /** Metodo per scrittore: CATEGORIA_ATTUATORE **/
    @Override
    public CategoriaAttuatore getInstance(Element el) throws Exception {
        return instanceElement(el, lettore);
	}

	/**
	 * Metodo per lettore: CATEGORIA_ATTUATORE
	 * 
	 * @throws Exception
	 **/
	public static CategoriaAttuatore instanceElement(Element el, LetturaDatiSalvati lettore) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE)) {
	        String nome;
	        String testoLibero;
	        CategoriaAttuatore c;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME);
	        } else
	            throw new NoSuchElementException("CategoriaAttuatoreXML.instanceElement(): attributo " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
	        if (childs.getLength() > 0) {
	            testoLibero = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("CategoriaAttuatoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO + " assente.");
	
	        c = new CategoriaAttuatore(nome, testoLibero);
	
	        //Lettura e aggiunta delle modalita
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String modalita = childs.item(i).getTextContent();
	                c.addModalita(lettore.leggiModalita(modalita, c.getNome()));
	            }
	        }
	
	        //ritorno istanza corretta
	        return c;
	    }
	    else
	        throw new NoSuchElementException("CategoriaAttuatoreXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE);
	}

}