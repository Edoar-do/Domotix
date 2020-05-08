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
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.Modalita;

public class CategoriaAttuatoreXML implements IstanziatoreXML<CategoriaAttuatore> {

    /** Metodo per scrittore: CATEGORIA_ATTUATORE **/
    @Override
    public CategoriaAttuatore getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: CATEGORIA_ATTUATORE **/
	public static Object leggiCategoriaAttuatore(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE)) {
	        String nome;
	        String testoLibero;
	        CategoriaAttuatore c;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME);
	        } else
	            throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): attributo " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
	        if (childs.getLength() > 0) {
	            testoLibero = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO + " assente.");
	
	        c = new CategoriaAttuatore(nome, testoLibero);
	
	        //Lettura e aggiunta delle modalita
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                String modalita = childs.item(i).getTextContent();
	                c.addModalita(LetturaDatiLocali.getInstance().leggiModalita(modalita, c.getNome()));
	            }
	        }
	
	        //ritorno istanza corretta
	        return c;
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_CATEGORIA_ATTUATORE);
	}

	
    
}