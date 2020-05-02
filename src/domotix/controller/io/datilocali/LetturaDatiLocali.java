package domotix.controller.io.datilocali;

import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.model.util.Costanti;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.controller.io.LetturaDatiSalvatiAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.nio.file.NotDirectoryException;
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

    private static LetturaDatiLocali _instance = null;

    public static LetturaDatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new LetturaDatiLocali();
        return _instance;
    }

    private DocumentBuilderFactory documentFactory = null;
    private DocumentBuilder documentBuilder = null;
    private HashMap<String, LettoriXML> lettori = null;

    private LetturaDatiLocali() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        //test esistenza struttura dati
        PercorsiFile.getInstance().controllaStruttura();

        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();

        //Popolo la tabella dei lettori
        lettori = new HashMap<>();
        lettori.put(Costanti.NODO_XML_AZIONE, LettoriXML.AZIONE);
        lettori.put(Costanti.NODO_XML_REGOLA, LettoriXML.REGOLA);
        lettori.put(Costanti.NODO_XML_ANTECEDENTE, LettoriXML.ANTECEDENTE);
        lettori.put(Costanti.NODO_XML_CONSEGUENTE, LettoriXML.CONSEGUENTE);
        lettori.put(Costanti.NODO_XML_ATTUATORE, LettoriXML.ATTUATORE);
        lettori.put(Costanti.NODO_XML_SENSORE, LettoriXML.SENSORE);
        lettori.put(Costanti.NODO_XML_ARTEFATTO, LettoriXML.ARTEFATTO);
        lettori.put(Costanti.NODO_XML_STANZA, LettoriXML.STANZA);
        lettori.put(Costanti.NODO_XML_UNITA_IMMOB, LettoriXML.UNITA_IMMOB);
        lettori.put(Costanti.NODO_XML_MODALITA, LettoriXML.MODALITA);
        lettori.put(Costanti.NODO_XML_CATEGORIA_ATTUATORE, LettoriXML.CATEGORIA_ATTUATORE);
        lettori.put(Costanti.NODO_XML_INFORILEVABILE, LettoriXML.INFORMAZIONE_RILEVABILE);
        lettori.put(Costanti.NODO_XML_CATEGORIA_SENSORE, LettoriXML.CATEGORIA_SENSORE);
    }

    @Override
    public List<String> getNomiCategorieSensori() {
        return PercorsiFile.getInstance().getNomiCategorieSensori();
    }

    @Override
    public List<String> getNomiInformazioniRilevabili(String categoriaSensore) {
        return PercorsiFile.getInstance().getNomiInformazioniRilevabili(categoriaSensore);
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        return PercorsiFile.getInstance().getNomiCategorieAttuatori();
    }

    @Override
    public List<String> getNomiModalita(String categoriaAttuatore) {
        return PercorsiFile.getInstance().getNomiModalita(categoriaAttuatore);
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        return PercorsiFile.getInstance().getNomiUnitaImmobiliare();
    }

    @Override
    public List<String> getNomiStanze(String unitaImmobiliare) {
        return PercorsiFile.getInstance().getNomiStanze(unitaImmobiliare);
    }

    @Override
    public List<String> getNomiArtefatti(String unitaImmobiliare) {
        return PercorsiFile.getInstance().getNomiArtefatti(unitaImmobiliare);
    }

    @Override
    public List<String> getNomiSensori() {
        return PercorsiFile.getInstance().getNomiSensori();
    }

    @Override
    public List<String> getNomiAttuatori() {
        return PercorsiFile.getInstance().getNomiAttuatori();
    }

    @Override
    public List<String> getNomiRegole(String unita) {
        return PercorsiFile.getInstance().getNomiRegola(unita);
    }

    @Override
    public List<String> getIdAzioniProgrammate() {
        return PercorsiFile.getInstance().getNomiAzioniProgramamte();
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

                LettoriXML lett = lettori.get(el.getTagName());
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
        String path = PercorsiFile.getInstance().getPercorsoCategoriaSensore(nome);
        return (CategoriaSensore)leggi(path);
    }

    @Override
    public InfoRilevabile leggiInfoRilevabile(String nome, String categoria) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoInformazioneRilevabile(nome, categoria);
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
        String path = PercorsiFile.getInstance().getPercorsoCategoriaAttuatore(nome);
        return (CategoriaAttuatore) leggi(path);
    }

    @Override
    public Modalita leggiModalita(String nome, String categoria) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoModalita(nome, categoria);
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
        String path = PercorsiFile.getInstance().getPercorsoUnitaImmobiliare(nome);
        return (UnitaImmobiliare) leggi(path);
    }

    @Override
    public Stanza leggiStanza(String nome, String unitaImmob) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoStanza(nome, unitaImmob);
        return (Stanza) leggi(path);
    }

    @Override
    public Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoArtefatto(nome, unitaImmob);
        return (Artefatto) leggi(path);
    }

    @Override
    public Sensore leggiSensore(String nome) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoSensore(nome);
        return (Sensore) leggi(path);
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws Exception {
        String path = PercorsiFile.getInstance().getAttuatore(nome);
        return (Attuatore) leggi(path);
    }

    @Override
    public Regola leggiRegola(String idRegola, String unita) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoRegola(idRegola, unita);
        return (Regola) leggi(path);
    }

    @Override
    public Azione leggiAzioneProgrammata(String id) throws Exception {
        String path = PercorsiFile.getInstance().getPercorsoAzioneProgrammabile(id);
        return (Azione) leggi(path);
    }
}
