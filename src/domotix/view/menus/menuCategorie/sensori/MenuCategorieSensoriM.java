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
    private static final String INDIETRO = "Indietro";

    private static final String INSERIMENTO_CATEGORIA_SENSORE = "Inserisci un nome per la nuova categoria di sensore";
    private static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del sensore";
    private static final String INSERIMENTO_INFO_RILEVABILE = "Inserisci l'informazione rilevabile dal sensore";
    private static final String CATEGORIE_ESISTENTI_SENSORI = "Elenco delle categorie di sensori esistenti: ";
    private static final String ERRORE_INSERIMENTO = "Inserimento della categoria fallito. Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_INSERIMENTO = "Inserimento della categoria avvenuto con successo";
    private static final String SUCCESSO_RIMOZIONE_CATEGORIA = "Rimozione categoria avvenuta con successo";
    private static final String ERRORE_RIMOZIONE_CATEGORIA = "Rimozione delle categoria fallita. Consultare la guida in linea per maggiori informazioni";

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
                    nome = InputDati.leggiStringaNonVuota(INSERIMENTO_CATEGORIA_SENSORE);
                    if(Modificatore.aggiungiCategoriaSensore(nome, InputDati.leggiStringaNonVuota(INSERIMENTO_TESTO_LIBERO),
                            InputDati.leggiStringaNonVuota(INSERIMENTO_INFO_RILEVABILE))){
                        System.out.println(SUCCESSO_INSERIMENTO);
                    }else { System.out.println(ERRORE_INSERIMENTO); }
                     break;
                case 2: //rimuovi categoria sensori
                    nome = premenu();
                    if(Modificatore.rimuoviCategoriaSensore(nome))
                        System.out.println(SUCCESSO_RIMOZIONE_CATEGORIA);
                    else
                        System.out.println(ERRORE_RIMOZIONE_CATEGORIA);
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
