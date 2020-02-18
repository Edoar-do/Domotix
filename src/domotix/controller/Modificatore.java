package domotix.controller;

import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

/**@author andrea e paolopasqua*/
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
        UnitaImmobiliare unitaImm = ElencoUnitaImmobiliari.getInstance().getUnita(unita);
        if (unitaImm == null)
            return false;

        unitaImm.removeStanza(stanza);
        return true;
    }

    public static boolean aggiungiArtefatto(Artefatto artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean aggiungiSensore(Sensore sensore, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviSensore(String sensore, String stanza, String unita) {
        return false;
    }

    public static boolean aggiungiSensore(Sensore sensore, String artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean aggiungiAttuatore(Attuatore attuatore, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        return false;
    }

    public static boolean aggiungiAttuatore(Attuatore attuatore, String artefatto, String stanza, String unita) {
        return false;
    }

    public static boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        return false;
    }
}
