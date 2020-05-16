package domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import static domotix.view.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuGestioneArtefattoF {

    private static final String TITOLO = "Menu Gestione Artefatto Fruitore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Artefatto", "Imposta la modalità operativa di un attuatore associato all'artefatto"};
    private static final String NONE = "Non ci sono modalità operative impostabili all'attuatore all'infuori di quella corrente";
    
    private MyMenu menu;
    private Rappresentatore r;
    private Interpretatore i;

    public MenuGestioneArtefattoF(MyMenu menu, Rappresentatore r, Interpretatore i) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.r = r;
        this.i = i;
    }

    /**
     * Presenta all'utente fruitore un menu che offre la possibilità di scegliere di visualizzare la descrizione di un artefatto, di impostare la modalità operativa
     * di un attuatore associato ad un artefatto oppure di tornare indietro e chiudere questo menu.
     * Tutte le operazioni sono effettuabili solo dopo che si è scelto su quale artefatto lavorare all'interno della stanza scelta
     * al menu precedente e il cui nome è passato come parametro. La scelta dell'artefatto avviene attraverso un menu le cui voci sono i nomi degli artefatti
     * presenti nella stanza.
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità entro cui si trova la stanza scelta al menu precedente
     * @param nomeStanza è il nome della stanza all'interno della quale si trova l'artefatto su cui lavoriamo
     */
    public void avvia(String nomeUnitaSuCuiLavorare, String nomeStanza) {

        String nomeArtefatto = premenuArtefatto(nomeUnitaSuCuiLavorare, nomeStanza);

        if (nomeArtefatto == null) return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza, nomeArtefatto));

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione artefatto
                    System.out.println(r.getDescrizioneArtefatto(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
                case 2: //imposta modalità operativa
                    String nomeAttuatore = premenuAttuatori(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare);
                    if(nomeAttuatore != null){
                        String mode = premenuModalitaOperative(nomeAttuatore);
                        if(mode == null) break; //scelto di tornare indietro
                        if(mode.equals((NONE))){ //non ci sono modalità impostabili all'infuori di quella corrente
                            System.out.println(NONE);
                            break;
                        }
                        if(i.setModalitaOperativa(nomeAttuatore, mode))
                            System.out.println(String.format(SUCCESSO_SETTAGGIO_MODALITA, mode));
                        else
                            System.out.println(String.format(FALLIMENTO_SETTAGGIO_MODALITA, mode));
                    }
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private  String premenuArtefatto(String unita, String stanza) {
        String[] nomiArtefatti = r.getNomiArtefatti(stanza, unita);

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiArtefatti.length == 1)
            return nomiArtefatti[0];

        MyMenu m = new MyMenu(ELENCO_ARTEFATTI, nomiArtefatti);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiArtefatti[scelta - 1];
    }

    private  String premenuAttuatori(String artefatto, String stanza, String unita) {
        String[] nomiAttuatori = r.getNomiAttuatori(artefatto, stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_ARTEFATTO, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatori[scelta - 1];
    }

    private  String premenuModalitaOperative(String attuatore){
        String[] modes = r.getModalitaOperativeImpostabili(attuatore); //ritorna le modalità operative non correnti
        if(modes.length == 0) //se non ce n'è sono allora l'attuatore ha una sola modalità operativa e non la si cambia
            return NONE;
        if(modes.length == 1) //se ce n'è solo una impostabile allora la scelta è automatica
            return modes[0];
        MyMenu m = new MyMenu(MODALITA_OPERATIVE, modes);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : modes[scelta-1];
    }
}
