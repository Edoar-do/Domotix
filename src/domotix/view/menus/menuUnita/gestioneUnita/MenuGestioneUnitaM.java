package domotix.view.menus.menuUnita.gestioneUnita;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;

import domotix.model.bean.system.Stanza;

/** @author Edoardo Coppola*/
public class MenuGestioneUnitaM {
    private static final String TITOLO = "Menu Unita Manutentore ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita", "Aggiungi Stanza all'unita", "Rimuovi Stanza dall'unita"};
    private static final boolean INDIETRO = true;

    private static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";
    private static final String INVALID_NAME = "Il nome inserito non è valido. Per maggiori informazioni consultare la guida in linea";
    private static final String INSERIMENTO_NOME_STANZA = "Inserisci il nome della stanza da aggiungere all'unità immobiliare: ";
    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(){

        String nomeUnitaSuCuiLavorare = premenuUnita();
        String nomeDaAggiungere, nomeDaRimuovere;
        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione unita
                    System.out.println(Recuperatore.getDescrizioneUnita(nomeUnitaSuCuiLavorare));
                    break;
                case 2: //aggiungi stanza all'unità
                    nomeDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_STANZA);
                    while(true){
                        if(Verificatore.checkValiditaStanza(nomeDaAggiungere, nomeUnitaSuCuiLavorare)){
                            Modificatore.aggiungiStanza(new Stanza(nomeDaAggiungere), nomeUnitaSuCuiLavorare);
                            break;
                        }else{
                            System.out.println(INVALID_NAME);
                        }
                    }
                    break;
                case 3: //rimuovi stanza all'unità
                    nomeDaRimuovere = premenuStanze(nomeUnitaSuCuiLavorare);
                    Modificatore.rimuoviStanza(nomeDaRimuovere, nomeUnitaSuCuiLavorare);
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

    private static String premenuStanze(String unita){
        String[] nomiStanze = Recuperatore.getNomiStanze(unita);
        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return nomiStanze[scelta-1];
    }
}
