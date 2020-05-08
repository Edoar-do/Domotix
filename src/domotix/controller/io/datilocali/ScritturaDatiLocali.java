package domotix.controller.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.visitor.Visitable;
import domotix.controller.io.ScritturaDatiSalvati;
import domotix.controller.io.ScritturaDatiSalvatiAdapter;
import domotix.controller.visitors.xml.VisitorXML;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;

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
    
    private VisitorXML traduttore = null;
    private TransformerFactory transformerFactory = null;
    private Transformer transformer = null;

    private PercorsiFile generatorePercorsi = null;
    
    public ScritturaDatiLocali(PercorsiFile generatorePercorsi, VisitorXML traduttore) throws TransformerConfigurationException {
        this.generatorePercorsi = generatorePercorsi;
        this.traduttore = traduttore;
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
    }

    private void salvaEntita(String path, Visitable obj) throws TransformerException, IOException {
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

        //scrive
        Document doc = (Document)this.traduttore.visita(obj);
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(docFile);

        transformer.transform(domSource, streamResult);
    }

    @Override
    public void salva(CategoriaSensore cat) throws TransformerException, IOException {
        //salvo prima le entita' interne
        for (InfoRilevabile info : cat.getInformazioniRilevabili()) {
            salva(info, cat.getNome());
        }

        String path = this.generatorePercorsi.getPercorsoCategoriaSensore(cat.getNome());
        salvaEntita(path, cat);
    }

    @Override
    public void salva(InfoRilevabile info, String cat) throws TransformerException, IOException {
        String path = this.generatorePercorsi.getPercorsoInformazioneRilevabile(info.getNome(), cat);
        salvaEntita(path, info);
    }

    @Override
    public void salva(CategoriaAttuatore cat) throws TransformerException, IOException {
        //salvo prima le entita' interne
        for (Modalita modalita : cat.getElencoModalita()) {
            salva(modalita, cat.getNome());
        }

        //salva la categoria
        String path = this.generatorePercorsi.getPercorsoCategoriaAttuatore(cat.getNome());
        salvaEntita(path, cat);
    }

    @Override
    public void salva(Modalita modalita, String cat) throws TransformerException, IOException {
        String path = this.generatorePercorsi.getPercorsoModalita(modalita.getNome(), cat);
        salvaEntita(path, modalita);
    }

    @Override
    public void salva(UnitaImmobiliare unita) throws TransformerException, IOException {
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
        for (Regola r : unita.getRegole()) {
            salva(r, unita.getNome());
        }

        //salva l'unita immobiliare
        String path = this.generatorePercorsi.getPercorsoUnitaImmobiliare(unita.getNome());
        salvaEntita(path, unita);
    }

    @Override
    public void salva(Stanza stanza, String unita) throws TransformerException, IOException {
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
        String path = this.generatorePercorsi.getPercorsoStanza(stanza.getNome(), unita);
        salvaEntita(path, stanza);
    }

    @Override
    public void salva(Artefatto artefatto, String unita) throws TransformerException, IOException {
        //salvo prima le entita' interne
        for (Sensore s : artefatto.getSensori()) {
            salva(s);
        }
        for (Attuatore a : artefatto.getAttuatori()) {
            salva(a);
        }

        //salva l'unita immobiliare
        String path = this.generatorePercorsi.getPercorsoArtefatto(artefatto.getNome(), unita);
        salvaEntita(path, artefatto);
    }

    @Override
    public void salva(Sensore sensore) throws TransformerException, IOException {
        String path = this.generatorePercorsi.getPercorsoSensore(sensore.getNome());
        salvaEntita(path, sensore);
    }

    @Override
    public void salva(Attuatore attuatore) throws TransformerException, IOException {
        //Modalita' gia' salvata a livello di categoria.
        //In questo caso la modalita' DEVE esistere a livello di categoria per la corretta esecuzione
        //Pertanto e' necessario risalvare la categoria in caso di aggiunta di nuova modalita'

        String path = this.generatorePercorsi.getAttuatore(attuatore.getNome());
        salvaEntita(path, attuatore);
    }

    @Override
    public void salva(Regola regola, String unita) throws TransformerException, IOException {
        String path = this.generatorePercorsi.getPercorsoRegola(regola.getId(), unita);
        salvaEntita(path, regola);
    }

    @Override
    public void salva(String id, Azione azione) throws TransformerException, IOException {
        String path = this.generatorePercorsi.getPercorsoAzioneProgrammabile(id);
        salvaEntita(path, azione);
    }
}
