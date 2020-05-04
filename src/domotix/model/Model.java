package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.List;

public interface Model {
    List<UnitaImmobiliare> getListaUnita();
    Stanza getStanza(String stanza, String unita);
    List<CategoriaSensore> getCategorieSensore();
    List<CategoriaAttuatore> getCategorieAttuatore();
    Artefatto getArtefatto(String artefatto, String stanza, String unita);
    UnitaImmobiliare getUnita(String unita);
    SensoreOrologio getOrologio();
    Sensore getSensore(String sensore);
    Attuatore getAttuatore(String attuatore);
    Azione getAzioneProgrammata(String id);
    List<Azione> getAzioniProgrammate();
    CategoriaAttuatore getCategoriaAttuatore(String nomeCategoriaAttuatore);
    CategoriaSensore getCategoriaSensore(String nomeCategoriaSensore);
}
