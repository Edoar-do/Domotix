package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.List;

/**
 * Classe che implementa l'interfaccia AccessoDatiSalvati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public abstract class LetturaDatiSalvatiAdapter implements LetturaDatiSalvati {
    @Override
    public List<String> getNomiCategorieSensori() {
        return null;
    }

    @Override
    public List<String> getNomiInformazioniRilevabili(String categoriaSensore) {
        return null;
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        return null;
    }

    @Override
    public List<String> getNomiModalita(String categoriaAttuatore) {
        return null;
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        return null;
    }

    @Override
    public List<String> getNomiStanze(String unitaImmobiliare) {
        return null;
    }

    @Override
    public List<String> getNomiArtefatti(String unitaImmobiliare) {
        return null;
    }

    @Override
    public List<String> getNomiSensori() {
        return null;
    }

    @Override
    public List<String> getNomiAttuatori() {
        return null;
    }

    @Override
    public List<CategoriaSensore> leggiCategorieSensori() throws Exception {
        return null;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        return null;
    }

    @Override
    public InfoRilevabile leggiInfoRilevabile(String nome, String categoria) throws Exception {
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
    public List<Sensore> leggiSensori() throws Exception {
        return null;
    }

    @Override
    public Sensore leggiSensore(String nome) throws Exception {
        return null;
    }

    @Override
    public List<Attuatore> leggiAttuatori() throws Exception {
        return null;
    }

    @Override
    public Attuatore leggiAttuatore(String nome) throws Exception {
        return null;
    }
}
