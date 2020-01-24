package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Classe che implementa l'interfaccia AccessoDatiSalvati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public abstract class AccessoDatiSalvatiAdapter implements AccessoDatiSalvati {
    @Override
    public List<CategoriaSensore> leggiCategorieSensori() throws Exception {
        return null;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        return null;
    }

    @Override
    public List<CategoriaAttuatore> leggiCategorieAttuatori() throws Exception {
        return null;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception {
        return null;
    }

    @Override
    public Modalita leggiModalita(String nome, String categoria) throws Exception {
        return null;
    }

    @Override
    public List<UnitaImmobiliare> leggiUnitaImmobiliare() throws Exception {
        return null;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception {
        return null;
    }

    @Override
    public Stanza leggiStanza(String nome, String unitaImmob) throws Exception {
        return null;
    }

    @Override
    public Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception {
        return null;
    }

    @Override
    public Sensore leggiSensore(String nome) throws Exception {
        return null;
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws Exception {
        return null;
    }

    @Override
    public void salva(CategoriaSensore cat) throws Exception {

    }

    @Override
    public void salva(CategoriaAttuatore cat) throws Exception {

    }

    @Override
    public void salva(Modalita modalita, String cat) throws Exception {

    }

    @Override
    public void salva(UnitaImmobiliare unita) throws Exception {

    }

    @Override
    public void salva(Stanza stanza, String unita) throws Exception {

    }

    @Override
    public void salva(Artefatto artefatto, String unita) throws Exception {

    }

    @Override
    public void salva(Sensore sensore) throws Exception {

    }

    @Override
    public void salva(Attuatore attuatore) throws Exception {

    }
}
