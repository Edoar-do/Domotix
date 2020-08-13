package domotix.view.menus;

import domotix.controller.Modificatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Recuperatore;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante il menu per le azioni programmate in conflitto.
 * Una azione programmata viene considerata in conflitto se, al momento dell'avvio del programma,
 * vi sono azioni salvate tra i dati. In questo caso e' demandato all'utente come processare tali azioni.
 * 
 * @author paolopasqua
 */
public class MenuAzioniConflitto {

    private static final String TITOLO_ELIMINA = "Seleziona quali azioni ignorare ed eliminare";
    private static final String TITOLO_ESEGUI = "Seleziona quali azioni eseguire immediatamente";

    private static final String AVVISA_PROSEGUIMENTO_DOPO_ESEGUI = "Tutte le azioni rimanenti saranno riprogrammate per l'esecuzione";

    private Rappresentatore rappresentatore = null;
    private Recuperatore recuperatore = null;
    private Modificatore modificatore = null;

    /**
     * Costruttore della classe.
     * 
     * @param rappresentatore  istanza del controller Recuperatore
     * @param modificatore  istanza del controller Modificatore
     */
    public MenuAzioniConflitto(Rappresentatore rappresentatore, Modificatore modificatore, Recuperatore recuperatore) {
        this.rappresentatore = rappresentatore;
        this.modificatore = modificatore;
        this.recuperatore = recuperatore;
    }

    /**
     * Avvia l'interazione con l'utente per risolvere i conflitti con le azioni programmate in conflitto
     */
    public void avvia(){
        List<String> idAzioni = this.recuperatore.getIdAzioniProgrammate();
        List<String> descrizioniAzione = new ArrayList<>();

        if (!idAzioni.isEmpty()) { //se ci sono azioni programmate
            //recupero le descrizioni delle azioni
            for (int i = 0; i < idAzioni.size(); i++) {
                descrizioniAzione.add(i, this.rappresentatore.getDescrizioneAzioneProgrammata(idAzioni.get(i)));
            }

            premenuElimina(idAzioni, descrizioniAzione); //FATTO

            if (idAzioni.size() > 0)
                premenuEsegui(idAzioni, descrizioniAzione);//FATTO

            if (idAzioni.size() > 0)
                JOptionPane.showOptionDialog(null, AVVISA_PROSEGUIMENTO_DOPO_ESEGUI, "Riprogrammazione Azioni rimaste", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Ok"}, null);

        }
    }

    private void premenuElimina(List<String> idAzioni, List<String> descrizioniAzioni) {

        DefaultListModel azioniModel = new DefaultListModel();
        for (String d : descrizioniAzioni) { azioniModel.addElement(d); }
        JList<String> jListDescrizioniAzioni = new JList<>(azioniModel);
        JScrollPane scrollPane = new JScrollPane(jListDescrizioniAzioni);

        Object[] widgets = new Object[]{
                "Lista delle descrizioni delle azioni programmate",
                scrollPane,
                (TITOLO_ELIMINA + ". Poi premi il bottone 'Elimina' ")
        };

        JOptionPane.showOptionDialog(null, widgets, "Eliminazione Azioni Programmate", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Elimina"}, null);

        int[] indiciSelezionati = jListDescrizioniAzioni.getSelectedIndices();

        for (int i = indiciSelezionati.length-1; i >= 0; i--){
            String id = idAzioni.get(indiciSelezionati[i]);
            this.modificatore.rimuoviAzioneProgrammata(id, false);
            idAzioni.remove(indiciSelezionati[i]);
            azioniModel.remove(indiciSelezionati[i]);
        }
    }

    private void premenuEsegui(List<String> idAzioni, List<String> descrizioniAzioni) {

        DefaultListModel azioniModel = new DefaultListModel();
        for (String d : descrizioniAzioni) { azioniModel.addElement(d); }
        JList<String> jListDescrizioniAzioni = new JList<>(azioniModel);
        JScrollPane scrollPane = new JScrollPane(jListDescrizioniAzioni);

        Object[] widgets = new Object[]{
                "Lista delle descrizioni delle azioni programmate",
                scrollPane,
                (TITOLO_ESEGUI + ". Poi premi il bottone 'Esegui' ")
        };

        JOptionPane.showOptionDialog(null, widgets, "Esecuzione Azioni Programmate", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Esegui"}, null);

        int[] indiciSelezionati = jListDescrizioniAzioni.getSelectedIndices();

        for (int i = indiciSelezionati.length-1; i >= 0; i--){
            String id = idAzioni.get(indiciSelezionati[i]);
            this.modificatore.rimuoviAzioneProgrammata(id, true);
            idAzioni.remove(indiciSelezionati[i]);
            azioniModel.remove(indiciSelezionati[i]);
        }
    }
}
