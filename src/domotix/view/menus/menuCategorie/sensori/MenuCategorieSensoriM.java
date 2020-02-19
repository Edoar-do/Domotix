package domotix.view.menus.menuCategorie.sensori;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;

import domotix.model.bean.device.CategoriaSensore;


/** @author Edoardo Coppola*/
public class MenuCategorieSensoriM {
    private static final String TITOLO = "Menu Categorie Sensori Manutentore ";
    private static final String[] VOCI = {"Aggiungi Categoria Sensore", "Rimuovi Categoria Sensore", "Visualizza Categorie Sensori"};
    private static final boolean INDIETRO = true;

    private static final String INSERIMENTO_CATEGORIA_SENSORE = "Inserisci un nome per la nuova categoria di sensore";
    private static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del sensore";
    private static final String INSERIMENTO_INFO_RILEVABILE = "Inserisci l'informazione rilevabile dal sensore";
    private static final String CATEGORIE_ESISTENTI_SENSORI = "Elenco delle categorie di sensori esistenti: ";
    private static final String INVALID_NAME = "Il nome della categoria inserita non è valido. Consultare la guida in linea per maggiori informazioni";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        int sceltaMenu = 0;
        String nome;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: // aggiungi categoria sensori
                    while(true){
                        nome = InputDati.leggiStringaNonVuota(INSERIMENTO_CATEGORIA_SENSORE);
                        if(Verificatore.checkValiditaCategoriaSensore(nome)){
                            Modificatore.aggiungiCategoriaSensore(new CategoriaSensore(nome,
                                    InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO), InputDati.leggiStringaNonVuota(INSERIMENTO_INFO_RILEVABILE)));
                            break;
                        }else{
                            System.out.println(INVALID_NAME);
                        }
                    }
                    break;
                case 2: //rimuovi categoria sensori
                    String nomeCategoriaDaRimuovere = premenu();
                    Modificatore.rimuoviCategoriaSensore(nomeCategoriaDaRimuovere);
                    break;
                case 3: //visualizza categorie sensori
                    for (String descrizione: Recuperatore.getDescrizioniCategorieSensori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenu(){
        String[] nomiCategorie = Recuperatore.getNomiCategorieSensori();
        MyMenu m = new MyMenu(CATEGORIE_ESISTENTI_SENSORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return nomiCategorie[scelta-1];
    }
}
