package domotix.model.io.datilocali;

import domotix.model.util.Costanti;
import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Classe enum per contenere tutti gli istanziatori o compilatori delle entita' presenti nel programma.
 * Questi elementi vengono sfruttati per il salvataggio su file nella classe DatiLocali.
 * Si intende creare un punto comune per facilitare l'aggiunta, la modifica o la rimozione di un'entita'.
 *
 * @author paolopasqua
 * @see DatiLocali
 */
public enum TrasformatoriXML {
    /**
     * Entita' Attuatore
     * @see Attuatore
     */
    ATTUATORE((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_ATTUATORE);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
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
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): attributo " + Costanti.NODO_XML_ATTUATORE_NOME + " assente.");

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_STATO);
                if (childs.getLength() > 0) {
                    stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_STATO + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_CATEGORIA);
                if (childs.getLength() > 0) {
                    categoria = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_CATEGORIA + " assente.");

                cat = ElencoCategorieAttuatori.getInstance().getCategoria(categoria);
                if (cat == null) {
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): lettura attuatore di categoria " + categoria + " inesistente o ancora non letta.");
                }

                childs = el.getElementsByTagName(Costanti.NODO_XML_ATTUATORE_MODALITA);
                if (childs.getLength() > 0) {
                    modalita = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_ATTUATORE_MODALITA + " assente.");

                mod = null;
                for (Modalita m : cat.getElencoModalita()) {
                    if (m.getNome().equals(modalita)) {
                        mod = m;
                        break;
                    }
                }
                if (cat == null) {
                    throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): lettura attuatore con modalita " + modalita + " sconosciuta.");
                }

                attuatore = new Attuatore(nome, cat, mod);
                attuatore.setStato(stato);

                //ritorno istanza corretta
                return attuatore;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): nodo " + Costanti.NODO_XML_ATTUATORE + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.ATTUATORE.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_ATTUATORE);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.ATTUATORE.compileInstance(): impossibile compilare oggetto non Attuatore");
    }),
    /**
     * Entita' Sensore
     * @see Sensore
     */
    SENSORE((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_SENSORE);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                boolean stato;
                String categoria;
                CategoriaSensore cat;
                int valore;
                Sensore sensore;

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_SENSORE_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_SENSORE_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): attributo " + Costanti.NODO_XML_SENSORE_NOME + " assente.");

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_STATO);
                if (childs.getLength() > 0) {
                    stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_STATO + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_CATEGORIA);
                if (childs.getLength() > 0) {
                    categoria = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_CATEGORIA + " assente.");

                cat = ElencoCategorieSensori.getInstance().getCategoria(categoria);
                if (cat == null) {
                    throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): lettura sensore di categoria " + categoria + " inesistente o ancora non letta.");
                }

                childs = el.getElementsByTagName(Costanti.NODO_XML_SENSORE_VALORE);
                if (childs.getLength() > 0) {
                    valore = Integer.parseInt(childs.item(0).getTextContent());
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): elemento " + Costanti.NODO_XML_SENSORE_VALORE + " assente.");

                sensore = new Sensore(nome, cat);
                sensore.setStato(stato);
                sensore.setValore(valore);

                //ritorno istanza corretta
                return sensore;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): nodo " + Costanti.NODO_XML_SENSORE + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.SENSORE.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_SENSORE);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.SENSORE.compileInstance(): impossibile compilare oggetto non Sensore");
    }),
    /**
     * Entita' Artefatto
     * @see Artefatto
     */
    ARTEFATTO((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_ARTEFATTO);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                String unitImmob;
                Artefatto artefatto;
                UnitaImmobiliare unit;

                //se parametri non presenti
                if (param == null || param.length <= 0) {
                    throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): parametri di lettura vuoti. Necessario almeno un parametro.");
                }
                //se parametro null
                if (param[0] == null) {
                    throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): parametro di lettura indicato non e' istanziato.");
                }
                //se parametro non istanza di UnitaImmobiliare
                if (!(param[0] instanceof UnitaImmobiliare)) {
                    throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): parametro di lettura indicato non di tipo UnitaImmobiliare.");
                }
                unit = (UnitaImmobiliare)param[0]; //recupero parametro UnitaImmobiliare. Istanza dell'unita' contenente la stanza

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_ARTEFATTO_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_ARTEFATTO_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): attributo " + Costanti.NODO_XML_ARTEFATTO_NOME + " assente.");

                if (el.hasAttribute(Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB)) {
                    unitImmob = el.getAttribute(Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): attributo " + Costanti.NODO_XML_ARTEFATTO_UNITA_IMMOB + " assente.");

                if (!unitImmob.equals(unit.getNome())) {
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): parametro di lettura unita immobiliare " + unit.getNome() + " differente dall'unita immobiliare aspettata " + unitImmob + ".");
                }

                artefatto = new Artefatto(nome, unitImmob);

                /*
                    La lettura dei componenti avviene qui in quanto unico posto in cui si ha tutta la chiave di parentela.
                    Infatti, i sensori/attuatori sono univoci a livello di categoria e queste sono condivise tra le unita' immobiliare.
                    Pertanto, non e' possibile individuare dal nome dei file le associazioni stanza-sensore e stanza-attuatore.
                */

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_ARTEFATTO_SENSORE);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String sensore = childs.item(i).getTextContent();
                        Sensore s = unit.getSensore(sensore); //verifico che non sia gia' stato letto (collegato a piu' stanze)

                        //se sensore ancora sconosciuto lo leggo
                        if (s == null) {
                            s = DatiLocali.getInstance().leggiSensore(sensore);
                        }

                        artefatto.addSensore(s);
                    }
                }
                //else
                    //throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): elemento " + Costanti.NODO_XML_ARTEFATTO_SENSORE + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_ARTEFATTO_ATTUATORE);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String attuatore = childs.item(i).getTextContent();
                        Attuatore a = unit.getAttuatore(attuatore); //verifico che non sia gia' stato letto (collegato a piu' stanze)

                        //se attuatore ancora sconosciuto lo leggo
                        if (a == null) {
                            a = DatiLocali.getInstance().leggiAttuatore(attuatore);
                        }

                        artefatto.addAttuatore(a);
                    }
                }
                //else
                    //throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): elemento " + Costanti.NODO_XML_ARTEFATTO_ATTUATORE + " assente.");

                //ritorno istanza corretta
                return artefatto;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): nodo " + Costanti.NODO_XML_ARTEFATTO + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.ARTEFATTO.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_ARTEFATTO);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.ARTEFATTO.compileInstance(): impossibile compilare oggetto non Artefatto");
    }),
    /**
     * Entita' Stanza
     * @see Stanza
     */
    STANZA((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_STANZA);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                String unitImmob;
                Stanza stanza;
                UnitaImmobiliare unit;

                //se parametri non presenti
                if (param == null || param.length <= 0) {
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): parametri di lettura vuoti. Necessario almeno un parametro.");
                }
                //se parametro null
                if (param[0] == null) {
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): parametro di lettura indicato non e' istanziato.");
                }
                //se parametro non istanza di UnitaImmobiliare
                if (!(param[0] instanceof UnitaImmobiliare)) {
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): parametro di lettura indicato non di tipo UnitaImmobiliare.");
                }
                unit = (UnitaImmobiliare)param[0]; //recupero parametro UnitaImmobiliare. Istanza dell'unita' contenente la stanza

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_STANZA_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_STANZA_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): attributo " + Costanti.NODO_XML_STANZA_NOME + " assente.");

                if (el.hasAttribute(Costanti.NODO_XML_STANZA_UNITA_IMMOB)) {
                    unitImmob = el.getAttribute(Costanti.NODO_XML_STANZA_UNITA_IMMOB);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): attributo " + Costanti.NODO_XML_STANZA_UNITA_IMMOB + " assente.");

                if (!unitImmob.equals(unit.getNome())) {
                    throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): parametro di lettura unita immobiliare " + unit.getNome() + " differente dall'unita immobiliare aspettata " + unitImmob + ".");
                }

                stanza = new Stanza(nome, unitImmob);

                /*
                La lettura dei componenti avviene qui in quanto unico posto in cui si ha tutta la chiave di parentela.
                Infatti, i sensori/attuatori sono univoci a livello di categoria e queste sono condivise tra le unita' immobiliare.
                Pertanto, non e' possibile individuare dal nome dei file le associazioni stanza-sensore e stanza-attuatore.

                Stesso discorso per gli artefatti. Questi sono collegati alle stanze, ma univoci a livello di unita' immobiliare.
                 */

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_SENSORE);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String sensore = childs.item(i).getTextContent();
                        Sensore s = unit.getSensore(sensore); //verifico che non sia gia' stato letto (collegato a piu' stanze)

                        //se sensore ancora sconosciuto lo leggo
                        if (s == null) {
                            s = DatiLocali.getInstance().leggiSensore(sensore);
                        }

                        stanza.addSensore(s);
                    }
                }
                //else
                    //throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): elemento " + Costanti.NODO_XML_STANZA_SENSORE + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_ATTUATORE);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String attuatore = childs.item(i).getTextContent();
                        Attuatore a = unit.getAttuatore(attuatore); //verifico che non sia gia' stato letto (collegato a piu' stanze)

                        //se attuatore ancora sconosciuto lo leggo
                        if (a == null) {
                            a = DatiLocali.getInstance().leggiAttuatore(attuatore);
                        }

                        stanza.addAttuatore(a);
                    }
                }
                //else
                    //throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): elemento " + Costanti.NODO_XML_STANZA_ATTUATORE + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_STANZA_ARTEFATTO);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String artefatto = childs.item(i).getTextContent();
                        String path = PercorsiFile.getInstance().getArtefatto(artefatto, unitImmob);
                        Artefatto a = DatiLocali.getInstance().leggiArtefatto(path, unit);
                        stanza.addArtefatto(a);
                    }
                }
                //else
                    //throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): elemento " + Costanti.NODO_XML_STANZA_ARTEFATTO + " assente.");

                //ritorno istanza corretta
                return stanza;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): nodo " + Costanti.NODO_XML_STANZA + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.STANZA.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_STANZA);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.STANZA.compileInstance(): impossibile compilare oggetto non Stanza");
    }),
    /**
     * Entita' UnitaImmobiliare
     * @see UnitaImmobiliare
     */
    UNITA_IMMOB((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_UNITA_IMMOB);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                UnitaImmobiliare unit;

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_UNITA_IMMOB_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_UNITA_IMMOB_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.UNITA_IMMOB.getInstance(): attributo " + Costanti.NODO_XML_UNITA_IMMOB_NOME + " assente.");

                unit = new UnitaImmobiliare(nome);

                /*Stanze lette esternamente in base ai file presenti

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_UNITA_IMMOB_STANZA);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String stanza = childs.item(i).getTextContent();
                        Stanza s = DatiLocali.getInstance().leggiStanza(stanza, nome);
                        unit.addStanza(s);
                    }
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.UNITA_IMMOB.getInstance(): elemento " + Costanti.NODO_XML_UNITA_IMMOB_STANZA + " assente.");
                */

                //ritorno istanza corretta
                return unit;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.UNITA_IMMOB.getInstance(): nodo " + Costanti.NODO_XML_UNITA_IMMOB + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.UNITA_IMMOB.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_UNITA_IMMOB);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.UNITA_IMMOB.compileInstance(): impossibile compilare oggetto non UnitaImmobiliare");
    }),
    /**
     * Entita' Modalita
     * @see Modalita
     */
    MODALITA((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_MODALITA);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_MODALITA_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_MODALITA_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.MODALITA.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");

                //ritorno istanza corretta
                return new Modalita(nome);
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.MODALITA.getInstance(): nodo " + Costanti.NODO_XML_MODALITA + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.MODALITA.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_MODALITA);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.MODALITA.compileInstance(): impossibile compilare oggetto non Modalita");
    }),
    /**
     * Entita' CategoriaAttuatore
     * @see CategoriaAttuatore
     */
    CATEGORIA_ATTUATORE((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_ATTUATORE);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                String testoLibero;
                CategoriaAttuatore c;

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_ATTUATORE.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_ATTUATORE_NOME + " assente.");

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO);
                if (childs.getLength() > 0) {
                    testoLibero = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO + " assente.");

                c = new CategoriaAttuatore(nome, testoLibero);
                /* Le modalita' vengono lette esternamente

                childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_ATTUATORE_MODALITA);
                if (childs.getLength() > 0) {
                    for (int i = 0; i < childs.getLength(); i++) {
                        String modalita = childs.item(i).getTextContent();
                        Modalita m = DatiLocali.getInstance().leggiModalita(modalita, nome);
                        c.addModalita(m);
                    }
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_ATTUATORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_ATTUATORE_MODALITA + " assente.");
                */

                //ritorno istanza corretta
                return c;
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_ATTUATORE.getInstance(): nodo " + Costanti.NODO_XML_CATEGORIA_ATTUATORE + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_ATTUATORE.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_CATEGORIA_ATTUATORE);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.CATEGORIA_ATTUATORE.compileInstance(): impossibile compilare oggetto non CategoriaAttuatore");
    }),
    /**
     * Entita' CategoriaSensore
     * @see CategoriaSensore
     */
    CATEGORIA_SENSORE((doc, param) -> {
        NodeList nList = doc.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_SENSORE);

        //controllo contenuto base
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0);

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element)nodo;
                String nome;
                String testoLibero;
                String infoRilev;

                //estrazione attrubuti
                if (el.hasAttribute(Costanti.NODO_XML_CATEGORIA_SENSORE_NOME)) {
                    nome = el.getAttribute(Costanti.NODO_XML_CATEGORIA_SENSORE_NOME);
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_SENSORE.getInstance(): attributo " + Costanti.NODO_XML_CATEGORIA_SENSORE_NOME + " assente.");

                //estrazione elementi
                NodeList childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO);
                if (childs.getLength() > 0) {
                    testoLibero = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_SENSORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO + " assente.");

                childs = el.getElementsByTagName(Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE);
                if (childs.getLength() > 0) {
                    infoRilev = childs.item(0).getTextContent();
                }
                else
                    throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_SENSORE.getInstance(): elemento " + Costanti.NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE + " assente.");

                //ritorno istanza corretta
                return new CategoriaSensore(nome, testoLibero, infoRilev);
            }
            else
                throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_SENSORE.getInstance(): nodo " + Costanti.NODO_XML_CATEGORIA_SENSORE + " non di tipo ELement.");
        }
        else
            throw new NoSuchElementException("TrasformatoriXML.CATEGORIA_SENSORE.getInstance(): documento non contiene elementi " + Costanti.NODO_XML_CATEGORIA_SENSORE);

    }, (obj, doc) -> {
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
            throw new IllegalArgumentException("TrasformatoriXML.CATEGORIA_SENSORE.compileInstance(): impossibile compilare oggetto non CategoriaSensore");
    });

    private interface IstanziatoreXML {
        /**
         * Ritorna l'istanza ottenuta dai dati contenuti nel documento, se quest'ultimo rispetta la struttura designata.
         * Assicurarsi di leggere il documento XML corretto per l'istanziatore utilizzato.
         *
         * @param doc   documento XML contenente i dati necessari all'istanziazione
         * @param param array di oggetti necessari per la buona riuscita della lettura dell'istanza
         * @return  Istanza dell'elemento
         * @throws IOException  Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
         * @throws ParserConfigurationException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
         * @throws TransformerConfigurationException    Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
         * @throws SAXException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
         */
        Object getInstance(Document doc, Object ...param) throws IOException, ParserConfigurationException, TransformerConfigurationException, SAXException;
    }
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

    private IstanziatoreXML istanziatore;
    private CompilatoreXML compilatore;

    private TrasformatoriXML(IstanziatoreXML istanziatore, CompilatoreXML compilatore) {
        this.istanziatore = istanziatore;
        this.compilatore = compilatore;
    }

    /**
     * Ritorna l'istanza ottenuta dai dati contenuti nel documento, se quest'ultimo rispetta la struttura designata.
     * Assicurarsi di leggere il documento XML corretto per l'istanziatore utilizzato.
     *
     * @param doc   documento XML contenente i dati necessari all'istanziazione
     * @param param array di oggetti necessari per la buona riuscita della lettura dell'istanza
     * @return  Istanza dell'elemento
     * @throws NullPointerException Eccezione scatenata dal passaggio di un riferimento a null per il documento.
     * @throws ParserConfigurationException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws SAXException Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws TransformerConfigurationException    Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     * @throws IOException  Eccezione scatenata dall'eventuale lettura di altri file XML collegati all'elemento.
     */
    public Object getInstance(Document doc, Object ...param) throws NullPointerException, ParserConfigurationException, SAXException, TransformerConfigurationException, IOException {
        if (doc == null)
            throw new NullPointerException(this.getClass().getName() + ": Document non puo' essere null");
        return istanziatore.getInstance(doc, param);
    }

    /**
     * Aggiunge in coda al documento passato l'elemento base contenente tutti i valori dell'istanza passata, codificati in una struttura XML.
     *
     * @param obj   istanza di cui eseguire la codifica
     * @param doc   istanza del documento cui appendere i dati codificati
     * @throws NullPointerException Eccezione scatenata dal passaggio di un riferimento a null per il documento o per l'oggetto da codificare.
     * @throws IllegalArgumentException Eccezione lanciata in caso l'istanza indicata non appartenga ad un tipo gestito dal compilatore.
     */
    public void appendiXML(Object obj, Document doc) throws NullPointerException, IllegalArgumentException, IOException, TransformerException, ParserConfigurationException {
        if (obj == null)
            throw new NullPointerException(this.getClass().getName() + ": Object non puo' essere null");
        if (doc == null)
            throw new NullPointerException(this.getClass().getName() + ": Document non puo' essere null");
        doc.appendChild(compilatore.compileInstance(obj, doc));
    }

}
