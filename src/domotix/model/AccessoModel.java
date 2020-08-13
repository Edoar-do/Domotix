package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.util.ObserverList;

import java.util.Arrays;
import java.util.List;

/**
 * Implementazione dell'interfaccia Model.
 */
public class AccessoModel implements Model {
    private ElencoCategorieAttuatori elencoCategorieAttuatori;
    private ElencoAzioniProgrammate elencoAzioniProgrammate;
    private ElencoAttuatori elencoAttuatori;
    private ElencoUnitaImmobiliari elencoUnitaImmobiliari;
    private ElencoSensori elencoSensori;
    private ElencoCategorieSensori elencoCategorieSensori;
    private SensoreOrologio sensoreOrologio;

    public AccessoModel() {
        this.sensoreOrologio = new SensoreOrologio();
        this.sensoreOrologio.setStato(true);
        this.elencoCategorieSensori = new ElencoCategorieSensori();
        this.elencoSensori = new ElencoSensori(this.sensoreOrologio);
        this.elencoUnitaImmobiliari = new ElencoUnitaImmobiliari();
        this.elencoAttuatori = new ElencoAttuatori();
        this.elencoAzioniProgrammate = new ElencoAzioniProgrammate();
        this.elencoCategorieAttuatori = new ElencoCategorieAttuatori();
    }

    @Override
    public List<UnitaImmobiliare> getListaUnita() {
        return this.elencoUnitaImmobiliari.getUnita();
    }

    @Override
    public Stanza getStanza(String stanza, String unita) {
        for (Stanza stz : this.getUnita(unita).getStanze()) {
            if (stz.getNome().equals(stanza))
                return stz;
        }
        return null;
    }

    @Override
    public List<CategoriaSensore> getCategorieSensore() {
        return this.elencoCategorieSensori.getCategorie();
    }

    @Override
    public List<CategoriaAttuatore> getCategorieAttuatore() {
        return this.elencoCategorieAttuatori.getCategorie();
    }

    @Override
    public Artefatto getArtefatto(String artefatto, String stanza, String unita) {
        return this.getStanza(stanza, unita).getArtefatto(artefatto);
    }

    @Override
    public UnitaImmobiliare getUnita(String unita) {
        return this.elencoUnitaImmobiliari.getUnita(unita);
    }

    @Override
    public UnitaImmobiliare generaUnitaBase() {
        UnitaImmobiliare unita = new UnitaImmobiliare(UnitaImmobiliare.NOME_UNITA_DEFAULT);
        return unita;
    }

    @Override
    public SensoreOrologio getOrologio() {
        return this.sensoreOrologio;
    }

    @Override
    public Sensore getSensore(String sensore) {
        return this.elencoSensori.getDispositivo(sensore);
    }

    @Override
    public List<Sensore> getSensori() {
        return Arrays.asList(this.elencoSensori.getDispositivi());
    }

    @Override
    public Attuatore getAttuatore(String attuatore) {
        return this.elencoAttuatori.getDispositivo(attuatore);
    }

    @Override
    public List<Attuatore> getAttuatori() {
        return Arrays.asList(this.elencoAttuatori.getDispositivi());
    }

    @Override
    public Azione getAzioneProgrammata(String id) {
        return this.elencoAzioniProgrammate.getAzione(id);
    }

    @Override
    public List<String> getIdAzioniProgrammate() {
        return this.elencoAzioniProgrammate.getIdAzioni();
    }

    @Override
    public List<Azione> getAzioniProgrammate() {
        return this.elencoAzioniProgrammate.getAzioni();
    }

    @Override
    public CategoriaAttuatore getCategoriaAttuatore(String nomeCategoriaAttuatore) {
        return this.elencoCategorieAttuatori.getCategoria(nomeCategoriaAttuatore);
    }

    @Override
    public CategoriaSensore getCategoriaSensore(String nomeCategoriaSensore) {
        return this.elencoCategorieSensori.getCategoria(nomeCategoriaSensore);
    }

    @Override
    public void addUnita(UnitaImmobiliare unitaImmobiliare) {
        unitaImmobiliare.aggiungiOsservatoreListaSensori(this.elencoSensori);
        unitaImmobiliare.aggiungiOsservatoreListaAttuatori(this.elencoAttuatori);
        unitaImmobiliare.setElencoAzioni(this.elencoAzioniProgrammate);
        elencoUnitaImmobiliari.add(unitaImmobiliare);
    }

    @Override
    public void removeUnita(String unitaImmobiliare) {
        elencoUnitaImmobiliari.remove(unitaImmobiliare);
    }

    @Override
    public void addCategoriaSensore(CategoriaSensore cs) {
        elencoCategorieSensori.add(cs);
    }

    @Override
    public void removeCategoriaSensore(String cs) {
        elencoCategorieSensori.remove(cs);
    }

    @Override
    public void addCategoriaAttuatore(CategoriaAttuatore ca) {
        elencoCategorieAttuatori.add(ca);
    }

    @Override
    public void removeCategoriaAttuatore(String ca) {
        elencoCategorieAttuatori.remove(ca);
    }

    @Override
    public void removeAzioneProgrammata(String idAzione) {
        elencoAzioniProgrammate.remove(idAzione);
    }

    @Override
    public void addAzioneProgrammata(Azione a) {
        elencoAzioniProgrammate.add(a);
    }

    @Override
    public ObserverList<Dispositivo> getOsservatoreSensori() {
        return this.elencoSensori;
    }

    @Override
    public ObserverList<Dispositivo> getOsservatoreAttuatori() {
        return this.elencoAttuatori;
    }
}
