package domotix.view.menus.menuUnita.gestioneUnita;


import domotix.controller.Recuperatore;
import domotix.logicUtil.MyMenu;
import domotix.logicUtil.StringUtil;


/** @author Edoardo Coppola*/
public class MenuGestioneUnitaF {
    private static final String TITOLO = "Menu Gestione Unità Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita"};
    private static final String INDIETRO = "Indietro";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare){
        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

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
}
