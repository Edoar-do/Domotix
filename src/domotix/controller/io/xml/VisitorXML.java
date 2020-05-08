package domotix.controller.io.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import domotix.controller.io.xml.compilatori.AntecedenteXML;
import domotix.controller.io.xml.compilatori.ArtefattoXML;
import domotix.controller.io.xml.compilatori.AttuatoreXML;
import domotix.controller.io.xml.compilatori.AzioneXML;
import domotix.controller.io.xml.compilatori.CategoriaAttuatoreXML;
import domotix.controller.io.xml.compilatori.CategoriaSensoreXML;
import domotix.controller.io.xml.compilatori.ConseguenteXML;
import domotix.controller.io.xml.compilatori.InfoRilevabileXML;
import domotix.controller.io.xml.compilatori.ModalitaXML;
import domotix.controller.io.xml.compilatori.RegolaXML;
import domotix.controller.io.xml.compilatori.SensoreXML;
import domotix.controller.io.xml.compilatori.StanzaXML;
import domotix.controller.io.xml.compilatori.UnitaImmobiliareXML;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.InfoRilevabile;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.regole.Antecedente;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Conseguente;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.visitor.AbstractVisitor;
import domotix.model.visitor.Visitable;

/**
 * Classe per contenere tutti i compilatori in XML delle entita' presenti nel programma.
 * Questi elementi vengono sfruttati per il salvataggio su file nella classe DatiLocali.
 * Si intende creare un punto comune per facilitare l'aggiunta, la modifica o la rimozione di un'entita'.
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public class VisitorXML extends AbstractVisitor {

    private DocumentBuilderFactory documentFactory = null;
    private DocumentBuilder documentBuilder = null;
    private Map<Class<? extends Visitable>, CompilatoreXML<? extends Visitable>> compilatori = null;

    public VisitorXML() throws ParserConfigurationException {
        compilatori = new HashMap<>();

        compilatori.put(Azione.class, new AzioneXML());
        compilatori.put(Regola.class, new RegolaXML());
        compilatori.put(Antecedente.class, new AntecedenteXML());
        compilatori.put(Conseguente.class, new ConseguenteXML());
        compilatori.put(Attuatore.class, new AttuatoreXML());
        compilatori.put(Sensore.class, new SensoreXML());
        compilatori.put(Artefatto.class, new ArtefattoXML());
        compilatori.put(Stanza.class, new StanzaXML());
        compilatori.put(UnitaImmobiliare.class, new UnitaImmobiliareXML());
        compilatori.put(Modalita.class, new ModalitaXML());
        compilatori.put(CategoriaAttuatore.class, new CategoriaAttuatoreXML());
        compilatori.put(InfoRilevabile.class, new InfoRilevabileXML());
        compilatori.put(CategoriaSensore.class, new CategoriaSensoreXML());

        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();
    }

    @Override
    public Object visita(Visitable visitable) {
        CompilatoreXML compilatore = this.compilatori.get(visitable.getClass());
        
        if (compilatore == null)
            return null;

        Document doc = documentBuilder.newDocument();
        doc.appendChild(compilatore.compileInstance(visitable, doc));

        return doc;
    }

    @Override
    public Object visitaAntecedente(Visitable antecedente) {
        return visita(antecedente);
    }

    @Override
    public Object visitaArtefatto(Visitable artefatto) {
        return visita(artefatto);
    }

    @Override
    public Object visitaAttuatore(Visitable attuatore) {
        return visita(attuatore);
    }

    @Override
    public Object visitaAzione(Visitable azione) {
        return visita(azione);
    }

    @Override
    public Object visitaCategoriaAttuatore(Visitable categoriaAttuatori) {
        return visita(categoriaAttuatori);
    }

    @Override
    public Object visitaCategoriaSensore(Visitable categoriaSensori) {
        return visita(categoriaSensori);
    }

    @Override
    public Object visitaCondizione(Visitable condizione) {
        return visita(condizione);
    }

    @Override
    public Object visitaConseguente(Visitable conseguente) {
        return visita(conseguente);
    }
    
    @Override
    public Object visitaInfoSensoriale(Visitable infoSensoriale) {
        return visita(infoSensoriale);
    }

    @Override
    public Object visitaInfoCostante(Visitable infoCostante) {
        return visitaInfoSensoriale(infoCostante);
    }
    
    @Override
    public Object visitaInfoTemporale(Visitable infoTemporale) {
        return visitaInfoSensoriale(infoTemporale);
    }
    
    @Override
    public Object visitaInfoVariabile(Visitable infoVariabile) {
        return visitaInfoSensoriale(infoVariabile);
    }
    
    @Override
    public Object visitaInfoRilevabile(Visitable infoRilevabile) {
        return visita(infoRilevabile);
    }
    
    @Override
    public Object visitaModalita(Visitable modalita) {
        return visita(modalita);
    }

    @Override
    public Object visitaParametro(Visitable parametro) {
        return visita(parametro);
    }

    @Override
    public Object visitaRegola(Visitable regola) {
        return visita(regola);
    }

    @Override
    public Object visitaSensore(Visitable sensore) {
        return visita(sensore);
    }

    @Override
    public Object visitaStanza(Visitable stanza) {
        return visita(stanza);
    }

    @Override
    public Object visitaUnitaImmobiliare(Visitable unitaImmobiliare) {
        return visita(unitaImmobiliare);
    }

}