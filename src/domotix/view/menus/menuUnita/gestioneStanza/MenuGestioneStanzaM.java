package domotix.view.menus.menuUnita.gestioneStanza;


import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;

import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;

import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoM;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaM {

    private static final String TITOLO = "Menu Gestione Stanza Manutentore ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Aggiungi sensore alla stanza", "Rimuovi sensore dalla stanza",
                                            "Aggiungi attuatore alla stanza", "Rimuovi attuatore dalla stanza", "Aggiungi artefatto alla stanza",
                                            "Rimuovi artefatto dalla stanza", "Menu Gestione Artefatto Manutentore" };
    private static final String INDIETRO = "Indietro";

    private static final String ERRORE_INSERIMENTO_SENSORE = "Inserimento del sensore fallito. Per maggiori informazioni consultare la guida in linea";
    private static final String ERRORE_INSERIMENTO_ATTUATORE = "Inserimento dell'attuatore fallito. Per maggiori informazioni consultare la guida in linea";
    private static final String ERRORE_INSERIMENTO_ARTEFATTO = "Inserimento dell'artefatto fallito. Per maggiori informazioni consultare la guida in linea";
    private static final String INSERIMENTO_NOME_SENSORE = "Inserisci il nome del sensore da aggiungere alla stanza: ";
    private static final String INSERIMENTO_NOME_ATTUATORE = "Inserisci il nome dell'attuatore da aggiungere alla stanza: ";
    private static final String INSERIMENTO_NOME_ARTEFATTO = "Inserisci il nome dell'artefatto da aggiungere alla stanza: ";
    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";
    private static final String ELENCO_CATEGORIE_SENSORI = "Elenco delle categorie di sensore esistenti: ";
    private static final String ELENCO_CATEGORIE_ATTUATORI = "Elenco delle categorie di attuatore esistenti: ";
    private static final String ELENCO_SENSORI_STANZA = "Elenco dei sensori della stanza: ";
    private static final String ELENCO_ATTUATORI_STANZA = "Elenco degli attuatori della stanza: ";
    private static final String ELENCO_ARTEFATTI_STANZA = "Elenco degli artefatti presenti nella stanza: ";


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare) {

        String nomeDispositivoDaAggiungere, nomeDispositivoDaRimuovere, categoriaDispositivo;
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
                case 2: //aggiungi sensore alla stanza
                    //TODO: presentazione in un premenu dei sensori già esistenti nell'unità e scelta di uno di questi per la condivisione con la stanza
                    //altrimenti creazione di uno nuovo come sotto con una voce inserisci nuovo che riporta a questo sotto
                    categoriaDispositivo = premenuCategoriaSensore();
                    while (true) {
                        nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_SENSORE); //nome di fantasia
                        if (Modificatore.aggiungiSensore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare)) {
                            break; //inserimento del sensore avvenuto con successo
                        } else {
                            System.out.println(ERRORE_INSERIMENTO_SENSORE);
                        }
                    }
                    break;
                case 3: //rimuovi sensore alla stanza
                    nomeDispositivoDaRimuovere = premenuSensori(nomeStanza, nomeUnitaSuCuiLavorare);
                    Modificatore.rimuoviSensore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare);
                    break;
                case 4://aggiungi attuatore alla stanza
                    //TODO: presentazione in un premenu dei sensori già esistenti nell'unità e scelta di uno di questi per la condivisione con la stanza
                    //altrimenti creazione di uno nuovo come sotto con una voce inserisci nuovo che riporta a questo sotto
                    categoriaDispositivo = premenuCategoriaAttuatore();
                    while (true) {
                        nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ATTUATORE); //nome di fantasia
                        if (Modificatore.aggiungiAttuatore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare)) {
                            break; //inserimento del sensore avvenuto con successo
                        } else {
                            System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                        }
                    }
                    break;
                case 5://rimuovi attuatore alla stanza
                    nomeDispositivoDaRimuovere = premenuAttuatori(nomeStanza, nomeUnitaSuCuiLavorare);
                    Modificatore.rimuoviSensore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare);
                    break;
                case 6://aggiungi artefatto alla stanza
                    while(true) {
                        nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ARTEFATTO);
                        if(Modificatore.aggiungiArtefatto(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare)){
                            break;
                        }else{
                            System.out.println(ERRORE_INSERIMENTO_ARTEFATTO);
                        }
                    }
                    break;
                case 7://rimuovi artefatto alla stanza
                    nomeDispositivoDaRimuovere = premenuArtefatti(nomeStanza, nomeUnitaSuCuiLavorare);
                    break;
                case 8://menu gestione artefatto manutentore
                    MenuGestioneArtefattoM.avvia();
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

    private static String premenuCategoriaSensore(){
        String[] nomiCategorie = Recuperatore.getNomiCategorieSensori();
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_SENSORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return nomiCategorie[scelta-1];
    }

    private static String premenuCategoriaAttuatore(){
        String[] nomiCategorie = Recuperatore.getNomiCategorieAttuatori();
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_ATTUATORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return nomiCategorie[scelta-1];
    }

    private static String premenuSensori(String stanza, String unita){
        String[] nomiSensori = Recuperatore.getNomiSensori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_STANZA, nomiSensori);
        int scelta = m.scegli(INDIETRO);
        return nomiSensori[scelta-1];
    }

    private static String premenuAttuatori(String stanza, String unita){
        String[] nomiAttuatori = Recuperatore.getNomiAttuatori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_STANZA, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return nomiAttuatori[scelta-1];
    }

    private static String premenuArtefatti(String stanza, String unita){
        String[] nomiArtefatti = Recuperatore.getNomiArtefatti(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ARTEFATTI_STANZA, nomiArtefatti);
        int scelta = m.scegli(INDIETRO);
        return nomiArtefatti[scelta-1];
    }

}
