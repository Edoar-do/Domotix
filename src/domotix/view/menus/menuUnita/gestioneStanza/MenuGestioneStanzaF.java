package domotix.view.menus.menuUnita.gestioneStanza;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoF;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoM;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaF {

    private static final String TITOLO = "Menu Gestione Stanza Fruitore ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Menu Gestione Artefatto Fruitore" };
    private static final String INDIETRO = "Indietro";


    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare) {
        String nomeStanza = premenuStanze(nomeUnitaSuCuiLavorare);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione stanza
                    System.out.println(Recuperatore.getDescrizioneStanza(nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
                case 2://menu gestione artefatto fruitore
                    MenuGestioneArtefattoF.avvia(nomeUnitaSuCuiLavorare, nomeStanza);
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private static String premenuStanze(String unita){
        String[] nomiStanze = Recuperatore.getNomiStanze(unita);
        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return nomiStanze[scelta-1];
    }
}
