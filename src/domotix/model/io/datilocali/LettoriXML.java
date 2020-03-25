package domotix.model.io.datilocali;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.util.Costanti;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Enum contenente i lettori XML per i file salvati localmente
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public enum LettoriXML {
    /**
     * Entita' Azione
     * @see domotix.model.bean.regole.Azione
     */
    AZIONE(LettoriXML::leggiAzione),
    /**
     * Entita' Conseguente
     * @see domotix.model.bean.regole.Conseguente
     */
    CONSEGUENTE(LettoriXML::leggiConseguente),
    /**
     * Entita' InfoSensoriale
     * @see domotix.model.bean.regole.InfoSensoriale
     */
    INFO_SENSORIALE(LettoriXML::leggiInfoSensoriale),
    /**
     * Entita' Condizione
     * @see domotix.model.bean.regole.Condizione
     */
    CONDIZIONE(LettoriXML::leggiCondizione),
    /**
     * Entita' Antecedente
     * @see domotix.model.bean.regole.Antecedente
     */
    ANTECEDENTE(LettoriXML::leggiAntecedente),
    /**
     * Entita' Regola
     * @see domotix.model.bean.regole.Regola
     */
    REGOLA(LettoriXML::leggiRegola),
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


    /** Metodo per lettore: AZIONE **/
    private static Object leggiAzione(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_AZIONE)) {
            Azione azione;
            Attuatore att;
            Modalita modalita;
            ArrayList<Parametro> parametri = new ArrayList<>();

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_AZIONE_ATTUATORE);
            if (childs.getLength() > 0) {
                String nomeAttuatore = childs.item(0).getTextContent();

                att = ElencoAttuatori.getInstance().getDispositivo(nomeAttuatore);
                if (att == null)
                    throw  new NoSuchElementException("LettoriXML.AZIONE.getInstance(): attuatore " + nomeAttuatore + " non trovato.");

            } else
                throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + Costanti.NODO_XML_AZIONE_ATTUATORE + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_AZIONE_MODALITA);
            if (childs.getLength() > 0) {
                String nomeModalita = childs.item(0).getTextContent();

                modalita = att.getCategoria().getModalita(nomeModalita);
                if (att == null)
                    throw  new NoSuchElementException("LettoriXML.AZIONE.getInstance(): modalita' " + nomeModalita + " per categoria attuatore " + att.getCategoria().getNome() + " non trovato.");

            } else
                throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + Costanti.NODO_XML_AZIONE_MODALITA + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_AZIONE_PARAMETRO);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    Element elParametro = (Element)childs.item(i);
                    parametri.add((Parametro)PARAMETRO_MODALITA.istanziatore.getInstance(elParametro));
                }
            } else
                throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + Costanti.NODO_XML_AZIONE_PARAMETRO + " assente.");

            //ritorno istanza corretta
            return new Azione(att, modalita, parametri);
        }
        else
            throw new NoSuchElementException("LettoriXML.AZIONE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_AZIONE);
    }

    /** Metodo per lettore: CONSEGUENTE **/
    private static Object leggiConseguente(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_CONSEGUENTE)) {
            Conseguente cons = new Conseguente();

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_AZIONE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    Element elAzione = (Element)childs.item(i);
                    cons.addAzione((Azione)AZIONE.istanziatore.getInstance(elAzione));
                }
            } else
                throw new NoSuchElementException("LettoriXML.CONSEGUENTE.getInstance(): elemento " + Costanti.NODO_XML_AZIONE_ATTUATORE + " assente.");

            //ritorno istanza corretta
            return cons;
        }
        else
            throw new NoSuchElementException("LettoriXML.CONSEGUENTE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_CONSEGUENTE);
    }

    /** Metodo per lettore: INFO_SENSORIALE **/
    private static Object leggiInfoSensoriale(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_INFO_SENSORIALE)) {
            InfoSensoriale info = null;

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_INFO_SENSORIALE_SENSORE);
            if (childs.getLength() > 0) {
                //Traduco un'informazione sensoriale di tipo varibile
                String nomeSensore = childs.item(0).getTextContent();
                Sensore sens = ElencoSensori.getInstance().getDispositivo(nomeSensore);

                if (sens == null)
                    throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): sensore " + nomeSensore + " non trovato.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_INFO_SENSORIALE_INFO_RILEV);
                if (childs.getLength() > 0) {
                    String nomeInfoRilev = childs.item(0).getTextContent();
                    InfoRilevabile infoRilev = sens.getCategoria().getInformazioneRilevabile(nomeInfoRilev);

                    if (infoRilev == null)
                        throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): informazione " + nomeInfoRilev + " per categoria sensore " + sens.getCategoria().getNome() + " non trovato.");

                    info = new InfoVariabile(sens, nomeInfoRilev);

                } else
                    throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + Costanti.NODO_XML_INFO_SENSORIALE_INFO_RILEV + " assente.");

            } else {
                //Traduco un'informazione sensoriale di tipo costante
                childs = el.getElementsByTagName(Costanti.NODO_XML_INFO_SENSORIALE_COSTANTE);
                if (childs.getLength() > 0) {
                    //Traduco un'informazione sensoriale di tipo varibile
                    String valoreCostanteStr = childs.item(0).getTextContent();
                    Object valore = null;

                    try {
                        int numInt = Integer.parseInt(valoreCostanteStr);
                        valore = numInt;
                    } catch (NumberFormatException e) {
                        //nothing
                    }

                    if (valore == null) {
                        //valore non intero
                        try {
                            double numDouble = Double.parseDouble(valoreCostanteStr);
                            valore = numDouble;
                        } catch (NumberFormatException e) {
                            //nothing
                        }

                        if (valore == null) {
                            //valore non intero e non double --> allora stringa
                            valore = valoreCostanteStr;
                        }
                    }

                    info = new InfoCostante(valore);

                } else //malformazione
                    throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + Costanti.NODO_XML_REGOLA_STATO + " assente.");
            }

            //ritorno istanza corretta
            return info;
        }
        else
            throw new NoSuchElementException("LettoriXML.INFO_SENSORIALE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_INFO_SENSORIALE);
    }

    /** Metodo per lettore: CONDIZIONE **/
    private static Object leggiCondizione(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_CONDIZIONE)) {
            Condizione cond;
            InfoSensoriale sinistra = null, destra = null;
            String op;

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_INFO_SENSORIALE);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    Element elInfoSens = (Element)childs.item(i);
                    if (elInfoSens.hasAttribute(Costanti.NODO_XML_CONDIZIONE_POSIZIONE)) {
                        String pos = elInfoSens.getAttribute(Costanti.NODO_XML_CONDIZIONE_POSIZIONE);
                        if (pos.equals(Costanti.NODO_XML_CONDIZIONE_SINISTRA))
                            sinistra = (InfoSensoriale)INFO_SENSORIALE.istanziatore.getInstance(elInfoSens);
                        else
                            destra = (InfoSensoriale)INFO_SENSORIALE.istanziatore.getInstance(elInfoSens);
                    }
                    else
                        throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + Costanti.NODO_XML_CONDIZIONE_POSIZIONE + " assente.");
                }

                if (sinistra == null)
                    throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + Costanti.NODO_XML_CONDIZIONE_SINISTRA + " assente.");
                if (destra == null)
                    throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + Costanti.NODO_XML_CONDIZIONE_DESTRA + " assente.");

            } else
                throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + Costanti.NODO_XML_INFO_SENSORIALE + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_CONDIZIONE_OPERATORE);
            if (childs.getLength() > 0) {
                op = childs.item(0).getTextContent();

                if (!op.equals(Condizione.MAGGIORE_UGUALE) &&
                    !op.equals(Condizione.MAGGIORE) &&
                    !op.equals(Condizione.UGUALE) &&
                    !op.equals(Condizione.MINORE) &&
                    !op.equals(Condizione.MINORE_UGUALE))
                    throw new IllegalArgumentException("LettoriXML.CONDIZIONE.getInstance(): operatore " + op + " non gestito.");
            } else
                throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + Costanti.NODO_XML_CONDIZIONE_OPERATORE + " assente.");

            //ritorno istanza corretta
            return new Condizione(sinistra, op, destra);
        }
        else
            throw new NoSuchElementException("LettoriXML.CONDIZIONE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_CONDIZIONE);
    }

    /** Metodo per lettore: ANTECEDENTE **/
    private static Object leggiAntecedente(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_ANTECEDENTE)) {
            Antecedente antecedente, next;
            Condizione cond;
            String op;

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_CONDIZIONE);
            if (childs.getLength() > 0) {
                Element elCondizione = (Element)childs.item(0);
                cond = (Condizione) CONDIZIONE.istanziatore.getInstance(elCondizione);
            } else
                throw new NoSuchElementException("LettoriXML.ANTECEDENTE.getInstance(): elemento " + Costanti.NODO_XML_CONDIZIONE + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_ANTECEDENTE_OPLOGICO);
            if (childs.getLength() > 0) {
                op = childs.item(0).getTextContent();

                if (!op.equals(Antecedente.OPERATORE_AND) &&
                        !op.equals(Antecedente.OPERATORE_OR))
                    throw new IllegalArgumentException("LettoriXML.ANTECEDENTE.getInstance(): operatore " + op + " non gestito.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_ANTECEDENTE);
                if (childs.getLength() > 0) {
                    Element elAntecedente = (Element)childs.item(0);
                    next = (Antecedente) ANTECEDENTE.istanziatore.getInstance(elAntecedente);
                } else
                    throw new NoSuchElementException("LettoriXML.ANTECEDENTE.getInstance(): elemento " + Costanti.NODO_XML_ANTECEDENTE + " assente.");
            } else
                throw new NoSuchElementException("LettoriXML.ANTECEDENTE.getInstance(): elemento " + Costanti.NODO_XML_ANTECEDENTE_OPLOGICO + " assente.");

            antecedente = new Antecedente(cond);

            if (op != null && next != null) {
                antecedente.addAntecedente(op, next);
            }

            //ritorno istanza corretta
            return antecedente;
        }
        else
            throw new NoSuchElementException("LettoriXML.ANTECEDENTE.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_ANTECEDENTE);
    }

    /** Metodo per lettore: REGOLA **/
    private static Object leggiRegola(Element el) throws Exception {
        //controllo tag elemento
        if (el.getTagName().equals(Costanti.NODO_XML_REGOLA)) {
            String id;
            boolean stato;
            Antecedente ant;
            Conseguente cons;

            //estrazione attrubuti
            if (el.hasAttribute(Costanti.NODO_XML_REGOLA_ID)) {
                id = el.getAttribute(Costanti.NODO_XML_REGOLA_ID);
            } else
                throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): attributo " + Costanti.NODO_XML_REGOLA_ID + " assente.");

            //estrazione elementi
            NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_REGOLA_STATO);
            if (childs.getLength() > 0) {
                stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
            } else
                throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + Costanti.NODO_XML_REGOLA_STATO + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_ANTECEDENTE);
            if (childs.getLength() > 0) {
                Element elAntecedente = (Element) childs.item(0);
                ant = (Antecedente) ANTECEDENTE.istanziatore.getInstance(elAntecedente);
            } else
                throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + Costanti.NODO_XML_REGOLA_STATO + " assente.");

            childs = el.getElementsByTagName(Costanti.NODO_XML_CONSEGUENTE);
            if (childs.getLength() > 0) {
                Element elConseguente = (Element) childs.item(0);
                cons = (Conseguente) CONSEGUENTE.istanziatore.getInstance(elConseguente);
            } else
                throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + Costanti.NODO_XML_REGOLA_STATO + " assente.");

            //ritorno istanza corretta
            return new Regola(id, stato, ant, cons);
        }
        else
            throw new NoSuchElementException("LettoriXML.REGOLA.getInstance(): elemento " + el.getTagName() + "non di tipo " + Costanti.NODO_XML_REGOLA);
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

            sensore = new Sensore(nome, cat);
            sensore.setStato(stato);

            childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_VALORE);
            if (childs.getLength() > 0) {
                if (childs.getLength() == 1) {
                    Element valore = (Element) childs.item(0);

                    if (!valore.hasAttribute(Costanti.NODO_XML_SENSORE_NOME_VALORE)) {
                        //v1 -> sensore con solo una info int
                        int val = Integer.parseInt(valore.getTextContent());
                        sensore.setValore(val);
                    }
                    else {
                        //sensore con piu' info, gestisco la prima
                        String nomeInfo = valore.getAttribute(Costanti.NODO_XML_SENSORE_NOME_VALORE);
                        boolean isNumerica = cat.getInformazioneRilevabile(nomeInfo).isNumerica();
                        Object val = null;

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
                    String nomeInfo = valore.getAttribute(Costanti.NODO_XML_SENSORE_NOME_VALORE);
                    boolean isNumerica = cat.getInformazioneRilevabile(nomeInfo).isNumerica();
                    Object val = null;

                    if (isNumerica) {
                        sensore.setValore(nomeInfo, Double.parseDouble(valore.getTextContent()));
                    }
                    else {
                        sensore.setValore(nomeInfo, valore.getTextContent());
                    }
                }
            } else
                throw new NoSuchElementException("LettoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_VALORE + " assente.");

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

            childs = el.getElementsByTagName(Costanti.NODO_XML_REGOLA);
            if (childs.getLength() > 0) {
                for (int i = 0; i < childs.getLength(); i++) {
                    String regola = childs.item(i).getTextContent();
                    Regola r = LetturaDatiSalvati.getInstance().leggiRegola(regola, nome);
                    unit.addRegola(r);
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
