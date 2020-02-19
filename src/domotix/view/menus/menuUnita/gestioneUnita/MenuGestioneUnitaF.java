package domotix.view.menus.menuUnita.gestioneUnita;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;
import domotix.model.bean.system.Stanza;

/** @author Edoardo Coppola*/
public class MenuGestioneUnitaF {
    private static final String TITOLO = "Menu Unita Fruitore ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita"};
    private static final boolean INDIETRO = true;

    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        String nomeUnitaSuCuiLavorare = premenuUnita();

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione unita
                    System.out.println(Recuperatore.getDescrizioneUnita(nomeUnitaSuCuiLavorare));
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuUnita(){
        String[] nomiUnitaImmobiliari = Recuperatore.getNomiUnita();
        MyMenu m = new MyMenu(UNITA_IMMOBILIARI_ESISTENTI, nomiUnitaImmobiliari);
        int scelta = m.scegli(INDIETRO);
        return nomiUnitaImmobiliari[scelta-1];
    }
}
