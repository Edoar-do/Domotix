package domotix.model.io.datilocali;

import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.util.Costanti;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Classe enum per contenere tutti i compilatori in elementi XML delle entita' presenti nel programma.
 * Questi elementi vengono sfruttati per il salvataggio su file nella classe DatiLocali.
 * Si intende creare un punto comune per facilitare l'aggiunta, la modifica o la rimozione di un'entita'.
 *
 * @author paolopasqua
 * @see DatiLocali
 */
public enum ScrittoriXML {
    /**
     * Entita' Attuatore
     * @see Attuatore
     */
    ATTUATORE((obj, doc) -> {
        if (obj instanceof Attuatore) {
            Attuatore attuatore = (Attuatore) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_ATTUATORE);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_ATTUATORE_NOME);
            nome.setValue(attuatore.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_ATTUATORE_STATO);
            elem.appendChild(doc.createTextNode(attuatore.getStato() ? "1" : "0"));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_ATTUATORE_CATEGORIA);
            elem.appendChild(doc.createTextNode(attuatore.getCategoria().getNome()));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_ATTUATORE_MODALITA);
            elem.appendChild(doc.createTextNode(attuatore.getModoOp().getNome()));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.ATTUATORE.compileInstance(): impossibile compilare oggetto non Attuatore");
    }),
    /**
     * Entita' Sensore
     * @see Sensore
     */
    SENSORE((obj, doc) -> {
        if (obj instanceof Sensore) {
            Sensore sensore = (Sensore) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_SENSORE);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_SENSORE_NOME);
            nome.setValue(sensore.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_SENSORE_STATO);
            elem.appendChild(doc.createTextNode(sensore.getStato() ? "1" : "0"));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_SENSORE_CATEGORIA);
            elem.appendChild(doc.createTextNode(sensore.getCategoria().getNome()));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_SENSORE_VALORE);
            elem.appendChild(doc.createTextNode(sensore.getValore()+""));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.SENSORE.compileInstance(): impossibile compilare oggetto non Sensore");
    }),
    /**
     * Entita' Artefatto
     * @see Artefatto
     */
    ARTEFATTO((obj, doc) -> {
        if (obj instanceof Artefatto) {
            Artefatto artefatto = (Artefatto) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_ARTEFATTO);

            //assegna attributo identificativo
            Attr attr = doc.createAttribute(Costanti.NODO_XML_ARTEFATTO_NOME);
            attr.setValue(artefatto.getNome());
            root.setAttributeNode(attr);

            attr = doc.createAttribute(Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB);
            attr.setValue(artefatto.getUnitaOwner());
            root.setAttributeNode(attr);

            //popola elemento base
            Element elem = null;

            for (Sensore s : artefatto.getSensori()) {
                elem = doc.createElement(Costanti.NODO_XML_ARTEFATTO_SENSORE);
                elem.appendChild(doc.createTextNode(s.getNome()));
                root.appendChild(elem);
            }

            for (Attuatore a : artefatto.getAttuatori()) {
                elem = doc.createElement(Costanti.NODO_XML_ARTEFATTO_ATTUATORE);
                elem.appendChild(doc.createTextNode(a.getNome()));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.ARTEFATTO.compileInstance(): impossibile compilare oggetto non Artefatto");
    }),
    /**
     * Entita' Stanza
     * @see Stanza
     */
    STANZA((obj, doc) -> {
        if (obj instanceof Stanza) {
            Stanza stanza = (Stanza) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_STANZA);

            //assegna attributo identificativo
            Attr attr = doc.createAttribute(Costanti.NODO_XML_STANZA_NOME);
            attr.setValue(stanza.getNome());
            root.setAttributeNode(attr);

            attr = doc.createAttribute(Costanti.NODO_XML_STANZA_UNITA_IMMOB);
            attr.setValue(stanza.getUnitaOwner());
            root.setAttributeNode(attr);

            //popola elemento base
            Element elem = null;

            for (Sensore s : stanza.getSensori()) {
                elem = doc.createElement(Costanti.NODO_XML_STANZA_SENSORE);
                elem.appendChild(doc.createTextNode(s.getNome()));
                root.appendChild(elem);
            }

            for (Attuatore a : stanza.getAttuatori()) {
                elem = doc.createElement(Costanti.NODO_XML_STANZA_ATTUATORE);
                elem.appendChild(doc.createTextNode(a.getNome()));
                root.appendChild(elem);
            }

            for (Artefatto a : stanza.getArtefatti()) {
                elem = doc.createElement(Costanti.NODO_XML_STANZA_ARTEFATTO);
                elem.appendChild(doc.createTextNode(a.getNome()));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.STANZA.compileInstance(): impossibile compilare oggetto non Stanza");
    }),
    /**
     * Entita' UnitaImmobiliare
     * @see UnitaImmobiliare
     */
    UNITA_IMMOB((obj, doc) -> {
        if (obj instanceof UnitaImmobiliare) {
            UnitaImmobiliare unitaImmobiliare = (UnitaImmobiliare) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_UNITA_IMMOB);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_UNITA_IMMOB_NOME);
            nome.setValue(unitaImmobiliare.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = null;

            /* Sensori salvati a livello di stanza/artefatto
            Inoltre le stanze sono lette esternamente in base ai file presenti

            for (Stanza s : unitaImmobiliare.getStanze()) {
                elem = doc.createElement(Costanti.NODO_XML_UNITA_IMMOB_STANZA);
                elem.appendChild(doc.createTextNode(s.getNome()));
                root.appendChild(elem);

                //salva la stanza sui file locali
                DatiLocali.getInstance().salva(s, unitaImmobiliare.getNome());
            }

            //salva i sensori e attuatori presenti nell'unita' immobiliare (essendo qui presenti tutti i riferimenti)
            for (Sensore s : unitaImmobiliare.getSensori()) {
                DatiLocali.getInstance().salva(s);
            }
            for (Attuatore a : unitaImmobiliare.getAttuatori()) {
                DatiLocali.getInstance().salva(a);
            }
            */

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.UNITA_IMMOB.compileInstance(): impossibile compilare oggetto non UnitaImmobiliare");
    }),
    /**
     * Entita' Modalita
     * @see Modalita
     */
    MODALITA((obj, doc) -> {
        if (obj instanceof Modalita) {
            Modalita modalita = (Modalita)obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_MODALITA);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_MODALITA_NOME);
            nome.setValue(modalita.getNome());
            root.setAttributeNode(nome);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.MODALITA.compileInstance(): impossibile compilare oggetto non Modalita");
    }),
    /**
     * Entita' CategoriaAttuatore
     * @see CategoriaAttuatore
     */
    CATEGORIA_ATTUATORE((obj, doc) -> {
        if (obj instanceof CategoriaAttuatore) {
            CategoriaAttuatore cat = (CategoriaAttuatore)obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_CATEGORIA_ATTUATORE);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME);
            nome.setValue(cat.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
            elem.appendChild(doc.createTextNode(cat.getTestoLibero()));
            root.appendChild(elem);

            /* Le modalita' non sono piu' forzate, leggo tutte quelle presenti nella cartella legata alla categoria

            for (Modalita modalita : cat.getElencoModalita()) {
                Element el = doc.createElement(Costanti.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
                el.appendChild(doc.createTextNode(modalita.getNome()));
                root.appendChild(el);
            }
            */

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CATEGORIA_ATTUATORE.compileInstance(): impossibile compilare oggetto non CategoriaAttuatore");
    }),
    /**
     * Entita' CategoriaSensore
     * @see CategoriaSensore
     */
    CATEGORIA_SENSORE((obj, doc) -> {
        if (obj instanceof CategoriaSensore) {
            CategoriaSensore cat = (CategoriaSensore)obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_CATEGORIA_SENSORE);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_CATEGORIA_SENSORE_NOME);
            nome.setValue(cat.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO);
            elem.appendChild(doc.createTextNode(cat.getTestoLibero()));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
            elem.appendChild(doc.createTextNode(cat.getInformazioneRilevabile()));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CATEGORIA_SENSORE.compileInstance(): impossibile compilare oggetto non CategoriaSensore");
    });

    private interface CompilatoreXML<T> {
        /**
         * Ritorna l'elemento base contenente tutti i valori dell'istanza passata codificati in una struttura XML.
         * Il documento XML passato come parametro deve essere utilizzato solo come generatore di elementi. Sara' compito
         * dell'utilizzatore di questo metodo appendere l'elemento generato al documento come necessita.
         *
         * @param obj   istanza di cui eseguire la codifica
         * @param doc   istanza del documento con cui generare gli elementi
         * @return  Elemento base contenente tutti i valori dell'istanza passata. In caso di errori sul tipo di istanza viene lanciata eccezione.
         * @throws IllegalArgumentException Eccezione lanciata in caso l'istanza indicata non appartenga ad un tipo gestito dal compilatore.
         */
        Element compileInstance(T obj, Document doc) throws IllegalArgumentException, IOException, ParserConfigurationException, TransformerException;
    }
    private CompilatoreXML compilatore;

    private ScrittoriXML(CompilatoreXML compilatore) {
        this.compilatore = compilatore;
    }

    /**
     * Aggiunge in coda al documento passato l'elemento base contenente tutti i valori dell'istanza passata, codificati in una struttura XML.
     *
     * @param obj   istanza di cui eseguire la codifica
     * @param doc   istanza del documento cui appendere i dati codificati
     * @throws NullPointerException Eccezione scatenata dal passaggio di un riferimento a null per il documento o per l'oggetto da codificare.
     * @throws IllegalArgumentException Eccezione lanciata in caso l'istanza indicata non appartenga ad un tipo gestito dal compilatore.
     */
    public void appendiDocumento(Object obj, Document doc) throws NullPointerException, IllegalArgumentException, IOException, TransformerException, ParserConfigurationException {
        if (obj == null)
            throw new NullPointerException(this.getClass().getName() + ": Object non puo' essere null");
        if (doc == null)
            throw new NullPointerException(this.getClass().getName() + ": Document non puo' essere null");
        doc.appendChild(compilatore.compileInstance(obj, doc));
    }
}
