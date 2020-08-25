package domotix.view.passiveView;


import domotix.view.Presenter;

import javax.swing.*;
import java.awt.*;

public class PannelloCentro extends JPanel implements Presenter {

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
