package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.List;
import java.util.Map;


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
    public void rimuoviInfoRilevabile(String info, String cat) throws Exception {

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

    @Override
    public void rimuoviRegola(String idRegola, String unita) throws Exception {

    }

    @Override
    public void rimuoviAzioneProgrammata(String id) throws Exception {

    }

    @Override
    public void sincronizzaCategorieSensore(List<CategoriaSensore> entita) throws Exception {

    }

    @Override
    public void sincronizzaInfoRilevabile(CategoriaSensore entita) throws Exception {

    }

    @Override
    public void sincronizzaCategorieAttuatore(List<CategoriaAttuatore> entita) throws Exception {

    }

    @Override
    public void sincronizzaModalita(CategoriaAttuatore entita) throws Exception {

    }

    @Override
    public void sincronizzaUnitaImmobiliari(List<UnitaImmobiliare> entita) throws Exception {

    }

    @Override
    public void sincronizzaStanze(UnitaImmobiliare entita) throws Exception {

    }

    @Override
    public void sincronizzaArtefatti(UnitaImmobiliare entita) throws Exception {

    }

    @Override
    public void sincronizzaSensori(List<Sensore> entita) throws Exception {

    }

    @Override
    public void sincronizzaAttuatori(List<Attuatore> entita) throws Exception {

    }

    @Override
    public void sincronizzaRegole(UnitaImmobiliare entita) throws Exception {

    }

    @Override
    public void sincronizzaAzioniProgrammate(Map<String, Azione> entita) throws Exception {

    }
}
