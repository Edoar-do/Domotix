package domotix.view.menus.menuUnita.gestioneUnita;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;

import domotix.controller.util.StringUtil;
import static domotix.view.ViewUtil.*;

/** @author Edoardo Coppola*/
public class MenuGestioneUnitaM {
    private static final String TITOLO = "Menu Gestione Unità Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Unita", "Aggiungi Stanza all'unita", "Rimuovi Stanza dall'unita"};

    private MyMenu menu;
    private Rappresentatore r;
    private Interpretatore i;

    public MenuGestioneUnitaM(MyMenu m, Rappresentatore r, Interpretatore i){
        this.menu = m;
        menu.setTitolo(TITOLO);
        menu.setVoci(VOCI);
        this.r = r;
        this.i = i;
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di aggiungere una stanza all'unità scelta (passata come parametro)
     * rimuoverne una dall'unità scelta oppure di visualizzarne una descrizione. Consente anche di tornare indietro e chiudere questo menu
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità immobiliare scelta nel menu precedente e su cui operare
     */
    public void avvia(String nomeUnitaSuCuiLavorare){
    ripristinaMenuOriginale(menu, TITOLO, VOCI);

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare));

        String nomeStanzaDaAggiungere, nomeStanzaDaRimuovere;

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione unita
                    System.out.println(r.getDescrizioneUnita(nomeUnitaSuCuiLavorare));
                    break;
                case 2: //aggiungi stanza all'unità
                    nomeStanzaDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_STANZA);
                    if(i.aggiungiStanza(nomeStanzaDaAggiungere, nomeUnitaSuCuiLavorare)){
                        System.out.println(SUCCESSO_INSERIMENTO_STANZA); //stanza aggiunta con successo
                    }else{
                        System.out.println(INSERIMENTO_STANZA_FALLITO);
                    }
                    break;
                case 3: //rimuovi stanza all'unità
                    nomeStanzaDaRimuovere = premenuStanze(nomeUnitaSuCuiLavorare);
                    if (nomeStanzaDaRimuovere != null) {
                        if (i.rimuoviStanza(nomeStanzaDaRimuovere, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_STANZA);
                        else
                            System.out.println(ERRORE_RIMOZIONE_STANZA);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }

    private String premenuStanze(String unita){
        String[] nomiStanze = r.getNomiStanze(unita, false);
        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta-1];
    }
}
