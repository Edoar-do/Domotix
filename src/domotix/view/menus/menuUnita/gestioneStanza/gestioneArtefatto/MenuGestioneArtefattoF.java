package domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;

/** @author Edoardo Coppola*/
public class MenuGestioneArtefattoF {

    private static final String TITOLO = "Menu Gestione Artefatto Fruitore ";
    private static final String[] VOCI = {"Visualizza Descrizione Artefatto"};
    private static final String INDIETRO = "Indietro";

    private static final String ELENCO_ARTEFATTI = "Elenco delle stanze presenti nell'unità: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare, String nomeStanza) {

        String nomeArtefatto = premenuArtefatto(nomeUnitaSuCuiLavorare, nomeStanza);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione artefatto
                    System.out.println(Recuperatore.getDescrizioneArtefatto(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private static String premenuArtefatto(String unita, String stanza) {
        String[] nomiArtefatti = Recuperatore.getNomiArtefatti(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ARTEFATTI, nomiArtefatti);
        int scelta = m.scegli(INDIETRO);
        return nomiArtefatti[scelta - 1];
    }
}
