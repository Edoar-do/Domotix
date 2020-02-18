package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

/**
 * Classe per implementare una parte di logica controller relativa all'aggiunta e rimozione di entita'.
 * @author andrea e paolopasqua
 **/
public class Modificatore {

    public static boolean aggiungiCategoriaSensore(CategoriaSensore cat) {
        return false;
    }

    public static void rimuoviCategoriaSensore(String cat) {
        ElencoCategorieSensori.getInstance().remove(cat);
    }

    public static boolean aggiungiCategoriaAttuatore(CategoriaAttuatore cat) {
        return false;
    }

    public static void rimuoviCategoriaAttuatore(String cat) {
        ElencoCategorieAttuatori.getInstance().remove(cat);
    }

    public static boolean aggiungiStanza(Stanza stanza, String unita) {
        return false;
    }

    public static boolean rimuoviStanza(String stanza, String unita) {
        UnitaImmobiliare unitaImm = Recuperatore.getUnita(unita);
        if (unitaImm == null)
            return false;

        unitaImm.removeStanza(stanza);
        return true;
    }

    public static boolean aggiungiArtefatto(Artefatto artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        stanzaInst.removeArtefatto(artefatto);
        return true;
    }

    public static boolean aggiungiSensore(Sensore sensore, String stanza, String unita) {
        return false;
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

    public static boolean aggiungiSensore(Sensore sensore, String artefatto, String stanza, String unita) {
        return false;
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

    public static boolean aggiungiAttuatore(Attuatore attuatore, String stanza, String unita) {
        return false;
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

    public static boolean aggiungiAttuatore(Attuatore attuatore, String artefatto, String stanza, String unita) {
        return false;
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
