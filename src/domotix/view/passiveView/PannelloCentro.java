package domotix.view.passiveView;


import domotix.view.Presenter;

import javax.swing.*;
import java.awt.*;

public class PannelloCentro extends JPanel implements Presenter {
    
    private static final String DESCRIZIONE_INIZIALE = "Benvenuto su Domotix, il software per la gestione del tuo impianto domotico. \n \n" +
        "Se accedi come Manutentore, selezionando il bottone in alto, potrai occuparti della creazione di entità come unità immobiliari, stanze, artefatti, sensori e attuatori. \n \n" +
        "Se invece accedi come Fruitore potrai personalizzare l'unità immobiliare corrente con regole di tua fantasia, potrai cambiarne lo stato e operare sui dispositivi presenti. \n \n" +
        "Come prima cosa apri la descrizione di un'unità immobiliare, importane una già fatta o creane una nuova. Per qualsiasi dubbio consulta le voci dell'Help";

    private JTextArea areaVisualizzazione;
    private JScrollPane scrollPane;

    /**
     * costruttore del pannello centrale che contiene un text area 'scrollable' per la visualizzazione
     */
    public PannelloCentro() {
        super();
        this.setLayout(new BorderLayout());
        areaVisualizzazione = new JTextArea();
        areaVisualizzazione.setEditable(false);
        areaVisualizzazione.setFont(new Font("TimesRoman", Font.PLAIN, 19));
        areaVisualizzazione.setText(DESCRIZIONE_INIZIALE);
        scrollPane = new JScrollPane(areaVisualizzazione);
        this.add(scrollPane);
    }

    /**
     * Metodo che restituisce l'area di visualizzazione del pannello centrale che la contiene
     * @return JTextarea di visualizzazione
     */
    public JTextArea getAreaVisualizzazione(){ return this.areaVisualizzazione; }

    @Override
    public void show(String descrizione) {
        this.areaVisualizzazione.setText(descrizione);
    }
}
