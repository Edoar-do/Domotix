package domotix.controller.io.xml;

import org.w3c.dom.Element;

public interface IstanziatoreXML<T> {
    /**
     * Ritorna l'istanza ottenuta dai dati contenuti nell'elemento, se quest'ultimo rispetta la struttura designata.
     * Assicurarsi di leggere il documento XML corretto per l'istanziatore utilizzato.
     *
     * @param el   elemento XML contenente i dati necessari all'istanziazione
     * @return  Istanza dell'elemento
     * @throws IOException  Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws ParserConfigurationException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws TransformerConfigurationException    Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws SAXException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     */
    T getInstance(Element el) throws Exception;
}