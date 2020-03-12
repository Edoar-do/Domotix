package domotix.model.io.datilocali;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.util.Costanti;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Enum contenente i lettori XML per i file salvati localmente
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public enum LettoriXML {
    /**
     * Entita' Attuatore
     * @see Attuatore
     */
    ATTUATORE(LettoriXML::leggiAttuatore),
    /**
     * Entita' Sensore
     * @see Sensore
     */
    SENSORE(LettoriXML::leggiSensore),
    /**
     * Entita' Artefatto
     * @see Artefatto
     */
    ARTEFATTO(LettoriXML::leggiArtefatto),
    /**
     * Entita' Stanza
     * @see Stanza
     */
    STANZA(LettoriXML::leggiStanza),
    /**
     * Entita' UnitaImmobiliare
     * @see UnitaImmobiliare
     */
    UNITA_IMMOB(LettoriXML::leggiUnitaImmobiliare),
    /**
     * Entita' Parametro per Modalita'
     * @see Parametro
     * @see Modalita
     */
    PARAMETRO_MODALITA(LettoriXML::leggiParametroModalita),
    /**
     * Entita' Modalita
     * @see Modalita
     */
    MODALITA(LettoriXML::leggiModalita),
    /**
     * Entita' CategoriaAttuatore
     * @see CategoriaAttuatore
     */
    CATEGORIA_ATTUATORE(LettoriXML::leggiCategoriaAttuatore),
    /**
     * Entita' InfoRilevabile
     * @see InfoRilevabile
     */
    INFORMAZIONE_RILEVABILE(LettoriXML::leggiInfoRilevabile),
    /**
     * Entita' CategoriaSensore
     * @see CategoriaSensore
     */
    CATEGORIA_SENSORE(LettoriXML::leggiCategoriaSensore);



    private interface IstanziatoreXML {
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
        Object getInstance(Element el) throws Exception;
    }

    private IstanziatoreXML istanziatore;

    private LettoriXML(IstanziatoreXML istanziatore) {
        this.istanziatore = istanziatore;
    }

    /**
     * Ritorna l'istanza ottenuta dai dati contenuti nel documento, se quest'ultimo rispetta la struttura designata.
     * Assicurarsi di leggere il documento XML corretto per l'istanziatore utilizzato.
     *
     * @param el   documento XML contenente i dati necessari all'istanziazione
     * @return  Istanza dell'elemento
     * @throws NullPointerException Eccezione scatenata dal passaggio di un riferimento a null per il documento.
     * @throws Exception Eccezione scatenata dall'accesso ai contenuti dati salvati tramite AccessoDatiSalvati
     * @see LetturaDatiSalvati
     */
    public Object getInstance(Element el) throws Exception {
        if (el == null)
            throw new NullPointerException(this.getClass().getName() + ": elemento passato non puo' essere null");
        return istanziatore.getInstance(el);
    }

    /** Metodo per lettore: ATTUATORE **/
    private static Object leggiAttuatore(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_ATTUATORE)) {
            String nome;
            boolean stato;
            String categoria;
            CategoriaAttuatore cat;
            String modalita;
            Modalita mod;
            Attuatore attuatore;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_ATTUATORE_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_ATTUATORE_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): attributo " + Costanti.NODO_XML_ATTUATORE_NOME + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_STATO);
            if (childs.getLength() > 0) {
                stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
            } else
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_STATO + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_CATEGORIA);
            if (childs.getLength() > 0) {
                categoria = childs.item(0).getTextContent();
            } else
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_CATEGORIA + " assente.");

            cat = ElencoCategorieAttuatori.getInstance().getCategoria(categoria);
            if (cat == null) {
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): lettura attuatore di categoria " + categoria + " inesistente o ancora non letta.");
            }

            childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_MODALITA);
            if (childs.getLength() > 0) {
                modalita = childs.item(0).getTextContent();
            } else
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_MODALITA + " assente.");

            mod = null;
            for (Modalita m : cat.getElencoModalita()) {
                if (m.getNome().equals(modalita)) {
                    mod = m;
                    break;
                }
            }
            if (cat == null) {
                throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): lettura attuatore con modalita " + modalita + " sconosciuta.");
            }

            attuatore = new Attuatore(nome, cat, mod);
            attuatore.setStato(stato);

            //ritorno istanza corretta
            return attuatore;
        }
        else
            throw new NoSuchElementException("LettoriXML.ATTUATORE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_ATTUATORE);
    }

    /** Metodo per lettore: SENSORE **/
    private static Object leggiSensore(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_SENSORE)) {
            String nome;
            boolean stato;
            String categoria;
            CategoriaSensore cat;
            int valore;
            Sensore sensore;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_SENSORE_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_SENSORE_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): attributo " + Costanti.NODO_XML_SENSORE_NOME + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_STATO);
            if (childs.getLength() > 0) {
                stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
            } else
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_STATO + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_CATEGORIA);
            if (childs.getLength() > 0) {
                categoria = childs.item(0).getTextContent();
            } else
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_CATEGORIA + " assente.");

            cat = ElencoCategorieSensori.getInstance().getCategoria(categoria);
            if (cat == null) {
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): lettura sensore di categoria " + categoria + " inesistente o ancora non letta.");
            }

            childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_VALORE);
            if (childs.getLength() > 0) {
                valore = Integer.parseInt(childs.item(0).getTextContent());
            } else
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_VALORE + " assente.");

            sensore = new Sensore(nome, cat);
            sensore.setStato(stato);
            sensore.setValore(valore);

            //ritorno istanza corretta
            return sensore;
        }
        else
            throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_SENSORE);
    }

    /** Metodo per lettore: ARTEFATTO **/
    private static Object leggiArtefatto(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_ARTEFATTO)) {
            String nome;
            String unitImmob;
            Artefatto artefatto;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_ARTEFATTO_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_ARTEFATTO_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): attributo " + Costanti.NODO_XML_ARTEFATTO_NOME + " assente.");

            if (el.hasAttribute(Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB)) {
                unitImmob = el.getAttribute(Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB);
            } else
                throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): attributo " + Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB + " assente.");

            artefatto = new Artefatto(nome);
            //Aggiungo gli elenchi globali come osservatori dei dispositivi
            artefatto.addOsservatoreListaSensori(ElencoSensori.getInstance());
            artefatto.addOsservatoreListaAttuatori(ElencoAttuatori.getInstance());
            artefatto.setUnitaOwner(unitImmob);

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_ARTEFATTO_SENSORE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String sensore = childs.item(i).getTextContent();
                    Sensore s = null;

                    //verifico che non sia gia' stato letto (collegato a piu' stanze)
                    if (ElencoSensori.getInstance().contains(sensore)) {
                        s = ElencoSensori.getInstance().getDispositivo(sensore);
                    }
                    //se sensore ancora sconosciuto lo leggo
                    else {
                        s = LetturaDatiSalvati.getInstance().leggiSensore(sensore);
                    }

                    artefatto.addSensore(s);
                }
            }

            childs = el.getElementsByTagName(Costanti.NODO_XML_ARTEFATTO_ATTUATORE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String attuatore = childs.item(i).getTextContent();
                    Attuatore a = null;

                    //verifico che non sia gia' stato letto (collegato a piu' stanze)
                    if (ElencoSensori.getInstance().contains(attuatore)) {
                        a = ElencoAttuatori.getInstance().getDispositivo(attuatore);
                    }
                    //se sensore ancora sconosciuto lo leggo
                    else {
                        a = LetturaDatiSalvati.getInstance().leggiAttuatore(attuatore);
                    }

                    artefatto.addAttuatore(a);
                }
            }

            //ritorno istanza corretta
            return artefatto;
        }
        else
            throw new NoSuchElementException("LettoriXML.ARTEFATTO.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_ARTEFATTO);
    }

    /** Metodo per lettore: STANZA **/
    private static Object leggiStanza(Element el) throws Exception {
//controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_STANZA)) {
            String nome;
            String unitImmob;
            Stanza stanza;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_STANZA_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_STANZA_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.STANZA.getInstance(): attributo " + Costanti.NODO_XML_STANZA_NOME + " assente.");

            if (el.hasAttribute(Costanti.NODO_XML_STANZA_UNITA_IMMOB)) {
                unitImmob = el.getAttribute(Costanti.NODO_XML_STANZA_UNITA_IMMOB);
            } else
                throw new NoSuchElementException("LettoriXML.STANZA.getInstance(): attributo " + Costanti.NODO_XML_STANZA_UNITA_IMMOB + " assente.");

            stanza = new Stanza(nome);
            //Aggiungo gli elenchi globali come osservatori dei dispositivi
            stanza.addOsservatoreListaSensori(ElencoSensori.getInstance());
            stanza.addOsservatoreListaAttuatori(ElencoAttuatori.getInstance());
            stanza.setUnitaOwner(unitImmob);


            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_SENSORE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String sensore = childs.item(i).getTextContent();
                    Sensore s = null;

                    //verifico che non sia gia' stato letto (collegato a piu' stanze)
                    if (ElencoSensori.getInstance().contains(sensore)) {
                        s = ElencoSensori.getInstance().getDispositivo(sensore);
                    }
                    //se sensore ancora sconosciuto lo leggo
                    else {
                        s = LetturaDatiSalvati.getInstance().leggiSensore(sensore);
                    }

                    stanza.addSensore(s);
                }
            }

            childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_ATTUATORE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String attuatore = childs.item(i).getTextContent();
                    Attuatore a = null;

                    //verifico che non sia gia' stato letto (collegato a piu' stanze)
                    if (ElencoSensori.getInstance().contains(attuatore)) {
                        a = ElencoAttuatori.getInstance().getDispositivo(attuatore);
                    }
                    //se sensore ancora sconosciuto lo leggo
                    else {
                        a = LetturaDatiSalvati.getInstance().leggiAttuatore(attuatore);
                    }

                    stanza.addAttuatore(a);
                }
            }

            childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_ARTEFATTO);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String artefatto = childs.item(i).getTextContent();
                    Artefatto a = LetturaDatiSalvati.getInstance().leggiArtefatto(artefatto, unitImmob);
                    stanza.addArtefatto(a);
                }
            }

            //ritorno istanza corretta
            return stanza;
        }
        else
            throw new NoSuchElementException("LettoriXML.STANZA.getInstance():  elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_STANZA);
    }

    /** Metodo per lettore: UNITA_IMMOB **/
    private static Object leggiUnitaImmobiliare(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_UNITA_IMMOB)) {
            String nome;
            UnitaImmobiliare unit;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_UNITA_IMMOB_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_UNITA_IMMOB_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.UNITA_IMMOB.getInstance(): attributo " + Costanti.NODO_XML_UNITA_IMMOB_NOME + " assente.");

            unit = new UnitaImmobiliare(nome);

            //Lettura e aggiunta di tutte le stanze
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_UNITA_IMMOB_STANZA);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String stanza = childs.item(i).getTextContent();
                    Stanza s = LetturaDatiSalvati.getInstance().leggiStanza(stanza, nome);
                    if (stanza.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT))
                        unit.setStanzaDefault(s);
                    else
                        unit.addStanza(s);
                }
            }

            //ritorno istanza corretta
            return unit;
        }
        else
            throw new NoSuchElementException("LettoriXML.UNITA_IMMOB.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_UNITA_IMMOB);
    }

    /** Metodo per lettore: PARAMETRO_MODALITA **/
    private static Object leggiParametroModalita(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_MODALITA_PARAMETRO)) {
            String nome;
            double valore;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_MODALITA_PARAMETRO_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_MODALITA_PARAMETRO_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance(): attributo " + Costanti.NODO_XML_MODALITA_PARAMETRO_NOME + " assente.");

            //Lettura e aggiunta dei parametri
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_MODALITA_PARAMETRO_VALORE);
            if (childs.getLength() > 0) {
                valore = Double.parseDouble(childs.item(0).getTextContent());
            } else
                throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance(): elemento " + Costanti.NODO_XML_MODALITA_PARAMETRO_VALORE + " assente.");

            //ritorno istanza corretta
            return new Parametro(nome, valore);
        }
        else
            throw new NoSuchElementException("LettoriXML.PARAMETRO_MODALITA.getInstance():  elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_MODALITA_PARAMETRO);
    }

    /** Metodo per lettore: MODALITA **/
    private static Object leggiModalita(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_MODALITA)) {
            String nome;
            Modalita m;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_MODALITA_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_MODALITA_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.MODALITA.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");

            m = new Modalita(nome);

            //Lettura e aggiunta dei parametri
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_MODALITA_PARAMETRO);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    Element e = (Element) childs.item(i);
                    m.addParametro((Parametro) PARAMETRO_MODALITA.getInstance(e));
                }
            }

            //ritorno istanza corretta
            return m;
        }
        else
            throw new NoSuchElementException("LettoriXML.MODALITA.getInstance():  elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_MODALITA);
    }

    /** Metodo per lettore: CATEGORIA_ATTUATORE **/
    private static Object leggiCategoriaAttuatore(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_CATEGORIA_ATTUATORE)) {
            String nome;
            String testoLibero;
            CategoriaAttuatore c;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
            if (childs.getLength() > 0) {
                testoLibero = childs.item(0).getTextContent();
            } else
                throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO + " assente.");

            c = new CategoriaAttuatore(nome, testoLibero);

            //Lettura e aggiunta delle modalita
            childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String modalita = childs.item(i).getTextContent();
                    c.addModalita(LetturaDatiSalvati.getInstance().leggiModalita(modalita, c.getNome()));
                }
            }

            //ritorno istanza corretta
            return c;
        }
        else
            throw new NoSuchElementException("LettoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_CATEGORIA_ATTUATORE);
    }

    /** Metodo per lettore: INFORMAZIONE_RILEVABILE **/
    private static Object leggiInfoRilevabile(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_INFORILEVABILE)) {
            String nome;
            boolean numerica;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_INFORILEVABILE_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_INFORILEVABILE_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): attributo " + Costanti.NODO_XML_INFORILEVABILE_NOME + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_INFORILEVABILE_NUMERICA);
            if (childs.getLength() > 0) {
                numerica = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
            } else
                throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): elemento " + Costanti.NODO_XML_INFORILEVABILE_NUMERICA + " assente.");

            //ritorno istanza corretta
            return new InfoRilevabile(nome, numerica);
        }
        else
            throw new NoSuchElementException("LettoriXML.INFORMAZIONE_RILEVABILE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_INFORILEVABILE);
    }

    /** Metodo per lettore: CATEGORIA_SENSORE **/
    private static Object leggiCategoriaSensore(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_CATEGORIA_SENSORE)) {
            String nome;
            String testoLibero;
            String infoRilev;
            CategoriaSensore c;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_CATEGORIA_SENSORE_NOME)) {
                nome = el.getAttribute(Costanti.NODO_XML_CATEGORIA_SENSORE_NOME);
            } else
                throw new NoSuchElementException("LettoriXML.CATEGORIA_SENSORE.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO);
            if (childs.getLength() > 0) {
                testoLibero = childs.item(0).getTextContent();
            } else
                throw new NoSuchElementException("LettoriXML.CATEGORIA_SENSORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
            if (childs.getLength() > 0) {
                //Per retro-compatibilita' con la versione 1, dove ogni categoria ha una sola informazione rilevabile e questa e' salvata direttamente
                //nella descrizione della categoria senza un file apposito, si verifica qui il numero di informazioni rilevabili e se corrisponde a 1
                //allora la si prova a leggere. In caso di fallimento di lettura allora si procede ad utilizzare il vecchio istanziamento.
                //Altrimenti si procede ad utilizzare quello nuovo
                if (childs.getLength() == 1) {
                    InfoRilevabile i = null;
                    String info = childs.item(0).getTextContent();
                    try {
                        i = LetturaDatiSalvati.getInstance().leggiInfoRilevabile(info, nome);
                    }
                    catch (Exception e) {
                        i = null;
                    }
                    //se l'informazione letta fosse null allora si procede alla vecchia istanzazione
                    if (i == null)
                        c = new CategoriaSensore(nome, testoLibero, info);
                    else //altrimenti e' della nuova versione ma con una sola informazione rilevabile
                        c = new CategoriaSensore(nome, testoLibero, i);
                }
                else {
                    //Altrimenti procedo ad istanziare la categoria ed ad aggiungere le varie informazioni rilevabili
                    String info = childs.item(0).getTextContent();

                    c = new CategoriaSensore(nome, testoLibero, LetturaDatiSalvati.getInstance().leggiInfoRilevabile(info, nome));

                    for (int i = 1; i < childs.getLength(); i++) {
                        info = childs.item(i).getTextContent();
                        c.addInformazioneRilevabile(LetturaDatiSalvati.getInstance().leggiInfoRilevabile(info, c.getNome()));
                    }
                }
            } else
                throw new NoSuchElementException("LettoriXML.CATEGORIA_SENSORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE + " assente.");

            //ritorno istanza corretta
            return c;
        }
        else
            throw new NoSuchElementException("LettoriXML.CATEGORIA_SENSORE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_CATEGORIA_SENSORE);
    }
}
