package domotix.controller.io.xml.istanziatori;

import java.time.LocalTime;
import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.ElencoSensori;
import domotix.model.bean.device.InfoRilevabile;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.InfoCostante;
import domotix.model.bean.regole.InfoSensoriale;
import domotix.model.bean.regole.InfoTemporale;
import domotix.model.bean.regole.InfoVariabile;

public class InfoSensorialeXML implements IstanziatoreXML<InfoSensoriale> {

    /** Metodo per scrittore: INFO_SENSORIALE **/
    @Override
    public InfoSensoriale getInstance(Element el) throws Exception {
        return null;
    }

	/** Metodo per lettore: INFO_SENSORIALE **/
	public static Object leggiInfoSensoriale(Element el) throws Exception {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_INFO_SENSORIALE)) {
	        InfoSensoriale info = null;
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFO_SENSORIALE_SENSORE);
	        if (childs.getLength() > 0) {
	            //Traduco un'informazione sensoriale di tipo varibile
	            String nomeSensore = childs.item(0).getTextContent();
	            Sensore sens = ElencoSensori.getInstance().getDispositivo(nomeSensore);
	
	            if (sens == null)
	                throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): sensore " + nomeSensore + " non trovato.");
	
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFO_SENSORIALE_INFO_RILEV);
	            if (childs.getLength() > 0) {
	                String nomeInfoRilev = childs.item(0).getTextContent();
	                InfoRilevabile infoRilev = sens.getCategoria().getInformazioneRilevabile(nomeInfoRilev);
	
	                if (infoRilev == null)
	                    throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): informazione " + nomeInfoRilev + " per categoria sensore " + sens.getCategoria().getNome() + " non trovato.");
	
	                info = new InfoVariabile(sens, nomeInfoRilev);
	
	            } else
	                throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + CostantiXML.NODO_XML_INFO_SENSORIALE_INFO_RILEV + " assente.");
	
	        }
	        if (info == null) {
	            //Traduco un'informazione sensoriale di tipo costante
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFO_SENSORIALE_COSTANTE);
	            if (childs.getLength() > 0) {
	                //Traduco un'informazione sensoriale di tipo varibile
	                String valoreCostanteStr = childs.item(0).getTextContent();
	                Object valore = null;
	
	                //provo a leggerlo come intero
	                try {
	                    int numInt = Integer.parseInt(valoreCostanteStr);
	                    valore = numInt;
	                } catch (NumberFormatException e) {
	                    //nothing
	                }
	                if (valore == null) {
	                    //valore non intero --> provo double
	                    try {
	                        double numDouble = Double.parseDouble(valoreCostanteStr);
	                        valore = numDouble;
	                    } catch (NumberFormatException e) {
	                        //nothing
	                    }
	                }
	                if (valore == null) {
	                    //valore non dei tipi precedenti --> allora stringa
	                    valore = valoreCostanteStr;
	                }
	
	                info = new InfoCostante(valore);
	            }
	        }
	        if (info == null) {
	            //Traduco un'informazione sensoriale di tipo tempo
	            childs = el.getElementsByTagName(CostantiXML.NODO_XML_INFO_SENSORIALE_TEMPORALE);
	            if (childs.getLength() > 0) {
	                //Traduco un'informazione sensoriale di tipo varibile
	                String valoreStr = childs.item(0).getTextContent();
	                LocalTime valore = null;
	
	                //traduco l'ora con il formato di sistema
	                valore = LocalTime.parse(valoreStr, SensoreOrologio.TIME_FORMATTER);
	
	                info = new InfoTemporale(valore);
	            }
	        }
	        if (info == null) //malformazione
	            throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + CostantiXML.NODO_XML_INFO_SENSORIALE + " con formato non gestito.");
	
	        //ritorno istanza corretta
	        return info;
	    }
	    else
	        throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_INFO_SENSORIALE);
	}

	
    
}