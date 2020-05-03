package domotix.view.menus.menuUnita.gestioneUnita;


import domotix.controller.Rappresentatore;

import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import static domotix.view.menus.ViewConstants.*;


/** @author Edoardo Coppola*/
public class MenuGestioneUnitaF {
    private static final String TITOLO = "Menu Gestione Unità Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita", "Cambia Stato Sensore", "Cambia Stato Attuatore"};

    private MyMenu menu;
    private Interpretatore i;
    private Rappresentatore r;

    public MenuGestioneUnitaF(MyMenu menu, Interpretatore i, Rappresentatore r) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.i = i;
        this.r = r;
    }

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di visualizzare una descrizione dell'unità immobiliare e cambiare lo stato di un sensore e  di un attuatore
     * Consente anche di tornare indietro e chiudere questo menu
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità immobiliare scelta nel menu precedente e su cui operare
     */
    public void avvia(String nomeUnitaSuCuiLavorare){
        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);
            String daCambiare;
            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione unita
                    System.out.println(r.getDescrizioneUnita(nomeUnitaSuCuiLavorare));
                    break;
                case 2: //cambia stato sensore
                    daCambiare = premenuSensoriUnita(nomeUnitaSuCuiLavorare);
                    if(daCambiare != null) {
                        if (i.cambiaStatoSensore(daCambiare, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_CAMBIO_STATO_S);
                        else System.out.println(ERRORE_CAMBIO_STATO_S);
                    }
                    break;
                case 3: //cambia stato attuatore
                    daCambiare = premenuAttuatoriUnita(nomeUnitaSuCuiLavorare);
                    if(daCambiare != null) {
                        if (i.cambiaStatoAttuatore(daCambiare, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_CAMBIO_STATO_A);
                        else System.out.println(ERRORE_CAMBIO_STATO_A);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenuSensoriUnita(String unita) {
        String[] sensoriUnita = r.getNomiSensori(unita);
        MyMenu m = new MyMenu(String.format(SENSORI_UNITA, unita), sensoriUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : sensoriUnita[scelta-1];
    }

    private String premenuAttuatoriUnita(String unita){
        String[] attuatoriUnita = r.getNomiAttuatori(unita);
        MyMenu m = new MyMenu(String.format(ATTUATORI_UNITA, unita), attuatoriUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : attuatoriUnita[scelta-1];
    }

}
