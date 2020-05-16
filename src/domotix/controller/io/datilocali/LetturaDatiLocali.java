package domotix.controller.io.datilocali;

import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.controller.Recuperatore;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.controller.io.LetturaDatiSalvatiAdapter;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.controller.io.xml.istanziatori.AntecedenteXML;
import domotix.controller.io.xml.istanziatori.ArtefattoXML;
import domotix.controller.io.xml.istanziatori.AttuatoreXML;
import domotix.controller.io.xml.istanziatori.AzioneXML;
import domotix.controller.io.xml.istanziatori.CategoriaAttuatoreXML;
import domotix.controller.io.xml.istanziatori.CategoriaSensoreXML;
import domotix.controller.io.xml.istanziatori.ConseguenteXML;
import domotix.controller.io.xml.istanziatori.InfoRilevabileXML;
import domotix.controller.io.xml.istanziatori.ModalitaXML;
import domotix.controller.io.xml.istanziatori.RegolaXML;
import domotix.controller.io.xml.istanziatori.SensoreXML;
import domotix.controller.io.xml.istanziatori.StanzaXML;
import domotix.controller.io.xml.istanziatori.UnitaImmobiliareXML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che implementa l'interfaccia LetturaDatiSalvati per definire un meccanismo di caricamento dei dati da file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see LetturaDatiSalvati
 */
public class LetturaDatiLocali extends LetturaDatiSalvatiAdapter {

    private PercorsiFile generatorePercorsi = null;
    //private Recuperatore recuperatore = null;
    private DocumentBuilderFactory documentFactory = null;
    private DocumentBuilder documentBuilder = null;
    private HashMap<String, IstanziatoreXML<? extends Object>> lettori = null;

    public LetturaDatiLocali(PercorsiFile generatorePercorsi, Recuperatore recuperatore) throws ParserConfigurationException {
        
        this.generatorePercorsi = generatorePercorsi;
        //this.recuperatore = recuperatore;
        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();

        //Popolo la tabella dei lettori
        lettori = new HashMap<>();
        lettori.put(CostantiXML.NODO_XML_AZIONE, new AzioneXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_REGOLA, new RegolaXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_ANTECEDENTE, new AntecedenteXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_CONSEGUENTE, new ConseguenteXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_ATTUATORE, new AttuatoreXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_SENSORE, new SensoreXML(recuperatore));
        lettori.put(CostantiXML.NODO_XML_ARTEFATTO, new ArtefattoXML(recuperatore, this));
        lettori.put(CostantiXML.NODO_XML_STANZA, new StanzaXML(recuperatore, this));
        lettori.put(CostantiXML.NODO_XML_UNITA_IMMOB, new UnitaImmobiliareXML(recuperatore, this));
        lettori.put(CostantiXML.NODO_XML_MODALITA, new ModalitaXML());
        lettori.put(CostantiXML.NODO_XML_CATEGORIA_ATTUATORE, new CategoriaAttuatoreXML(this));
        lettori.put(CostantiXML.NODO_XML_INFORILEVABILE, new InfoRilevabileXML());
        lettori.put(CostantiXML.NODO_XML_CATEGORIA_SENSORE, new CategoriaSensoreXML(this));
    }

    @Override
    public List<String> getNomiCategorieSensori() {
        return this.generatorePercorsi.getNomiCategorieSensori();
    }

    @Override
    public List<String> getNomiInformazioniRilevabili(String categoriaSensore) {
        return this.generatorePercorsi.getNomiInformazioniRilevabili(categoriaSensore);
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        return this.generatorePercorsi.getNomiCategorieAttuatori();
    }

    @Override
    public List<String> getNomiModalita(String categoriaAttuatore) {
        return this.generatorePercorsi.getNomiModalita(categoriaAttuatore);
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        return this.generatorePercorsi.getNomiUnitaImmobiliare();
    }

    @Override
    public List<String> getNomiStanze(String unitaImmobiliare) {
        return this.generatorePercorsi.getNomiStanze(unitaImmobiliare);
    }

    @Override
    public List<String> getNomiArtefatti(String unitaImmobiliare) {
        return this.generatorePercorsi.getNomiArtefatti(unitaImmobiliare);
    }

    @Override
    public List<String> getNomiSensori() {
        return this.generatorePercorsi.getNomiSensori();
    }

    @Override
    public List<String> getNomiAttuatori() {
        return this.generatorePercorsi.getNomiAttuatori();
    }

