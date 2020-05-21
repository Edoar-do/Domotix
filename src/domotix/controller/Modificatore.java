package domotix.controller;


import domotix.controller.util.StringUtil;
import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class Modificatore {

    private Model model;
    private  Recuperatore recuperatore;
    private Verificatore verificatore;

    public Modificatore(Model mod, Recuperatore rec, Verificatore ver) {
        model = mod;
        recuperatore = rec;
        verificatore = ver;
    }

    
    public boolean aggiungiParametro(String nomeCategoria, String nomeModalita, Parametro p) {
        if (!verificatore.checkValiditaParametro(nomeCategoria, nomeModalita, p.getNome())) return false;
        CategoriaAttuatore categoria = recuperatore.getCategoriaAttuatore(nomeCategoria);
        Modalita modalita = categoria.getModalita(nomeModalita);
        modalita.addParametro(p);
        return true;
    }

    
    public boolean aggiungiUnitaImmobiliare(UnitaImmobiliare u) {
        if (!verificatore.checkValiditaUnitaImmobiliare(u.getNome())) return false;
        model.addUnita(u);
        return true;
    }

    
    public boolean rimuoviUnitaImmobiliare(String nomeUnita) {
        if (recuperatore.getUnita(nomeUnita) == null) return false;
        model.removeUnita(nomeUnita);
        return true;
    }

    public boolean setModalitaOperativa(String nomeAttuatore, String nomeModalita) {
        if (!verificatore.checkValiditaModalitaOperativaPerAttuatore(nomeAttuatore, nomeModalita)) return false;
        Attuatore attuatore = recuperatore.getAttuatore(nomeAttuatore);
        CategoriaAttuatore categoriaAttuatore = attuatore.getCategoria();
        attuatore.setModoOp(categoriaAttuatore.getModalita(nomeModalita));
        return true;
    }


    public boolean aggiungiInfoRilevabile(String nomeCat, InfoRilevabile ir) {
        if (!verificatore.checkValiditaInfoRilevabile(nomeCat, ir.getNome())) return false;
        return recuperatore.getCategoriaSensore(nomeCat).addInformazioneRilevabile(ir);
    }


    
    public boolean aggiungiCategoriaSensore(CategoriaSensore cs) {
        if (!verificatore.checkValiditaCategoriaSensore(cs.getNome())) return false;
        model.addCategoriaSensore(cs);
        return true;
    }

    
    public boolean rimuoviCategoriaSensore(String cat) {
        if (recuperatore.getCategoriaSensore(cat) == null) return false;
        model.removeCategoriaSensore(cat);
        return true;
    }

    
    public boolean aggiungiCategoriaAttuatore(CategoriaAttuatore ca) {
        if (!verificatore.checkValiditaCategoriaAttuatore(ca.getNome())) return false;
        model.addCategoriaAttuatore(ca);
        return true;
    }

    
    public boolean aggiungiModalitaCategoriaAttuatore(String nomeCat, Modalita mod) {
        if (!verificatore.checkValiditaModalitaOperativa(mod.getNome())) return false;
        recuperatore.getCategoriaAttuatore(nomeCat).addModalita(mod);
        return true;
    }

    
    public boolean rimuoviCategoriaAttuatore(String cat) {
        if (recuperatore.getCategoriaAttuatore(cat) == null) return false;
        model.removeCategoriaAttuatore(cat);
        return true;
    }
    
    public boolean aggiungiStanza(Stanza s) {
        if (!verificatore.checkValiditaStanza(s.getNome(), s.getUnitaOwner())) return false;
        recuperatore.getUnita(s.getUnitaOwner()).addStanza(s);
        return true;
    }

    
    public boolean rimuoviStanza(String stanza, String unita) {
        UnitaImmobiliare unitaImm = recuperatore.getUnita(unita);
        if (unitaImm == null)
            return false;

        unitaImm.removeStanza(stanza);
        return true;
    }

    
    public boolean aggiungiArtefatto(Artefatto a, String stanza, String unita) {
        if (!verificatore.checkValiditaArtefatto(a.getNome(), unita)) return false;
        recuperatore.getStanza(stanza, unita).addArtefatto(a);
        return true;
    }

    
    public boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        Stanza stanzaInst = recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        stanzaInst.removeArtefatto(artefatto);
        return true;
    }

    
    public boolean aggiungiSensore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!verificatore.checkValiditaSensore(nomeComposto, categoria, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        recuperatore.getStanza(stanza, unita).addSensore(sensore);
        return true;
    }

    
    public boolean collegaSensore(String nomeSensore, String nomeStanza, String nomeUnita) {
        Sensore sens = recuperatore.getSensore(nomeSensore);
        Stanza stanza = recuperatore.getStanza(nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (stanza == null)
            return false;

        stanza.addSensore(sens);
        return true;
    }

    
    public boolean rimuoviSensore(String sensore, String stanza, String unita) {
        Stanza stanzaInst = recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        stanzaInst.removeSensore(sens);
        return true;
    }

    
    public boolean aggiungiSensore(String fantasia, String categoria,  String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!verificatore.checkValiditaSensore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        recuperatore.getArtefatto(artefatto, stanza, unita).addSensore(sensore);
        return true;
    }

    
    public boolean collegaSensore(String nomeSensore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Sensore sens = recuperatore.getSensore(nomeSensore);
        Artefatto artefatto = recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (artefatto == null)
            return false;

        artefatto.addSensore(sens);
        return true;
    }

    
    public boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        artefattoInst.removeSensore(sens);
        return true;
    }

    
    public boolean aggiungiAttuatore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!verificatore.checkValiditaAttuatore(nomeComposto, categoria, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, recuperatore.getCategoriaAttuatore(categoria), recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        recuperatore.getStanza(stanza, unita).addAttuatore(attuatore);
        return true;
    }

    
    public boolean collegaAttuatore(String nomeAttuatore, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = recuperatore.getAttuatore(nomeAttuatore);
        Stanza stanza = recuperatore.getStanza(nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (stanza == null)
            return false;

        stanza.addAttuatore(attuatore);
        return true;
    }

    
    public boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        Stanza stanzaInst = recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        stanzaInst.removeAttuatore(att);
        return true;
    }

    
    public boolean aggiungiAttuatore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!verificatore.checkValiditaAttuatore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, recuperatore.getCategoriaAttuatore(categoria), recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        recuperatore.getArtefatto(artefatto, stanza, unita).addAttuatore(attuatore);
        return true;
    }

    
    public boolean collegaAttuatore(String nomeAttuatore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = recuperatore.getAttuatore(nomeAttuatore);
        Artefatto artefatto = recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (artefatto == null)
            return false;

        artefatto.addAttuatore(attuatore);
        return true;
    }

    
    public boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        artefattoInst.removeAttuatore(att);
        return true;
    }

    
    public String aggiungiRegola(Regola regola, String nomeUnita) {
        UnitaImmobiliare unita = recuperatore.getUnita(nomeUnita);
        if (unita == null) return null;
        unita.addRegola(regola);
        return regola.getId();
    }

    private InfoVariabile costruisciInfoDaSensore(String sinistroVar) {
        String[] campi = sinistroVar.split(Pattern.quote("."));
        String nomeSensore = campi[0];
        String nomeInfo = campi[1];
        Sensore sensore = recuperatore.getSensore(nomeSensore);
        InfoVariabile sinistro = new InfoVariabile(sensore, nomeInfo);
        return sinistro;
    }

    //logica if else fatta da me
    private boolean aggiungiComponenteCostanteAntecedente(String sinistroVar, String op, Object destroConst, String unita, String idRegola) {
        try {
            Regola regola = recuperatore.getUnita(unita).getRegola(idRegola);
            InfoVariabile sinistro = costruisciInfoDaSensore(sinistroVar);
            InfoSensoriale destro;
            if(verificatore.checkIsSensoreOrologio(sinistroVar)){
                    destro = new InfoTemporale(SensoreOrologio.getTempo((Double) destroConst));
            }else {
                destro = new InfoCostante(destroConst);
            }
            regola.addCondizone(new Condizione(sinistro, op, destro));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    
    public boolean aggiungiComponenteAntecedente(String sinistroVar, String op, double destroConst, String unita, String idRegola) {
        return aggiungiComponenteCostanteAntecedente(sinistroVar, op, destroConst, unita, idRegola);
    }

    
    public boolean aggiungiComponenteAntecedente(String sinistroVar, String op, String destro, boolean scalare, String unita, String idRegola) {
        try {
            if (scalare) {
                return aggiungiComponenteCostanteAntecedente(sinistroVar, op, destro, unita, idRegola);
            }
            Regola regola = recuperatore.getUnita(unita).getRegola(idRegola);
            InfoVariabile sx = costruisciInfoDaSensore(sinistroVar);
            InfoVariabile dx = costruisciInfoDaSensore(destro);
            regola.addCondizone(new Condizione(sx, op, dx));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    
    public boolean aggiungiOperatoreLogico(String unita, String regola, String op) {
        if (recuperatore.getUnita(unita) == null) return false;
        if (recuperatore.getUnita(unita).getRegola(regola) == null) return false;
        if (!verificatore.checkValiditaOperatoreLogico(op)) return false;
        recuperatore.getUnita(unita)
                .getRegola(regola)
                .addOperatore(op);
        return true;
    }

    
    public boolean rimuoviRegola(String unita, String idRegola) {
        UnitaImmobiliare unitaImm = recuperatore.getUnita(unita);
        if (unitaImm == null || unitaImm.getRegola(idRegola) == null) return false;
        unitaImm.removeRegola(idRegola);
        return true;
    }

    
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, Map<String, Double> listaParams, String unita, String idRegola) { //SENZA START
        if (recuperatore.getUnita(unita) == null) return false;
        if (recuperatore.getUnita(unita).getRegola(idRegola) == null) return false;
        if (recuperatore.getAttuatore(attuatore) == null) return false;
        if (!recuperatore.getAttuatore(attuatore).getCategoria().hasModalita(modalita)) return false;
        Attuatore att = recuperatore.getAttuatore(attuatore);
        Modalita mod = att.getCategoria().getModalita(modalita);
        List<Parametro> parametri = new ArrayList<>();
        listaParams.forEach((k, v) -> parametri.add(new Parametro(k, v)));
        recuperatore.getUnita(unita)
                .getRegola(idRegola)
                .addAzione(new Azione(att, mod, parametri));
        return true;
    }

    
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, String unita, String idRegola) { //SENZA START
        return aggiungiAzioneConseguente(attuatore, modalita, new HashMap<>(), unita, idRegola);
    }

    
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, Map<String, Double> listaParams, double orarioStart, String unita, String idRegola){ //CON START
        if (recuperatore.getUnita(unita) == null) return false;
        if (recuperatore.getUnita(unita).getRegola(idRegola) == null) return false;
        if (recuperatore.getAttuatore(attuatore) == null) return false;
        if (!recuperatore.getAttuatore(attuatore).getCategoria().hasModalita(modalita)) return false;
        //String orario = String.valueOf(orarioStart);
        Attuatore att = recuperatore.getAttuatore(attuatore);
        Modalita mod = att.getCategoria().getModalita(modalita);
        List<Parametro> parametri = new ArrayList<>();
        listaParams.forEach((k, v) -> parametri.add(new Parametro(k, v)));
        recuperatore.getUnita(unita)
                .getRegola(idRegola)
                .addAzione(new Azione(att, mod, parametri, SensoreOrologio.getTempo(orarioStart)));
        return true;
    }

    
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, double orarioStart, String unita, String idRegola){ //CON START
       return aggiungiAzioneConseguente(attuatore, modalita, new HashMap<>(), orarioStart, unita, idRegola);
    }

    
    public boolean cambiaStatoSensore(String sensore, String unita){
        Sensore s = recuperatore.getSensore(sensore);
        if(s == null) return false;
        boolean opposto = !s.getStato();
        s.setStato(opposto);
        //eventuale sospensione/attivazione regola
        UnitaImmobiliare u = recuperatore.getUnita(unita);
        Regola[] regole = u.getRegole();
        for (Regola r: regole) {
            if(r.contieneSensore(sensore)){//se la regola contiene il sensore a cui ho cambiato stato me ne curo altrimenti me ne frego e vado a quella dopo
                if(s.getStato() == false) {//sensore appena spento
                    if(!(r.getStato().equals(StatoRegola.DISATTIVA))) { //sospendo solo se attiva o sospesa. Se disattiva lascio com'è
                        r.setStato(StatoRegola.SOSPESA);
                    }
                }
                else { //sensore appena acceso - guardo se è possibile riattivare la regola
                   if(r.getStato().equals(StatoRegola.SOSPESA) && r.isAttivabile()) { r.setStato(StatoRegola.ATTIVA); }
                }
            }
        }
        //fine sospensione/attivazione
        return true;
    }

    
    public boolean cambiaStatoAttuatore(String attuatore, String unita){
        Attuatore a = recuperatore.getAttuatore(attuatore);
        if(a == null) return false;
        boolean opposto = !a.getStato();
        a.setStato(opposto);
        //eventuale sospensione/attivazione regola
        UnitaImmobiliare u = recuperatore.getUnita(unita);
        Regola[] regole = u.getRegole();
        for (Regola r: regole) {
            if(r.contieneAttuatore(attuatore)){//se la regola contiene l'attuatore a cui ho cambiato stato me ne curo altrimenti me ne frego e vado a quella dopo
                if(a.getStato() == false) {//attuatore appena spento
                    if(!(r.getStato().equals(StatoRegola.DISATTIVA))) { //sospendo solo se attiva o sospesa. Se disattiva lascio com'è
                        r.setStato(StatoRegola.SOSPESA);
                    }
                }
                else {//attuatore appena acceso - guardo se posso riattivare la regola
                    if (r.getStato().equals(StatoRegola.SOSPESA) && r.isAttivabile()){ r.setStato(StatoRegola.ATTIVA); }
                }
            }
        }
        //fine sospensione/attivazione
        return true;
    }


    
    public int cambioStatoRegola(String idRegola, String unita){
        Regola r = recuperatore.getUnita(unita).getRegola(idRegola);
        if(r == null)  return -1;
        StatoRegola s = r.getStato();
        if(s.name().equals("ATTIVA")){ s = StatoRegola.DISATTIVA; r.setStato(s); return 1; }
        else{
            if(r.isAttivabile()){ s = StatoRegola.ATTIVA; r.setStato(s); return 2; }
            else{ s = StatoRegola.SOSPESA; r.setStato(s); return 3; }
        }
    }

    
    public boolean rimuoviAzioneProgrammata(String idAzione, boolean esegui) {
        Azione a = recuperatore.getAzioneProgrammata(idAzione);
        if (a != null) {
            if (esegui)
                a.esegui();
            model.removeAzioneProgrammata(idAzione);
            return true;
        }
        return false;
    }


    public boolean aggiungiAzioneProgrammata(String s, Azione a) {
        this.model.addAzioneProgrammata(a);
        return true;
    }
}
