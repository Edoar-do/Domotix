package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Modalita;

public class ModalitaXML implements IstanziatoreXML<Modalita> {
    
    /** Metodo per scrittore: MODALITA **/
    @Override
    public Modalita getInstance(Element el) throws Exception {
        return instanceElement(el);
	}

	/** Metodo per lettore: MODALITA **/
	public static Modalita instanceElement(Element el) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_MODALITA)) {
	        String nome;
	        Modalita m;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_MODALITA_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_MODALITA_NOME);
	        } else
	            throw new NoSuchElementException("ModalitaXML.instanceElement(): attributo " + CostantiXML.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");
	
	        m = new Modalita(nome);
	
	        //Lettura e aggiunta dei parametri
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_MODALITA_PARAMETRO);
	        if (childs.getLength() > 0) {
	            for (int i = 0; i < childs.getLength(); i++) {
	                Element e = (Element) childs.item(i);
	                m.addParametro(ParametroXML.instanceElement(e));
	            }
	        }
	
	        //ritorno istanza corretta
	        return m;
	    }
	    else
	        throw new NoSuchElementException("ModalitaXML.instanceElement():  elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_MODALITA);
	}

	
    
}