package domotix.view.menus.menuCategorie.sensori;


import domotix.controller.Recuperatore;
import domotix.view.MyMenu;

/** @author Edoardo Coppola*/
public class MenuCategorieSensoriF {
    private static final String TITOLO = "Menu Categorie Sensori Fruitore ";
    private static final String[] VOCI = { "Visualizza Categorie Sensori"};
    private static final String INDIETRO = "Indietro";


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza categorie sensori
                    for (String descrizione: Recuperatore.getDescrizioniCategorieSensori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
