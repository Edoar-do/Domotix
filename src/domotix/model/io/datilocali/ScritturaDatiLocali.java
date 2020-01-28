package domotix.model.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.ScritturaDatiSalvati;
import domotix.model.io.ScritturaDatiSalvatiAdapter;
import domotix.model.util.Costanti;
import org.w3c.dom.Document;

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
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;

/**
 * Classe che implementa l'interfaccia ScritturaDatiSalvati per definire un meccanismo di salvataggio dei dati su file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see ScritturaDatiSalvati
 */
public class ScritturaDatiLocali extends ScritturaDatiSalvatiAdapter {

    private static ScritturaDatiLocali _instance = null;

    public static ScritturaDatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new ScritturaDatiLocali();
        return _instance;
    }

    private DocumentBuilderFactory documentFactory = null;
    private DocumentBuilder documentBuilder = null;
    private TransformerFactory transformerFactory = null;
    private Transformer transformer = null;
    private HashMap<Class, ScrittoriXML> scrittori = null;

    private ScritturaDatiLocali() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        //test esistenza struttura dati
        PercorsiFile.getInstance().controllaStruttura();

        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();

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
            docFile.getParentFile().mkdirs();
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
        String path = PercorsiFile.getInstance().getPercorsoCategoriaSensore(cat.getNome());
        salva(path, cat);
    }

    @Override
    public void salva(CategoriaAttuatore cat) throws TransformerException, IOException, ParserConfigurationException {
        //salvo prima le entita' interne
        for (Modalita modalita : cat.getElencoModalita()) {
            salva(modalita, cat.getNome());
        }

        //salva la categoria
        String path = PercorsiFile.getInstance().getPercorsoCategoriaAttuatore(cat.getNome());
        salva(path, cat);
    }

    @Override
    public void salva(Modalita modalita, String cat) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getPercorsoModalita(modalita.getNome(), cat);
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
        String path = PercorsiFile.getInstance().getPercorsoUnitaImmobiliare(unita.getNome());
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
        String path = PercorsiFile.getInstance().getPercorsoStanza(stanza.getNome(), unita);
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
        String path = PercorsiFile.getInstance().getPercorsoArtefatto(artefatto.getNome(), unita);
        salva(path, artefatto);
    }

    @Override
    public void salva(Sensore sensore) throws TransformerException, IOException, ParserConfigurationException {
        String path = PercorsiFile.getInstance().getPercorsoSensore(sensore.getNome());
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