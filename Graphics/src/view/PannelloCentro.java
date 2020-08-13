package view;


import javax.swing.*;

public class PannelloCentro extends JPanel {

    private JTextArea areaVisualizzazione;
    private JScrollPane scrollPane;

    /**
     * costruttore del pannello centrale che contiene un text area 'scrollable' per la visualizzazione
     */
    public PannelloCentro() {
        super();
        areaVisualizzazione = new JTextArea(25, 65);
        areaVisualizzazione.setEditable(false);
        scrollPane = new JScrollPane(areaVisualizzazione);
        this.add(scrollPane);
    }

    /**
     * Metodo che restituisce l'area di visualizzazione del pannello centrale che la contiene
     * @return JTextarea di visualizzazione
     */
    public JTextArea getAreaVisualizzazione(){ return this.areaVisualizzazione; }
}
