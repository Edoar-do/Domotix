package domotix.model.io.datilocali;

import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.util.Costanti;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.AccessoDatiSalvatiAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che implementa l'interfaccia AccessoDatiSalvati per definire un meccanismo di salvataggio e caricamento dei dati da file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.model.io.AccessoDatiSalvati
 */
public class DatiLocali extends AccessoDatiSalvatiAdapter {

    private static DatiLocali _instance = null;

    public static DatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new DatiLocali();
        return _instance;
    }

    private DocumentBuilderFactory documentFactory = null;
    private DocumentBuilder documentBuilder = null;
    private TransformerFactory transformerFactory = null;
    private Transformer transformer = null;
    private HashMap<String, LettoriXML> lettori = null;
    private HashMap<Class, ScrittoriXML> scrittori = null;

    private DatiLocali() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        //test esistenza struttura dati
        controllaCartella(Costanti.PERCORSO_CARTELLA_DATI);
        controllaCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI);
        controllaCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI);
        controllaCartella(Costanti.PERCORSO_CARTELLA_MODALITA);
        controllaCartella(Costanti.PERCORSO_CARTELLA_UNITA_IMMOB);
        controllaCartella(Costanti.PERCORSO_CARTELLA_STANZE);
        controllaCartella(Costanti.PERCORSO_CARTELLA_ARTEFATTI);
        controllaCartella(Costanti.PERCORSO_CARTELLA_SENSORI);
        controllaCartella(Costanti.PERCORSO_CARTELLA_ATTUATORI);

        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();

        //Popolo la tabella dei lettori
        lettori = new HashMap<>();
        lettori.put(Costanti.NODO_XML_ATTUATORE, LettoriXML.ATTUATORE);
        lettori.put(Costanti.NODO_XML_SENSORE, LettoriXML.SENSORE);
        lettori.put(Costanti.NODO_XML_ARTEFATTO, LettoriXML.ARTEFATTO);
        lettori.put(Costanti.NODO_XML_STANZA, LettoriXML.STANZA);
        lettori.put(Costanti.NODO_XML_UNITA_IMMOB, LettoriXML.UNITA_IMMOB);
        lettori.put(Costanti.NODO_XML_MODALITA, LettoriXML.MODALITA);
        lettori.put(Costanti.NODO_XML_CATEGORIA_ATTUATORE, LettoriXML.CATEGORIA_ATTUATORE);
        lettori.put(Costanti.NODO_XML_CATEGORIA_SENSORE, LettoriXML.CATEGORIA_SENSORE);

        //Popolo la tabella degli scrittori
        scrittori = new HashMap<>();
        scrittori.put(Attuatore.class, ScrittoriXML.ATTUATORE);
        scrittori.put(Sensore.class, ScrittoriXML.SENSORE);
        scrittori.put(Artefatto.class, ScrittoriXML.ARTEFATTO);
        scrittori.put(Stanza.class, ScrittoriXML.STANZA);
        scrittori.put(UnitaImmobiliare.class, ScrittoriXML.UNITA_IMMOB);
        scrittori.put(Modalita.class, ScrittoriXML.MODALITA);
        scrittori.put(CategoriaAttuatore.class, ScrittoriXML.CATEGORIA_ATTUATORE);
        scrittori.put(CategoriaSensore.class, ScrittoriXML.CATEGORIA_SENSORE);
    }

    private void controllaCartella(String percorso) throws NotDirectoryException {
        File cartella = new File(percorso);
        if (!cartella.exists()) {
            cartella.mkdirs();
        }
        if (!cartella.isDirectory()) {
            throw new NotDirectoryException(this.getClass().getName() + ": " + percorso + " esiste come file.");
        }
    }

    @Override
    public List<String> getNomiCategorieSensori() {
        return PercorsiFile.getInstance().getNomiCategorieSensori();
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
        String path = PercorsiFile.getInstance().getCategoriaSensore(nome);
        return (CategoriaSensore)leggi(path);
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
        String path = PercorsiFile.getInstance().getCategoriaAttuatore(nome);
        return (CategoriaAttuatore) leggi(path);
    }

    @Override
    public Modalita leggiModalita(String nome, String categoria) throws Exception {
        String path = PercorsiFile.getInstance().getModalita(nome, categoria);
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
        String path = PercorsiFile.getInstance().getUnitaImmobiliare(nome);
        return (UnitaImmobiliare) leggi(path);
    }

    @Override
    public Stanza leggiStanza(String nome, String unitaImmob) throws Exception {
        String path = PercorsiFile.getInstance().getStanza(nome, unitaImmob);
        return (Stanza) leggi(path);
    }

    @Override
    public Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception {
        String path = PercorsiFile.getInstance().getArtefatto(nome, unitaImmob);
        return (Artefatto) leggi(path);
    }

    @Override
    public Sensore leggiSensore(String nome) throws Exception {
        String path = PercorsiFile.getInstance().getSensore(nome);
        return (Sensore) leggi(path);
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws Exception {
        String path = PercorsiFile.getInstance().getAttuatore(nome);
        return (Attuatore) leggi(path);
    }

    private void salva(String path, Object obj) throws TransformerException, IOException, ParserConfigurationException {
        Document doc = documentBuilder.newDocument();
        File docFile = new File(path);

        //controlla il file
        if (docFile.exists()) {
            if (docFile.isDirectory())
                throw new FileSystemException(this.getClass().getName() + ": " + path + " esiste come cartella.");
            if (!docFile.canWrite() && !docFile.setWritable(true))
                throw new FileSystemException(this.getClass().getName() + ": " + path + " impossibile scrivere.");
        }
        else {
            docFile.createNewFile();
            docFile.setWritable(true);
            docFile.setReadable(true);
        }

        ScrittoriXML scritt = scrittori.get(obj.getClass());
        if (scritt == null) {
            throw new IllegalArgumentException(this.getClass().getName() + ": oggetto di tipo " + obj.getClass().getName() + " non gestito.");
        }

        //riempie il documento da scrivere
        scritt.appendiDocumento(obj, doc);

        //scrive
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(docFile);

        transformer.transform(domSource, streamResult);
    }

    @Override
    public void salva(CategoriaSensore cat) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getCategoriaSensore(cat.getNome());
        salva(path, cat);
    }

    @Override
    public void salva(CategoriaAttuatore cat) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Modalita modalita : cat.getElencoModalita()) {
            salva(modalita, cat.getNome());
        }

        //salva la categoria
        String path = PercorsiFile.getInstance().getCategoriaAttuatore(cat.getNome());
        salva(path, cat);
    }

    @Override
    public void salva(Modalita modalita, String cat) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getModalita(modalita.getNome(), cat);
        salva(path, modalita);
    }

    @Override
    public void salva(UnitaImmobiliare unita) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Stanza s : unita.getStanze()) {
            salva(s,unita.getNome());
        }
        /*
        NOTA BENE: i sensori e gli attuatori sono salvati a livello di stanze ed artefatti
                sebbene questo porti a una diminuzione delle prestazioni (un sensore/attuatore puo'
                essere associato a piu' stanze o artefatti in contemporanea e pertanto salvato piu'
                volte).
                Questa scelta e' pero' profittevole in senso di salvataggio di una sola stanza/artefatto.
                Infatti, in questo modo anche il salvataggio di una singola stanza/artefatto porta al salvataggio
                dei sensori da lei contenuti, senza dover salvare l'intera unita immobiliare.
         */

        //salva l'unita immobiliare
        String path = PercorsiFile.getInstance().getUnitaImmobiliare(unita.getNome());
        salva(path, unita);
    }

    @Override
    public void salva(Stanza stanza, String unita) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Sensore s : stanza.getSensori()) {
            salva(s);
        }
        for (Attuatore a : stanza.getAttuatori()) {
            salva(a);
        }
        for (Artefatto a : stanza.getArtefatti()) {
            salva(a, unita);
        }

        //salva l'unita immobiliare
        String path = PercorsiFile.getInstance().getStanza(stanza.getNome(), unita);
        salva(path, stanza);
    }

    @Override
    public void salva(Artefatto artefatto, String unita) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Sensore s : artefatto.getSensori()) {
            salva(s);
        }
        for (Attuatore a : artefatto.getAttuatori()) {
            salva(a);
        }

        //salva l'unita immobiliare
        String path = PercorsiFile.getInstance().getArtefatto(artefatto.getNome(), unita);
        salva(path, artefatto);
    }

    @Override
    public void salva(Sensore sensore) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getSensore(sensore.getNome());
        salva(path, sensore);
    }

    @Override
    public void salva(Attuatore attuatore) throws TransformerException, IOException, ParserConfigurationException {
        //Modalita' gia' salvata a livello di categoria.
        //In questo caso la modalita' DEVE esistere a livello di categoria per la corretta esecuzione
        //Pertanto e' necessario risalvare la categoria in caso di aggiunta di nuova modalita'

        String path = PercorsiFile.getInstance().getAttuatore(attuatore.getNome());
        salva(path, attuatore);
    }
}
