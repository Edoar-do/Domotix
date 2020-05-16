package domotix.view.menus.menuUnita.gestioneStanza;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoM;
import static domotix.view.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaM {

    private static final String TITOLO = "Menu Gestione Stanza Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Aggiungi sensore alla stanza", "Rimuovi sensore dalla stanza",
            "Aggiungi attuatore alla stanza", "Rimuovi attuatore dalla stanza", "Aggiungi artefatto alla stanza",
            "Rimuovi artefatto dalla stanza", "Menu Gestione Artefatto Manutentore"};

    private MyMenu menu;
    private Rappresentatore r;
    private Interpretatore i;
    private Verificatore v;
    private MenuGestioneArtefattoM menuGestioneArtefattoM;

    public MenuGestioneStanzaM(MyMenu menu, Rappresentatore r, Interpretatore i, Verificatore v) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.r = r;
        this.i = i;
        this.v = v;
        menuGestioneArtefattoM = new MenuGestioneArtefattoM(menu, r, i);
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di visualizzare la descrizione di una stanza all'interno dell'unità (specificata tramite paramentro),
     * aggiungere/rimuovere un sensore ad una stanza, aggiungere/rimuovere un attuatore ad una stanza, aggiungere/rimuovere un artefatto ad una stanza
     * oppure di tornare indietro e chiudere questo menu.
     * Tali operazioni vengono svolte dopo l'utente manutentore ha scelto su quale stanza, all'interno dell'unità, andare a lavorare. Tale scelta avviene
     * attraverso un menu le cui voci sono le stanze presenti
     * L'aggiunta di un sensore alla stanza scelta comporta prima la decisione di agguingerne uno completamente nuovo oppure collegarne uno già esistente
     * mettendo quindi tale sensore in condivisione fra più stanze
     * Vale lo stesso per l'aggiunta degli attuatori
     * La rimozione dei sensori comporta prima la scelta di quale sensore rimuovere dalla stanza
     * Vale lo stesso per gli attuatori
     * Lo stesso ragionamento si applica alla rimozione di un artefatto dalla stanza (va prima specificato quale)
     * L'aggiunta di un artefatto è invece normale e richiede solo le informazioni necessarie alla sua creazione
     * @param nomeUnitaSuCuiLavorare è il nome dell'unità immobiliare dal quale scegliere la stanza su cui lavorare
     */
    public void avvia(String nomeUnitaSuCuiLavorare) {

        String nomeDispositivoDaAggiungere, nomeDispositivoDaRimuovere, categoriaDispositivo;
        String nomeStanza = premenuStanze(nomeUnitaSuCuiLavorare);

        if (nomeStanza == null) return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));

        MyMenu azioni = new MyMenu(TITOLO_AGGIUNTA_SENSORE_STANZA, AZIONI_AGGIUNTA_SENSORE_STANZA); //tanto poi li cambio con setters
        int scelta = 0;

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione stanza
                    System.out.println(r.getDescrizioneStanza(nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
                case 2: //aggiungi sensore alla stanza: inserimento nuovo sensore oppure collegamento di uno preesistente
                    azioni.setTitolo(TITOLO_AGGIUNTA_SENSORE_STANZA);
                    azioni.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));
                    azioni.setVoci(AZIONI_AGGIUNTA_SENSORE_STANZA);
                    scelta = azioni.scegli(INDIETRO);
                    switch (scelta) {
                        case 0:
                            break;
                        case 1: //nuovo sensore
                            categoriaDispositivo = premenuCategoriaSensore();
                            if (categoriaDispositivo != null) {
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_SENSORE_STANZA);
                                if (i.aggiungiSensore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_SENSORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_SENSORE);
                            }
                            break;
                        case 2: //collegamento sensore esistente alla stanza
                            nomeDispositivoDaAggiungere = premenuSensoriCollegabili(nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (i.collegaSensore(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_SENSORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_SENSORE);
                            }
                            break;
                    }
                    break;
                case 3: //rimuovi sensore alla stanza
                    nomeDispositivoDaRimuovere = premenuSensori(nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (i.rimuoviSensore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_SENSORE);
                        else
                            System.out.println(ERRORE_RIMOZIONE_SENSORE);
                    }
                    break;
                case 4://aggiungi attuatore alla stanza
                    azioni.setTitolo(TITOLO_AGGIUNTA_ATTUATORE_STANZA);
                    azioni.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));
                    azioni.setVoci(AZIONI_AGGIUNTA_ATTUATORE_STANZA);
                    scelta = azioni.scegli(INDIETRO);
                    switch (scelta) {
                        case 0:
                            break;
                        case 1: //nuovo attuatore
                            categoriaDispositivo = premenuCategoriaAttuatore();
                            if (categoriaDispositivo != null) {
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ATTUATORE_STANZA);
                                if (i.aggiungiAttuatore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_ATTUATORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                            }
                            break;
                        case 2: //collegamento attuatore esistente alla stanza
                            nomeDispositivoDaAggiungere = premenuAttuatoriCollegabili(nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (i.collegaAttuatore(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_ATTUATORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                            }
                            break;
                    }
                    break;
                case 5://rimuovi attuatore alla stanza
                    nomeDispositivoDaRimuovere = premenuAttuatori(nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (i.rimuoviAttuatore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_ATTUATORE);
                        else
                            System.out.println(ERRORE_RIMOZIONE_ATTUATORE);
                    }
                    break;
                case 6://aggiungi artefatto alla stanza
                    nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ARTEFATTO_STANZA);
                    if (i.aggiungiArtefatto(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
                        System.out.println(SUCCESSO_INSERIMENTO_ARTEFATTO);
                    else
                        System.out.println(ERRORE_INSERIMENTO_ARTEFATTO);
                    break;
                case 7://rimuovi artefatto alla stanza
                    nomeDispositivoDaRimuovere = premenuArtefatti(nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (i.rimuoviArtefatto(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_ARTEFATTO);
                        else
                            System.out.println(ERRORE_RIMOZIONE_ARTEFATTO);
                    }
                    break;
                case 8://menu gestione artefatto manutentore
                    menuGestioneArtefattoM.avvia(nomeUnitaSuCuiLavorare, nomeStanza);
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private String premenuStanze(String unita) {
        String[] nomiStanze = r.getNomiStanze(unita);

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiStanze.length == 1)
            return nomiStanze[0];

        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta - 1];
    }

    private String premenuCategoriaSensore() {
        String[] nomiCategorie = r.getNomiCategorieSensori();
        String sottotitolo = nomiCategorie.length == 0 ? ATTENZIONE_NO_CATEGORIA_SENSORE : "";
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_SENSORI, sottotitolo, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private String premenuCategoriaAttuatore() {
        String[] nomiCategorie = r.getNomiCategorieAttuatori();
        String sottotitolo = nomiCategorie.length == 0 ? ATTENZIONE_NO_CATEGORIA_ATTUATORE : "";
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_ATTUATORI, sottotitolo, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private String premenuSensori(String stanza, String unita) {
        String[] nomiSensori = r.getNomiSensori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_STANZA, nomiSensori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiSensori[scelta - 1];
    }

    private  String premenuAttuatori(String stanza, String unita) {
        String[] nomiAttuatori = r.getNomiAttuatori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_STANZA, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatori[scelta - 1];
    }

    private  String premenuArtefatti(String stanza, String unita) {
        String[] nomiArtefatti = r.getNomiArtefatti(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ARTEFATTI_STANZA, nomiArtefatti);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiArtefatti[scelta - 1];
    }

    private  String premenuSensoriCollegabili(String stanza, String unita) {
        String[] nomiSensoriCollegabili = r.getNomiSensoriAggiungibiliStanza(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_COLLEGABILI, nomiSensoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiSensoriCollegabili[scelta - 1];
    }

    private  String premenuAttuatoriCollegabili(String stanza, String unita) {
        String[] nomiAttuatoriCollegabili = r.getNomiAttuatoriAggiungibiliStanza(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_COLLEGABILI, nomiAttuatoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatoriCollegabili[scelta - 1];
    }
}
