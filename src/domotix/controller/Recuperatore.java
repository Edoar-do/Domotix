package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Sistema;
import domotix.model.bean.system.Stanza;

import java.util.stream.Stream;

/**@author andrea*/
public class Recuperatore {
    static UnitaImmobiliare getUnita(String nomeUnita) {
        UnitaImmobiliare unita = ElencoUnitaImmobiliari.getInstance().getUnita(nomeUnita);
        return unita;
    }

    static Stanza getStanza(String nomeStanza, String nomeUnita) {
        Stanza[] stanze = getUnita(nomeUnita).getStanze();
        Stanza stanza = null;
        for (Stanza s : stanze) {
            if (s.getNome().equals(nomeStanza)) {
                stanza = s;
                break;
            }
        }
        return stanza;
    }

    static Artefatto getArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Artefatto[] artefatti = getStanza(nomeStanza, nomeUnita).getArtefatti();
        Artefatto artefatto = null;
        for (Artefatto a : artefatti) {
            if (a.getNome().equals(nomeArtefatto)) {
                artefatto = a;
                break;
            }
        }
        return artefatto;
    }

    static Sensore getSensore(String nomeSensore) {
        return ElencoSensori.getInstance().getDispositivo(nomeSensore);
    }

    static Attuatore getAttuatore(String nomeAttuatore) {
        return ElencoAttuatori.getInstance().getDispositivo(nomeAttuatore);
    }

    static CategoriaSensore getCategoriaSensore(String nomeCategoria) {
        return ElencoCategorieSensori.getInstance().getCategoria(nomeCategoria);
    }

    static CategoriaAttuatore getCategoriaAttuatore(String nomeCategoria) {
        return ElencoCategorieAttuatori.getInstance().getCategoria(nomeCategoria);
    }

    public static String[] getNomiUnita() {
        return ElencoUnitaImmobiliari.getInstance().getUnita()
                .stream()
                .map(u -> u.getNome())
                .toArray(String[]::new);
    }

    public static String[] getDescrizioniUnita() {
        return ElencoUnitaImmobiliari.getInstance().getUnita()
                .stream()
                .map(u -> u.toString())
                .toArray(String[]::new);
    }

    public static String[] getNomiStanze(String nomeUnita) {
        UnitaImmobiliare unita = getUnita(nomeUnita);
        return Stream.of(unita.getStanze()).map(s -> s.getNome()).toArray(String[]::new);
    }

    private static String[] getNomiAttuatoriSistema(Sistema sistema) {
        return Stream.of(sistema.getAttuatori()).map(a -> a.getNome()).toArray(String[]::new);
    }

    private static String[] getNomiSensoriSistema(Sistema sistema) {
        return Stream.of(sistema.getSensori()).map(s -> s.getNome()).toArray(String[]::new);
    }

    public static String[] getNomiAttuatori(String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriSistema(getStanza(nomeStanza, nomeUnita));
    }

    public static String[] getNomiSensori(String nomeStanza, String nomeUnita) {
        return getNomiSensoriSistema(getStanza(nomeStanza, nomeUnita));
    }

    public static String[] getNomiAttuatori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    public static String[] getNomiSensori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiSensoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    public static String[] getNomiArtefatti(String nomeStanza, String nomeUnita) {
        Artefatto[] artefatti = getStanza(nomeStanza, nomeUnita).getArtefatti();
        return Stream.of(artefatti).map(a -> a.getNome()).toArray(String[]::new);
    }

    public static String[] getNomiCategorieSensori() {
        return ElencoCategorieSensori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.getNome())
                .toArray(String[]::new);
    }

    public static String[] getNomiCategorieAttuatori() {
        return ElencoCategorieAttuatori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.getNome())
                .toArray(String[]::new);
    }

    public static String[] getDescrizioniCategorieSensori() {
        return ElencoCategorieSensori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.toString())
                .toArray(String[]::new);
    }

    public static String[] getDescrizioniCategorieAttuatori() {
        return ElencoCategorieAttuatori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.toString())
                .toArray(String[]::new);
    }

    public static String getDescrizioneUnita(String nomeUnita) {
        return getUnita(nomeUnita).toString();
    }

    public static String getDescrizioneStanza(String nomeStanza, String nomeUnita) {
        return getStanza(nomeStanza, nomeUnita).toString();
    }

    public static String getDescrizioneArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).toString();
    }

    private static String[] getNomiSensoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        Sensore[] sensori = getUnita(nomeUnita).getSensori();
        return Stream.of(sensori)
                .filter(s -> !sistema.contieneCategoriaSensore(s.getCategoria().getNome()))
                .map(s -> s.getNome())
                .toArray(String[]::new);
    }

    private static String[] getNomiAttuatoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        Attuatore[] attuatori = getUnita(nomeUnita).getAttuatori();
        return Stream.of(attuatori)
                .filter(a -> !sistema.contieneCategoriaAttuatore(a.getCategoria().getNome()))
                .map(a -> a.getNome())
                .toArray(String[]::new);
    }

    public static String[] getNomiSensoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return getNomiSensoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
    }

    public static String[] getNomiSensoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiSensoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    public static String[] getNomiAttuatoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
    }

    public static String[] getNomiAttuatoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }
}
