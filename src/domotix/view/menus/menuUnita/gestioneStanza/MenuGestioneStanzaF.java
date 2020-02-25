package domotix.view.menus.menuUnita.gestioneStanza;

import domotix.controller.Recuperatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoF;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaF {

    private static final String TITOLO = "Menu Gestione Stanza Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Menu Gestione Artefatto Fruitore" };
    private static final String INDIETRO = "Indietro";


    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare) {
        String nomeStanza = premenuStanze(nomeUnitaSuCuiLavorare);

        if (nomeStanza == null) return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));

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

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiStanze.length == 1)
            return nomiStanze[0];

        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta-1];
    }
}
