package domotix.view.menus.menuUnita.gestioneStanza.gestioneArtefatto;


import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.InputDati;
import domotix.view.MyMenu;
import domotix.controller.util.StringUtil;
import static domotix.view.ViewConstants.*;

/** @author Edoardo Coppola*/
public class MenuGestioneArtefattoM {

    private static final String TITOLO = "Menu Gestione Artefatto Manutentore ";
    private static final String SOTTOTITOLO = "oggetto: ";
    private static final String[] VOCI = {"Visualizza Descrizione Artefatto", "Aggiungi sensore all'artefatto", "Rimuovi sensore dall'artefatto",
                                            "Aggiungi attuatore all'artefatto", "Rimuovi attuatore dall'artefatto"};

    private static final String TITOLO_AGGIUNTA_SENSORE_ARTEFATTO = "Inserisci un nuovo sensore o collega un sensore già esistente all'artefatto attuale: ";
    private static final String[] AZIONI_AGGIUNTA_SENSORE_ARTEFATTO = {"Inserisci un nuovo sensore all'artefatto", "Collega un sensore esistente all'artefatto"};
    private static final String TITOLO_AGGIUNTA_ATTUATORE_ARTEFATTO = "Inserisci un nuovo attuatore o collega un attuatore già esistente all'artefatto attuale: ";
    private static final String[] AZIONI_AGGIUNTA_ATTUATORE_ARTEFATTO = {"Inserisci un nuovo attuatore all'artefatto", "Collega un attuatore esistente all'artefatto"};

    private MyMenu menu;
    private Rappresentatore r;
    private Interpretatore i;

