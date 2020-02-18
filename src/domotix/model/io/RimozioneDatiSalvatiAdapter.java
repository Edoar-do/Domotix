package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;


/**
 * Classe che implementa l'interfaccia RimozioneDatiSalvati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public class RimozioneDatiSalvatiAdapter implements RimozioneDatiSalvati {
    @Override
    public void rimuoviCategoriaSensore(String cat) throws Exception {

    }

    @Override
    public void rimuoviCategoriaAttuatore(String cat) throws Exception {

    }

    @Override
    public void rimuoviModalita(String modalita, String cat) throws Exception {

    }

    @Override
    public void rimuoviUnitaImmobiliare(String unita) throws Exception {

    }

    @Override
    public void rimuoviStanza(String stanza, String unita) throws Exception {

    }

    @Override
    public void rimuoviArtefatto(String artefatto, String unita) throws Exception {

    }

    @Override
    public void rimuoviSensore(String sensore) throws Exception {

    }

    @Override
    public void rimuoviAttuatore(String attuatore) throws Exception {

    }
}
