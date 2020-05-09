package domotix.view.menus.menuUnita;


import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.MenuGestioneStanzaM;
import domotix.view.menus.menuUnita.gestioneUnita.MenuGestioneUnitaM;
import static domotix.view.menus.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuUnitaM {

    private static final String TITOLO = "Menu Unita Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Menu Gestione Unita Manutentore", "Menu Gestione Stanza Manutentore"};

    private static final String NONE = "Nessuna unità immobiliare esistente. L'utente manutentore deve prima crearne una";

    private MyMenu menu;
    private Interpretatore i;
    private Rappresentatore r;
    private Verificatore v;
    private MenuGestioneUnitaM menuGestioneUnitaM;
    private MenuGestioneStanzaM menuGestioneStanzaM;

    public MenuUnitaM(Interpretatore i, Verificatore v, Rappresentatore r, MyMenu m){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.i = i;
        this.r = r;
        this.v = v;
        menuGestioneUnitaM = new MenuGestioneUnitaM(menu, r, i);
        menuGestioneStanzaM = new MenuGestioneStanzaM(menu, r, i, v);
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di aprire un menu per manutentori per la gestione dell'unità immobiliare
     * o di aprire un menu per manutentori per la gestione di una stanza all'interno dell'unità immobiliare. Entrambe le operazioni avvengono dopo che l'utente ha scelto
     * su quale unità immobiliare lavorare. Se esiste solo un'unità immobiliare allora la scelta viene effettuata automaticamente. Se non ne esistono allora si torna al
     * menu precedente perché bisogna crearne una.
     * Il menu consete anche di tornare indietro e chiudere questo menu
     */
    public void avvia(){

        String nomeUnitaSuCuiLavorare = premenuUnita();

        if(nomeUnitaSuCuiLavorare == null) return;

        if(nomeUnitaSuCuiLavorare.equals(NONE)){
            System.out.println(NONE);
            return;
        }

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // menu gestione unita
                    menuGestioneUnitaM.avvia(nomeUnitaSuCuiLavorare);
                    break;
                case 2: //menu gestione stanza
                    menuGestioneStanzaM.avvia(nomeUnitaSuCuiLavorare);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenuUnita(){
        String[] nomiUnitaImmobiliari = r.getNomiUnita();

        if(nomiUnitaImmobiliari.length == 0) //non esistono unità immobiliari
            return NONE;

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiUnitaImmobiliari.length == 1)
            return nomiUnitaImmobiliari[0];

        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnitaImmobiliari[scelta-1];
    }

}
