package domotix.model.bean.system;

import domotix.controller.util.StringUtil;
import domotix.model.ElencoAttuatori;
import domotix.model.ElencoSensori;
import domotix.model.bean.device.*;
import domotix.model.util.ElencoDispositivi;
import domotix.model.util.ObserverList;

import java.util.Arrays;

public abstract class Sistema implements Osservabile, Azionabile {
    private static final String NO_SENSORI = "Non e' presente alcun sensore";
    private static final String NO_ATTUATORI = "Non e' presente alcun attuatore";
    private String nome;
    private ElencoDispositivi sensori;
    private ElencoDispositivi attuatori;

    public Sistema(String nome) {
        this(nome, new ElencoDispositivi(), new ElencoDispositivi());
    }

    public Sistema(String nome, ElencoDispositivi sensoriIniziali, ElencoDispositivi attuatoriIniziali) {
        this.nome = nome;
        this.attuatori = attuatoriIniziali;
        this.sensori = sensoriIniziali;
        this.addOsservatoreListaAttuatori(ElencoAttuatori.getInstance());
        this.addOsservatoreListaSensori(ElencoSensori.getInstance());
    }

    /**
     * Recupera il nome del Sistema.
     * @return Il nome del Sistema
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del Sistema.
     * @param nome Nuovo nome del Sistema
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Per svuotare completamente il sistema, eliminando i vari sensori contenuti e i collegamenti agli osservatori.
     */
    public void distruggi() {
        for (Dispositivo d : sensori.getDispositivi())
            sensori.remove(d);
        for(Dispositivo d : attuatori.getDispositivi())
            attuatori.remove(d);
        sensori.svuotaOsservatori();
        attuatori.svuotaOsservatori();
    }

    /**
     * Metodo che, dato un altro Sistema, ne eredita tutti gli Osservatori, siano
     * essi Osservatori dei Sensori o degli Attuatori.
     * @param sis Il Sistema da cui ereditare gli Osservatori
     */
    public void ereditaOsservatoriLista(Sistema sis) {
        sis.sensori.getOsservatori().forEach(dispositivoOsservatoreLista -> this.sensori.aggiungiOsservatore(dispositivoOsservatoreLista));
        sis.attuatori.getOsservatori().forEach(dispositivoOsservatoreLista -> this.attuatori.aggiungiOsservatore(dispositivoOsservatoreLista));
    }

    /**
     * Metodo di aggiunta di un osservatore (di tipo ObserverList)
     * che dovrà osservare i Sensori del Sistema.
     * @param oss Osservatore da aggiungere
     */
    public void addOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        sensori.aggiungiOsservatore(oss);
    }

    /**
     * Metodo di rimozione di un osservatore (di tipo ObserverList)
     * che sta osservando i Sensori del Sistema.
     * @param oss Osservatore da rimuovere
     */
    public void removeOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        sensori.rimuoviOsservatore(oss);
    }

    /**
     * Metodo di aggiunta di un osservatore (di tipo ObserverList)
     * che dovrà osservare gli Attuatori del Sistema.
     * @param oss Osservatore da aggiungere
     */
    public void addOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        attuatori.aggiungiOsservatore(oss);
    }

    /**
     * Metodo di rimozione di un osservatore (di tipo ObserverList)
     * che sta osservando gli Attuatori del Sistema.
     * @param oss Osservatore da rimuovere
     */
    public void removeOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        attuatori.rimuoviOsservatore(oss);
    }

    @Override
    public boolean addSensore(Sensore sensore) {
        return sensori.add(sensore, sensore.getCategoria().getNome());
    }

    @Override
    public void removeSensore(Sensore sensore) {
        sensori.remove(sensore.getCategoria().getNome());
    }

    @Override
    public void removeSensore(String categoriaSensore) {
        sensori.remove(categoriaSensore);
    }

    @Override
    public Sensore[] getSensori() {
        Dispositivo[] arraySensori = sensori.getDispositivi();
        return Arrays.copyOf(arraySensori, arraySensori.length, Sensore[].class);
    }

    @Override
    public boolean addAttuatore(Attuatore attuatore) {
        return attuatori.add(attuatore, attuatore.getCategoria().getNome());
    }

    @Override
    public void removeAttuatore(Attuatore attuatore) {
        attuatori.remove(attuatore.getCategoria().getNome());
    }

    @Override
    public void removeAttuatore(String categoriaAttuatore) {
        attuatori.remove(categoriaAttuatore);
    }

    @Override
    public Attuatore[] getAttuatori() {
        Dispositivo[] arrayAttuatori = attuatori.getDispositivi();
        return Arrays.copyOf(arrayAttuatori, arrayAttuatori.length, Attuatore[].class);
    }

    private String getStringaDispositivi(Dispositivo[] dispositivi) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < dispositivi.length; i++) {
            buffer.append(dispositivi[i].toString() + (i < dispositivi.length - 1 ? "\n" : ""));
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome() + ":" + "\n");
        buffer.append("\tSENSORI:");
        if (getSensori().length > 0) {
            String stringaSensori = getStringaDispositivi(getSensori());
            buffer.append(StringUtil.indent("\n" + stringaSensori, 2) + "\n");
        } else {
            buffer.append(StringUtil.indent("\n" + NO_SENSORI, 2) + "\n");
        }
        buffer.append("\tATTUATORI:");
        if (getAttuatori().length > 0) {
            String stringaAttuatori = "\n" + getStringaDispositivi(getAttuatori());
            buffer.append(StringUtil.indent(stringaAttuatori, 2));
        } else {
            buffer.append(StringUtil.indent("\n" + NO_ATTUATORI, 2));
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Sistema)) return false;
        Sistema other = (Sistema) obj;
        return this.nome.equals(((Sistema) obj).nome);
    }

    /**
     * Metodo che dice se il Sistema contiene gia' la categoria di sensore specificata.
     * @param categoriaSensore Nome della categoria di sensore
     * @return True se la categoria di sensore e' gia' presente
     */
    public boolean contieneCategoriaSensore(String categoriaSensore) {
        for (Dispositivo d : sensori.getDispositivi()) {
            Sensore s = (Sensore) d;
            if (categoriaSensore.equals(s.getCategoria().getNome())) return true;
        }
        return false;
    }

    /**
     * Metodo che dice se il Sistema contiene gia' la categoria di attuatore specificata.
     * @param categoriaAttuatore Nome della categoria di attuatore
     * @return True se la categoria di attuatore e' gia' presente
     */
    public boolean contieneCategoriaAttuatore(String categoriaAttuatore) {
        for (Dispositivo d : attuatori.getDispositivi()) {
            Attuatore a = (Attuatore) d;
            if (categoriaAttuatore.equals(a.getCategoria().getNome())) return true;
        }
        return false;
    }
}
