package domotix.view.menus.menuPrincipale;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.view.menus.menuCategorie.MenuCategorieM;
import domotix.view.menus.menuUnita.MenuUnitaM;

/** @author Edoardo Coppola*/
public class MenuManutentore {
    private static final String TITOLO = "Menu Manutentore ";
    private static final String[] VOCI = {"Menu Categorie Dispositivi Manutentore", "Menu Unita Manutentore", "Aggiungi un'unità immobiliare", "Rimuovi un'unità immobiliare"};
    private static final String INDIETRO = "Indietro";

    private static final String SUCCESSO_INSERIMENTO_UNITA = "L'inserimento della nuova unità immobiliare è avvenuto con successo";
    private static final String ERRORE_INSERIMENTO_UNITA = "L'inserimento della nuova unità immobiliare è fallito. Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_RIMOZIONE_UNITA = "Rimozione dell'unità immobiliare scelta avvenuta con successo";
    private static final String ERRORE_RIMOZIONE_UNITA = "Rimozione dell'unità immobiliare scelta fallita. Consultare la guida in linea per maggiori informazioni";
    private static final String NOMI_UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliare esistenti: ";
    private static final String NOME_NUOVA_UNITA = "Inserisci il nome della nuova unità immobiliare da aggiungere: ";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di aprire un menu per menutentori per la gestione delle categorie di
     * sensori e attuatori, di aprire un menu per la gestione dell'unità immobiliare, di aggiungere o rimuovere un'unità immobiliare oppure di tornare indietro e chiudere questo menu
     */
    public static void avvia(){

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //Menu Categorie Dispositivi Manutentore
                    MenuCategorieM.avvia();
                    break;
                case 2: //Menu Unita Manutentore
                    MenuUnitaM.avvia();
                    break;
                case 3: //aggiungi unità
                    if(Modificatore.aggiungiUnitaImmmobiliare(InputDati.leggiStringaNonVuota(NOME_NUOVA_UNITA)))
                        System.out.println(SUCCESSO_INSERIMENTO_UNITA);
                    else
                        System.out.println(ERRORE_INSERIMENTO_UNITA);
                    break;
                case 4: //rimuovi unità
                    String nomeUnitaDaRimuovere = premenuUnita();
                    if(nomeUnitaDaRimuovere == null) //scelto di tornare indietro
                        break;
                    if(Modificatore.rimuoviUnitaImmobiliare(nomeUnitaDaRimuovere))
                        System.out.println(SUCCESSO_RIMOZIONE_UNITA);
                    else
                        System.out.println(ERRORE_RIMOZIONE_UNITA);
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private static String premenuUnita(){
        String[] nomiUnita = Recuperatore.getNomiUnita();
        MyMenu m = new MyMenu(NOMI_UNITA_IMMOBILIARI_ESISTENTI, nomiUnita);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiUnita[scelta-1];

    }
}
