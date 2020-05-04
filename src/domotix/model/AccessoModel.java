package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.List;

public AccessoModel implements Model {
    private ElencoCategorieAttuatori elencoCategorieAttuatori; 
    private ElencoAzioniProgrammate elencoAzioniProgrammate; 
    private ElencoAttuatori elencoAttuatori; 
    private ElencoUnitaImmobiliari elencoUnitaImmobiliari; 
    private ElencoSensori elencoSensori; 
    private ElencoCategorieSensori elencoCategorieSensori; 
    private SensoreOrologio sensoreOrologio;

    public AccessoModel() {
        this.elencoCategorieSensori = new ElencoCategorieSensori();
        this.elencoSensori = new ElencoSensori();
        this.elencoUnitaImmobiliari = new ElencoUnitaImmobiliari();
        this.elencoAttuatori = new ElencoAttuatori();
        this.elencoAzioniProgrammate = new ElencoAzioniProgrammate();
        this.elencoCategorieAttuatori = new ElencoCategorieAttuatori();
        this.sensoreOrologio = new SensoreOrologio();
    }

    @Override
    public List<UnitaImmobiliare> getListaUnita() {
        return Arrays.asList(this.elencoUnitaImmobiliari.getUnita()); 
    }

    @Override
    public Stanza getStanza(String stanza, String unita) {
       return this.getUnita(unita).getStanza(stanza);
    }

    @Override
    public List<CategoriaSensore> getCategorieSensore() {
        return Arrays.asList(this.elencoCategorieSensori.elencoCategorieSensori.getCategorie());
    }

    @Override
    public List<CategoriaAttuatore> getCategorieAttuatore() {
        return Arrays.asList(this.elencoCategorieAttuatori.getCategorie());
    }

    @Override
    public Artefatto getArtefatto(String artefatto, String stanza, String unita) {
        return this.getStanza(unita, stanza).getArtefatto(artefatto);
    }

    @Override
    public UnitaImmobiliare getUnita(String unita) {
        return this.elencoUnitaImmobiliari.getUnita(unita);
    }

    @Override
    public SensoreOrologio getOrologio() {
        return this.sensoreOrologio;
    }

    @Override
    public Sensore getSensore(String sensore) {
        return this.elencoSensori.getSensore(sensore);
    }

    @Override
    public Attuatore getAttuatore(String attuatore) {
        return this.elencoAttuatori.getAttuatore(attuatore);
    }

    @Override
    public Azione getAzioneProgrammata(String id) {
        return Arrays.asList(this.elencoAzioniProgrammate.getAzioneProgrammata(id));
    }

    @Override
    public List<Azione> getAzioniProgrammate() {
        return Arrays.asList(this.elencoAzioniProgrammate.getAzioniProgrammate());
    }

    @Override
    public CategoriaAttuatore getCategoriaAttuatore(String nomeCategoriaAttuatore) {
        return this.elencoCategorieAttuatore.getCategoria(nomeCategoriaAttuatore);
    }

    @Override
    public CategoriaSensore getCategoriaSensore(String nomeCategoriaSensore) {
        return this.elencoCategorieSensore.getCategoria(nomeCategoriaSensore);
    }
}
