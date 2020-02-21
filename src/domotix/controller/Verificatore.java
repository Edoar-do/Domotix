package domotix.controller;

import domotix.logicUtil.StringUtil;
import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

/**@author andrea*/
public class Verificatore {
    private static final String REGEX_NOMI = "[A-Za-z][A-Za-z0-9]*";
    private static final String REGEX_NOMI_DISPOSITIVI = REGEX_NOMI + "_" + REGEX_NOMI;

    private static boolean isNomeValido(String nome) {
        return nome.matches("^(" + REGEX_NOMI + ")$");
    }

    private static boolean isNomeDispositivoValido(String nome) {
        return nome.matches("^(" + REGEX_NOMI_DISPOSITIVI + ")$");
    }

    public static boolean checkValiditaModalitaOperativa(String nome) {
        return isNomeValido(nome);
    }

    public static boolean checkValiditaCategoriaSensore(String nome) {
        return isNomeValido(nome) &&
                !ElencoCategorieSensori.getInstance().contains(nome);
    }

    public static boolean checkValiditaCategoriaAttuatore(String nome) {
        return isNomeValido(nome) &&
                !ElencoCategorieAttuatori.getInstance().contains(nome);
    }

    public static boolean checkValiditaStanza(String nomeStanza, String nomeUnita) {
        return isNomeValido(nomeStanza) &&
                !nomeStanza.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT) &&
                Recuperatore.getUnita(nomeUnita) != null &&
                Recuperatore.getStanza(nomeStanza, nomeUnita) == null;
    }

    public static boolean checkValiditaArtefatto(String nomeArtefatto, String nomeUnita) {
        if (!isNomeValido(nomeArtefatto)) return false;
        if (Recuperatore.getUnita(nomeUnita) == null) return false;
        for (Stanza s : Recuperatore.getUnita(nomeUnita).getStanze()) {
            for (Artefatto a : s.getArtefatti()) {
                if (a.getNome().equals(nomeArtefatto)) return false;
            }
        }
        return true;
    }

    public static boolean checkUnivocitaSensore(String nomeSensore) {
        for (Sensore s : ElencoSensori.getInstance().getDispositivi()) {
            if (s.getNome().equals(nomeSensore)) return false;
        }
        return true;
    }

    public static boolean checkUnivocitaAttuatore(String nomeAttuatore) {
        for (Attuatore a : ElencoAttuatori.getInstance().getDispositivi()) {
            if (a.getNome().equals(nomeAttuatore)) return false;
        }
        return true;
    }

    public static boolean checkValiditaSensore(String nomeComposto, String nomeCategoria, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
                checkUnivocitaSensore(nomeComposto) &&
                Recuperatore.getUnita(nomeUnita) != null &&
                Recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
                !Recuperatore.getStanza(nomeStanza, nomeUnita).contieneCategoriaSensore(nomeCategoria);
    }

    public static boolean checkValiditaAttuatore(String nomeComposto, String nomeCategoria, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
                checkUnivocitaAttuatore(nomeComposto) &&
                Recuperatore.getUnita(nomeUnita) != null &&
                Recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
                !Recuperatore.getStanza(nomeStanza, nomeUnita).contieneCategoriaAttuatore(nomeCategoria);
    }

    public static boolean checkValiditaSensore(String nomeComposto, String nomeCategoria, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
                checkUnivocitaSensore(nomeComposto) &&
                Recuperatore.getUnita(nomeUnita) != null &&
                Recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
                Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita) != null &&
                !Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).contieneCategoriaSensore(nomeCategoria);
    }

    public static boolean checkValiditaAttuatore(String nomeComposto, String nomeCategoria, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
                checkUnivocitaAttuatore(nomeComposto) &&
                Recuperatore.getUnita(nomeUnita) != null &&
                Recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
                Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita) != null &&
                !Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).contieneCategoriaAttuatore(nomeCategoria);
    }
}
