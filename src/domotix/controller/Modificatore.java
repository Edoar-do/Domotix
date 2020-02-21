package domotix.controller;

import domotix.logicUtil.StringUtil;
import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
/**
 * Classe per implementare una parte di logica controller relativa all'aggiunta e rimozione di entita'.
 * @author andrea e paolopasqua
 **/
public class Modificatore {

    public static boolean aggiungiCategoriaSensore(String nomeCat, String testoLibero, String infoRilevabile) {
        if (!Verificatore.checkValiditaCategoriaSensore(nomeCat)) return false;
        ElencoCategorieSensori.getInstance().add(new CategoriaSensore(nomeCat, testoLibero, infoRilevabile));
        return true;
    }

    public static boolean rimuoviCategoriaSensore(String cat) {
        if (Recuperatore.getCategoriaSensore(cat) == null) return false;
        ElencoCategorieSensori.getInstance().remove(cat);
        return true;
    }

    public static boolean aggiungiCategoriaAttuatore(String nomeCat, String testoLibero) {
        if (Verificatore.checkValiditaCategoriaAttuatore(nomeCat)) return false;
        ElencoCategorieAttuatori.getInstance().add(new CategoriaAttuatore(nomeCat, testoLibero));
        return true;
    }

    public static boolean aggiungiModalitaCategoriaAttuatore(String nomeCat, String nomeModalita) {
        if (!Verificatore.checkValiditaModalitaOperativa(nomeModalita)) return false;
        Recuperatore.getCategoriaAttuatore(nomeCat).addModalita(new Modalita(nomeModalita));
        return true;
    }

    public static boolean rimuoviCategoriaAttuatore(String cat) {
        if (Recuperatore.getCategoriaAttuatore(cat) == null) return false;
        ElencoCategorieAttuatori.getInstance().remove(cat);
        return true;
    }

    public static boolean aggiungiStanza(String nomeStanza, String unita) {
        if (Verificatore.checkValiditaStanza(nomeStanza, unita)) return false;
        Recuperatore.getUnita(unita).addStanza(new Stanza(nomeStanza));
        return true;
    }

    public static boolean rimuoviStanza(String stanza, String unita) {
        UnitaImmobiliare unitaImm = Recuperatore.getUnita(unita);
        if (unitaImm == null)
            return false;

        unitaImm.removeStanza(stanza);
        return true;
    }

    public static boolean aggiungiArtefatto(String artefatto, String stanza, String unita) {
        if (!Verificatore.checkValiditaArtefatto(artefatto, unita)) return false;
        Recuperatore.getStanza(stanza, unita).addArtefatto(new Artefatto(artefatto));
        return true;
    }

    public static boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        stanzaInst.removeArtefatto(artefatto);
        return true;
    }

    public static boolean aggiungiSensore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, stanza, unita)) return false;
        Recuperatore.getStanza(stanza, unita).addSensore(new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria)));
        return true;
    }

    public static boolean rimuoviSensore(String sensore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = ElencoSensori.getInstance().getDispositivo(sensore);
        if (sens == null)
            return false;

        stanzaInst.removeSensore(sens);
        return true;
    }

    public static boolean aggiungiSensore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Recuperatore.getArtefatto(artefatto, stanza, unita).addSensore(new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria)));
        return true;
    }

    public static boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = ElencoSensori.getInstance().getDispositivo(sensore);
        if (sens == null)
            return false;

        artefattoInst.removeSensore(sens);
        return true;
    }

    public static boolean aggiungiAttuatore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, stanza, unita)) return false;
        Recuperatore.getStanza(stanza, unita).addAttuatore(new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalita(0)));
        return true;
    }

    public static boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = ElencoAttuatori.getInstance().getDispositivo(attuatore);
        if (att == null)
            return false;

        stanzaInst.removeAttuatore(att);
        return true;
    }

    public static boolean aggiungiAttuatore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Recuperatore.getArtefatto(artefatto, stanza, unita).addAttuatore(new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalita(0)));
        return true;
    }

    public static boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = ElencoAttuatori.getInstance().getDispositivo(attuatore);
        if (att == null)
            return false;

        artefattoInst.removeAttuatore(att);
        return true;
    }
}
