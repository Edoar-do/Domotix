package domotix.model.io.datilocali;

import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.util.Costanti;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.AccessoDatiSalvatiAdapter;
import org.w3c.dom.Document;
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
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
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

    private static final String NOME_GENERICO_ENTITA = ".*";

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

    private Object leggi(String path, TrasformatoriXML trasformatore, Object ...param) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
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

        //genera e ritorna l'istanza
        return trasformatore.getInstance(doc, param);
    }

    @Override
    public List<CategoriaSensore> leggiCategorieSensori() throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        ArrayList<CategoriaSensore> catSens = new ArrayList<>();

        File cartella = new File(Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI);
        for(File f : cartella.listFiles()) {
            catSens.add(leggiCategoriaSensore(f.getName()));
        }

        return catSens;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getCategoriaSensore(nome);
        CategoriaSensore cat = (CategoriaSensore)leggi(path, TrasformatoriXML.CATEGORIA_SENSORE);
        return cat;
    }

    @Override
    public List<CategoriaAttuatore> leggiCategorieAttuatori() throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        ArrayList<CategoriaAttuatore> catAtt = new ArrayList<>();

        File cartella = new File(Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI);
        for(File f : cartella.listFiles()) {
            catAtt.add(leggiCategoriaAttuatore(f.getName()));
        }

        return catAtt;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getCategoriaAttuatore(nome);
        CategoriaAttuatore cat = (CategoriaAttuatore) leggi(path, TrasformatoriXML.CATEGORIA_ATTUATORE);

        //Aggiunta delle Modalita relative
        File cartellaModalita = new File(Costanti.PERCORSO_CARTELLA_MODALITA);
        File[] modalitaCategoria = cartellaModalita.listFiles((dir, name) -> name.matches(PercorsiFile.getInstance().getNomeModalita(NOME_GENERICO_ENTITA, cat.getNome())));
        for (File f : modalitaCategoria) {
            Modalita m = leggiModalita(f.getPath());
            cat.addModalita(m);
        }

        return cat;
    }

    @Override
    public Modalita leggiModalita(String nome, String categoria) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getModalita(nome, categoria);
        return leggiModalita(path);
    }

    public Modalita leggiModalita(String path) throws SAXException, ParserConfigurationException, TransformerConfigurationException, IOException {
        return (Modalita) leggi(path, TrasformatoriXML.MODALITA);
    }

    @Override
    public List<UnitaImmobiliare> leggiUnitaImmobiliare() throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        ArrayList<UnitaImmobiliare> unita = new ArrayList<>();

        File cartella = new File(Costanti.PERCORSO_CARTELLA_UNITA_IMMOB);
        for(File f : cartella.listFiles()) {
            unita.add(leggiUnitaImmobiliare(f.getName()));
        }

        return unita;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getUnitaImmobiliare(nome);
        UnitaImmobiliare unita = (UnitaImmobiliare) leggi(path, TrasformatoriXML.UNITA_IMMOB);

        //Aggiunta delle Stanze relative
        File cartellaModalita = new File(Costanti.PERCORSO_CARTELLA_STANZE);
        File[] stanzeUnita = cartellaModalita.listFiles((dir, name) -> name.matches(PercorsiFile.getInstance().getNomeStanza(NOME_GENERICO_ENTITA, unita.getNome())));
        for (File f : stanzeUnita) {
            Stanza s = leggiStanza(f.getPath(), unita);
            if (s.getNome().equals(UnitaImmobiliare.NOME_STANZA_DEFAULT))
                unita.setStanzaDefault(s);
            else
                unita.addStanza(s);
        }

        return unita;
    }

    @Override
    public Stanza leggiStanza(String nome, String unitaImmob) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getStanza(nome, unitaImmob);
        return leggiStanza(path, ElencoUnitaImmobiliari.getInstance().getUnita(unitaImmob));
    }

    public Stanza leggiStanza(String path, UnitaImmobiliare unita) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        return (Stanza) leggi(path, TrasformatoriXML.STANZA, unita);
    }

    @Override
    public Artefatto leggiArtefatto(String nome, String unitaImmob) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getArtefatto(nome, unitaImmob);
        return leggiArtefatto(path, ElencoUnitaImmobiliari.getInstance().getUnita(unitaImmob));
    }

    public Artefatto leggiArtefatto(String path, UnitaImmobiliare unita) throws SAXException, ParserConfigurationException, TransformerConfigurationException, IOException {
        return (Artefatto) leggi(path, TrasformatoriXML.ARTEFATTO, unita);
    }

    @Override
    public Sensore leggiSensore(String nome) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getSensore(nome);
        return (Sensore) leggi(path, TrasformatoriXML.SENSORE);
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException {
        String path = PercorsiFile.getInstance().getAttuatore(nome);
        return (Attuatore) leggi(path, TrasformatoriXML.ATTUATORE);
    }

    private void salva(String path, Object obj, TrasformatoriXML trasformatore) throws TransformerException, IOException, ParserConfigurationException {
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

        //riempie il documento da scrivere
        trasformatore.appendiXML(obj, doc);

        //scrive
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(docFile);

        transformer.transform(domSource, streamResult);
    }

    @Override
    public void salva(CategoriaSensore cat) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getCategoriaSensore(cat.getNome());
        salva(path, cat, TrasformatoriXML.CATEGORIA_SENSORE);
    }

    @Override
    public void salva(CategoriaAttuatore cat) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Modalita modalita : cat.getElencoModalita()) {
            salva(modalita, cat.getNome());
        }

        //salva la categoria
        String path = PercorsiFile.getInstance().getCategoriaAttuatore(cat.getNome());
        salva(path, cat, TrasformatoriXML.CATEGORIA_ATTUATORE);
    }

    @Override
    public void salva(Modalita modalita, String cat) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getModalita(modalita.getNome(), cat);
        salva(path, modalita, TrasformatoriXML.MODALITA);
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
        salva(path, unita, TrasformatoriXML.UNITA_IMMOB);
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
        salva(path, stanza, TrasformatoriXML.STANZA);
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
        salva(path, artefatto, TrasformatoriXML.ARTEFATTO);
    }

    @Override
    public void salva(Sensore sensore) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getSensore(sensore.getNome());
        salva(path, sensore, TrasformatoriXML.SENSORE);
    }

    @Override
    public void salva(Attuatore attuatore) throws TransformerException, IOException, ParserConfigurationException {
        //Modalita' gia' salvata a livello di categoria.
        //In questo caso la modalita' DEVE esistere a livello di categoria per la corretta esecuzione
        //Pertanto e' necessario risalvare la categoria in caso di aggiunta di nuova modalita'

        String path = PercorsiFile.getInstance().getAttuatore(attuatore.getNome());
        salva(path, attuatore, TrasformatoriXML.ATTUATORE);
    }
}