    @Override
    public List<String> getNomiRegole(String unita) {
        return this.generatorePercorsi.getNomiRegola(unita);
    }

    @Override
    public List<String> getIdAzioniProgrammate() {
        return this.generatorePercorsi.getNomiAzioniProgramamte();
    }

    private Object leggi(String path) throws Exception {
        File input = new File(path);

        //controlla il file
        if (input.exists()) {
            if (input.isDirectory())
                throw new FileSystemException(this.getClass().getName() + ": " + path + " esiste come cartella.");
            if (!input.canRead() && !input.setReadable(true))
                throw new FileSystemException(this.getClass().getName() + ": " + path + " impossibile leggere.");
        }
        else {
            throw new FileNotFoundException(this.getClass().getName() + ": " + path + " non esiste.");
        }

        //legge e parsa il file
        Document doc = documentBuilder.parse(input);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getChildNodes();;

        //elaboro contenuto
        if (nList.getLength() > 0) {
            Node nodo = nList.item(0); //i documenti contengono un elemento ciascuno

            //controllo tipo contenuto
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodo;

                IstanziatoreXML<? extends Object> lett = lettori.get(el.getTagName());
                if (lett == null) {
                    throw new IllegalArgumentException(this.getClass().getName() + ": elemento XML " + el.getTagName() + " non gestito.");
                }

                return lett.getInstance(el);
            }
        }

        return null;
    }

    @Override
    public List<CategoriaSensore> leggiCategorieSensori() throws Exception {
        ArrayList<CategoriaSensore> catSens = new ArrayList<>();

        for (String s : getNomiCategorieSensori()) {
            catSens.add(leggiCategoriaSensore(s));
        }

        return catSens;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        String path = this.generatorePercorsi.getPercorsoCategoriaSensore(nome);
        return (CategoriaSensore)leggi(path);
    }

    @Override
    public InfoRilevabile leggiInfoRilevabile(String nome, String categoria) throws Exception {
        String path = this.generatorePercorsi.getPercorsoInformazioneRilevabile(nome, categoria);
        return (InfoRilevabile)leggi(path);
    }

    @Override
    public List<CategoriaAttuatore> leggiCategorieAttuatori() throws Exception {
        ArrayList<CategoriaAttuatore> catAtt = new ArrayList<>();

        for (String s : getNomiCategorieAttuatori()) {
            catAtt.add(leggiCategoriaAttuatore(s));
        }

        return catAtt;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception {
        String path = this.generatorePercorsi.getPercorsoCategoriaAttuatore(nome);
        return (CategoriaAttuatore) leggi(path);
    }

    @Override
    public Modalita leggiModalita(String nome, String categoria) throws Exception {
        String path = this.generatorePercorsi.getPercorsoModalita(nome, categoria);
        return (Modalita) leggi(path);
    }

    @Override
    public List<UnitaImmobiliare> leggiUnitaImmobiliare() throws Exception {
        ArrayList<UnitaImmobiliare> unita = new ArrayList<>();

        for (String s : getNomiUnitaImmobiliare()) {
            unita.add(leggiUnitaImmobiliare(s));
        }

        return unita;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception {
        String path = this.generatorePercorsi.getPercorsoUnitaImmobiliare(nome);
        return (UnitaImmobiliare) leggi(path);
    }

    @Override
    public Stanza leggiStanza(String nome, String unitaImmob) throws Exception {
        String path = this.generatorePercorsi.getPercorsoStanza(nome, unitaImmob);
        return (Stanza) leggi(path);
    }

    @Override
    public Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception {
        String path = this.generatorePercorsi.getPercorsoArtefatto(nome, unitaImmob);
        return (Artefatto) leggi(path);
    }

    @Override
    public Sensore leggiSensore(String nome) throws Exception {
        String path = this.generatorePercorsi.getPercorsoSensore(nome);
        return (Sensore) leggi(path);
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws Exception {
        String path = this.generatorePercorsi.getAttuatore(nome);
        return (Attuatore) leggi(path);
    }

    @Override
    public Regola leggiRegola(String idRegola, String unita) throws Exception {
        String path = this.generatorePercorsi.getPercorsoRegola(idRegola, unita);
        return (Regola) leggi(path);
    }

    @Override
    public Azione leggiAzioneProgrammata(String id) throws Exception {
        String path = this.generatorePercorsi.getPercorsoAzioneProgrammabile(id);
        return (Azione) leggi(path);
    }
}
