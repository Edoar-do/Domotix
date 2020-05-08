package domotix.controller.io.xml.compilatori;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domotix.controller.io.xml.CompilatoreXML;
import domotix.controller.io.xml.CostantiXML;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.InfoCostante;
import domotix.model.bean.regole.InfoSensoriale;
import domotix.model.bean.regole.InfoTemporale;
import domotix.model.bean.regole.InfoVariabile;

public class InfoSensorialeXML implements CompilatoreXML<InfoSensoriale> {

    /** Metodo per scrittore: INFO_SENSORIALE **/
    @Override
    public Element compileInstance(InfoSensoriale obj, Document doc) {
		return compileElement(obj, doc);
	}

	public static Element compileElement(InfoSensoriale obj, Document doc) {
        if (obj instanceof InfoVariabile) {
	        InfoVariabile infoVariabile = (InfoVariabile) obj;
	
	        //crea elemento base
	        Element root = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE);
	
	        //popola elemento base
	        Element elem = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE_SENSORE);
	        elem.appendChild(doc.createTextNode(infoVariabile.getSensore().getNome()));
	        root.appendChild(elem);
	
	        elem = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE_INFO_RILEV);
	        elem.appendChild(doc.createTextNode(infoVariabile.getNomeInfo()));
	        root.appendChild(elem);
	
	        return root;
	    } else if (obj instanceof InfoCostante) {
	        InfoCostante infoCostante = (InfoCostante) obj;
	
	        //crea elemento base
	        Element root = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE);
	
	        //popola elemento base
	        Element elem = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE_COSTANTE);
	        elem.appendChild(doc.createTextNode(infoCostante.getInfo().toString()));
	        root.appendChild(elem);
	
	        return root;
	    } else if (obj instanceof InfoTemporale) {
	        InfoTemporale infoTemporale = (InfoTemporale) obj;
	
	        //crea elemento base
	        Element root = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE);
	
	        //popola elemento base
	        Element elem = doc.createElement(CostantiXML.NODO_XML_INFO_SENSORIALE_TEMPORALE);
	        elem.appendChild(doc.createTextNode(infoTemporale.getTempo().format(SensoreOrologio.TIME_FORMATTER)));
	        root.appendChild(elem);
	
	        return root;
	    } else
	        throw new IllegalArgumentException("ScrittoriXML.INFO_SENSORIALE.compileInstance(): impossibile compilare oggetto non InfoSensoriale: " + obj.getClass().getName());
    }

	
    
}