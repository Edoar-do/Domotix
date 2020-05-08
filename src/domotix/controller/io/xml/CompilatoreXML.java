package domotix.controller.io.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface CompilatoreXML<T> {
    /**
     * Ritorna l'elemento base contenente tutti i valori dell'istanza passata codificati in una struttura XML.
     * Il documento XML passato come parametro deve essere utilizzato solo come generatore di elementi. Sara' compito
     * dell'utilizzatore di questo metodo appendere l'elemento generato al documento come necessita.
     *
     * @param obj   istanza di cui eseguire la codifica
     * @param doc   istanza del documento con cui generare gli elementi
     * @return  Elemento base contenente tutti i valori dell'istanza passata. In caso di errori sul tipo di istanza viene lanciata eccezione.
     */
    Element compileInstance(T obj, Document doc);
}