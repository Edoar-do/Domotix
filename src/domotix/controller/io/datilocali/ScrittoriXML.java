package domotix.controller.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.util.Costanti;
import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Classe enum per contenere tutti i compilatori in elementi XML delle entita' presenti nel programma.
 * Questi elementi vengono sfruttati per il salvataggio su file nella classe DatiLocali.
 * Si intende creare un punto comune per facilitare l'aggiunta, la modifica o la rimozione di un'entita'.
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public enum ScrittoriXML {
    /**
     * Entita' Azione
     * @see domotix.model.bean.regole.Azione
     */
    AZIONE(ScrittoriXML::compilaAzione),
    /**
     * Entita' Conseguente
     * @see domotix.model.bean.regole.Conseguente
     */
    CONSEGUENTE(ScrittoriXML::compilaConseguente),
    /**
     * Entita' InfoSensoriale
     * @see domotix.model.bean.regole.InfoSensoriale
     */
    INFO_SENSORIALE(ScrittoriXML::compilaInfoSensoriale),
    /**
     * Entita' Condizione
     * @see domotix.model.bean.regole.Condizione
     */
    CONDIZIONE(ScrittoriXML::compilaCondizione),
    /**
     * Entita' Antecedente
     * @see domotix.model.bean.regole.Antecedente
     */
    ANTECEDENTE(ScrittoriXML::compilaAntecedente),
    /**
     * Entita' Regola
     * @see domotix.model.bean.regole.Regola
     */
    REGOLA(ScrittoriXML::compilaRegola),
    /**
     * Entita' Attuatore
     * @see Attuatore
     */
    ATTUATORE(ScrittoriXML::compilaAttuatore),
    /**
     * Entita' Sensore
     * @see Sensore
     */
    SENSORE(ScrittoriXML::compilaSensore),
    /**
     * Entita' Artefatto
     * @see Artefatto
     */
    ARTEFATTO(ScrittoriXML::compilaArtefatto),
    /**
     * Entita' Stanza
     * @see Stanza
     */
    STANZA(ScrittoriXML::compilaStanza),
    /**
     * Entita' UnitaImmobiliare
     * @see UnitaImmobiliare
     */
    UNITA_IMMOB(ScrittoriXML::compilaUnitaImmobiliare),
    /**
     * Entita' Parametro per Modalita'
     * @see Parametro
     * @see Modalita
     */
    PARAMETRO_MODALITA(ScrittoriXML::compilaParametroModalita),
    /**
     * Entita' Modalita
     * @see Modalita
     */
    MODALITA(ScrittoriXML::compilaModalita),
    /**
     * Entita' CategoriaAttuatore
     * @see CategoriaAttuatore
     */
    CATEGORIA_ATTUATORE(ScrittoriXML::compilaCategoriaAttuatore),
    /**
     * Entita' InfoRilevabile
     * @see InfoRilevabile
     */
    INFORMAZIONE_RILEVABILE(ScrittoriXML::compilaInfoRilevabile),
    /**
     * Entita' CategoriaSensore
     * @see CategoriaSensore
     */
    CATEGORIA_SENSORE(ScrittoriXML::compilaCategoriaSensore);



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

    /** Metodo per scrittore: AZIONE **/
    private static Element compilaAzione(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Azione) {
            Azione azione = (Azione) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_AZIONE);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_AZIONE_ATTUATORE);
            elem.appendChild(doc.createTextNode(azione.getAttuatore().getNome()));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_AZIONE_MODALITA);
            elem.appendChild(doc.createTextNode(azione.getModalita().getNome()));
            root.appendChild(elem);

            for (Parametro p : azione.getParametri()) {
                elem = PARAMETRO_MODALITA.compilatore.compileInstance(p, doc);
                root.appendChild(elem);
            }

            if (azione.getStart() != null) {
                elem = doc.createElement(Costanti.NODO_XML_AZIONE_START);
                elem.appendChild(doc.createTextNode(azione.getStart().format(SensoreOrologio.TIME_FORMATTER)));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.AZIONE.compileInstance(): impossibile compilare oggetto non Azione: " + getClassName(obj));
    }

    /** Metodo per scrittore: CONSEGUENTE **/
    private static Element compilaConseguente(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Conseguente) {
            Conseguente conseguente = (Conseguente) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_CONSEGUENTE);

            Element elem;
            for (Azione a : conseguente.getAzioni()) {
                elem = AZIONE.compilatore.compileInstance(a, doc);
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CONSEGUENTE.compileInstance(): impossibile compilare oggetto non Conseguente: " + getClassName(obj));
    }

    /** Metodo per scrittore: INFO_SENSORIALE **/
    private static Element compilaInfoSensoriale(Object obj, Document doc) {
        if (obj instanceof InfoVariabile) {
            InfoVariabile infoVariabile = (InfoVariabile) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE_SENSORE);
            elem.appendChild(doc.createTextNode(infoVariabile.getSensore().getNome()));
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE_INFO_RILEV);
            elem.appendChild(doc.createTextNode(infoVariabile.getNomeInfo()));
            root.appendChild(elem);

            return root;
        } else if (obj instanceof InfoCostante) {
            InfoCostante infoCostante = (InfoCostante) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE_COSTANTE);
            elem.appendChild(doc.createTextNode(infoCostante.getInfo().toString()));
            root.appendChild(elem);

            return root;
        } else if (obj instanceof InfoTemporale) {
            InfoTemporale infoTemporale = (InfoTemporale) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_INFO_SENSORIALE_TEMPORALE);
            elem.appendChild(doc.createTextNode(infoTemporale.getTempo().format(SensoreOrologio.TIME_FORMATTER)));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.INFO_SENSORIALE.compileInstance(): impossibile compilare oggetto non InfoSensoriale: " + getClassName(obj));
    }

    /** Metodo per scrittore: CONDIZIONE **/
    private static Element compilaCondizione(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Condizione) {
            Condizione condizione = (Condizione) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_CONDIZIONE);

            //popola elemento base
            Element elem = INFO_SENSORIALE.compilatore.compileInstance(condizione.getSinistra(), doc);
            Attr posizione = doc.createAttribute(Costanti.NODO_XML_CONDIZIONE_POSIZIONE);
            posizione.setValue(Costanti.NODO_XML_CONDIZIONE_SINISTRA);
            elem.setAttributeNode(posizione);
            root.appendChild(elem);

            elem = INFO_SENSORIALE.compilatore.compileInstance(condizione.getDestra(), doc);
            posizione = doc.createAttribute(Costanti.NODO_XML_CONDIZIONE_POSIZIONE);
            posizione.setValue(Costanti.NODO_XML_CONDIZIONE_DESTRA);
            elem.setAttributeNode(posizione);
            root.appendChild(elem);

            elem = doc.createElement(Costanti.NODO_XML_CONDIZIONE_OPERATORE);
            elem.appendChild(doc.createTextNode(condizione.getOperatore()));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CONDIZIONE.compileInstance(): impossibile compilare oggetto non Condizione: " + getClassName(obj));
    }

    /** Metodo per scrittore: ANTECEDENTE **/
    private static Element compilaAntecedente(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Antecedente) {
            Antecedente antecedente = (Antecedente) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_ANTECEDENTE);

            //popola elemento base
            Element elem = CONDIZIONE.compilatore.compileInstance(antecedente.getCondizione(), doc);
            root.appendChild(elem);

            if (!antecedente.isLast()) {
                elem = doc.createElement(Costanti.NODO_XML_ANTECEDENTE_OPLOGICO);
                elem.appendChild(doc.createTextNode(antecedente.getOperatoreLogico()));
                root.appendChild(elem);

                elem = ANTECEDENTE.compilatore.compileInstance(antecedente.getProssimoAntecedente(), doc);
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.ANTECEDENTE.compileInstance(): impossibile compilare oggetto non Antecedente: " + getClassName(obj));
    }

    /** Metodo per scrittore: REGOLA **/
    private static Element compilaRegola(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Regola) {
            Regola regola = (Regola) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_REGOLA);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_REGOLA_ID);
            nome.setValue(regola.getId());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_REGOLA_STATO);
            //elem.appendChild(doc.createTextNode(regola.getStato() ? "1" : "0"));
            elem.appendChild(doc.createTextNode(regola.getStato().name()));
            root.appendChild(elem);

            //antecedente e conseguente
            if (regola.getAntecedente() != null) {
                elem = ANTECEDENTE.compilatore.compileInstance(regola.getAntecedente(), doc);
                root.appendChild(elem);
            } //se antecedente = null allora e' TRUE e non lo scrivo

            elem = CONSEGUENTE.compilatore.compileInstance(regola.getConseguente(), doc);
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.REGOLA.compileInstance(): impossibile compilare oggetto non Regola: " + getClassName(obj));
    }

    /** Metodo per scrittore: ATTUATORE **/
    private static Element compilaAttuatore(Object obj, Document doc) {
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
            throw new IllegalArgumentException("ScrittoriXML.ATTUATORE.compileInstance(): impossibile compilare oggetto non Attuatore: " + getClassName(obj));
    }

    /** Metodo per scrittore: SENSORE **/
    private static Element compilaSensore(Object obj, Document doc) {
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

            sensore.getValori().forEach((nomeInfo, valore) -> {
                Element elemInfo = doc.createElement(Costanti.NODO_XML_SENSORE_VALORE);

                Attr attrNomeInfo = doc.createAttribute(Costanti.NODO_XML_SENSORE_NOME_VALORE);
                attrNomeInfo.setValue(nomeInfo);
                elemInfo.setAttributeNode(attrNomeInfo);

                elemInfo.appendChild(doc.createTextNode(valore+""));
                root.appendChild(elemInfo);
            });

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.SENSORE.compileInstance(): impossibile compilare oggetto non Sensore: " + getClassName(obj));
    }

    /** Metodo per scrittore: ARTEFATTO **/
    private static Element compilaArtefatto(Object obj, Document doc) {
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
            throw new IllegalArgumentException("ScrittoriXML.ARTEFATTO.compileInstance(): impossibile compilare oggetto non Artefatto: " + getClassName(obj));
    }

    /** Metodo per scrittore: Stanza **/
    private static Element compilaStanza(Object obj, Document doc) {
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
            throw new IllegalArgumentException("ScrittoriXML.STANZA.compileInstance(): impossibile compilare oggetto non Stanza: " + getClassName(obj));
    }

    /** Metodo per scrittore: UNITA_IMMOB **/
    private static Element compilaUnitaImmobiliare(Object obj, Document doc) {
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

            for (Stanza s : unitaImmobiliare.getStanze()) {
                elem = doc.createElement(Costanti.NODO_XML_UNITA_IMMOB_STANZA);
                elem.appendChild(doc.createTextNode(s.getNome()));
                root.appendChild(elem);
            }

            /* Sensori salvati a livello di stanza/artefatto

            //salva i sensori e attuatori presenti nell'unita' immobiliare (essendo qui presenti tutti i riferimenti)
            for (Sensore s : unitaImmobiliare.getSensori()) {
                DatiLocali.getInstance().salva(s);
            }
            for (Attuatore a : unitaImmobiliare.getAttuatori()) {
                DatiLocali.getInstance().salva(a);
            }
            */

            for (Regola r : unitaImmobiliare.getRegole()) {
                elem = doc.createElement(Costanti.NODO_XML_REGOLA);
                elem.appendChild(doc.createTextNode(r.getId()));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.UNITA_IMMOB.compileInstance(): impossibile compilare oggetto non UnitaImmobiliare: " + getClassName(obj));
    }

    /** Metodo per scrittore: PARAMETRO_MODALITA **/
    private static Element compilaParametroModalita(Object obj, Document doc) {
        if (obj instanceof Parametro) {
            Parametro parametro = (Parametro) obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_MODALITA_PARAMETRO);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_MODALITA_PARAMETRO_NOME);
            nome.setValue(parametro.getNome());
            root.setAttributeNode(nome);

            Element elem;
            elem = doc.createElement(Costanti.NODO_XML_MODALITA_PARAMETRO_VALORE);
            elem.appendChild(doc.createTextNode(Double.toString(parametro.getValore())));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.PARAMETRO_MODALITA.compileInstance(): impossibile compilare oggetto non Parametro: " + getClassName(obj));
    }

    /** Metodo per scrittore: MODALITA **/
    private static Element compilaModalita(Object obj, Document doc) throws ParserConfigurationException, TransformerException, IOException {
        if (obj instanceof Modalita) {
            Modalita modalita = (Modalita)obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_MODALITA);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_MODALITA_NOME);
            nome.setValue(modalita.getNome());
            root.setAttributeNode(nome);

            Element elem;
            for (Parametro par : modalita.getParametri()) {
                elem = PARAMETRO_MODALITA.compilatore.compileInstance(par, doc);
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.MODALITA.compileInstance(): impossibile compilare oggetto non Modalita: " + getClassName(obj));
    }

    /** Metodo per scrittore: CATEGORIA_ATTUATORE **/
    private static Element compilaCategoriaAttuatore(Object obj, Document doc) {
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

            for (Modalita modalita : cat.getElencoModalita()) {
                elem = doc.createElement(Costanti.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
                elem.appendChild(doc.createTextNode(modalita.getNome()));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CATEGORIA_ATTUATORE.compileInstance(): impossibile compilare oggetto non CategoriaAttuatore: " + getClassName(obj));
    }

    /** Metodo per scrittore: INFORMAZIONE_RILEVABILE **/
    private static Element compilaInfoRilevabile(Object obj, Document doc) {
        if (obj instanceof InfoRilevabile) {
            InfoRilevabile info = (InfoRilevabile)obj;

            //crea elemento base
            Element root = doc.createElement(Costanti.NODO_XML_INFORILEVABILE);

            //assegna attributo identificativo
            Attr nome = doc.createAttribute(Costanti.NODO_XML_INFORILEVABILE_NOME);
            nome.setValue(info.getNome());
            root.setAttributeNode(nome);

            //popola elemento base
            Element elem = doc.createElement(Costanti.NODO_XML_INFORILEVABILE_NUMERICA);
            elem.appendChild(doc.createTextNode(info.isNumerica() ? "1" : "0"));
            root.appendChild(elem);

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.INFORMAZIONE_RILEVABILE.compileInstance(): impossibile compilare oggetto non InfoRilevabile: " + getClassName(obj));
    }

    /** Metodo per scrittore: CATEGORIA_SENSORE **/
    private static Element compilaCategoriaSensore(Object obj, Document doc) {
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

            for (InfoRilevabile i : cat.getInformazioniRilevabili()) {
                elem = doc.createElement(Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
                elem.appendChild(doc.createTextNode(i.getNome()));
                root.appendChild(elem);
            }

            return root;
        } else
            throw new IllegalArgumentException("ScrittoriXML.CATEGORIA_SENSORE.compileInstance(): impossibile compilare oggetto non CategoriaSensore: " + getClassName(obj));
    }

    /**
     * Ritorna il nome della classe dell'oggetto passato oppure la stringa "null" nel caso l'oggetto sia null
     * @param obj   oggetto da analizzare
     * @return  stringa con nome classe
     */
    private static String getClassName(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }
}
