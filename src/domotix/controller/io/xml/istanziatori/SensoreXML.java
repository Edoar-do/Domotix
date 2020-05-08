package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.Sensore;

public class SensoreXML implements IstanziatoreXML<Sensore> {

	private Recuperatore recuperatore = null;

	public SensoreXML(Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per lettore: SENSORE **/
    @Override
    public Sensore getInstance(Element el) throws Exception {
        return instanceElement(el, this.recuperatore);
	}

	/** Metodo per lettore: SENSORE **/
	public static Sensore instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_SENSORE)) {
	        String nome;
	        boolean stato;
	        String categoria;
	        CategoriaSensore cat;
	        Sensore sensore;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_SENSORE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_SENSORE_NOME);
	        } else
	            throw new NoSuchElementException("SensoreXML.instanceElement(): attributo " + CostantiXML.NODO_XML_SENSORE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_SENSORE_STATO);
	        if (childs.getLength() > 0) {
	            stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
	        } else
	            throw new NoSuchElementException("SensoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_SENSORE_STATO + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_SENSORE_CATEGORIA);
	        if (childs.getLength() > 0) {
	            categoria = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("SensoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_SENSORE_CATEGORIA + " assente.");
	
	        cat = recuperatore.getCategoriaSensore(categoria);
	        if (cat == null) {
	            throw new NoSuchElementException("SensoreXML.instanceElement(): lettura sensore di categoria " + categoria + " inesistente o ancora non letta.");
	        }
	
	        sensore = new Sensore(nome, cat);
	        sensore.setStato(stato);
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_SENSORE_VALORE);
	        if (childs.getLength() > 0) {
	            if (childs.getLength() == 1) {
	                Element valore = (Element) childs.item(0);
	
	                if (!valore.hasAttribute(CostantiXML.NODO_XML_SENSORE_NOME_VALORE)) {
	                    //v1 -> sensore con solo una info int
	                    int val = Integer.parseInt(valore.getTextContent());
	                    sensore.setValore(val);
	                }
	                else {
	                    //sensore con piu' info, gestisco la prima
	                    String nomeInfo = valore.getAttribute(CostantiXML.NODO_XML_SENSORE_NOME_VALORE);
	                    boolean isNumerica = cat.getInformazioneRilevabile(nomeInfo).isNumerica();
	
	                    if (isNumerica) {
	                        sensore.setValore(nomeInfo, Double.parseDouble(valore.getTextContent()));
	                    }
	                    else {
	                        sensore.setValore(nomeInfo, valore.getTextContent());
	                    }
	                }
	            }
	
	            for (int i = 1; i < childs.getLength(); i++) {
	                Element valore = (Element) childs.item(i);
	                String nomeInfo = valore.getAttribute(CostantiXML.NODO_XML_SENSORE_NOME_VALORE);
	                boolean isNumerica = cat.getInformazioneRilevabile(nomeInfo).isNumerica();
	
	                if (isNumerica) {
	                    sensore.setValore(nomeInfo, Double.parseDouble(valore.getTextContent()));
	                }
	                else {
	                    sensore.setValore(nomeInfo, valore.getTextContent());
	                }
	            }
	        } else
	            throw new NoSuchElementException("SensoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_SENSORE_VALORE + " assente.");
	
	        //ritorno istanza corretta
	        return sensore;
	    }
	    else
	        throw new NoSuchElementException("SensoreXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_SENSORE);
	}
    
}