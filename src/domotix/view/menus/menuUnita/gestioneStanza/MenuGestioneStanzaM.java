package domotix.view.menus.menuUnita.gestioneStanza;


import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;

import domotix.view.InputDati;
import domotix.view.MyMenu;

import domotix.controller.util.StringUtil;
import domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto.MenuGestioneArtefattoM;

/** @author Edoardo Coppola*/
public class MenuGestioneStanzaM {

    private static final String TITOLO = "Menu Gestione Stanza Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Stanza", "Aggiungi sensore alla stanza", "Rimuovi sensore dalla stanza",
            "Aggiungi attuatore alla stanza", "Rimuovi attuatore dalla stanza", "Aggiungi artefatto alla stanza",
            "Rimuovi artefatto dalla stanza", "Menu Gestione Artefatto Manutentore"};
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

    private static final String SUCCESSO_INSERIMENTO_SENSORE = "Sensore aggiunto con successo";
    private static final String SUCCESSO_INSERIMENTO_ATTUATORE = "Attuatore inserito con successo";
    private static final String SUCCESSO_RIMOZIONE_ATTUATORE = "Attuatore rimosso con successo";
    private static final String SUCCESSO_RIMOZIONE_ARTEFATTO = "Artefatto rimosso con successo";
    private static final String SUCCESSO_RIMOZIONE_SENSORE = "Sensore rimosso con successo";
    private static final String ERRORE_RIMOZIONE_SENSORE = "Rimozione sensore fallita. Consultare la guida in linea per maggiori informazioni";
    private static final String ERRORE_RIMOZIONE_ATTUATORE = "Rimozione dell'attuatore fallita. Consultare la guida in linea per maggiori informazioni";
    private static final String SUCCESSO_INSERIMENTO_ARTEFATTO = "Artefatto inserito con successo";
    private static final String ERRORE_RIMOZIONE_ARTEFATTO = "Rimozione dell'artefatto fallita. Consultare la guida in linea per maggiori informazioni";
    private static final String TITOLO_AGGIUNTA_SENSORE_STANZA = "Inserisci un nuovo sensore o collega un sensore già esistente alla stanza attuale: ";
    private static final String[] AZIONI_AGGIUNTA_SENSORE_STANZA = {"Inserisci un nuovo sensore nella stanza", "Collega un sensore esistente alla stanza"};
    private static final String TITOLO_AGGIUNTA_ATTUATORE_STANZA = "Inserisci un nuovo attuatore o collega un attuatore già esistente alla stanza attuale: ";
    private static final String[] AZIONI_AGGIUNTA_ATTUATORE_STANZA = {"Inserisci un nuovo attuatore nella stanza", "Collega un attuatore esistente alla stanza"};
    private static final String ELENCO_SENSORI_COLLEGABILI = "Elenco dei sensori collegabili: ";
    private static final String ELENCO_ATTUATORI_COLLEGABILI = "Elenco dei attuatori collegabili: ";

    private static final String ATTENZIONE_NO_CATEGORIA_SENSORE = "Attenzione! Creare prima una categoria sensore";
    private static final String ATTENZIONE_NO_CATEGORIA_ATTUATORE = "Attenzione! Creare prima una categoria attuatore";

    private static MyMenu menu = new MyMenu(TITOLO, VOCI);

    public static void avvia(String nomeUnitaSuCuiLavorare) {

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
                    System.out.println(Recuperatore.getDescrizioneStanza(nomeStanza, nomeUnitaSuCuiLavorare));
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
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_SENSORE);
                                if (Modificatore.aggiungiSensore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_SENSORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_SENSORE);
                            }
                            break;
                        case 2: //collegamento sensore esistente alla stanza
                            nomeDispositivoDaAggiungere = premenuSensoriCollegabili(nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (Modificatore.collegaSensore(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
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
                        if (Modificatore.rimuoviSensore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
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
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ATTUATORE);
                                if (Modificatore.aggiungiAttuatore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_ATTUATORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                            }
                            break;
                        case 2: //collegamento attuatore esistente alla stanza
                            nomeDispositivoDaAggiungere = premenuAttuatoriCollegabili(nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (Modificatore.collegaAttuatore(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
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
                        if (Modificatore.rimuoviAttuatore(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_ATTUATORE);
                        else
                            System.out.println(ERRORE_RIMOZIONE_ATTUATORE);
                    }
                    break;
                case 6://aggiungi artefatto alla stanza
                    nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ARTEFATTO);
                    if (Modificatore.aggiungiArtefatto(nomeDispositivoDaAggiungere, nomeStanza, nomeUnitaSuCuiLavorare))
                        System.out.println(SUCCESSO_INSERIMENTO_ARTEFATTO);
                    else
                        System.out.println(ERRORE_INSERIMENTO_ARTEFATTO);
                    break;
                case 7://rimuovi artefatto alla stanza
                    nomeDispositivoDaRimuovere = premenuArtefatti(nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (Modificatore.rimuoviArtefatto(nomeDispositivoDaRimuovere, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_ARTEFATTO);
                        else
                            System.out.println(ERRORE_RIMOZIONE_ARTEFATTO);
                    }
                    break;
                case 8://menu gestione artefatto manutentore
                    MenuGestioneArtefattoM.avvia(nomeUnitaSuCuiLavorare, nomeStanza);
                    break;
            }
        } while (sceltaMenu != 0);
    }

    private static String premenuStanze(String unita) {
        String[] nomiStanze = Recuperatore.getNomiStanze(unita);

        //se solo una scelta allora seleziono quella e procedo automaticamente
        if (nomiStanze.length == 1)
            return nomiStanze[0];

        MyMenu m = new MyMenu(ELENCO_STANZE, nomiStanze);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiStanze[scelta - 1];
    }

    private static String premenuCategoriaSensore() {
        String[] nomiCategorie = Recuperatore.getNomiCategorieSensori();
        String sottotitolo = nomiCategorie.length == 0 ? ATTENZIONE_NO_CATEGORIA_SENSORE : "";
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_SENSORI, sottotitolo, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private static String premenuCategoriaAttuatore() {
        String[] nomiCategorie = Recuperatore.getNomiCategorieAttuatori();
        String sottotitolo = nomiCategorie.length == 0 ? ATTENZIONE_NO_CATEGORIA_ATTUATORE : "";
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_ATTUATORI, sottotitolo, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private static String premenuSensori(String stanza, String unita) {
        String[] nomiSensori = Recuperatore.getNomiSensori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_STANZA, nomiSensori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiSensori[scelta - 1];
    }

    private static String premenuAttuatori(String stanza, String unita) {
        String[] nomiAttuatori = Recuperatore.getNomiAttuatori(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_STANZA, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatori[scelta - 1];
    }

    private static String premenuArtefatti(String stanza, String unita) {
        String[] nomiArtefatti = Recuperatore.getNomiArtefatti(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ARTEFATTI_STANZA, nomiArtefatti);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiArtefatti[scelta - 1];
    }

    private static String premenuSensoriCollegabili(String stanza, String unita) {
        String[] nomiSensoriCollegabili = Recuperatore.getNomiSensoriAggiungibiliStanza(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_COLLEGABILI, nomiSensoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiSensoriCollegabili[scelta - 1];
    }

    private static String premenuAttuatoriCollegabili(String stanza, String unita) {
        String[] nomiAttuatoriCollegabili = Recuperatore.getNomiAttuatoriAggiungibiliStanza(stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_COLLEGABILI, nomiAttuatoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatoriCollegabili[scelta - 1];
    }
}