    public MenuGestioneArtefattoM(MyMenu menu, Rappresentatore r, Interpretatore i) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.r = r;
        this.i = i;
    }

    /**
     * Presenta all'utente manutentore un menu che offre la possibilità di visualizzare la descrizione di un artefatto all'interno della stanza scelta al
     * menu precedente e il cui nome è passato come parametro, di aggiungere/rimuovere un sensore da un artefatto oppure di aggiungere/rimuovere un attuatore
     * da un artefatto. Il menu consente anche di tornare indietro e chiudere il menu stesso.
     * Tale operazioni sono effettuabili solo dopo che l'utente manutentore ha scelto su quale artefatto operare. La scelta è pilota tramite un menu le cui voci
     * sono i nomi degli artefatti all'interno della stanza fissata precedentemente
     * L'aggiunta di sensori all'artefatto passa per la scelta di aggiungerne uno totalmente nuovo o di collegarne uno esistente, realizzando una condivisione
     * di sensori fra più artefatti
     * Vale lo stesso per l'aggiunta di attuatori all'artefatto
     * La rimozione di un sensore da un attuatore passa dalla scelta da parte dell'utente manutentore di quale sensore rimuovere
     * Vale lo stesso per la rimozione di attuatori
     * @param nomeUnitaSuCuiLavorare
     * @param nomeStanza
     */
    public void avvia(String nomeUnitaSuCuiLavorare, String nomeStanza) {
ripristinaMenuOriginale(menu, TITOLO, VOCI);

        String nomeDispositivoDaAggiungere, nomeDispositivoDaRimuovere, categoriaDispositivo;
        String nomeArtefatto = premenuArtefatto(nomeUnitaSuCuiLavorare, nomeStanza);

        if (nomeArtefatto == null)  return;

        menu.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza, nomeArtefatto));

        MyMenu azioni = new MyMenu(TITOLO_AGGIUNTA_SENSORE_ARTEFATTO, AZIONI_AGGIUNTA_SENSORE_ARTEFATTO); //tanto poi li cambio con setters
        int scelta = 0;
        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch (sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza descrizione artefatto
                    System.out.println(r.getDescrizioneArtefatto(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare));
                    break;
                case 2: //aggiungi sensore all'artefatto: inserimento nuovo sensore oppure collegamento di uno preesistente
                    azioni.setTitolo(TITOLO_AGGIUNTA_SENSORE_ARTEFATTO);
                    azioni.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));
                    azioni.setVoci(AZIONI_AGGIUNTA_SENSORE_ARTEFATTO);
                    scelta = azioni.scegli(INDIETRO);
                    switch (scelta) {
                        case 0:
                            break;
                        case 1: //nuovo sensore
                            categoriaDispositivo = premenuCategoriaSensore();
                            if (categoriaDispositivo != null) {
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_SENSORE_ARTEFATTO);
                                if (i.aggiungiSensore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_SENSORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_SENSORE);
                            }
                            break;
                        case 2: //collegamento sensore esistente all'artefatto
                            nomeDispositivoDaAggiungere = premenuSensoriCollegabili(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (i.collegaSensore(nomeDispositivoDaAggiungere, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_SENSORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_SENSORE);
                            }
                            break;
                    }
                    break;
                case 3: //rimuovi sensore all'artefatto
                    nomeDispositivoDaRimuovere = premenuSensori(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (i.rimuoviSensore(nomeDispositivoDaRimuovere, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_SENSORE);
                        else
                            System.out.println(ERRORE_RIMOZIONE_SENSORE);
                    }
                    break;
                case 4://aggiungi attuatore all'artefatto
                    azioni.setTitolo(TITOLO_AGGIUNTA_ATTUATORE_ARTEFATTO);
                    azioni.setSottotitolo(SOTTOTITOLO + StringUtil.componiPercorso(nomeUnitaSuCuiLavorare, nomeStanza));
                    azioni.setVoci(AZIONI_AGGIUNTA_ATTUATORE_ARTEFATTO);
                    scelta = azioni.scegli(INDIETRO);
                    switch (scelta) {
                        case 0:
                            break;
                        case 1: //nuovo attuatore
                            categoriaDispositivo = premenuCategoriaAttuatore();
                            if (categoriaDispositivo != null) {
                                nomeDispositivoDaAggiungere = InputDati.leggiStringaNonVuota(INSERIMENTO_NOME_ATTUATORE_ARTEFATTO);
                                if (i.aggiungiAttuatore(nomeDispositivoDaAggiungere, categoriaDispositivo, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_ATTUATORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                            }
                            break;
                        case 2: //collegamento attuatore esistente all'artefatto
                            nomeDispositivoDaAggiungere = premenuAttuatoriCollegabili(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare);
                            if (nomeDispositivoDaAggiungere != null) {
                                if (i.collegaAttuatore(nomeDispositivoDaAggiungere, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                                    System.out.println(SUCCESSO_INSERIMENTO_ATTUATORE);
                                else
                                    System.out.println(ERRORE_INSERIMENTO_ATTUATORE);
                            }
                            break;
                    }
                    break;
                case 5://rimuovi attuatore all'artefatto
                    nomeDispositivoDaRimuovere = premenuAttuatori(nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare);
                    if (nomeDispositivoDaRimuovere != null) {
                        if (i.rimuoviAttuatore(nomeDispositivoDaRimuovere, nomeArtefatto, nomeStanza, nomeUnitaSuCuiLavorare))
                            System.out.println(SUCCESSO_RIMOZIONE_ATTUATORE);
                        else
                            System.out.println(ERRORE_RIMOZIONE_ATTUATORE);
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

    private  String premenuCategoriaSensore() {
        String[] nomiCategorie = r.getNomiCategorieSensori();
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_SENSORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private  String premenuCategoriaAttuatore() {
        String[] nomiCategorie = r.getNomiCategorieAttuatori();
        MyMenu m = new MyMenu(ELENCO_CATEGORIE_ATTUATORI, nomiCategorie);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiCategorie[scelta - 1];
    }

    private  String premenuSensori(String artefatto, String stanza, String unita) {
        String[] nomiSensori = r.getNomiSensori(artefatto, stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_ARTEFATTTO, nomiSensori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null :nomiSensori[scelta - 1];
    }

    private  String premenuAttuatori(String artefatto, String stanza, String unita) {
        String[] nomiAttuatori = r.getNomiAttuatori(artefatto, stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_ARTEFATTO, nomiAttuatori);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatori[scelta - 1];
    }

    private  String premenuSensoriCollegabili(String artefatto, String stanza, String unita) {
        String[] nomiSensoriCollegabili = r.getNomiSensoriAggiungibiliArtefatto(artefatto, stanza, unita);
        MyMenu m = new MyMenu(ELENCO_SENSORI_COLLEGABILI, nomiSensoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiSensoriCollegabili[scelta - 1];
    }

    private  String premenuAttuatoriCollegabili(String artefatto, String stanza, String unita) {
        String[] nomiAttuatoriCollegabili = r.getNomiAttuatoriAggiungibiliArtefatto(artefatto, stanza, unita);
        MyMenu m = new MyMenu(ELENCO_ATTUATORI_COLLEGABILI, nomiAttuatoriCollegabili);
        int scelta = m.scegli(INDIETRO);
        return scelta == 0 ? null : nomiAttuatoriCollegabili[scelta - 1];
    }
}
