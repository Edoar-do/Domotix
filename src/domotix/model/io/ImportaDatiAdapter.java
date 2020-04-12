package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;

import java.util.List;

/**
 * Classe che implementa l'interfaccia ImportaDati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public class ImportaDatiAdapter implements ImportaDati {
    @Override
    public List<String> getNomiCategorieSensori() {
        return null;
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        return null;
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        return null;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        return null;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception {
        return null;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception {
        return null;
    }

    @Override
    public boolean storicizzaCategoriaSensore(String nome) throws Exception {
        return false;
    }

    @Override
    public boolean storicizzaCategoriaAttuatore(String nome) throws Exception {
        return false;
    }

    @Override
    public boolean storicizzaUnitaImmobiliare(String nome) throws Exception {
        return false;
    }
}
