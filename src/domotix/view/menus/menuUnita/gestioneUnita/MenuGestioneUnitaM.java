package domotix.view.menus.menuUnita.gestioneUnita;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.logicUtil.InputDati;
import domotix.logicUtil.MyMenu;

import domotix.logicUtil.StringUtil;
import domotix.model.bean.system.Stanza;

/** @author Edoardo Coppola*/
public class MenuGestioneUnitaM {
    private static final String TITOLO = "Menu Gestione Unità Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita", "Aggiungi Stanza all'unita", "Rimuovi Stanza dall'unita"};
    private static final String INDIETRO = "Indietro";


    private static final String INSERIMENTO_FALLITO = "L'inserimento della stanza è fallito. Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_INSERIMENTO_STANZA = "Inserimento stanza avvenuto con successo.";
    private static final String SUCCESSO_RIMOZIONE_STANZA = "Rimozione stanza avvenuta con successo";
    private static final String ERRORE_RIMOZIONE_STANZA = "Rimozione stanza fallita. Consultare la guida in linea per maggori informazioni";
    private static final String INSERIMENTO_NOME_STANZA = "Inserisci il nome della stanza da aggiungere all'unità immobiliare: ";
    private static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";


    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare){

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        String nomeStanzaDaAggiungere, nomeStanzaDaRimuovere;

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
                    nomeStanzaDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_STANZA);
                    if(Modificatore.aggiungiStanza(nomeStanzaDaAggiungere, nomeUnitaSuCuiLavorare)){
                        System.out.println(SUCCESSO_INSERIMENTO_STANZA); //stanza aggiunta con successo
                    }else{
                        System.out.println(INSERIMENTO_FALLITO);
                    }
                    break;
                case 3: //rimuovi stanza all'unità
                    nomeStanzaDaRimuovere = premenuStanze(nomeUnitaSuCuiLavorare);
                    if (nomeStanzaDaRimuovere != null) {
                        if (Modificatore.rimuoviStanza(nomeStanzaDaRimuovere, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_STANZA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_STANZA);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }



    private static String premenuStanze(String unita){
        String[] nomiStanze = Recuperatore.getNomiStanze(unita, false);
        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta-1];
    }
}
